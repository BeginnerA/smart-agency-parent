package tt.smart.agency.component.word.tools;

import cn.hutool.core.bean.BeanUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.RoundingMode;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;

/**
 * <p>
 * EL 表达式支持工具类
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@SuppressWarnings("rawtypes")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PoiElTools {

    public static final String LENGTH = "le:";
    public static final String FOREACH = "fe:";
    public static final String FOREACH_NOT_CREATE = "!fe:";
    public static final String FOREACH_AND_SHIFT = "$fe:";
    public static final String FOREACH_COL = "#fe:";
    public static final String FOREACH_COL_VALUE = "v_fe:";
    public static final String START_STR = "{{";
    public static final String END_STR = "}}";
    public static final String START_WRAP = "[";
    public static final String END_WRAP = "]";
    public static final String NUMBER_SYMBOL = "n:";
    public static final String MERGE = "merge:";
    public static final String STYLE_SELF = "sy:";
    public static final String FORMAT_DATE = "fd:";
    public static final String FORMAT_NUMBER = "fn:";
    public static final String SUM = "sum:";
    public static final String IF_DELETE = "!if:";
    public static final String EMPTY = "";
    public static final String BLANK_SPACE = " ";
    public static final String CONST = "'";
    public static final String NULL = "&NULL&";
    public static final String INDEX = "&INDEX&";
    public static final String LEFT_BRACKET = "(";
    public static final String RIGHT_BRACKET = ")";
    public static final String CAL = "cal:";
    public static final String DICT_HANDLER = "dict:";
    public static final String I18N_HANDLER = "i18n:";
    public static final String DESENSITIZATION_RULE = "deru:";

    /**
     * 解析字符串，支持 le、fd、fn、!if、三目
     *
     * @param text 模板
     * @param map  数据
     * @return 值
     */
    public static Object eval(String text, Map<String, Object> map) {
        Object eval = innerEval(text, map);
        return text.equals(eval) ? EMPTY : eval;
    }

    /**
     * 解析字符串，支持 le、fd、fn、!if、三目
     *
     * @param text 模板
     * @param data 参数
     * @return 值
     */
    public static Object eval(String text, Object data) {
        Map<String, Object> map = BeanUtil.beanToMap(data);
        return eval(text, map);
    }

    /**
     * 解析字符串，支持 le、fd、fn、!if、三目。找不到返回原值
     *
     * @param text 模板
     * @param map  数据
     * @return 值
     */
    public static Object evalNoParse(String text, Map<String, Object> map) {
        Object obj = eval(text, map);
        if (EMPTY.equals(obj.toString())) {
            obj = innerEval(text, map);
        }
        return obj;
    }

    /**
     * 解析字符串，支持 le、fd、fn、!if、三目
     *
     * @param text 模板
     * @param map  数据
     * @return 值
     */
    public static Object innerEval(String text, Map<String, Object> map) {
        try {
            if (text.contains("?") && text.contains(":")) {
                return trinocular(text, map);
            }
            if (text.contains(LENGTH)) {
                return length(text, map);
            }
            if (text.contains(FORMAT_DATE)) {
                return formatDate(text, map);
            }
            if (text.contains(FORMAT_NUMBER)) {
                return formatNumber(text, map);
            }
            if (text.contains(IF_DELETE)) {
                return ifDelete(text, map);
            }
            if (text.contains(CAL)) {
                return calculate(text, map);
            }
            if (text.contains(".")) {
                String newText = text.split("\\.")[1];
                if (map.containsKey(newText)) {
                    return PoiPublicTools.getParamsValue(newText, map);
                } else {
                    return EMPTY;
                }
            }
            if (text.startsWith("'")) {
                return text.replace("'", EMPTY);
            }
            if (map.containsKey(text)) {
                return PoiPublicTools.getParamsValue(text, map);
            }
        } catch (Exception e) {
            throw new RuntimeException("Word 模板解析失败，检查配置模板是否符合 EL 表达式", e);
        }

        return text;
    }

    /**
     * 根据数据表达式计算结果
     *
     * @param text 模板
     * @param map  数据
     * @return 值
     */
    private static Object calculate(String text, Map<String, Object> map) {
        // 所有的数据都改成真是值
        // 支持 + - * / () 所以按照这些字段切割
        text = text.replace(CAL, EMPTY);
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        char[] chars = text.toCharArray();
        char[] operations = new char[]{'+', '-', '*', '/', '(', ')', ' '};
        for (char aChar : chars) {
            if (operations[0] == aChar || operations[1] == aChar || operations[2] == aChar || operations[3] == aChar || operations[4] == aChar || operations[5] == aChar) {
                if (!temp.isEmpty()) {
                    sb.append(evalNoParse(temp.toString().trim(), map).toString());
                    temp = new StringBuilder();
                }
                sb.append(aChar);
            } else {
                temp.append(aChar);
            }
        }
        if (!temp.isEmpty()) {
            sb.append(evalNoParse(temp.toString().trim(), map).toString());
        }
        return Calculator.conversion(sb.toString());
    }

    /**
     * 是不是删除列
     *
     * @param text 模板
     * @param map  数据
     * @return 值
     */
    private static Object ifDelete(String text, Map<String, Object> map) {
        // 把多个空格变成一个空格
        text = text.replaceAll("\\s+", BLANK_SPACE).trim();
        String[] keys = getKey(IF_DELETE, text).split(BLANK_SPACE);
        text = text.replace(IF_DELETE, EMPTY);
        return isTrue(keys, map);
    }

    /**
     * 是不是真
     *
     * @param keys key
     * @param map  数据
     * @return 结果
     */
    private static Boolean isTrue(String[] keys, Map<String, Object> map) {
        if (keys.length == 1) {
            String constant = null;
            if ((constant = isConstant(keys[0])) != null) {
                return Boolean.valueOf(constant);
            }
            return Boolean.valueOf(eval(keys[0], map).toString());
        }
        if (keys.length == 3) {
            Object first = evalNoParse(keys[0], map);
            Object second = evalNoParse(keys[2], map);
            return PoiFunctionTools.isTrue(first, keys[1], second);
        }
        throw new RuntimeException("Word 导出判断参数不对");
    }

    /**
     * 判断是不是常量
     *
     * @param param 参数
     * @return 值
     */
    private static String isConstant(String param) {
        if (param.contains("'")) {
            return param.replace("'", EMPTY);
        }
        return null;
    }

    /**
     * 格式化数字
     *
     * @param text 模板
     * @param map  数据
     * @return 值
     */
    private static Object formatNumber(String text, Map<String, Object> map) {
        String[] key = getKey(FORMAT_NUMBER, text).split(";");
        text = text.replace(FORMAT_NUMBER, EMPTY);
        return innerEval(replacinnerValue(text, PoiFunctionTools.formatNumber(eval(key[0], map), key[1])), map);
    }

    /**
     * 格式化时间
     *
     * @param text 模板
     * @param map  数据
     * @return 值
     */
    private static Object formatDate(String text, Map<String, Object> map) {
        String[] key = getKey(FORMAT_DATE, text).split(";");
        text = text.replace(FORMAT_DATE, EMPTY);
        return innerEval(replacinnerValue(text, PoiFunctionTools.formatDate(eval(key[0], map), key[1])), map);
    }

    /**
     * 计算这个的长度
     *
     * @param text 模板
     * @param map  数据
     * @return 值
     */
    private static Object length(String text, Map<String, Object> map) {
        String key = getKey(LENGTH, text);
        text = text.replace(LENGTH, EMPTY);
        Object val = eval(key, map);
        return innerEval(replacinnerValue(text, PoiFunctionTools.length(val)), map);
    }

    /**
     * 替换值
     *
     * @param text 模板
     * @param val  对象长度
     * @return 值
     */
    private static String replacinnerValue(String text, Object val) {
        String sb = text.substring(0, text.indexOf(LEFT_BRACKET)) +
                BLANK_SPACE +
                val +
                BLANK_SPACE +
                text.substring(text.indexOf(RIGHT_BRACKET) + 1);
        return sb.trim();
    }

    /**
     * 获取 key
     *
     * @param prefix 前缀
     * @param text   参数
     * @return key
     */
    private static String getKey(String prefix, String text) {
        int leftBracket = 1, rigthBracket = 0, position = 0;
        int index = text.indexOf(prefix) + prefix.length();
        while (text.charAt(index) == ' ') {
            text = text.substring(0, index) + text.substring(index + 1);
        }
        for (int i = text.indexOf(prefix + LEFT_BRACKET) + prefix.length() + 1; i < text
                .length(); i++) {
            if (text.charAt(i) == LEFT_BRACKET.charAt(0)) {
                leftBracket++;
            }
            if (text.charAt(i) == RIGHT_BRACKET.charAt(0)) {
                rigthBracket++;
            }
            if (leftBracket == rigthBracket) {
                position = i;
                break;
            }
        }
        return text.substring(text.indexOf(prefix + LEFT_BRACKET) + 1 + prefix.length(), position)
                .trim();
    }

    /**
     * 三目运算
     *
     * @param text 模板
     * @param map  数据
     * @return 值
     */
    private static Object trinocular(String text, Map<String, Object> map) {
        // 把多个空格变成一个空格
        text = text.replaceAll("\\s+", BLANK_SPACE).trim();
        String testText = text.substring(0, text.indexOf("?"));
        text = text.substring(text.indexOf("?") + 1).trim();
        text = innerEval(text, map).toString();
        String[] keys = text.split(":");
        Object first = null, second = null;
        if (keys.length > 2) {
            if (keys[0].trim().contains("?")) {
                StringBuilder trinocular = new StringBuilder(keys[0]);
                for (int i = 1; i < keys.length - 1; i++) {
                    trinocular.append(":").append(keys[i]);
                }
                first = evalNoParse(trinocular.toString(), map);
                second = evalNoParse(keys[keys.length - 1].trim(), map);
            } else {
                first = evalNoParse(keys[0].trim(), map);
                String trinocular = keys[1];
                for (int i = 2; i < keys.length; i++) {
                    trinocular += ":" + keys[i];
                }
                second = evalNoParse(trinocular, map);
            }
        } else {
            first = evalNoParse(keys[0].trim(), map);
            second = evalNoParse(keys[1].trim(), map);
        }
        return isTrue(testText.split(BLANK_SPACE), map) ? first : second;
    }


    /**
     * 算数表达式求值
     * 直接调用Calculator的类方法conversion()
     * 传入算数表达式，将返回一个浮点值结果
     * 如果计算过程错误，将返回一个NaN
     */
    private static class Calculator {
        // 后缀式栈
        private final Stack<String> postfixStack = new Stack<String>();
        // 运算符栈
        private final Stack<Character> opStack = new Stack<Character>();
        // 运用运算符ASCII码-40做索引的运算符优先级
        private final int[] operatPriority = new int[]{0, 3, 2, 1, -1, 1, 0, 2};

        public static Object conversion(String expression) {
            double result = 0;
            Calculator cal = new Calculator();
            try {
                expression = transform(expression);
                result = cal.calculate(expression);
            } catch (Exception e) {
                // 运算错误返回 NaN
                return "NaN";
            }
            return result;
        }

        /**
         * 将表达式中负数的符号更改
         *
         * @param expression 例如-2+-1*(-3E-2)-(-1) 被转为 ~2+~1*(~3E~2)-(~1)
         * @return 结果
         */
        private static String transform(String expression) {
            char[] arr = expression.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == '-') {
                    if (i == 0) {
                        arr[i] = '~';
                    } else {
                        char c = arr[i - 1];
                        if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == 'E' || c == 'e') {
                            arr[i] = '~';
                        }
                    }
                }
            }
            if (arr[0] == '~' || arr[1] == '(') {
                arr[0] = '-';
                return "0" + new String(arr);
            } else {
                return new String(arr);
            }
        }

        /**
         * 按照给定的表达式计算
         *
         * @param expression 要计算的表达式例如：5+12*(3+5)/7
         * @return 结果
         */
        public double calculate(String expression) {
            Stack<String> resultStack = new Stack<String>();
            prepare(expression);
            Collections.reverse(postfixStack);
            String firstValue, secondValue, currentValue;
            while (!postfixStack.isEmpty()) {
                currentValue = postfixStack.pop();
                if (!isOperator(currentValue.charAt(0))) {
                    currentValue = currentValue.replace("~", "-");
                    resultStack.push(currentValue);
                } else {
                    // 如果是运算符则从操作数栈中取两个值和该数值一起参与运算
                    secondValue = resultStack.pop();
                    firstValue = resultStack.pop();

                    // 将负数标记符改为负号
                    firstValue = firstValue.replace("~", "-");
                    secondValue = secondValue.replace("~", "-");

                    String tempResult = calculate(firstValue, secondValue, currentValue.charAt(0));
                    resultStack.push(tempResult);
                }
            }
            return Double.parseDouble(resultStack.pop());
        }

        /**
         * 数据准备阶段将表达式转换成为后缀式栈
         *
         * @param expression 要计算的表达式例如：5+12*(3+5)/7
         */
        private void prepare(String expression) {
            // 运算符放入栈底元素逗号，此符号优先级最低
            opStack.push(',');
            char[] arr = expression.toCharArray();
            // 当前字符的位置
            int currentIndex = 0;
            // 上次算术运算符到本次算术运算符的字符的长度便于或者之间的数值
            int count = 0;
            // 当前操作符和栈顶操作符
            char currentOp, peekOp;
            for (int i = 0; i < arr.length; i++) {
                currentOp = arr[i];
                // 如果当前字符是运算符
                if (isOperator(currentOp)) {
                    if (count > 0) {
                        // 取两个运算符之间的数字
                        postfixStack.push(new String(arr, currentIndex, count));
                    }
                    peekOp = opStack.peek();
                    // 遇到反括号则将运算符栈中的元素移除到后缀式栈中直到遇到左括号
                    if (currentOp == ')') {
                        while (opStack.peek() != '(') {
                            postfixStack.push(String.valueOf(opStack.pop()));
                        }
                        opStack.pop();
                    } else {
                        while (currentOp != '(' && peekOp != ',' && compare(currentOp, peekOp)) {
                            postfixStack.push(String.valueOf(opStack.pop()));
                            peekOp = opStack.peek();
                        }
                        opStack.push(currentOp);
                    }
                    count = 0;
                    currentIndex = i + 1;
                } else {
                    count++;
                }
            }
            // 最后一个字符不是括号或者其他运算符的则加入后缀式栈中
            if (count > 1 || (count == 1 && !isOperator(arr[currentIndex]))) {
                postfixStack.push(new String(arr, currentIndex, count));
            }

            while (opStack.peek() != ',') {
                // 将操作符栈中的剩余的元素添加到后缀式栈中
                postfixStack.push(String.valueOf(opStack.pop()));
            }
        }

        /**
         * 判断是否为算术符号
         *
         * @param c 算术符号
         * @return 是否为算术符号
         */
        private boolean isOperator(char c) {
            return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')';
        }

        /**
         * 利用 ASCII 码-40做下标去算术符号优先级
         *
         * @param cur  当前操作符
         * @param peek 栈顶操作符
         * @return 结果
         */
        public boolean compare(char cur, char peek) {
            // 如果是 peek 优先级高于 cur，返回 true，默认都是 peek 优先级要低
            return operatPriority[(peek) - 40] >= operatPriority[(cur) - 40];
        }

        /**
         * 按照给定的算术运算符做计算
         *
         * @param firstValue  第一个值
         * @param secondValue 第二个值
         * @param currentOp   当前操作符
         * @return 结果
         */
        private String calculate(String firstValue, String secondValue, char currentOp) {
            return switch (currentOp) {
                case '+' -> String.valueOf(ArithHelper.add(firstValue, secondValue));
                case '-' -> String.valueOf(ArithHelper.sub(firstValue, secondValue));
                case '*' -> String.valueOf(ArithHelper.mul(firstValue, secondValue));
                case '/' -> String.valueOf(ArithHelper.div(firstValue, secondValue));
                default -> EMPTY;
            };
        }
    }


    private static class ArithHelper {

        // 默认除法运算精度
        private static final int DEF_DIV_SCALE = 16;

        // 这个类不能实例化
        private ArithHelper() {
        }

        /**
         * 提供精确的加法运算。
         *
         * @param v1 被加数
         * @param v2 加数
         * @return 两个参数的和
         */

        public static double add(double v1, double v2) {
            java.math.BigDecimal b1 = new java.math.BigDecimal(Double.toString(v1));
            java.math.BigDecimal b2 = new java.math.BigDecimal(Double.toString(v2));
            return b1.add(b2).doubleValue();
        }

        public static double add(String v1, String v2) {
            java.math.BigDecimal b1 = new java.math.BigDecimal(v1);
            java.math.BigDecimal b2 = new java.math.BigDecimal(v2);
            return b1.add(b2).doubleValue();
        }

        /**
         * 提供精确的减法运算。
         *
         * @param v1 被减数
         * @param v2 减数
         * @return 两个参数的差
         */

        public static double sub(double v1, double v2) {
            java.math.BigDecimal b1 = new java.math.BigDecimal(Double.toString(v1));
            java.math.BigDecimal b2 = new java.math.BigDecimal(Double.toString(v2));
            return b1.subtract(b2).doubleValue();
        }

        public static double sub(String v1, String v2) {
            java.math.BigDecimal b1 = new java.math.BigDecimal(v1);
            java.math.BigDecimal b2 = new java.math.BigDecimal(v2);
            return b1.subtract(b2).doubleValue();
        }

        /**
         * 提供精确的乘法运算。
         *
         * @param v1 被乘数
         * @param v2 乘数
         * @return 两个参数的积
         */

        public static double mul(double v1, double v2) {
            java.math.BigDecimal b1 = new java.math.BigDecimal(Double.toString(v1));
            java.math.BigDecimal b2 = new java.math.BigDecimal(Double.toString(v2));
            return b1.multiply(b2).doubleValue();
        }

        public static double mul(String v1, String v2) {
            java.math.BigDecimal b1 = new java.math.BigDecimal(v1);
            java.math.BigDecimal b2 = new java.math.BigDecimal(v2);
            return b1.multiply(b2).doubleValue();
        }

        /**
         * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
         *
         * @param v1 被除数
         * @param v2 除数
         * @return 两个参数的商
         */
        public static double div(double v1, double v2) {
            return div(v1, v2, DEF_DIV_SCALE);
        }

        /**
         * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
         *
         * @param v1 被除数
         * @param v2 除数
         * @return 两个参数的商
         */
        public static double div(String v1, String v2) {
            java.math.BigDecimal b1 = new java.math.BigDecimal(v1);
            java.math.BigDecimal b2 = new java.math.BigDecimal(v2);
            return b1.divide(b2, DEF_DIV_SCALE, RoundingMode.HALF_UP).doubleValue();
        }

        /**
         * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
         *
         * @param v1    被除数
         * @param v2    除数
         * @param scale 表示表示需要精确到小数点以后几位。
         * @return 两个参数的商
         */

        public static double div(double v1, double v2, int scale) {
            if (scale < 0) {
                throw new IllegalArgumentException("The   scale   must   be   a   positive   integer   or   zero");
            }
            java.math.BigDecimal b1 = new java.math.BigDecimal(Double.toString(v1));
            java.math.BigDecimal b2 = new java.math.BigDecimal(Double.toString(v2));
            return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
        }

        /**
         * 提供精确的小数位四舍五入处理。
         *
         * @param v     需要四舍五入的数字
         * @param scale 小数点后保留几位
         * @return 四舍五入后的结果
         */
        public static double round(double v, int scale) {
            if (scale < 0) {
                throw new IllegalArgumentException("The   scale   must   be   a   positive   integer   or   zero");
            }
            java.math.BigDecimal b = new java.math.BigDecimal(Double.toString(v));
            java.math.BigDecimal one = new java.math.BigDecimal("1");
            return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
        }

        /**
         * 提供精确的小数位四舍五入处理。
         *
         * @param v     需要四舍五入的数字
         * @param scale 小数点后保留几位
         * @return 四舍五入后的结果
         */
        public static double round(String v, int scale) {
            if (scale < 0) {
                throw new IllegalArgumentException("The   scale   must   be   a   positive   integer   or   zero");
            }
            java.math.BigDecimal b = new java.math.BigDecimal(v);
            java.math.BigDecimal one = new java.math.BigDecimal("1");
            return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
        }
    }
}
