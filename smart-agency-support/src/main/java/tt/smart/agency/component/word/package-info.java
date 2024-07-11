/**
 * <p>
 * poi word 模板导出。模板表达式支持如下：<br>
 * - 空格分割 <br>
 * - 三目运算 {{test ? obj:obj2}} <br>
 * - n: 表示这个 cell 是数值类型 {{n:}} <br>
 * - le: 代表长度{{le:()}} 在 if/else 运用{{le:() > 8 ? obj1 : obj2}} <br>
 * - fd: 格式化时间 {{fd:(obj;yyyy-MM-dd)}} <br>
 * - fn: 格式化数字 {{fn:(obj;###.00)}} <br>
 * - fe: 遍历数据，创建 row <br>
 * - !fe: 遍历数据不创建 row <br>
 * - $fe: 遍历数据下移一行创建 row 插入。在定义模板时 {{$fe:列表字段}} 需要定义在列表表头，数据填充行单元格需要 [对象字段] 定义填充字段 <br>
 * - !if: 删除当前列 {{!if:(test)}} <br>
 * - 单引号表示常量值 '' 比如'1' 那么输出的就是 1 <br>
 * - &NULL& 控制 <br>
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
package tt.smart.agency.component.word;