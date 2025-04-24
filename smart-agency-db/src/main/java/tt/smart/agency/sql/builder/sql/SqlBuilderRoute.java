package tt.smart.agency.sql.builder.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tt.smart.agency.sql.builder.sql.dml.*;
import tt.smart.agency.sql.builder.sql.dql.FromSqlBuilder;
import tt.smart.agency.sql.builder.sql.dql.SelectSqlBuilder;
import tt.smart.agency.sql.builder.sql.dql.WhereSqlBuilder;
import tt.smart.agency.sql.builder.sql.dql.WhereSqlBuilderRoute;
import tt.smart.agency.sql.constant.CommonConstant;
import tt.smart.agency.sql.constant.InsertMode;
import tt.smart.agency.sql.constant.QueryRule;
import tt.smart.agency.sql.domain.Alias;
import tt.smart.agency.sql.exception.SqlBuilderException;
import tt.smart.agency.sql.inner.ObjectMapper;
import tt.smart.agency.sql.tools.SqlTemplateTools;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * DQL 构造器<br>
 * Sql Builder 顶部接口是一个引导程序
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface SqlBuilderRoute extends PreparedStatementSupport {

    Logger log = LoggerFactory.getLogger(SqlBuilderRoute.class);

    /**
     * 核心方法<br>
     * 它定义了如何构建 SQL 或 SQL 片段
     *
     * @return SQL 或 SQL 片段
     */
    default String build() {
        return SqlTemplateTools.parseTemplate(precompileSql(), precompileArgs());
    }

    /**
     * SELECT 实例
     *
     * @return SelectSqlBuilder
     */
    static SelectSqlBuilder selectAll() {
        return new SelectSqlBuilder(null, CommonConstant.EMPTY_OBJECT_ARRAY, "*");
    }

    /**
     * SELECT 实例
     *
     * @param columns 列
     * @return SelectSqlBuilder
     */
    static SelectSqlBuilder select(String... columns) {
        return new SelectSqlBuilder(null, CommonConstant.EMPTY_OBJECT_ARRAY, columns);
    }

    /**
     * SELECT 实例
     *
     * @param columns 列
     * @return SelectSqlBuilder
     */
    static SelectSqlBuilder select(Alias... columns) {
        return new SelectSqlBuilder(null, CommonConstant.EMPTY_OBJECT_ARRAY, columns);
    }

    /**
     * SELECT 实例
     *
     * @param columns 列
     * @return SelectSqlBuilder
     */
    static SelectSqlBuilder select(Object... columns) {
        return new SelectSqlBuilder(null, CommonConstant.EMPTY_OBJECT_ARRAY, columns);
    }

    /**
     * SELECT 实例
     *
     * @param lambdaFunctions LambdaFunction
     * @return SelectSqlBuilder
     */
    @SafeVarargs
    static <P> SelectSqlBuilder select(LambdaFunction<P, ?>... lambdaFunctions) {
        return new SelectSqlBuilder(null, CommonConstant.EMPTY_OBJECT_ARRAY, lambdaFunctions);
    }

    /**
     * FROM 实例
     *
     * @param modelClass 类
     * @return FromSqlBuilder
     */
    static FromSqlBuilder model(Class<?> modelClass) {
        return new SelectSqlBuilder(null, CommonConstant.EMPTY_OBJECT_ARRAY, modelClass).from(modelClass);
    }

    /**
     * INSERT 实例
     *
     * @param mode    INSERT 模式
     * @param table   表
     * @param columns 列
     * @return InsertSqlBuilder
     */
    static InsertSqlBuilder insert(InsertMode mode, String table, String... columns) {
        return new InsertSqlBuilder(table, mode, columns);
    }

    /**
     * INSERT 实例
     *
     * @param mode            INSERT 模式
     * @param table           表
     * @param lambdaFunctions LambdaFunction
     * @return InsertSqlBuilder
     */
    @SafeVarargs
    static <P> InsertSqlBuilder insert(InsertMode mode, String table, LambdaFunction<P, ?>... lambdaFunctions) {
        return new InsertSqlBuilder(table, mode, lambdaFunctions);
    }

    /**
     * INSERT 实例
     *
     * @param table   表
     * @param columns 列
     * @return InsertSqlBuilder
     */
    static InsertSqlBuilder insertInto(String table, String... columns) {
        return new InsertSqlBuilder(table, InsertMode.INSERT_INTO, columns);
    }

    /**
     * INSERT 实例
     *
     * @param table           表
     * @param lambdaFunctions LambdaFunction
     * @return InsertSqlBuilder
     */
    @SafeVarargs
    static <P> InsertSqlBuilder insertInto(String table, LambdaFunction<P, ?>... lambdaFunctions) {
        return new InsertSqlBuilder(table, InsertMode.INSERT_INTO, lambdaFunctions);
    }

    /**
     * INSERT 实例
     *
     * @param table   表
     * @param columns 列
     * @return InsertSqlBuilder
     */
    static InsertSqlBuilder insertIgnore(String table, String... columns) {
        return new InsertSqlBuilder(table, InsertMode.INSERT_IGNORE, columns);
    }

    /**
     * INSERT 实例
     *
     * @param table           表
     * @param lambdaFunctions LambdaFunction
     * @return InsertSqlBuilder
     */
    @SafeVarargs
    static <P> InsertSqlBuilder insertIgnore(String table, LambdaFunction<P, ?>... lambdaFunctions) {
        return new InsertSqlBuilder(table, InsertMode.INSERT_IGNORE, lambdaFunctions);
    }

    /**
     * INSERT 实例
     *
     * @param table   表
     * @param columns 列
     * @return InsertSqlBuilder
     */
    static InsertSqlBuilder insertOverwrite(String table, String... columns) {
        return new InsertSqlBuilder(table, InsertMode.INSERT_OVERWRITE, columns);
    }

    /**
     * INSERT 实例
     *
     * @param table           表
     * @param lambdaFunctions LambdaFunction
     * @return InsertSqlBuilder
     */
    @SafeVarargs
    static <P> InsertSqlBuilder insertOverwrite(String table, LambdaFunction<P, ?>... lambdaFunctions) {
        return new InsertSqlBuilder(table, InsertMode.INSERT_OVERWRITE, lambdaFunctions);
    }

    /**
     * INSERT 实例
     *
     * @param table   表
     * @param columns 列
     * @return InsertSqlBuilder
     */
    static InsertSqlBuilder replaceInto(String table, String... columns) {
        return new InsertSqlBuilder(table, InsertMode.REPLACE_INTO, columns);
    }

    /**
     * INSERT 实例
     *
     * @param table           表
     * @param lambdaFunctions LambdaFunction
     * @return InsertSqlBuilder
     */
    @SafeVarargs
    static <P> InsertSqlBuilder replaceInto(String table, LambdaFunction<P, ?>... lambdaFunctions) {
        return new InsertSqlBuilder(table, InsertMode.REPLACE_INTO, lambdaFunctions);
    }

    /**
     * VALUES 实例
     *
     * @param mode   INSERT 模式
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    @SafeVarargs
    static <T> ValuesSqlBuilder insert(InsertMode mode, T... models) {
        return insert(mode, Arrays.asList(models));
    }

    /**
     * VALUES 实例
     *
     * @param mode   INSERT 模式
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    static <T> ValuesSqlBuilder insert(InsertMode mode, List<T> models) {
        if (models == null || models.isEmpty()) {
            throw new SqlBuilderException("插入的模型不能为空");
        }
        Class<?> clazz = models.getFirst().getClass();
        String tableName = ObjectMapper.getTableName(clazz);
        List<Alias> fields = ObjectMapper.getColumnFields(clazz);
        InsertSqlBuilder builder = insert(mode, tableName, fields.stream().map(Alias::getOrigin).toArray(String[]::new));
        return builder.values().addValues(
                models.stream().map(e -> {
                    Object[] values = new Object[fields.size()];
                    for (int i = 0; i < fields.size(); i++) {
                        try {
                            values[i] = ObjectMapper.getFieldValue(e, fields.get(i).getAlias());
                        } catch (IllegalAccessException | InvocationTargetException illegalAccessException) {
                            values[i] = null;
                            log.warn("插入的模型字段的值 {} 没有得到适当的处理，被忽略了", fields.get(i).getAlias());
                        }
                    }
                    return values;
                }).collect(Collectors.toList())
        );
    }

    /**
     * VALUES 实例
     *
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    @SafeVarargs
    static <T> ValuesSqlBuilder insertInto(T... models) {
        return insert(InsertMode.INSERT_INTO, models);
    }

    /**
     * VALUES 实例
     *
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    static <T> ValuesSqlBuilder insertInto(List<T> models) {
        return insert(InsertMode.INSERT_INTO, models);
    }

    /**
     * VALUES 实例
     *
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    @SafeVarargs
    static <T> ValuesSqlBuilder insertIgnore(T... models) {
        return insert(InsertMode.INSERT_IGNORE, models);
    }

    /**
     * VALUES 实例
     *
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    static <T> ValuesSqlBuilder insertIgnore(List<T> models) {
        return insert(InsertMode.INSERT_IGNORE, models);
    }

    /**
     * VALUES 实例
     *
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    @SafeVarargs
    static <T> ValuesSqlBuilder insertOverwrite(T... models) {
        return insert(InsertMode.INSERT_OVERWRITE, models);
    }

    /**
     * VALUES 实例
     *
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    static <T> ValuesSqlBuilder insertOverwrite(List<T> models) {
        return insert(InsertMode.INSERT_OVERWRITE, models);
    }

    /**
     * VALUES 实例
     *
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    @SafeVarargs
    static <T> ValuesSqlBuilder replaceInto(T... models) {
        return insert(InsertMode.REPLACE_INTO, models);
    }

    /**
     * VALUES 实例
     *
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    static <T> ValuesSqlBuilder replaceInto(List<T> models) {
        return insert(InsertMode.REPLACE_INTO, models);
    }

    /**
     * UPDATE 实例
     *
     * @param table 表
     * @return UpdateSqlBuilder
     */
    static UpdateSqlBuilder update(String table) {
        return new UpdateSqlBuilder(table);
    }

    /**
     * UPDATE 实例
     *
     * @param clazz 类
     * @return UpdateSqlBuilder
     */
    static UpdateSqlBuilder update(Class<?> clazz) {
        return new UpdateSqlBuilder(ObjectMapper.getTableName(clazz));
    }

    /**
     * WHERE 实例
     *
     * @param model 模型
     * @return WhereSqlBuilder
     */
    static <T> WhereSqlBuilder update(T model) {
        Class<?> clazz = model.getClass();
        String tableName = ObjectMapper.getTableName(clazz);
        List<Alias> fields = ObjectMapper.getColumnFields(clazz);
        List<Alias> primaries = ObjectMapper.getPrimaries(clazz);
        Map<String, Object> primaryMapping;
        try {
            if (primaries == null || primaries.isEmpty()) {
                primaryMapping = Collections.emptyMap();
            } else {
                primaryMapping = ObjectMapper.getColumnAndValues(model, primaries);
                fields.removeAll(primaries);
            }
            Map<String, Object> updateMapping = ObjectMapper.getColumnAndValues(model, fields);

            if (updateMapping.isEmpty()) {
                throw new SqlBuilderException("更新后的字段中至少不能有一个值为空");
            }

            UpdateSqlBuilder updateSqlBuilder = update(tableName);
            SetSqlBuilder setSqlBuilder = null;
            for (Map.Entry<String, Object> entry : updateMapping.entrySet()) {
                if (setSqlBuilder == null) {
                    setSqlBuilder = updateSqlBuilder.set(entry.getKey(), entry.getValue());
                } else {
                    setSqlBuilder = setSqlBuilder.addSet(entry.getKey(), entry.getValue());
                }
            }

            if (setSqlBuilder == null) {
                throw new SqlBuilderException("更新 model 属性至少有一个非主键列");
            }

            return getWhereSqlBuilder(primaryMapping, setSqlBuilder);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new SqlBuilderException("获取模型值异常：", e);
        }
    }

    /**
     * DELETE 实例
     *
     * @param table 表
     * @return DeleteSqlBuilder
     */
    static DeleteSqlBuilder delete(String table) {
        return new DeleteSqlBuilder(table);
    }

    /**
     * DELETE 实例
     *
     * @param clazz 类
     * @return DeleteSqlBuilder
     */
    static DeleteSqlBuilder delete(Class<?> clazz) {
        return new DeleteSqlBuilder(ObjectMapper.getTableName(clazz));
    }

    /**
     * DELETE 实例
     *
     * @param model 模型
     * @return WhereSqlBuilder
     */
    static <T> WhereSqlBuilder delete(T model) {
        Class<?> clazz = model.getClass();
        DeleteSqlBuilder deleteSqlBuilder = new DeleteSqlBuilder(ObjectMapper.getTableName(clazz));
        List<Alias> primaries = ObjectMapper.getPrimaries(clazz);
        try {
            Map<String, Object> primaryMapping = primaries == null || primaries.isEmpty() ? Collections.emptyMap() : ObjectMapper.getColumnAndValues(model, primaries);

            return getWhereSqlBuilder(primaryMapping, deleteSqlBuilder);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new SqlBuilderException("获取模型值异常：", e);
        }

    }

    /**
     * 获取 WHERE 实例
     *
     * @param primaryMapping       主键
     * @param whereSqlBuilderRoute WhereSqlBuilderRoute
     * @return WhereSqlBuilder
     */
    private static WhereSqlBuilder getWhereSqlBuilder(Map<String, Object> primaryMapping, WhereSqlBuilderRoute whereSqlBuilderRoute) {
        WhereSqlBuilder whereSqlBuilder = null;
        if (!primaryMapping.isEmpty()) {
            for (Map.Entry<String, Object> entry : primaryMapping.entrySet()) {
                if (entry.getValue() != null) {
                    if (whereSqlBuilder == null) {
                        whereSqlBuilder = whereSqlBuilderRoute.where(entry.getKey(), QueryRule.EQ, entry.getValue());
                    } else {
                        whereSqlBuilder = whereSqlBuilder.and(entry.getKey(), QueryRule.EQ, entry.getValue());
                    }
                }
            }
        } else {
            whereSqlBuilder = whereSqlBuilderRoute.where("");
        }
        return whereSqlBuilder;
    }

}
