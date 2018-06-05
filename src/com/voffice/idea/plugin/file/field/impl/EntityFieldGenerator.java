package com.voffice.idea.plugin.file.field.impl;

import com.voffice.idea.plugin.file.JavaCodeElement;
import com.voffice.idea.plugin.file.field.FieldGenerator;
import com.voffice.idea.plugin.jdbc.ColumnInfo;
import com.voffice.idea.plugin.jdbc.JdbcTypeTranslater;
import com.voffice.idea.plugin.mysqlconfig.MySqlPersistent;
import org.apache.commons.lang.StringUtils;

public class EntityFieldGenerator implements FieldGenerator {


    @Override
    public boolean isEnum(ColumnInfo columnInfo) {
        String enumSuffix = MySqlPersistent.getMySqlConfig().getEnumSuffix();
        if(StringUtils.isEmpty(enumSuffix)){
            return false;
        }
        String[] split = enumSuffix.split(",");
        for (String s : split) {
            if(StringUtils.endsWith(columnInfo.getColumnName(), s)){
                return  true;
            }
        }
        return false;
    }

    @Override
    public String generatorFieldDesc(ColumnInfo columnInfo) {
        String comment = comment(columnInfo.getColumnComment(), columnInfo.getColumnType());
        StringBuffer sb=new StringBuffer(comment);
        if(StringUtils.equals("PRI", columnInfo.getColumnKey())){
            sb.append("@Id(");
        }else{
            sb.append("@Column(");
        }
        sb.append("name=\""+columnInfo.getColumnName()+"\"");
        if(StringUtils.equals("YES", columnInfo.getIsNullable())){
            sb.append(", nullable = true");
        }
        if(StringUtils.equals("auto_increment", columnInfo.getExtra())){
            sb.append(", autoIncrease = true");
        }
        sb.append(")"+ JavaCodeElement.NEWLINE);
        sb.append(JavaCodeElement.PRIVATE);
        if(isEnum(columnInfo)){
            sb.append(StringUtils.capitalize(getFieldName(columnInfo.getColumnName())))
                    .append(" ")
                    .append(getFieldName(columnInfo.getColumnName())).append(JavaCodeElement.SEMICOLON+ JavaCodeElement.NEWLINE);
        }else{
            sb.append(JdbcTypeTranslater.getJavaClassName(columnInfo.getJdbcType()))
                    .append(" ")
                    .append(getFieldName(columnInfo.getColumnName())).append(JavaCodeElement.SEMICOLON+ JavaCodeElement.NEWLINE);
        }
        return sb.toString();
    }
}
