package com.reid.smart.agency.support;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class CommunityCorrectionDataGenerator {
    private static final Random random = new Random();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String[] COURTS = {"人民法院", "中级人民法院", "高级人民法院"};
    private static final String[] CORRECTION_TYPES = {"社区矫正", "缓刑", "假释", "暂予监外执行"};
    private static final String[] CRIME_CATEGORIES = {"盗窃", "诈骗", "故意伤害", "交通肇事", "毒品犯罪", "经济犯罪", "暴力犯罪"};
    private static final String[] RISK_LEVELS = {"低风险", "中风险", "高风险"};
    private static final String[] ETHNICITIES = {"汉族", "壮族", "回族", "满族", "维吾尔族", "苗族", "彝族", "傣族", "藏族", "蒙古族"};
    private static final String[] KEY_GROUP_TYPES = {"未成年人", "老年人", "残疾人", "精神障碍患者", "无业人员", ""};
    private static final String[] PROVINCES = {"11", "12", "13", "14", "15", "21", "22", "23",
            "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44",
            "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65"};
    private static final int[] WEIGHT = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private static final char[] VALIDATE_CODE = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    private static final String[] MOBILE_PREFIXES = {"130", "131", "132", "133", "134", "135", "136", "137", "138", "139",
            "150", "151", "152", "153", "155", "156", "157", "158", "159",
            "180", "181", "182", "183", "184", "185", "186", "187", "188", "189"};

    public static void main(String[] args) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("数据");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "原判法院", "矫正开始日期", "矫正结束日期", "矫正类型", "犯罪类别",
                "姓名", "性别", "身份证号码", "民族", "户籍", "现居住地", "联系电话", "风险等级", "重点群体类型"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 生成数据
        for (int i = 1; i <= 50000; i++) {
            Row row = sheet.createRow(i);

            // ID
            row.createCell(0).setCellValue(i);

            // 原判法院
            row.createCell(1).setCellValue(COURTS[random.nextInt(COURTS.length)]);

            // 矫正日期
            Date startDate = randomDate();
            Date endDate = new Date(startDate.getTime() + TimeUnit.DAYS.toMillis(random.nextInt(336) + 30));
            row.createCell(2).setCellValue(dateFormat.format(startDate));
            row.createCell(3).setCellValue(dateFormat.format(endDate));

            // 矫正类型
            row.createCell(4).setCellValue(CORRECTION_TYPES[random.nextInt(CORRECTION_TYPES.length)]);

            // 犯罪类别
            row.createCell(5).setCellValue(CRIME_CATEGORIES[random.nextInt(CRIME_CATEGORIES.length)]);

            // 姓名
            String name = generateChineseName();
            row.createCell(6).setCellValue(name);

            // 性别
            String gender = random.nextBoolean() ? "男" : "女";
            row.createCell(7).setCellValue(gender);

            // 身份证号码（严格符合规则）
            row.createCell(8).setCellValue(generateValidIDNumber(gender));

            // 民族
            row.createCell(9).setCellValue(ETHNICITIES[random.nextInt(ETHNICITIES.length)]);

            // 户籍
            String provinceCode = PROVINCES[random.nextInt(PROVINCES.length)];
            String provinceName = getProvinceName(provinceCode);
            row.createCell(10).setCellValue(provinceName + (random.nextBoolean() ? "市" : "县"));

            // 现居住地
            row.createCell(11).setCellValue(provinceName + "市" + (random.nextBoolean() ? "城区" : "乡镇") + "某地址");

            // 联系电话（严格符合手机号规则）
            row.createCell(12).setCellValue(generateValidMobileNumber());

            // 风险等级
            row.createCell(13).setCellValue(RISK_LEVELS[random.nextInt(RISK_LEVELS.length)]);

            // 重点群体类型
            row.createCell(14).setCellValue(KEY_GROUP_TYPES[random.nextInt(KEY_GROUP_TYPES.length)]);
        }

        // 写入文件
        try (FileOutputStream outputStream = new FileOutputStream("社区矫正对象_5万条.xlsx")) {
            workbook.write(outputStream);
            System.out.println("Excel文件已生成: 社区矫正对象_5万条.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Date randomDate() {
        long now = System.currentTimeMillis();
        long oneYearAgo = now - TimeUnit.DAYS.toMillis(365);
        return new Date(ThreadLocalRandom.current().nextLong(oneYearAgo, now));
    }

    private static String generateChineseName() {
        String[] surnames = {"王", "李", "张", "刘", "陈", "杨", "赵", "黄", "周", "吴"};
        String[] names = {"伟", "芳", "娜", "秀英", "敏", "静", "丽", "强", "磊", "军", "洋", "勇", "艳", "杰", "娟", "涛", "明", "超", "秀兰", "霞", "平", "刚", "桂英"};
        return surnames[random.nextInt(surnames.length)] + names[random.nextInt(names.length)];
    }

    /**
     * 生成符合规则的身份证号码
     */
    private static String generateValidIDNumber(String gender) {
        StringBuilder sb = new StringBuilder();

        // 1. 前6位 - 地区码（使用真实的行政区划代码）
        String provinceCode = PROVINCES[random.nextInt(PROVINCES.length)];
        sb.append(provinceCode);

        // 随机生成市、区县代码（简化处理，实际应使用真实代码）
        sb.append(String.format("%02d", random.nextInt(30) + 1))
                .append(String.format("%02d", random.nextInt(30) + 1));

        // 2. 出生日期（18-60岁之间）
        int year = 1960 + random.nextInt(43); // 1960-2003
        int month = 1 + random.nextInt(12);
        int day = 1 + random.nextInt(28); // 简化处理，不考虑闰月
        sb.append(String.format("%04d%02d%02d", year, month, day));

        // 3. 顺序码（3位）
        int sequenceCode = random.nextInt(999);
        if (gender.equals("男")) {
            sequenceCode = sequenceCode | 1; // 确保是奇数
        } else {
            sequenceCode = (sequenceCode & 0xFFFE); // 确保是偶数
        }
        sb.append(String.format("%03d", sequenceCode));

        // 4. 计算校验码
        String id17 = sb.toString();
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += (id17.charAt(i) - '0') * WEIGHT[i];
        }
        int mod = sum % 11;
        sb.append(VALIDATE_CODE[mod]);

        return sb.toString();
    }

    /**
     * 生成符合规则的手机号码
     */
    private static String generateValidMobileNumber() {
        // 1. 以1开头
        StringBuilder sb = new StringBuilder("1");

        // 2. 第二位：3-9
        int second = 3 + random.nextInt(7);
        sb.append(second);

        // 3. 第三位：根据第二位决定
        int third;
        if (second == 3) {
            third = random.nextInt(2) + 0; // 30-31
        } else if (second == 4) {
            third = random.nextInt(2) + 5; // 45-46
        } else if (second == 5) {
            third = random.nextInt(6) + 0; // 50-55
        } else if (second == 6) {
            third = random.nextInt(2) + 6; // 66-67
        } else if (second == 7) {
            third = random.nextInt(5) + 0; // 70-74
        } else if (second == 8) {
            third = random.nextInt(2) + 0; // 80-81
        } else { // 9
            third = random.nextInt(10); // 90-99
        }
        sb.append(third);

        // 4. 后8位随机
        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    private static String getProvinceName(String code) {
        switch (code) {
            case "11": return "北京";
            case "12": return "天津";
            case "13": return "河北";
            case "14": return "山西";
            case "15": return "内蒙古";
            case "21": return "辽宁";
            case "22": return "吉林";
            case "23": return "黑龙江";
            case "31": return "上海";
            case "32": return "江苏";
            case "33": return "浙江";
            case "34": return "安徽";
            case "35": return "福建";
            case "36": return "江西";
            case "37": return "山东";
            case "41": return "河南";
            case "42": return "湖北";
            case "43": return "湖南";
            case "44": return "广东";
            case "45": return "广西";
            case "46": return "海南";
            case "50": return "重庆";
            case "51": return "四川";
            case "52": return "贵州";
            case "53": return "云南";
            case "54": return "西藏";
            case "61": return "陕西";
            case "62": return "甘肃";
            case "63": return "青海";
            case "64": return "宁夏";
            case "65": return "新疆";
            default: return "未知";
        }
    }
}
