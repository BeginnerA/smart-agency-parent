package com.reid.smart.agency.support.component.word;

import cn.hutool.core.collection.CollUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tt.smart.agency.tools.WordExportTools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * Word 模板导出测试
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class DaoChuWordTest {

    private static final String basePath = "D:\\work\\IdeaProjects\\Reid\\reid-smart-agency\\smart-agency-test\\src\\main\\resources\\templates\\";

    /**
     * 开始
     */
    @BeforeAll
    public static void beforeClass() {
        System.out.println("\n\n------------------------ 基础测试 star ...");
    }

    /**
     * 结束
     */
    @AfterAll
    public static void afterClass() {
        System.out.println("\n\n------------------------ 基础测试 end ... \n");
    }

    /**
     * 导出简单的 Word 模板
     */
    @Test
    public void exportSimpleWord() throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curTime = format.format(new Date());

        Map<String, Object> data = new HashMap<String, Object>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("type", "个人所得税");
        map1.put("presum", "1580");
        map1.put("thissum", "1750");
        map1.put("curmonth", "1-11月");
        map1.put("now", curTime);
        mapList.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("type", "增值税");
        map2.put("presum", "1080");
        map2.put("thissum", "1650");
        map2.put("curmonth", "1-11月");
        map2.put("now", curTime);
        mapList.add(map2);
        data.put("taxlist", mapList);
        data.put("totalpreyear", "2660");
        data.put("totalthisyear", "3400");

        data.put("type", "增值税");
        data.put("presum", "1080");
        data.put("thissum", "1650");
        data.put("curmonth", "1-11月");
        data.put("now", curTime);

        // 单列
        XWPFDocument document = WordExportTools.exportWord07(basePath + "simple.docx", data);
        File file = new File(basePath + "simple_new.docx");
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fos = new FileOutputStream(basePath + "simple_new.docx");
        document.write(fos);
        fos.close();
    }


    /**
     * 导出简单的 Word 模板
     */
    @Test
    public void exportComplexWord() throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("period", "2024年06月");
        data.put("serviceName", "xx年度消防设施维护保养项目");
        data.put("entrustedDeptIdDictLabel", "××有限责任公司");
        data.put("serviceManager", "张三：18888888888");
        data.put("serviceDeptIdDictLabel", "××有限责任公司");
        data.put("serviceLeader", "李四：17777777777");
        data.put("servicePeriod", "2024年06月至2028年06月");
        data.put("construtRange", "全部");
        data.put("currentDate", "2024年06月21日");

        List<Map<String, Object>> firstStandards = CollUtil.newArrayList();
        Map<String, Object> firstStandard = new HashMap<>();
        firstStandard.put("standardName", "火灾报警（联动）控制器");
        firstStandard.put("runStatus", "异常");
        firstStandard.put("standardName2", "火灾自动报警系统");
        firstStandard.put("runStatus2", "正常");
        firstStandards.add(firstStandard);
        firstStandards.add(firstStandard);
        data.put("firstStandards", firstStandards);

        List<Map<String, Object>> checkFaults = CollUtil.newArrayList();
        Map<String, Object> checkFault = new HashMap<>();
        checkFault.put("checkObject", "火灾报警（联动）控制器");
        checkFault.put("checkPart", "建筑1：地下一层/测试工位gst500");
        checkFault.put("checkProblem", "控制器有遮挡物影响操作、维修（测量值：系统静压20MPa；系统动压15MPa）。控制器显示异常信息。控制器没有接地装置。");
        checkFault.put("dealSituation", "清理杂物，保证控制器正常运行环境。对照异常信息排查并处理故障，恢复控制器正常监视状态。按照设计规范增设控制器接地装置。");
        checkFaults.add(checkFault);
        checkFaults.add(checkFault);
        data.put("checkFaults", checkFaults);

        List<Map<String, Object>> certificates = CollUtil.newArrayList();
        certificates.add(new HashMap<>() {{
            put("certificateName", "系统架构设计师");
            put("certificateNo", "编号：1010110");
        }});
        certificates.add(new HashMap<>() {{
            put("certificateName", "信息系统项目管理师");
            put("certificateNo", "编号：1110111");
        }});
        List<Map<String, Object>> signPersons = CollUtil.newArrayList();
        signPersons.add(new HashMap<>() {{
            put("nickName", "姓名1");
            put("sign", "签名");
            put("certificates", certificates);
            put("roles", List.of("角色1", "角色2"));
        }});
        signPersons.add(new HashMap<>() {{
            put("nickName", "姓名2");
            put("sign", "签名");
            put("certificates", certificates);
            put("roles", List.of("角色1", "角色2"));
        }});

        data.put("signPersons", signPersons);
        data.put("checkFiles", List.of());
        data.put("rectifyFiles", List.of());

        // 单列
        XWPFDocument document = WordExportTools.exportWord07(basePath + "complex.docx", data);
        File file = new File(basePath + "complex_new.docx");
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fos = new FileOutputStream(basePath + "complex_new.docx");
        document.write(fos);
        fos.close();
    }
}
