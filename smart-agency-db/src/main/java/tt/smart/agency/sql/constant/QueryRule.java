package tt.smart.agency.sql.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * Query 规则
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Getter
@AllArgsConstructor
public enum QueryRule {

    /**
     * 查询规则 大于
     */
    GT(">", "大于"),
    /**
     * 查询规则 大于等于
     */
    GE(">=", "大于等于"),
    /**
     * 查询规则 小于
     */
    LT("<", "小于"),
    /**
     * 查询规则 小于等于
     */
    LE("<=", "小于等于"),
    /**
     * 查询规则 等于
     */
    EQ("=", "等于"),
    /**
     * 查询规则 不等于
     */
    NE("!=", "不等于"),
    /**
     * 查询规则 包含
     */
    IN("IN", "包含"),
    /**
     *
     */
    NOT_IN("NOT IN", "不包含"),
    /**
     * 查询规则 全模糊
     */
    LIKE("LIKE", "全模糊"),
    /**
     * 查询规则 左模糊
     */
    LEFT_LIKE("LEFT_LIKE", "左模糊"),
    /**
     * 查询规则 右模糊
     */
    RIGHT_LIKE("RIGHT_LIKE", "右模糊"),
    /**
     * 查询规则 全模糊不包含
     */
    NOT_LIKE("NOT LIKE", "全模糊不包含"),
    /**
     * 查询规则 左模糊不包含
     */
    NOT_LEFT_LIKE("NOT LIKE", "左模糊不包含"),
    /**
     * 查询规则 右模糊不包含
     */
    NOT_RIGHT_LIKE("NOT LIKE", "右模糊不包含"),
    /**
     * 查询规则 区间
     */
    BETWEEN_AND("BETWEEN", "区间"),
    /**
     * 查询规则 区间不包含
     */
    NOT_BETWEEN_AND("NOT BETWEEN", "区间不包含"),
    /**
     * 查询规则 是 NULL
     */
    IS_NULL("IS NULL", "是 NULL"),
    /**
     * 查询规则 不是 NULL
     */
    NOT_NULL("IS NOT NULL", "不是 NULL"),
    /**
     * 查询规则 子查询
     */
    EXISTS("EXISTS", "子查询"),
    /**
     * 查询规则 子查询不包含
     */
    NOT_EXISTS("NOT EXISTS", "子查询不包含"),
    /**
     * 查询规则 自定义 SQL 片段
     */
    SQL_RULES("EXTEND_SQL", "自定义SQL片段");

    private final String rule;

    private final String desc;

}
