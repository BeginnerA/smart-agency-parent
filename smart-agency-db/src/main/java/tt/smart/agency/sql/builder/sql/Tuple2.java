package tt.smart.agency.sql.builder.sql;

/**
 * <p>
 * 元组
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class Tuple2<T1, T2> {
    public T1 t1;
    public T2 t2;

    private Tuple2(T1 t1, T2 t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public static <T1, T2> Tuple2<T1, T2> of(T1 t1, T2 t2) {
        return new Tuple2<>(t1, t2);
    }
}
