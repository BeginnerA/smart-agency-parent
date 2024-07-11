package tt.smart.agency.component.word.base;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.log4j.Log4j2;
import tt.smart.agency.component.word.domain.BaseTypeConstants;
import tt.smart.agency.component.word.domain.params.ExcelExportEntity;
import tt.smart.agency.component.word.domain.params.ImageEntity;
import tt.smart.agency.component.word.domain.params.SpecialSymbolsEntity;
import tt.smart.agency.component.word.handler.IExcelDataHandler;
import tt.smart.agency.component.word.handler.IExcelDictHandler;
import tt.smart.agency.component.word.handler.II18nHandler;
import tt.smart.agency.component.word.tools.PoiPublicTools;
import tt.smart.agency.component.word.tools.PoiReflectorTools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

/**
 * <p>
 * 导出基础处理服务
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Log4j2
@SuppressWarnings("rawtypes")
public class ExportCommonService {

    protected IExcelDataHandler dataHandler;
    protected IExcelDictHandler dictHandler;
    protected II18nHandler i18nHandler;
    protected List<String> needHandlerList;

    /**
     * 日期格式值处理
     *
     * @param value  值
     * @param entity 导出对象
     * @return 结果
     */
    private Object dateFormatValue(Object value, ExcelExportEntity entity) {
        Date temp = null;
        try {
            if (value instanceof String && StrUtil.isNotBlank(value.toString())) {
                SimpleDateFormat format = new SimpleDateFormat(entity.getDatabaseFormat());
                temp = format.parse(value.toString());
            } else if (value instanceof Date) {
                temp = (Date) value;
            } else if (value instanceof Instant instant) {
                temp = Date.from(instant);
            } else if (value instanceof LocalDate localDate) {
                temp = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            } else if (value instanceof LocalDateTime localDateTime) {
                temp = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            } else if (value instanceof ZonedDateTime zonedDateTime) {
                temp = Date.from(zonedDateTime.toInstant());
            } else if (value instanceof OffsetDateTime offsetDateTime) {
                temp = Date.from(offsetDateTime.toInstant());
            }
            if (temp != null) {
                SimpleDateFormat format = new SimpleDateFormat(entity.getFormat());
                if (StrUtil.isNotEmpty(entity.getTimezone())) {
                    format.setTimeZone(TimeZone.getTimeZone(entity.getTimezone()));
                }
                value = format.format(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException("Word 导出日期值处理失败", e);
        }

        return value;
    }

    /**
     * 数字格式值处理
     *
     * @param value  值
     * @param entity 导出对象
     * @return 结果
     */
    private Object numFormatValue(Object value, ExcelExportEntity entity) {
        if (value == null) {
            return null;
        }
        if (!NumberUtil.isNumber(value.toString())) {
            log.error("数据需要 num 格式，但不是 num，值为:{}", value);
            return null;
        }
        Double d = Double.parseDouble(value.toString());
        DecimalFormat df = new DecimalFormat(entity.getNumFormat());
        return df.format(d);
    }

    /**
     * 获取需要导出的全部字段
     *
     * @param fields      字段列表
     * @param excelParams 导出参数列表
     * @param pojoClass   数据 class
     * @param getMethods  get 方法列表
     */
    public void getAllExcelField(Field[] fields, List<ExcelExportEntity> excelParams, Class<?> pojoClass,
                                 List<Method> getMethods) {
        ExcelExportEntity excelEntity;
        // 遍历全部字段
        for (Field field : fields) {
            if (Collection.class.isAssignableFrom(field.getType())) {
                ParameterizedType pt = (ParameterizedType) field.getGenericType();
                Class<?> clz = (Class<?>) pt.getActualTypeArguments()[0];
                List<ExcelExportEntity> list = new ArrayList<>();
                getAllExcelField(PoiPublicTools.getClassFields(clz), list, clz, null);
                excelEntity = new ExcelExportEntity();
                if (i18nHandler != null) {
                    excelEntity.setName(i18nHandler.getLocaleName(excelEntity.getName()));
                }
                excelEntity.setMethod(PoiReflectorTools.fromCache(pojoClass).getGetMethod(field.getName()));
                excelEntity.setList(list);
                excelParams.add(excelEntity);
            } else {
                List<Method> newMethods = new ArrayList<Method>();
                if (getMethods != null) {
                    newMethods.addAll(getMethods);
                }
                newMethods.add(PoiReflectorTools.fromCache(pojoClass).getGetMethod(field.getName()));
                getAllExcelField(PoiPublicTools.getClassFields(field.getType()), excelParams, field.getType(),
                        newMethods);
            }
        }
    }

    /**
     * 获取填如这个 cell 的值,提供一些附加功能
     *
     * @param entity 导出对象
     * @param obj    当前对象
     * @return 单元格值
     */
    @SuppressWarnings("unchecked")
    public Object getCellValue(ExcelExportEntity entity, Object obj) {
        Object value;
        if (obj instanceof Map) {
            value = ((Map<?, ?>) obj).get(entity.getKey());
        } else {
            // 考虑直接用对象导出只能每次获取值的办法
            if (entity.getMethods() == null && entity.getMethod() == null) {
                value = PoiPublicTools.getParamsValue(entity.getKey().toString(), obj);
            } else {
                value = entity.getMethods() != null ? getFieldBySomeMethod(entity.getMethods(), obj, entity.getMethodsParams())
                        : getFieldByMethod(entity.getMethod(), obj, entity.getMethodParams());
            }
        }
        if (value instanceof ImageEntity) {
            entity.setType(BaseTypeConstants.IMAGE_TYPE);
            return value;
        }
        if (value instanceof SpecialSymbolsEntity) {
            entity.setType(BaseTypeConstants.SYMBOL_TYPE);
            return value;
        }
        if (StrUtil.isNotEmpty(entity.getFormat())) {
            value = dateFormatValue(value, entity);
        }
        if (entity.getReplace() != null && entity.getReplace().length > 0) {
            value = replaceValue(entity.getReplace(), String.valueOf(value));
        }
        if (StrUtil.isNotEmpty(entity.getNumFormat())) {
            value = numFormatValue(value, entity);
        }
        if (StrUtil.isNotEmpty(entity.getDict()) && dictHandler != null) {
            value = dictHandler.toName(entity.getDict(), obj, entity.getName(), value);
        }
        if (needHandlerList != null && needHandlerList.contains(entity.getName())) {
            value = dataHandler.exportHandler(obj, entity.getName(), value);
        }
        if (StrUtil.isNotEmpty(entity.getSuffix()) && value != null) {
            value = value + entity.getSuffix();
        }
        if (entity.getDesensitizationType() != null && value != null) {
            value = DesensitizedUtil.desensitized(value.toString(), entity.getDesensitizationType());
        }
        if (value != null && StrUtil.isNotEmpty(entity.getEnumExportField())) {
            value = PoiReflectorTools.fromCache(value.getClass()).getValue(value, entity.getEnumExportField());
        }
        return value == null ? "" : value.toString();
    }

    /**
     * 多个反射获取值
     *
     * @param list          get方法
     * @param t             当前对象
     * @param methodsParams 方法参数列表
     * @return 结果
     */
    public Object getFieldBySomeMethod(List<Method> list, Object t, List<List<Object>> methodsParams) {
        try {
            for (int i = 0; i < list.size(); i++) {
                if (t == null) {
                    t = "";
                    break;
                }
                Method m = list.get(i);
                if (m.getParameterCount() == 0) {
                    t = m.invoke(t);
                } else {
                    t = m.invoke(t, methodsParams.get(i).toArray());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Word 导出获取模板值失败", e);
        }
        return t;
    }

    /**
     * 按方法获取值
     *
     * @param method get 方法
     * @param t      当前对象
     * @param params 参数列表
     * @return 值
     */
    public Object getFieldByMethod(Method method, Object t, List<Object> params) {
        try {
            if (method.getParameterCount() == 0) {
                return method.invoke(t);
            }
            return method.invoke(t, params.toArray());
        } catch (Exception e) {
            throw new RuntimeException("Word 导出获取模板值失败", e);
        }
    }

    /**
     * 获取行高
     *
     * @param excelParams 导出参数列表
     * @return 行高
     */
    public short getRowHeight(List<ExcelExportEntity> excelParams) {
        double maxHeight = 0;
        for (ExcelExportEntity excelParam : excelParams) {
            maxHeight = Math.max(maxHeight, excelParam.getHeight());
            if (excelParam.getList() != null) {
                for (int j = 0; j < excelParam.getList().size(); j++) {
                    maxHeight = Math.max(maxHeight, excelParam.getList().get(j).getHeight());
                }
            }
        }
        return (short) (maxHeight * 50);
    }

    /**
     * 替换值
     *
     * @param replace 取代列表
     * @param value   值
     * @return 结果
     */
    private Object replaceValue(String[] replace, String value) {
        String[] temp;
        for (String str : replace) {
            temp = str.split("_");
            if (value.equals(temp[1])) {
                value = temp[0];
                break;
            }
        }
        return value;
    }

    /**
     * 对字段根据用户设置排序
     *
     * @param excelParams 导出参数列表
     */
    public void sortAllParams(List<ExcelExportEntity> excelParams) {
        // 自然排序,group 内部排序,集合内部排序
        // 把有groupName的统一收集起来,内部先排序
        Map<String, List<ExcelExportEntity>> groupMap = new HashMap<String, List<ExcelExportEntity>>();
        for (int i = excelParams.size() - 1; i > -1; i--) {
            // 集合内部排序
            if (excelParams.get(i).getList() != null) {
                Collections.sort(excelParams.get(i).getList());
            } else if (StrUtil.isNotBlank(excelParams.get(i).getGroupName())) {
                if (!groupMap.containsKey(excelParams.get(i).getGroupName())) {
                    groupMap.put(excelParams.get(i).getGroupName(), new ArrayList<>());
                }
                groupMap.get(excelParams.get(i).getGroupName()).add(excelParams.get(i));
                excelParams.remove(i);
            }
        }
        Collections.sort(excelParams);
        if (!groupMap.isEmpty()) {
            // group 内部排序
            for (Map.Entry<String, List<ExcelExportEntity>> stringListEntry : groupMap.entrySet()) {
                Collections.sort(stringListEntry.getValue());
                // 插入到 excelParams 当中
                boolean isInsert = false;
                String groupName = "START";
                for (int i = 0; i < excelParams.size(); i++) {
                    // 跳过 groupName 的元素,防止破会内部结构
                    if (excelParams.get(i).getOrderNum() > stringListEntry.getValue().getFirst().getOrderNum()
                            && !groupName.equals(excelParams.get(i).getGroupName())) {
                        if (StrUtil.isNotEmpty(excelParams.get(i).getGroupName())) {
                            groupName = excelParams.get(i).getGroupName();
                        }
                        excelParams.addAll(i, stringListEntry.getValue());
                        isInsert = true;
                        break;
                    } else if (!groupName.equals(excelParams.get(i).getGroupName()) &&
                            StrUtil.isNotEmpty(excelParams.get(i).getGroupName())) {
                        groupName = excelParams.get(i).getGroupName();
                    }
                }
                //如果都比他小就插入到最后
                if (!isInsert) {
                    excelParams.addAll(stringListEntry.getValue());
                }
            }
        }
    }

}
