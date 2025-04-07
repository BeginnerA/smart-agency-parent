package tt.smart.agency.sql.builder.sql.dql;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import tt.smart.agency.sql.annotation.Queries;
import tt.smart.agency.sql.annotation.Query;
import tt.smart.agency.sql.builder.sql.LambdaFunction;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.builder.sql.Tuple2;
import tt.smart.agency.sql.config.GlobalConfig;
import tt.smart.agency.sql.constant.CommonConstant;
import tt.smart.agency.sql.constant.ConditionPriority;
import tt.smart.agency.sql.constant.QueryRule;
import tt.smart.agency.sql.domain.Alias;
import tt.smart.agency.sql.domain.Column;
import tt.smart.agency.sql.exception.SqlBuilderException;
import tt.smart.agency.sql.inner.ObjectMapper;
import tt.smart.agency.sql.tools.ConvertTools;
import tt.smart.agency.sql.tools.LambdaTools;
import tt.smart.agency.sql.tools.SqlNameTools;
import tt.smart.agency.sql.tools.SqlTemplateTools;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <p>
 * 条件构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Slf4j
@SuppressWarnings({"unchecked"})
public class ConditionSqlBuilder<T extends ConditionSqlBuilder<T>> implements SqlBuilderRoute {

    /**
     * 前缀
     */
    protected final String prefix;
    /**
     * 条件
     */
    protected final StringBuilder conditionBuilder;
    /**
     * WHERE
     */
    protected String sign = "WHERE";
    /**
     * 预编译参数
     */
    protected List<Object> precompileArgs = new ArrayList<>();
    /**
     * 条件优先级
     */
    protected int level = 2;

