package com.voffice.idea.plugin.file.mapperxml;


import com.intellij.psi.PsiDirectory;
import com.voffice.idea.plugin.file.TextFileCreate;
import com.voffice.idea.plugin.jdbc.TableInfo;
import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.file.JavaCodeElement;
import com.voffice.idea.plugin.file.field.FieldGenerator;
import com.voffice.idea.plugin.jdbc.ColumnInfo;
import com.voffice.idea.plugin.jdbc.JdbcTypeTranslater;
import org.apache.commons.lang.StringUtils;

public class MapperXmlFile implements JavaCodeElement,TextFileCreate {

    TableInfo tableInfo;

    FieldGenerator fieldGenerator;

    public MapperXmlFile(TableInfo tableInfo, FieldGenerator fieldGenerator) {
        this.tableInfo = tableInfo;
        this.fieldGenerator=fieldGenerator;
    }

    protected String  getBaseEntityName(){
        return  convertTableNameToClassName(tableInfo.getTableName());
    }

    @Override
    public String getFileContent() {
        String poClass=getBaseEntityName();
        String className = poClass+"Mapper";
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        stringBuffer.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
        stringBuffer.append("<mapper namespace=\""+DirectoryManager.getMapperPackage()+"."+className+"\">\n");
        stringBuffer.append("<resultMap id=\"resultMap\" type=\""+ DirectoryManager.getPoPackage()+"."+poClass+"\">\n");
        for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {

            if(StringUtils.equals("PRI", columnInfo.getColumnKey())){
                stringBuffer.append("<id ");
            }else{
                stringBuffer.append("<result ");
            }
            stringBuffer.append("column=\"").append(columnInfo.getColumnName()).append("\" jdbcType=\"")
                    .append(JdbcTypeTranslater.getJdbcTypeName(columnInfo.getJdbcType()))
                    .append("\" property=\"")
                    .append(getFieldName(columnInfo.getColumnName()))
                    .append("\" ");
            if(fieldGenerator.isEnum(columnInfo)){
                stringBuffer.append("typeHandler=\"com.maqv.mybatis.converter.CommonEnumTypeHandler\"");
            }
            stringBuffer.append("/>\n");
        }
        stringBuffer.append("</resultMap>\n");
        stringBuffer.append("</mapper>\n");
        return stringBuffer.toString();
    }

    @Override
    public String getFileName() {
        return convertTableNameToClassName(tableInfo.getTableName())+"Mapper.xml";
    }

    @Override
    public PsiDirectory getDirectory() {
        return DirectoryManager.getXmlMapperDirectory();
    }
}
