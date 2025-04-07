package tt.smart.agency.sql.constant;

/**
 * <p>
 * 条件的优先级
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public enum ConditionPriority {

    /**
     * 优先条件（默认：AND > RO）
     */
    DEFAULT,

    /**
     * 优先条件（默认：左 > 右）
     */
    LEFT_TO_RIGHT,

}
