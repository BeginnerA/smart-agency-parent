package tt.smart.agency.sql.domain;

import lombok.Getter;
import tt.smart.agency.sql.builder.sql.LambdaFunction;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.tools.LambdaTools;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * <p>
 * 别名
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Getter
public class Alias implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String origin;

    private final String alias;

    private Alias(String origin) {
        this.origin = origin;
        this.alias = null;
    }

    private Alias(String origin, String alias) {
        this.origin = origin;
        this.alias = alias;
    }

    public static <P> Alias of(LambdaFunction<P, ?> lambdaFunction, String alias) {
        return new Alias(LambdaTools.getColumnName(lambdaFunction), alias);
    }

    public static Alias of(String origin) {
        return new Alias(origin);
    }

    public static Alias of(String origin, String alias) {
        return new Alias(origin, alias);
    }

    public static Alias of(SqlBuilderRoute sb) {
        return new Alias("(" + sb.build() + ")");
    }

    public static Alias of(SqlBuilderRoute sb, String alias) {
        return new Alias("(" + sb.build() + ")", alias);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Alias alias1 = (Alias) o;
        return Objects.equals(origin, alias1.origin) && Objects.equals(alias, alias1.alias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, alias);
    }

    @Override
    public String toString() {
        if (alias == null || alias.isEmpty()) {
            return origin;
        }
        return origin + " AS " + alias;
    }

}
