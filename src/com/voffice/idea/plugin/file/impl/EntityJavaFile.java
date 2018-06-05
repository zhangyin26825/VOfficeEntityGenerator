package com.voffice.idea.plugin.file.impl;

import com.intellij.psi.PsiDirectory;
import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.file.field.FieldGenerator;
import com.voffice.idea.plugin.jdbc.ColumnInfo;
import com.voffice.idea.plugin.jdbc.TableInfo;
import com.voffice.idea.plugin.file.MysqlJavaFileCreate;
import org.apache.commons.lang.StringUtils;

public class EntityJavaFile extends MysqlJavaFileCreate {

    FieldGenerator fieldGenerator;

    public EntityJavaFile(TableInfo tableInfo, FieldGenerator fieldGenerator){
        super(tableInfo);
        this.fieldGenerator=fieldGenerator;
        addLombokImport();
        addMaqvMysqlImport();
        addEnumClass();
    }

//    @Override
//    public PsiDirectory getDirectory() {
//        return DirectoryManager.getPsiPoDirectory();
//    }

    public void addEnumClass(){
        for (ColumnInfo columnInfo : columnInfos) {
            if(fieldGenerator.isEnum(columnInfo)){
               addImport(DirectoryManager.getTypePackage()+"."+StringUtils.capitalize(getFieldName(columnInfo.getColumnName())));
            }
        }
    }

    @Override
    public String getClassName() {
        return getBaseEntityName();
    }

    @Override
    public String getPackageName() {
        return DirectoryManager.getPoPackage();
    }

    @Override
    public String classCommentCode() {
        StringBuffer stringBuffer=new StringBuffer("Entity for "+tableName);
        if(StringUtils.isNotEmpty(tableComment)){
            stringBuffer.append("("+tableComment+")");
        }
        return comment(stringBuffer.toString(),"");
    }

    @Override
    public String classAnnotation() {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("@Setter"+NEWLINE);
        stringBuffer.append("@Getter"+NEWLINE);
        stringBuffer.append("@Table(name = \""+tableName+"\", alias = \""+getTableAlias()+"\")"+NEWLINE);
        return  stringBuffer.toString();
    }

    @Override
    public String classIdentification() {
         return PUBLIC+CLASS+getClassName();
    }

    @Override
    public String classbody() {
        StringBuffer stringBuffer = new StringBuffer();
        for (ColumnInfo columnInfo : columnInfos) {
            stringBuffer.append(fieldGenerator.generatorFieldDesc(columnInfo));
        }
        return stringBuffer.toString();
    }
}
