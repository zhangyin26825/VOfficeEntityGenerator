package com.voffice.idea.plugin.file;

import org.apache.commons.lang.StringUtils;

/**
 * 构造Java源代码一些公用的元素
 */
public interface JavaCodeElement {

    String NEWLINE = "\n";

    String LEFT_BRACES = "{";

    String RIGHT_BRACES = "}";

    String PRIVATE = "private ";

    String PUBLIC = "public ";

    String SEMICOLON=";";

    String PACKAGE="package ";

    String IMPORT="import ";

    String CLASS="class ";

    String ENUM="enum ";

    String INTERFACE="interface ";

    String EXTENDS=" extends ";

    String IMPLEMENTS=" implements ";


    /**
     * 注释
     *
     * @param comments
     * @return
     */
    default String comment(String... comments) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("/**" + NEWLINE);
        for (String comment : comments) {
            stringBuffer.append("*" + comment + NEWLINE);
        }
        stringBuffer.append("*/" + NEWLINE);
        return stringBuffer.toString();
    }

    //去掉连字符后的字母大写
    default String removeUnderline(String old) {
        StringBuffer stringBuffer = new StringBuffer();
        char[] chars = old.toCharArray();
        boolean isHyphen = false;
        for (char aChar : chars) {
            if (aChar == '_') {
                isHyphen = true;
                continue;
            }
            if (isHyphen) {
                stringBuffer.append(Character.toUpperCase(aChar));
                isHyphen = false;
            } else {
                stringBuffer.append(aChar);
            }
        }
        return stringBuffer.toString();
    }

    default String convertTableNameToClassName(String tableName){
        String result = tableName;
        if (tableName.startsWith("sz_")) {
            result = tableName.replaceFirst("sz_", "");
        }
        if (tableName.startsWith("xm_")) {
            result = tableName.replaceFirst("xm_", "");
        }
        String s = removeUnderline(result);
        return StringUtils.capitalize(s);
    }

    default  String getFieldName(String columnName){
        return removeUnderline(columnName);
    }

    default String getAlias(String tableName) {
        StringBuffer sb=new StringBuffer();
        String[] split = tableName.split("_");
        for (String s : split) {
            sb.append(s.charAt(0));
        }
        return sb.toString();
    }


}