    protected ConditionSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, T b) {
        this.prefix = prefix;
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
        if (Boolean.TRUE.equals(predicate)) {
            if (b != null) {
                this.conditionBuilder = b.conditionBuilder;
                this.sign = b.sign;
                this.precompileArgs.addAll(b.precompileArgs);
                this.level = b.level;
            } else {
                this.conditionBuilder = new StringBuilder();
            }
        } else {
            this.conditionBuilder = new StringBuilder();
        }
    }

    protected ConditionSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs) {
        this.prefix = prefix;
        this.conditionBuilder = new StringBuilder();
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
    }

    protected ConditionSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, String condition, Object... values) {
        this.prefix = prefix;
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
        if (Boolean.TRUE.equals(predicate)) {
            this.conditionBuilder = new StringBuilder(condition);
            if (GlobalConfig.CONDITION_PRIORITY == ConditionPriority.LEFT_TO_RIGHT) {
                if (condition.contains("OR")) {
                    level = 1;
                } else if (condition.contains("AND")) {
                    level = 2;
                }
            }
            this.precompileArgs.addAll(Arrays.asList(values));
        } else {
            this.conditionBuilder = new StringBuilder();
        }
    }

    protected ConditionSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, String condition, Supplier<Object[]> supplier) {
        this.prefix = prefix;
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
        if (Boolean.TRUE.equals(predicate)) {
            this.conditionBuilder = new StringBuilder(condition);
            if (GlobalConfig.CONDITION_PRIORITY == ConditionPriority.LEFT_TO_RIGHT) {
                if (condition.contains("OR")) {
                    level = 1;
                } else if (condition.contains("AND")) {
                    level = 2;
                }
            }
            this.precompileArgs.addAll(Arrays.asList(supplier.get()));
        } else {
            this.conditionBuilder = new StringBuilder();
        }
    }

    protected ConditionSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, Object... queryCriteria) {
        this.prefix = prefix;
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
        if (Boolean.TRUE.equals(predicate)) {
            T builder = conditionQueryCriteria(queryCriteria);
            if (builder != null) {
                this.conditionBuilder = builder.conditionBuilder;
                this.sign = builder.sign;
                this.precompileArgs.addAll(builder.precompileArgs);
                this.level = builder.level;
            } else {
                this.conditionBuilder = new StringBuilder();
            }
        } else {
            this.conditionBuilder = new StringBuilder();
        }
    }

    /**
     * 标准查询条件
     *
     * @param queryCriteria 查询条件
     * @return 结果
     */
    private T conditionQueryCriteria(Object... queryCriteria) {
        T conditionSqlBuilder = null;
        for (Object criteria : queryCriteria) {
            if (criteria instanceof Map<?, ?> criteriaMap) {
                for (Map.Entry<?, ?> entry : criteriaMap.entrySet()) {
                    try {
                        if (entry.getValue() instanceof Collection || (entry.getValue() != null
                                && entry.getValue().getClass().isArray())) {
                            conditionSqlBuilder = handleQueryConditionBuilder(conditionSqlBuilder,
                                    String.valueOf(entry.getKey()), QueryRule.IN, entry.getValue());
                        } else if (entry.getValue() == null) {
                            conditionSqlBuilder = handleQueryConditionBuilder(conditionSqlBuilder,
                                    String.valueOf(entry.getKey()), QueryRule.IS_NULL, CommonConstant.EMPTY_OBJECT_ARRAY);
                        } else {
                            conditionSqlBuilder = handleQueryConditionBuilder(conditionSqlBuilder,
                                    String.valueOf(entry.getKey()), QueryRule.EQ, entry.getValue());
                        }
                    } catch (IllegalAccessException | NoSuchMethodException | InstantiationException |
                             InvocationTargetException e) {
                        throw new SqlBuilderException("条件模型 " + criteria.getClass().getName()
                                + " 字段 " + entry.getKey() + " 处理异常", e);
                    }
                }
            } else {
                Class<?> criteriaClass = criteria.getClass();
                List<Alias> fields = ObjectMapper.getColumnFields(criteriaClass);
                for (Alias alias : fields) {
                    try {
                        Field field = ObjectMapper.getField(criteriaClass, alias.getAlias());
                        Object value = ObjectMapper.getFieldValue(criteria, alias.getAlias());
                        if (field != null && value != null && (!(value instanceof Collection)
                                || !((Collection<?>) value).isEmpty())) {
                            Query[] queries = getQueries(field);
                            if (queries != null) {
                                for (Query query : queries) {
                                    if (query != null) {
                                        Object queryValue = handleQuery(query, alias, criteria, value);
                                        if (queryValue != null) {
                                            Object queryColumn = "".equals(query.value())
                                                    ? alias.getOrigin() : handleQueryColumn(query.value(), value);
                                            if (queryColumn instanceof Collection && queryValue instanceof Collection
                                                    && ((Collection<?>) queryColumn).size() == ((Collection<?>) queryValue).size()) {
                                                Iterator<?> queryColumnIterator = ((Collection<?>) queryColumn)
                                                        .iterator(), queryValueIterator = ((Collection<?>) queryValue)
                                                        .iterator();
                                                while (queryColumnIterator.hasNext() && queryValueIterator.hasNext()) {
                                                    conditionSqlBuilder = handleQueryConditionBuilder(conditionSqlBuilder,
                                                            String.valueOf(queryColumnIterator.next()), query.type(),
                                                            queryValueIterator.next());
                                                }
                                            } else {
                                                if (queryColumn instanceof Collection && !((Collection<?>) queryColumn).isEmpty()) {
                                                    queryColumn = ((Collection<?>) queryColumn).iterator().next();
                                                }
                                                conditionSqlBuilder = handleQueryConditionBuilder(conditionSqlBuilder,
                                                        String.valueOf(queryColumn), query.type(), queryValue);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IllegalAccessException | NoSuchMethodException | InstantiationException |
                             InvocationTargetException e) {
                        throw new SqlBuilderException("条件模型 " + criteria.getClass().getName()
                                + " 字段 " + alias.getAlias() + " 处理异常", e);
                    }
                }
            }
        }
        return conditionSqlBuilder;
    }

    /**
     * 处理查询条件构造器
     *
     * @param conditionSqlBuilder 条件构造器
     * @param columnName          列名
     * @param rule                查询规则
     * @param queryValue          查询值
     * @return 条件实例
     * @throws InvocationTargetException 调用目标异常
     * @throws NoSuchMethodException     没有这样的方法例外
     * @throws InstantiationException    实例化异常
     * @throws IllegalAccessException    非法访问异常
     */
    private T handleQueryConditionBuilder(T conditionSqlBuilder, String columnName, QueryRule rule, Object queryValue)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (conditionSqlBuilder == null) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(columnName),
                    rule, queryValue);
            return (T) ObjectMapper.getInstance(getClass(), CommonConstant.CONDITION_CONSTRUCTOR_PARAMETER_TYPES,
                    true, null, CommonConstant.EMPTY_OBJECT_ARRAY, pt.t1, pt.t2);
        }
        return (T) conditionSqlBuilder.and(columnName, rule, queryValue);
    }

    /**
     * 获取查询注解
     *
     * @param field 字段
     * @return 查询注解
     */
    private static Query @Nullable [] getQueries(Field field) {
        Queries queriesAnnotation = field.getAnnotation(Queries.class);
        Query queryAnnotation;
        Query[] queries = null;
        if (queriesAnnotation != null && queriesAnnotation.value().length > 0) {
            queries = queriesAnnotation.value();
        } else if ((queryAnnotation = field.getAnnotation(Query.class)) != null) {
            queries = new Query[]{queryAnnotation};
        }
        return queries;
    }

    /**
     * 处理查询
     *
     * @param query      查询注解
     * @param alias      别名
     * @param criteria   查询条件
     * @param fieldValue 字段值
     * @return 结果
     * @throws InvocationTargetException 调用目标异常
     * @throws NoSuchMethodException     没有这样的方法例外
     * @throws InstantiationException    实例化异常
     * @throws IllegalAccessException    非法访问异常
     */
    private Object handleQuery(Query query, Alias alias, Object criteria, Object fieldValue) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object value = fieldValue;
        Class<?> mapFunClass = query.mapFun();
        String attr = query.attr();
        if (mapFunClass != Void.class) {
            Object mapFun = ObjectMapper.getSingleObject(mapFunClass);
            if (mapFun instanceof Function) {
                value = ((Function<Object, ?>) mapFun).apply(fieldValue);
            } else if (mapFun instanceof BiFunction) {
                value = ((BiFunction<Object, Object, ?>) mapFun).apply(criteria, fieldValue);
            } else {
                throw new SqlBuilderException("字段 \"" + alias.getAlias() + "\" 映射函数 \"" + mapFunClass.getName() + "\" 不是 java.util.Function 接口的实现");
            }
        } else if (!"".equals(attr)) {
            value = ObjectMapper.getAttr(fieldValue, attr.split("\\."), 0);
        } else if (query.openBooleanToConst()) {
            value = null;
            if (query.trueToConstType() != Void.class && Boolean.TRUE.equals(fieldValue)) {
                value = ConvertTools.strTo(query.trueToConst(), query.trueToConstType());
            } else if (query.falseToConstType() != Void.class && Boolean.FALSE.equals(fieldValue)) {
                value = ConvertTools.strTo(query.falseToConst(), query.falseToConstType());
            }
        }
        if (value instanceof Collection && ((Collection<?>) value).isEmpty()) {
            value = null;
        }
        return value;
    }

    /**
     * 处理查询列
     *
     * @param queryColumn 查询列
     * @param fieldValue  字段值
     * @return 结果
     * @throws InvocationTargetException 调用目标异常
     * @throws IllegalAccessException    非法访问异常
     */
    private Object handleQueryColumn(String queryColumn, Object fieldValue) throws InvocationTargetException,
            IllegalAccessException {
        if (queryColumn.startsWith("${") && queryColumn.endsWith("}") && queryColumn.length() > 3) {
            String[] queryColumnFieldNames = queryColumn.substring(2, queryColumn.length() - 1).split("\\.");
            return ObjectMapper.getAttr(fieldValue, queryColumnFieldNames, 0);
        }
        return queryColumn;
    }

    /**
     * 添加条件
     *
     * @param condition 条件
     * @param level     等级
     */
    private void addCondition(String condition, int level) {
        if (GlobalConfig.CONDITION_PRIORITY == ConditionPriority.LEFT_TO_RIGHT
                && !conditionBuilder.isEmpty() && this.level < level) {
            conditionBuilder.insert(0, "(").append(")").append(condition);
        } else {
            conditionBuilder.append(condition);
        }
        this.level = 2;
    }

    public T and(String column, QueryRule rule, Object... values) {
        Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), rule, values);
        return and(pt.t1, pt.t2);
    }

    public T and(String condition, Object... values) {
        if (condition != null && !condition.isEmpty()) {
            if (!conditionBuilder.isEmpty()) {
                condition = " AND " + condition;
            }
            addCondition(condition, 2);
            if (values.length > 0) {
                precompileArgs.addAll(Arrays.stream(values).map(e -> {
                    if (e instanceof SqlBuilderRoute) {
                        return Column.as("(" + ((SqlBuilderRoute) e).build() + ")");
                    }
                    return e;
                }).toList());
            }
        }
        return (T) this;
    }

    private T and(String condition, List<Object> values) {
        if (condition != null && !condition.isEmpty()) {
            if (!conditionBuilder.isEmpty()) {
                condition = " AND " + condition;
            }
            addCondition(condition, 2);
            if (values != null && !values.isEmpty()) {
                precompileArgs.addAll(values.stream().map(e -> {
                    if (e instanceof SqlBuilderRoute) {
                        // 将 SQL 作为列处理
                        return Column.as("(" + ((SqlBuilderRoute) e).build() + ")");
                    }
                    return e;
                }).toList());
            }
        }
        return (T) this;
    }

    public <P> T and(LambdaFunction<P, ?> lambdaFunction, QueryRule option, Object... values) {
        return and(LambdaTools.getColumnName(lambdaFunction), option, values);
    }

    public T and(T... wrappers) {
        for (T wrapper : wrappers) {
            if (wrapper != null) {
                T and = and("(" + wrapper.conditionBuilder.toString() + ")", wrapper.precompileArgs);
            }
        }
        return (T) this;
    }

    public T and(Object... queryCriteria) {
        if (queryCriteria.length > 0) {
            return and(conditionQueryCriteria(queryCriteria));
        }
        return (T) this;
    }

    public <P> T and(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, QueryRule option, Object... values) {
        return and(predicate, LambdaTools.getColumnName(lambdaFunction), option, values);
    }

    public T and(Boolean predicate, String column, QueryRule option, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), option, values);
            return and(pt.t1, pt.t2);
        }
        return (T) this;
    }

    public <P> T and(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, QueryRule option, Supplier<Object[]> values) {
        return and(predicate, LambdaTools.getColumnName(lambdaFunction), option, values);
    }

    public T and(Boolean predicate, String column, QueryRule option, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), option, values.get());
            return and(pt.t1, pt.t2);
        }
        return (T) this;
    }

    public T and(Boolean predicate, String condition, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(condition, values);
        }
        return (T) this;
    }

    public T and(Boolean predicate, Supplier<String> condition, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(condition.get(), values);
        }
        return (T) this;
    }

    public T and(Boolean predicate, String condition, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(condition, values.get());
        }
        return (T) this;
    }

    public T and(Boolean predicate, Supplier<String> condition, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(condition.get(), values.get());
        }
        return (T) this;
    }

    public T and(Boolean predicate, T... wrappers) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(wrappers);
        }
        return (T) this;
    }

    public T and(Boolean predicate, Supplier<T[]> wrapper) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(wrapper.get());
        }
        return (T) this;
    }

    public T and(Boolean predicate, Object... queryCriteria) {
        if (predicate) {
            return and(queryCriteria);
        }
        return (T) this;
    }


    public T or(String condition, Object... values) {
        if (condition != null && !condition.isEmpty()) {
            if (!conditionBuilder.isEmpty()) {
                condition = " OR " + condition;
            }
            addCondition(condition, 1);
            if (values.length > 0) {
                precompileArgs.addAll(Arrays.stream(values).map(e -> {
                    if (e instanceof SqlBuilderRoute) {
                        // deal sql as a column
                        return Column.as("(" + ((SqlBuilderRoute) e).build() + ")");
                    }
                    return e;
                }).toList());
            }
        }
        return (T) this;
    }

    private T or(String condition, List<Object> values) {
        if (condition != null && !condition.isEmpty()) {
            if (!conditionBuilder.isEmpty()) {
                condition = " OR " + condition;
            }
            addCondition(condition, 1);
            if (values != null && !values.isEmpty()) {
                precompileArgs.addAll(values.stream().map(e -> {
                    if (e instanceof SqlBuilderRoute) {
                        // deal sql as a column
                        return Column.as("(" + ((SqlBuilderRoute) e).build() + ")");
                    }
                    return e;
                }).toList());
            }
        }
        return (T) this;
    }

    public T or(String column, QueryRule option, Object... values) {
        Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), option, values);
        return or(pt.t1, pt.t2);
    }

    public <P> T or(LambdaFunction<P, ?> lambdaFunction, QueryRule option, Object... values) {
        return or(LambdaTools.getColumnName(lambdaFunction), option, values);
    }

    public T or(T... wrappers) {
        if (wrappers.length > 0) {
            for (T wrapper : wrappers) {
                if (wrapper != null) {
                    or("(" + wrapper.conditionBuilder.toString() + ")", wrapper.precompileArgs);
                }
            }
        }
        return (T) this;
    }

    public T or(Object... queryCriteria) {
        if (queryCriteria.length > 0) {
            return or(conditionQueryCriteria(queryCriteria));
        }
        return (T) this;
    }

    public T or(Boolean predicate, String column, QueryRule option, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), option, values);
            return or(pt.t1, pt.t2);
        }
        return (T) this;
    }

    public <P> T or(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, QueryRule option, Object... values) {
        return or(predicate, LambdaTools.getColumnName(lambdaFunction), option, values);
    }

    public T or(Boolean predicate, String column, QueryRule option, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), option, values.get());
            return or(pt.t1, pt.t2);
        }
        return (T) this;
    }

    public <P> T or(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, QueryRule option, Supplier<Object[]> values) {
        return or(predicate, LambdaTools.getColumnName(lambdaFunction), option, values);
    }

    public T or(Boolean predicate, String condition, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(condition, values);
        }
        return (T) this;
    }

    public T or(Boolean predicate, Supplier<String> condition, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(condition.get(), values);
        }
        return (T) this;
    }

    public T or(Boolean predicate, String condition, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(condition, values.get());
        }
        return (T) this;
    }

    public T or(Boolean predicate, Supplier<String> condition, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(condition.get(), values.get());
        }
        return (T) this;
    }

    public T or(Boolean predicate, T... wrappers) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(wrappers);
        }
        return (T) this;
    }

    public T or(Boolean predicate, Supplier<T[]> wrapper) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(wrapper.get());
        }
        return (T) this;
    }

    public T or(Boolean predicate, Object... queryCriteria) {
        if (predicate) {
            return or(queryCriteria);
        }
        return (T) this;
    }

    public T andEq(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, QueryRule.EQ, value.get());
        }
        return (T) this;
    }

    public <P> T andEq(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andEq(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T andEq(Boolean predicate, String column, Object value) {
        return and(predicate, column, QueryRule.EQ, value);
    }

    public <P> T andEq(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andEq(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }


    public T andEq(String column, Object value) {
        return and(column, QueryRule.EQ, value);
    }

    public <P> T andEq(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andEq(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T andExists(Boolean predicate, Object builder) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(CommonConstant.EMPTY_STRING, QueryRule.EXISTS, new Object[]{builder});
        }
        return (T) this;
    }

    public T andExists(Boolean predicate, Supplier<Object> builder) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(CommonConstant.EMPTY_STRING, QueryRule.EXISTS, new Object[]{builder.get()});
        }
        return (T) this;
    }

    public T andExists(Object builder) {
        return and(CommonConstant.EMPTY_STRING, QueryRule.EXISTS, new Object[]{builder});
    }

    public T andNotExists(Boolean predicate, Object builder) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(CommonConstant.EMPTY_STRING, QueryRule.NOT_EXISTS, new Object[]{builder});
        }
        return (T) this;
    }

    public T andNotExists(Boolean predicate, Supplier<Object> builder) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(CommonConstant.EMPTY_STRING, QueryRule.NOT_EXISTS, new Object[]{builder.get()});
        }
        return (T) this;
    }

    public T andNotExists(Object builder) {
        return and(CommonConstant.EMPTY_STRING, QueryRule.NOT_EXISTS, new Object[]{builder});
    }

    public T andGt(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, QueryRule.GT, value.get());
        }
        return (T) this;
    }

    public <P> T andGt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andGt(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T andGt(Boolean predicate, String column, Object value) {
        return and(predicate, column, QueryRule.GT, value);
    }

    public <P> T andGt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andGt(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T andGt(String column, Object value) {
        return and(column, QueryRule.GT, value);
    }

    public <P> T andGt(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andGt(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T andGe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, QueryRule.GE, value.get());
        }
        return (T) this;
    }

    public <P> T andGe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andGe(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T andGe(Boolean predicate, String column, Object value) {
        return and(predicate, column, QueryRule.GE, value);
    }

    public <P> T andGe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andGe(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T andGe(String column, Object value) {
        return and(column, QueryRule.GE, value);
    }

    public <P> T andGe(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andGe(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T andLt(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, QueryRule.LT, value.get());
        }
        return (T) this;
    }

    public T andLt(Boolean predicate, String column, Object value) {
        return and(predicate, column, QueryRule.LT, value);
    }

    public T andLt(String column, Object value) {
        return and(column, QueryRule.LT, value);
    }


    public <P> T andLt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andLt(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T andLt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andLt(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T andLt(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andLt(LambdaTools.getColumnName(lambdaFunction), value);
    }


    public T andLe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, QueryRule.LE, value.get());
        }
        return (T) this;
    }

    public T andLe(Boolean predicate, String column, Object value) {
        return and(predicate, column, QueryRule.LE, value);
    }

    public T andLe(String column, Object value) {
        return and(column, QueryRule.LE, value);
    }


    public <P> T andLe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andLe(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T andLe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andLe(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T andLe(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andLe(LambdaTools.getColumnName(lambdaFunction), value);
    }


    public T andNe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, QueryRule.NE, value.get());
        }
        return (T) this;
    }

    public T andNe(Boolean predicate, String column, Object value) {
        return and(predicate, column, QueryRule.NE, value);
    }

    public T andNe(String column, Object value) {
        return and(column, QueryRule.NE, value);
    }

    public <P> T andNe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andNe(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T andNe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andNe(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T andNe(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andNe(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T andLLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, QueryRule.LEFT_LIKE, value.get());
        }
        return (T) this;
    }

    public T andLLike(Boolean predicate, String column, Object value) {
        return and(predicate, column, QueryRule.LEFT_LIKE, value);
    }

    public T andLLike(String column, Object value) {
        return and(column, QueryRule.LEFT_LIKE, value);
    }


    public <P> T andLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andLLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T andLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andLLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T andLLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andLLike(LambdaTools.getColumnName(lambdaFunction), value);
    }


    public T andNotLLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, QueryRule.NOT_LEFT_LIKE, value.get());
        }
        return (T) this;
    }

    public T andNotLLike(Boolean predicate, String column, Object value) {
        return and(predicate, column, QueryRule.NOT_LEFT_LIKE, value);
    }

    public T andNotLLike(String column, Object value) {
        return and(column, QueryRule.NOT_LEFT_LIKE, value);
    }

    public <P> T andNotLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andNotLLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T andNotLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andNotLLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T andNotLLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andNotLLike(LambdaTools.getColumnName(lambdaFunction), value);
    }


    public T andRLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, QueryRule.RIGHT_LIKE, value.get());
        }
        return (T) this;
    }

    public T andRLike(Boolean predicate, String column, Object value) {
        return and(predicate, column, QueryRule.RIGHT_LIKE, value);
    }

    public T andRLike(String column, Object value) {
        return and(column, QueryRule.RIGHT_LIKE, value);
    }

    public <P> T andRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andRLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T andRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andRLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T andRLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andRLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T andNotRLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, QueryRule.NOT_RIGHT_LIKE, value.get());
        }
        return (T) this;
    }

    public T andNotRLike(Boolean predicate, String column, Object value) {
        return and(predicate, column, QueryRule.NOT_RIGHT_LIKE, value);
    }

    public T andNotRLike(String column, Object value) {
        return and(column, QueryRule.NOT_RIGHT_LIKE, value);
    }

    public <P> T andNotRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andNotRLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T andNotRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andNotRLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T andNotRLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andNotRLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T andLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, QueryRule.LIKE, value.get());
        }
        return (T) this;
    }

    public T andLike(Boolean predicate, String column, Object value) {
        return and(predicate, column, QueryRule.LIKE, value);
    }

    public T andLike(String column, Object value) {
        return and(column, QueryRule.LIKE, value);
    }

    public <P> T andLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T andLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T andLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T andNotLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, QueryRule.NOT_LIKE, value.get());
        }
        return (T) this;
    }

    public T andNotLike(Boolean predicate, String column, Object value) {
        return and(predicate, column, QueryRule.NOT_LIKE, value);
    }

    public T andNotLike(String column, Object value) {
        return and(column, QueryRule.NOT_LIKE, value);
    }

    public <P> T andNotLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andNotLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T andNotLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andNotLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T andNotLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return andNotLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T andIn(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, QueryRule.IN, values.get());
        }
        return (T) this;
    }

    public T andIn(Boolean predicate, String column, Object... values) {
        return and(predicate, column, QueryRule.IN, values);
    }

    public T andIn(String column, Object... values) {
        return and(column, QueryRule.IN, values);
    }

    public <P> T andIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> values) {
        return andIn(predicate, LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T andIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return andIn(predicate, LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T andIn(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return andIn(LambdaTools.getColumnName(lambdaFunction), values);
    }

    public T andNotIn(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, QueryRule.NOT_IN, values.get());
        }
        return (T) this;
    }

    public T andNotIn(Boolean predicate, String column, Object... values) {
        return and(predicate, column, QueryRule.NOT_IN, values);
    }

    public T andNotIn(String column, Object... values) {
        return and(column, QueryRule.NOT_IN, values);
    }

    public <P> T andNotIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> values) {
        return andNotIn(predicate, LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T andNotIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return andNotIn(predicate, LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T andNotIn(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return andNotIn(LambdaTools.getColumnName(lambdaFunction), values);
    }

    public T andBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, QueryRule.BETWEEN_AND, values.get());
        }
        return (T) this;
    }

    public T andBetween(Boolean predicate, String column, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, QueryRule.BETWEEN_AND, value1.get(), value2.get());
        }
        return (T) this;
    }

    public T andBetween(Boolean predicate, String column, Object... values) {
        return and(predicate, column, QueryRule.BETWEEN_AND, values);
    }

    public T andBetween(Boolean predicate, String column, Object value1, Object value2) {
        return and(predicate, column, QueryRule.BETWEEN_AND, value1, value2);
    }

    public T andBetween(String column, Object... values) {
        return and(column, QueryRule.BETWEEN_AND, values);
    }

    public T andBetween(String column, Object value1, Object value2) {
        return and(column, QueryRule.BETWEEN_AND, value1, value2);
    }

    public <P> T andBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> values) {
        return andBetween(predicate, LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T andBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        return andBetween(predicate, LambdaTools.getColumnName(lambdaFunction), value1, value2);
    }

    public <P> T andBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return andBetween(predicate, LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T andBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value1, Object value2) {
        return andBetween(predicate, LambdaTools.getColumnName(lambdaFunction), value1, value2);
    }

    public <P> T andBetween(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return andBetween(LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T andBetween(LambdaFunction<P, ?> lambdaFunction, Object value1, Object value2) {
        return andBetween(LambdaTools.getColumnName(lambdaFunction), value1, value2);
    }

    public T andNotBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, QueryRule.NOT_BETWEEN_AND, values.get());
        }
        return (T) this;
    }

    public T andNotBetween(Boolean predicate, String column, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, QueryRule.NOT_BETWEEN_AND, value1.get(), value2.get());
        }
        return (T) this;
    }

    public T andNotBetween(Boolean predicate, String column, Object... values) {
        return and(predicate, column, QueryRule.NOT_BETWEEN_AND, values);
    }

    public T andNotBetween(Boolean predicate, String column, Object value1, Object value2) {
        return and(predicate, column, QueryRule.NOT_BETWEEN_AND, value1, value2);
    }

    public T andNotBetween(String column, Object... values) {
        return and(column, QueryRule.NOT_BETWEEN_AND, values);
    }

    public T andNotBetween(String column, Object value1, Object value2) {
        return and(column, QueryRule.NOT_BETWEEN_AND, value1, value2);
    }

    public <P> T andNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> values) {
        return andNotBetween(predicate, LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T andNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        return andNotBetween(predicate, LambdaTools.getColumnName(lambdaFunction), value1, value2);
    }

    public <P> T andNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return andNotBetween(predicate, LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T andNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value1, Object value2) {
        return andNotBetween(predicate, LambdaTools.getColumnName(lambdaFunction), value1, value2);
    }

    public <P> T andNotBetween(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return andNotBetween(LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T andNotBetween(LambdaFunction<P, ?> lambdaFunction, Object value1, Object value2) {
        return andNotBetween(LambdaTools.getColumnName(lambdaFunction), value1, value2);
    }

    public T andNull(Boolean predicate, String column) {
        return and(predicate, column, QueryRule.IS_NULL);
    }

    public T andNull(String column) {
        return and(column, QueryRule.IS_NULL);
    }

    public <P> T andNull(Boolean predicate, LambdaFunction<P, ?> lambdaFunction) {
        return andNull(predicate, LambdaTools.getColumnName(lambdaFunction));
    }

    public <P> T andNull(LambdaFunction<P, ?> lambdaFunction) {
        return andNull(LambdaTools.getColumnName(lambdaFunction));
    }

    public T andNotNull(Boolean predicate, String column) {
        return and(predicate, column, QueryRule.NOT_NULL);
    }

    public T andNotNull(String column) {
        return and(column, QueryRule.NOT_NULL);
    }

    public <P> T andNotNull(Boolean predicate, LambdaFunction<P, ?> lambdaFunction) {
        return andNotNull(predicate, LambdaTools.getColumnName(lambdaFunction));
    }

    public <P> T andNotNull(LambdaFunction<P, ?> lambdaFunction) {
        return andNotNull(LambdaTools.getColumnName(lambdaFunction));
    }

    public T orEq(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, QueryRule.EQ, value.get());
        }
        return (T) this;
    }

    public T orEq(Boolean predicate, String column, Object value) {
        return or(predicate, column, QueryRule.EQ, value);
    }

    public T orEq(String column, Object value) {
        return or(column, QueryRule.EQ, value);
    }

    public <P> T orEq(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orEq(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orEq(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orEq(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orEq(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orEq(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T orExists(Boolean predicate, Object builder) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(CommonConstant.EMPTY_STRING, QueryRule.EXISTS, new Object[]{builder});
        }
        return (T) this;
    }

    public T orExists(Boolean predicate, Supplier<Object> builder) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(CommonConstant.EMPTY_STRING, QueryRule.EXISTS, new Object[]{builder.get()});
        }
        return (T) this;
    }

    public T orExists(Object builder) {
        return or(CommonConstant.EMPTY_STRING, QueryRule.EXISTS, new Object[]{builder});
    }

    public T orNotExists(Boolean predicate, Object builder) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(CommonConstant.EMPTY_STRING, QueryRule.NOT_EXISTS, new Object[]{builder});
        }
        return (T) this;
    }

    public T orNotExists(Boolean predicate, Supplier<Object> builder) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(CommonConstant.EMPTY_STRING, QueryRule.NOT_EXISTS, new Object[]{builder.get()});
        }
        return (T) this;
    }

    public T orNotExists(Object builder) {
        return or(CommonConstant.EMPTY_STRING, QueryRule.NOT_EXISTS, new Object[]{builder});
    }

    public T orGt(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, QueryRule.GT, value.get());
        }
        return (T) this;
    }

    public T orGt(Boolean predicate, String column, Object value) {
        return or(predicate, column, QueryRule.GT, value);
    }

    public T orGt(String column, Object value) {
        return or(column, QueryRule.GT, value);
    }

    public <P> T orGt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orGt(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orGt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orGt(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orGt(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orGt(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T orGe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, QueryRule.GE, value.get());
        }
        return (T) this;
    }

    public T orGe(Boolean predicate, String column, Object value) {
        return or(predicate, column, QueryRule.GE, value);
    }

    public T orGe(String column, Object value) {
        return or(column, QueryRule.GE, value);
    }

    public <P> T orGe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orGe(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orGe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orGe(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orGe(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orGe(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T orLt(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, QueryRule.LT, value.get());
        }
        return (T) this;
    }

    public T orLt(Boolean predicate, String column, Object value) {
        return or(predicate, column, QueryRule.LT, value);
    }

    public T orLt(String column, Object value) {
        return or(column, QueryRule.LT, value);
    }

    public <P> T orLt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orLt(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orLt(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orLt(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orLt(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orLt(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T orLe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, QueryRule.LE, value.get());
        }
        return (T) this;
    }

    public T orLe(Boolean predicate, String column, Object value) {
        return or(predicate, column, QueryRule.LE, value);
    }

    public T orLe(String column, Object value) {
        return or(column, QueryRule.LE, value);
    }

    public <P> T orLe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orLe(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orLe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orLe(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orLe(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orLe(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T orNe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, QueryRule.NE, value.get());
        }
        return (T) this;
    }

    public T orNe(Boolean predicate, String column, Object value) {
        return or(predicate, column, QueryRule.NE, value);
    }

    public T orNe(String column, Object value) {
        return or(column, QueryRule.NE, value);
    }

    public <P> T orNe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orNe(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orNe(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orNe(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orNe(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orNe(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T orLLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, QueryRule.LEFT_LIKE, value.get());
        }
        return (T) this;
    }

    public T orLLike(Boolean predicate, String column, Object value) {
        return or(predicate, column, QueryRule.LEFT_LIKE, value);
    }

    public T orLLike(String column, Object value) {
        return or(column, QueryRule.LEFT_LIKE, value);
    }

    public <P> T orLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orLLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orLLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orLLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orLLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T orNotLLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, QueryRule.NOT_LEFT_LIKE, value.get());
        }
        return (T) this;
    }

    public T orNotLLike(Boolean predicate, String column, Object value) {
        return or(predicate, column, QueryRule.NOT_LEFT_LIKE, value);
    }

    public T orNotLLike(String column, Object value) {
        return or(column, QueryRule.NOT_LEFT_LIKE, value);
    }

    public <P> T orNotLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orNotLLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orNotLLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orNotLLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orNotLLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orNotLLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T orRLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, QueryRule.RIGHT_LIKE, value.get());
        }
        return (T) this;
    }

    public T orRLike(Boolean predicate, String column, Object value) {
        return or(predicate, column, QueryRule.RIGHT_LIKE, value);
    }

    public T orRLike(String column, Object value) {
        return or(column, QueryRule.RIGHT_LIKE, value);
    }

    public <P> T orRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orRLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orRLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orRLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orRLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T orNotRLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, QueryRule.NOT_RIGHT_LIKE, value.get());
        }
        return (T) this;
    }

    public T orNotRLike(Boolean predicate, String column, Object value) {
        return or(predicate, column, QueryRule.NOT_RIGHT_LIKE, value);
    }

    public T orNotRLike(String column, Object value) {
        return or(column, QueryRule.NOT_RIGHT_LIKE, value);
    }

    public <P> T orNotRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orNotRLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orNotRLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orNotRLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orNotRLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orNotRLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T orLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, QueryRule.LIKE, value.get());
        }
        return (T) this;
    }

    public T orLike(Boolean predicate, String column, Object value) {
        return or(predicate, column, QueryRule.LIKE, value);
    }

    public T orLike(String column, Object value) {
        return or(column, QueryRule.LIKE, value);
    }

    public <P> T orLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T orNotLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, QueryRule.NOT_LIKE, value.get());
        }
        return (T) this;
    }

    public T orNotLike(Boolean predicate, String column, Object value) {
        return or(predicate, column, QueryRule.NOT_LIKE, value);
    }

    public T orNotLike(String column, Object value) {
        return or(column, QueryRule.NOT_LIKE, value);
    }

    public <P> T orNotLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orNotLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orNotLike(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orNotLike(predicate, LambdaTools.getColumnName(lambdaFunction), value);
    }

    public <P> T orNotLike(LambdaFunction<P, ?> lambdaFunction, Object value) {
        return orNotLike(LambdaTools.getColumnName(lambdaFunction), value);
    }

    public T orIn(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, QueryRule.IN, values.get());
        }
        return (T) this;
    }

    public T orIn(Boolean predicate, String column, Object... values) {
        return or(predicate, column, QueryRule.IN, values);
    }

    public T orIn(String column, Object... values) {
        return or(column, QueryRule.IN, values);
    }

    public <P> T orIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> values) {
        return orIn(predicate, LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T orIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return orIn(predicate, LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T orIn(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return orIn(LambdaTools.getColumnName(lambdaFunction), values);
    }

    public T orNotIn(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, QueryRule.NOT_IN, values.get());
        }
        return (T) this;
    }

    public T orNotIn(Boolean predicate, String column, Object... values) {
        return or(predicate, column, QueryRule.NOT_IN, values);
    }

    public T orNotIn(String column, Object... values) {
        return or(column, QueryRule.NOT_IN, values);
    }

    public <P> T orNotIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> values) {
        return orNotIn(predicate, LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T orNotIn(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return orNotIn(predicate, LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T orNotIn(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return orNotIn(LambdaTools.getColumnName(lambdaFunction), values);
    }

    public T orBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, QueryRule.BETWEEN_AND, values.get());
        }
        return (T) this;
    }

    public T orBetween(Boolean predicate, String column, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, QueryRule.BETWEEN_AND, value1.get(), value2.get());
        }
        return (T) this;
    }

    public T orBetween(Boolean predicate, String column, Object... values) {
        return or(predicate, column, QueryRule.BETWEEN_AND, values);
    }

    public T orBetween(Boolean predicate, String column, Object value1, Object value2) {
        return or(predicate, column, QueryRule.BETWEEN_AND, value1, value2);
    }

    public T orBetween(String column, Object... values) {
        return or(column, QueryRule.BETWEEN_AND, values);
    }

    public T orBetween(String column, Object value1, Object value2) {
        return or(column, QueryRule.BETWEEN_AND, value1, value2);
    }

    public <P> T orBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> values) {
        return orBetween(predicate, LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T orBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        return orBetween(predicate, LambdaTools.getColumnName(lambdaFunction), value1, value2);
    }

    public <P> T orBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return orBetween(predicate, LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T orBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value1, Object value2) {
        return orBetween(predicate, LambdaTools.getColumnName(lambdaFunction), value1, value2);
    }

    public <P> T orBetween(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return orBetween(LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T orBetween(LambdaFunction<P, ?> lambdaFunction, Object value1, Object value2) {
        return orBetween(LambdaTools.getColumnName(lambdaFunction), value1, value2);
    }

    public T orNotBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, QueryRule.NOT_BETWEEN_AND, values.get());
        }
        return (T) this;
    }

    public T orNotBetween(Boolean predicate, String column, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, QueryRule.NOT_BETWEEN_AND, value1.get(), value2.get());
        }
        return (T) this;
    }

    public T orNotBetween(Boolean predicate, String column, Object... values) {
        return or(predicate, column, QueryRule.NOT_BETWEEN_AND, values);
    }

    public T orNotBetween(Boolean predicate, String column, Object value1, Object value2) {
        return or(predicate, column, QueryRule.NOT_BETWEEN_AND, value1, value2);
    }

    public T orNotBetween(String column, Object... values) {
        return or(column, QueryRule.NOT_BETWEEN_AND, values);
    }

    public T orNotBetween(String column, Object value1, Object value2) {
        return or(column, QueryRule.NOT_BETWEEN_AND, value1, value2);
    }

    public <P> T orNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object[]> values) {
        return orNotBetween(predicate, LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T orNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        return orNotBetween(predicate, LambdaTools.getColumnName(lambdaFunction), value1, value2);
    }

    public <P> T orNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return orNotBetween(predicate, LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T orNotBetween(Boolean predicate, LambdaFunction<P, ?> lambdaFunction, Object value1, Object value2) {
        return orNotBetween(predicate, LambdaTools.getColumnName(lambdaFunction), value1, value2);
    }

    public <P> T orNotBetween(LambdaFunction<P, ?> lambdaFunction, Object... values) {
        return orNotBetween(LambdaTools.getColumnName(lambdaFunction), values);
    }

    public <P> T orNotBetween(LambdaFunction<P, ?> lambdaFunction, Object value1, Object value2) {
        return orNotBetween(LambdaTools.getColumnName(lambdaFunction), value1, value2);
    }

    public T orNull(Boolean predicate, String column) {
        return or(predicate, column, QueryRule.IS_NULL);
    }

    public T orNull(String column) {
        return or(column, QueryRule.IS_NULL);
    }

    public <P> T orNull(Boolean predicate, LambdaFunction<P, ?> lambdaFunction) {
        return orNull(predicate, LambdaTools.getColumnName(lambdaFunction));
    }

    public <P> T orNull(LambdaFunction<P, ?> lambdaFunction) {
        return orNull(LambdaTools.getColumnName(lambdaFunction));
    }

    public T orNotNull(Boolean predicate, String column) {
        return or(predicate, column, QueryRule.NOT_NULL);
    }

    public T orNotNull(String column) {
        return or(column, QueryRule.NOT_NULL);
    }

    public <P> T orNotNull(Boolean predicate, LambdaFunction<P, ?> lambdaFunction) {
        return orNotNull(predicate, LambdaTools.getColumnName(lambdaFunction));
    }

    public <P> T orNotNull(LambdaFunction<P, ?> lambdaFunction) {
        return orNotNull(LambdaTools.getColumnName(lambdaFunction));
    }

    @Override
    public String precompileSql() {
        boolean prefixEmpty = prefix == null || prefix.isEmpty(), conditionEmpty = conditionBuilder.isEmpty();
        if (prefixEmpty && conditionEmpty) {
            return "";
        }
        if (prefixEmpty) {
            return conditionBuilder.toString();
        }
        if (conditionEmpty) {
            return prefix;
        }
        return prefix + " " + sign + " " + conditionBuilder;
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs.toArray(CommonConstant.EMPTY_OBJECT_ARRAY);
    }

}
