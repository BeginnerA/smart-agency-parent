package com.reid.smart.agency.db.sql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tt.smart.agency.sql.config.GlobalConfig;
import tt.smart.agency.sql.tools.SqlNameTools;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>
 * SQL 名称工具测试
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class SqlNameToosTest {

    @BeforeEach
    void setUp() {
        GlobalConfig.OPEN_STRICT_MODE = true;
    }

    @Test
    void testHandleName() {
        assertEquals("*", SqlNameTools.handleName("*"));
        assertEquals("*", SqlNameTools.handleName(" * "));
        assertEquals("`table`.*", SqlNameTools.handleName("table.*"));
        assertEquals("`table`.*", SqlNameTools.handleName("`table`.*"));
        assertEquals("`table`.*", SqlNameTools.handleName(" table.* "));
        assertEquals("`table`.`field`", SqlNameTools.handleName("table.field"));
        assertEquals("`table`.`field`", SqlNameTools.handleName("`table`.`field`"));
        assertEquals("`table`.`field`", SqlNameTools.handleName(" table.field "));
        assertEquals("`table` as `table1`", SqlNameTools.handleName("table as table1"));
        assertEquals("`table` as `table1`", SqlNameTools.handleName("`table` as `table1`"));
        assertEquals("`table` as `table1`", SqlNameTools.handleName(" table as table1 "));
        assertEquals("`task_table` as `task`", SqlNameTools.handleName("task_table as task"));
        assertEquals("`task`", SqlNameTools.handleName("task"));
        assertEquals("`task`", SqlNameTools.handleName("`task`"));
        assertEquals("`task`", SqlNameTools.handleName(" task "));
        assertEquals("`as`", SqlNameTools.handleName("as"));
        assertEquals("`as`", SqlNameTools.handleName(" as "));
    }
}
