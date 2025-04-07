package tt.smart.agency.sql.tools;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.builder.sql.Tuple2;
import tt.smart.agency.sql.constant.CommonConstant;
import tt.smart.agency.sql.constant.QueryRule;
import tt.smart.agency.sql.domain.Column;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * SQL 模板工具
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SqlTemplateTools {

    private final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    /**
     * 解析列条件
     *
     * @param column1   列1
     * @param queryRule 查询规则
     * @param column2   列2
     * @return 结果
     */
    public static String parseConditionColumn(String column1, QueryRule queryRule, String column2) {
        return column1 + CommonConstant.BLANK_SPACE + queryRule.getRule() + CommonConstant.BLANK_SPACE + column2;
    }

    /**
     * 解析预编译条件
     *
     * @param column 列
     * @param queryRule 查询规则
     * @param paramSupplier 值
     * @return 结果
     * @param <T> 值类型
     */
    public static <T> Tuple2<String, Object[]> parsePrecompileCondition(String column, QueryRule queryRule, Supplier<T> paramSupplier) {
        T param = paramSupplier.get();
        Tuple2<String, Object[]> pt;
        if (param instanceof Object[]) {
            // 处理数组类型参数
            pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), queryRule, (Object[]) param);
        } else if (param != null) {
            // 处理非空单值参数
            pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), queryRule, param);
        } else {
            // 处理空值（兼容原始逻辑）
            pt = SqlTemplateTools.parsePrecompileCondition(SqlNameTools.handleName(column), queryRule, (Object[]) null);
        }
        return pt;
    }

    /**
     * 解析预编译条件
     *
     * @param column    列
     * @param queryRule 查询规则
     * @param values    值
     * @return 结果
     */
    public static Tuple2<String, Object[]> parsePrecompileCondition(String column, QueryRule queryRule, Object... values) {
        Tuple2<String, Object[]> tuple = null;
        if (queryRule != null) {
            switch (queryRule) {
                case IN:
                case NOT_IN:
                    List<Object> vs = Arrays.stream(values).filter(Objects::nonNull)
                            .flatMap(SqlTemplateTools::handleMoreFlatMap).collect(Collectors.toList());
                    StringBuilder templateBuilder = new StringBuilder(column + CommonConstant.BLANK_SPACE
                            + queryRule.getRule() + CommonConstant.BLANK_SPACE + "(");
                    Object[] precompileArgs = null;
                    for (int i = 0; i < vs.size(); i++) {
                        if (i > 0) {
                            templateBuilder.append(", ");
                        }
                        if (vs.get(i) instanceof SqlBuilderRoute) {
                            // 嵌套查询应该直接退出
                            templateBuilder.append(((SqlBuilderRoute) vs.get(i)).precompileSql());
                            precompileArgs = ((SqlBuilderRoute) vs.get(i)).precompileArgs();
                            break;
                        } else if (vs.get(i) instanceof Column) {
                            templateBuilder.append(((Column) vs.get(i)).getName());
                        } else {
                            templateBuilder.append("?");
                        }
                    }
                    templateBuilder.append(")");
                    if (precompileArgs == null) {
                        precompileArgs = vs.toArray();
                    }
                    tuple = Tuple2.of(templateBuilder.toString(), precompileArgs);
                    break;
                case LIKE:
                case NOT_LIKE:
                    tuple = Tuple2.of(column + CommonConstant.BLANK_SPACE + queryRule.getRule() + " ?", new Object[]{"%" + values[0] + "%"});
                    break;
                case LEFT_LIKE:
                case NOT_LEFT_LIKE:
                    tuple = Tuple2.of(column + CommonConstant.BLANK_SPACE + queryRule.getRule() + " ?", new Object[]{"%" + values[0]});
                    break;
                case RIGHT_LIKE:
                case NOT_RIGHT_LIKE:
                    tuple = Tuple2.of(column + CommonConstant.BLANK_SPACE + queryRule.getRule() + " ?", new Object[]{values[0] + "%"});
                    break;
                case BETWEEN_AND:
                case NOT_BETWEEN_AND:
                    String precompileTemplate = column + CommonConstant.BLANK_SPACE + queryRule.getRule() + " ? AND ?";
                    List<Object> vs1 = Arrays.stream(values).filter(Objects::nonNull)
                            .flatMap(SqlTemplateTools::handleMoreFlatMap).collect(Collectors.toList());
                    tuple = Tuple2.of(precompileTemplate, new Object[]{vs1.get(0), vs1.get(1)});
                    break;
                case EXISTS:
                case NOT_EXISTS:
                    if (values[0] instanceof SqlBuilderRoute) {
                        tuple = Tuple2.of(queryRule.getRule() + "(" + ((SqlBuilderRoute) values[0]).precompileSql() + ")",
                                ((SqlBuilderRoute) values[0]).precompileArgs());
                    } else {
                        tuple = Tuple2.of(queryRule.getRule() + "(" + values[0] + ")", CommonConstant.EMPTY_OBJECT_ARRAY);
                    }
                    break;
                case IS_NULL:
                case NOT_NULL:
                    tuple = Tuple2.of(column + CommonConstant.BLANK_SPACE + queryRule.getRule(), CommonConstant.EMPTY_OBJECT_ARRAY);
                    break;
                default:
                    if (values[0] instanceof SqlBuilderRoute) {
                        tuple = Tuple2.of(column + CommonConstant.BLANK_SPACE + queryRule.getRule()
                                + " (" + ((SqlBuilderRoute) values[0]).precompileSql() + ")", ((SqlBuilderRoute) values[0]).precompileArgs());
                    } else if (values[0] instanceof Column) {
                        tuple = Tuple2.of(column + CommonConstant.BLANK_SPACE + queryRule.getRule()
                                + CommonConstant.BLANK_SPACE + ((Column) values[0]).getName(), CommonConstant.EMPTY_OBJECT_ARRAY);
                    } else {
                        tuple = Tuple2.of(column + CommonConstant.BLANK_SPACE + queryRule.getRule() + " ?", new Object[]{values[0]});
                    }
                    break;
            }
        }
        return tuple;
    }

    /**
     * 对象转 stream 流
     *
     * @param e 对象
     * @return stream
     */
    public static Stream<?> handleMoreFlatMap(Object e) {
        if (e instanceof Collection) {
            return ((Collection<?>) e).stream();
        } else if (e.getClass().isArray()) {
            return Stream.of((Object[]) e);
        }
        return Stream.of(e);
    }

    /**
     * 解析值
     *
     * @param value 值对象
     * @return 解析结果
     */
    public static String parseValue(Object value) {
        if (value instanceof CharSequence) {
            return "'" + value.toString().replace("'", "\\'") + "'";
        } else if (value instanceof Column) {
            return ((Column) value).getName();
        } else if (value instanceof SqlBuilderRoute) {
            return "(" + ((SqlBuilderRoute) value).build() + ")";
        } else if (value instanceof Date) {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_PATTERN);
            return parseValue(formatter.format(value));
        } else if (value instanceof TemporalAccessor) {
            return parseValue(DATE_TIME_FORMATTER.format((TemporalAccessor) value));
        } else if (value instanceof BigDecimal) {
            return ((BigDecimal) value).toPlainString();
        } else if (value instanceof Collection) {
            return ((Collection<?>) value).stream().map(SqlTemplateTools::parseValue)
                    .collect(Collectors.joining(", "));
        }
        return String.valueOf(value);
    }

    /**
     * 解析模板
     *
     * @param template 模板
     * @param values   值对象
     * @return 解析结果
     */
    public static String parseTemplate(String template, Object... values) {
        if (values.length == 0) {
            return template;
        }
        values = Arrays.stream(values).flatMap(e -> {
            if (e instanceof Collection) {
                return ((Collection<?>) e).stream();
            }
            return Stream.of(e);
        }).toArray();
        int index, offset = 0;
        List<Tuple2<Integer, Object>> valueCacheList = new ArrayList<>();
        for (Object value : values) {
            if (offset >= template.length()) {
                break;
            }
            if ((index = template.substring(offset).indexOf("?")) >= 0) {
                valueCacheList.add(Tuple2.of(index + offset, value));
                offset += index + 1;
            } else if (valueCacheList.isEmpty()) {
                return template;
            }
        }
        int peek = 0;
        for (Tuple2<Integer, Object> indexAndValue : valueCacheList) {
            int length = template.length();
            template = parseTemplateValue(template, indexAndValue.t1 + peek, indexAndValue.t2);
            peek += template.length() - length;
        }
        return template;
    }

    /**
     * 解析模板值
     *
     * @param template 模板
     * @param index    索引
     * @param value    值对象
     * @return 解析结果
     */
    private static String parseTemplateValue(String template, int index, Object value) {
        if (index == 0) {
            template = parseValue(value) + template.substring(1);
        } else if (index == template.length() - 1) {
            template = template.substring(0, template.length() - 1) + parseValue(value);
        } else {
            template = template.substring(0, index) + parseValue(value) + template.substring(index + 1);
        }
        return template;
    }

    /**
     * 解析模板列
     *
     * @param template 模板
     * @param index    索引
     * @param value    值对象
     * @return 解析结果
     */
    private static String parseTemplateColumn(String template, int index, Object value) {
        if (index == 0) {
            template = value + template.substring(2);
        } else if (index == template.length() - 2) {
            template = template.substring(0, template.length() - 2) + value;
        } else {
            template = template.substring(0, index) + value + template.substring(index + 2);
        }
        return template;
    }

}
