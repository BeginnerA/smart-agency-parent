package tt.smart.agency.sql.domain;

import lombok.Getter;
import tt.smart.agency.sql.builder.sql.LambdaFunction;
import tt.smart.agency.sql.tools.LambdaTools;
import tt.smart.agency.sql.tools.SqlNameTools;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 列
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Getter
public class Column implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String name;

    protected Column(String name) {
        this.name = name;
    }

    public static Column as(String column) {
        return new Column(SqlNameTools.handleName(column));
    }

    public static <P> Column as(LambdaFunction<P, ?> lambdaFunction) {
        return new Column(SqlNameTools.handleName(LambdaTools.getColumnName(lambdaFunction)));
    }

}
