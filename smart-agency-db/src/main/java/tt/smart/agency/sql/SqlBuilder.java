package tt.smart.agency.sql;

import tt.smart.agency.sql.builder.sql.LambdaFunction;
import tt.smart.agency.sql.builder.sql.SqlBuilderRoute;
import tt.smart.agency.sql.builder.sql.dml.DeleteSqlBuilder;
import tt.smart.agency.sql.builder.sql.dml.InsertSqlBuilder;
import tt.smart.agency.sql.builder.sql.dml.UpdateSqlBuilder;
import tt.smart.agency.sql.builder.sql.dml.ValuesSqlBuilder;
import tt.smart.agency.sql.builder.sql.dql.FromSqlBuilder;
import tt.smart.agency.sql.builder.sql.dql.SelectSqlBuilder;
import tt.smart.agency.sql.builder.sql.dql.WhereSqlBuilder;
import tt.smart.agency.sql.constant.InsertMode;
import tt.smart.agency.sql.domain.Alias;

import java.util.List;

/**
 * <p>
 * SQL 构造器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class SqlBuilder {

    /**
     * SELECT 实例
     *
     * @return SelectSqlBuilder
     */
    public static SelectSqlBuilder selectAll() {
        return SqlBuilderRoute.selectAll();
    }

    /**
     * SELECT 实例
     *
     * @param columns 列
     * @return SelectSqlBuilder
     */
    public static SelectSqlBuilder select(String... columns) {
        return SqlBuilderRoute.select(columns);
    }

    /**
     * SELECT 实例
     *
     * @param columns 列
     * @return SelectSqlBuilder
     */
    public static SelectSqlBuilder select(Alias... columns) {
        return SqlBuilderRoute.select(columns);
    }

    /**
     * SELECT 实例
     *
     * @param columns 列
     * @return SelectSqlBuilder
     */
    public static SelectSqlBuilder select(Object... columns) {
        return SqlBuilderRoute.select(columns);
    }

    /**
     * SELECT 实例
     *
     * @param lambdaFunctions LambdaFunction
     * @return SelectSqlBuilder
     */
    @SafeVarargs
    public static <P> SelectSqlBuilder select(LambdaFunction<P, ?>... lambdaFunctions) {
        return SqlBuilderRoute.select(lambdaFunctions);
    }

    /**
     * FROM 实例
     *
     * @param modelClass 类
     * @return FromSqlBuilder
     */
    public static FromSqlBuilder model(Class<?> modelClass) {
        return SqlBuilderRoute.model(modelClass);
    }

    /**
     * INSERT 实例
     *
     * @param mode    INSERT 模式
     * @param table   表
     * @param columns 列
     * @return InsertSqlBuilder
     */
    public static InsertSqlBuilder insert(InsertMode mode, String table, String... columns) {
        return SqlBuilderRoute.insert(mode, table, columns);
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
    public static <P> InsertSqlBuilder insert(InsertMode mode, String table, LambdaFunction<P, ?>... lambdaFunctions) {
        return SqlBuilderRoute.insert(mode, table, lambdaFunctions);
    }

    /**
     * INSERT 实例
     *
     * @param table   表
     * @param columns 列
     * @return InsertSqlBuilder
     */
    public static InsertSqlBuilder insertInto(String table, String... columns) {
        return SqlBuilderRoute.insertInto(table, columns);
    }

    /**
     * INSERT 实例
     *
     * @param table           表
     * @param lambdaFunctions LambdaFunction
     * @return InsertSqlBuilder
     */
    @SafeVarargs
    public static <P> InsertSqlBuilder insertInto(String table, LambdaFunction<P, ?>... lambdaFunctions) {
        return SqlBuilderRoute.insertInto(table, lambdaFunctions);
    }

    /**
     * INSERT 实例
     *
     * @param table   表
     * @param columns 列
     * @return InsertSqlBuilder
     */
    public static InsertSqlBuilder insertIgnore(String table, String... columns) {
        return SqlBuilderRoute.insertIgnore(table, columns);
    }

    /**
     * INSERT 实例
     *
     * @param table           表
     * @param lambdaFunctions LambdaFunction
     * @return InsertSqlBuilder
     */
    @SafeVarargs
    public static <P> InsertSqlBuilder insertIgnore(String table, LambdaFunction<P, ?>... lambdaFunctions) {
        return SqlBuilderRoute.insertIgnore(table, lambdaFunctions);
    }

    /**
     * INSERT 实例
     *
     * @param table   表
     * @param columns 列
     * @return InsertSqlBuilder
     */
    public static InsertSqlBuilder insertOverwrite(String table, String... columns) {
        return SqlBuilderRoute.insertOverwrite(table, columns);
    }

    /**
     * INSERT 实例
     *
     * @param table           表
     * @param lambdaFunctions LambdaFunction
     * @return InsertSqlBuilder
     */
    @SafeVarargs
    public static <P> InsertSqlBuilder insertOverwrite(String table, LambdaFunction<P, ?>... lambdaFunctions) {
        return SqlBuilderRoute.insertOverwrite(table, lambdaFunctions);
    }

    /**
     * INSERT 实例
     *
     * @param table   表
     * @param columns 列
     * @return InsertSqlBuilder
     */
    public static InsertSqlBuilder replaceInto(String table, String... columns) {
        return SqlBuilderRoute.replaceInto(table, columns);
    }

    /**
     * INSERT 实例
     *
     * @param table           表
     * @param lambdaFunctions LambdaFunction
     * @return InsertSqlBuilder
     */
    @SafeVarargs
    public static <P> InsertSqlBuilder replaceInto(String table, LambdaFunction<P, ?>... lambdaFunctions) {
        return SqlBuilderRoute.replaceInto(table, lambdaFunctions);
    }

    /**
     * VALUES 实例
     *
     * @param mode   INSERT 模式
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    @SafeVarargs
    public static <T> ValuesSqlBuilder insert(InsertMode mode, T... models) {
        return SqlBuilderRoute.insert(mode, models);
    }

    /**
     * VALUES 实例
     *
     * @param mode   INSERT 模式
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    public static <T> ValuesSqlBuilder insert(InsertMode mode, List<T> models) {
        return SqlBuilderRoute.insert(mode, models);
    }

    /**
     * VALUES 实例
     *
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    @SafeVarargs
    public static <T> ValuesSqlBuilder insertInto(T... models) {
        return SqlBuilderRoute.insertInto(models);
    }

    /**
     * VALUES 实例
     *
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    public static <T> ValuesSqlBuilder insertInto(List<T> models) {
        return SqlBuilderRoute.insertIgnore(models);
    }

    /**
     * VALUES 实例
     *
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    @SafeVarargs
    public static <T> ValuesSqlBuilder insertIgnore(T... models) {
        return SqlBuilderRoute.insertIgnore(models);
    }

    /**
     * VALUES 实例
     *
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    public static <T> ValuesSqlBuilder insertIgnore(List<T> models) {
        return SqlBuilderRoute.insertIgnore(models);
    }

    /**
     * VALUES 实例
     *
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    @SafeVarargs
    public static <T> ValuesSqlBuilder insertOverwrite(T... models) {
        return SqlBuilderRoute.insertOverwrite(models);
    }

    /**
     * VALUES 实例
     *
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    public static <T> ValuesSqlBuilder insertOverwrite(List<T> models) {
        return SqlBuilderRoute.insertOverwrite(models);
    }

    /**
     * VALUES 实例
     *
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    @SafeVarargs
    public static <T> ValuesSqlBuilder replaceInto(T... models) {
        return SqlBuilderRoute.replaceInto(models);
    }

    /**
     * VALUES 实例
     *
     * @param models 模型
     * @return ValuesSqlBuilder
     */
    public static <T> ValuesSqlBuilder replaceInto(List<T> models) {
        return SqlBuilderRoute.replaceInto(models);
    }

    /**
     * UPDATE 实例
     *
     * @param table 表
     * @return UpdateSqlBuilder
     */
    public static UpdateSqlBuilder update(String table) {
        return SqlBuilderRoute.update(table);
    }

    /**
     * UPDATE 实例
     *
     * @param clazz 类
     * @return UpdateSqlBuilder
     */
    public static UpdateSqlBuilder update(Class<?> clazz) {
        return SqlBuilderRoute.update(clazz);
    }

    /**
     * WHERE 实例
     *
     * @param model 模型
     * @return WhereSqlBuilder
     */
    public static <T> WhereSqlBuilder update(T model) {
        return SqlBuilderRoute.update(model);
    }

    /**
     * DELETE 实例
     *
     * @param table 表
     * @return DeleteSqlBuilder
     */
    public static DeleteSqlBuilder delete(String table) {
        return SqlBuilderRoute.delete(table);
    }

    /**
     * DELETE 实例
     *
     * @param clazz 类
     * @return DeleteSqlBuilder
     */
    public static DeleteSqlBuilder delete(Class<?> clazz) {
        return SqlBuilderRoute.delete(clazz);
    }

    /**
     * DELETE 实例
     *
     * @param model 模型
     * @return WhereSqlBuilder
     */
    public static <T> WhereSqlBuilder delete(T model) {
        return SqlBuilderRoute.delete(model);
    }

}
