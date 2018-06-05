package com.voffice.idea.plugin.file.impl;

import com.intellij.psi.PsiDirectory;
import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.jdbc.TableInfo;
import com.voffice.idea.plugin.file.MysqlJavaFileCreate;

public class DaoJavaFile extends MysqlJavaFileCreate {

    public DaoJavaFile(TableInfo tableInfo) {
        super(tableInfo);
        addBaseEntityImport();
        addDaoImport();
    }
//    @Override
//    public PsiDirectory getDirectory() {
//        return DirectoryManager.getPsiDaoDirectory();
//    }
    @Override
    public String getClassName() {
        return getBaseEntityName()+"Dao";
    }

    @Override
    public String getPackageName() {
        return DirectoryManager.getDaoPackage();
    }

    @Override
    public String classCommentCode() {
        return comment(" Dao for "+tableName,"",AUTHOR);
    }

    @Override
    public String classAnnotation() {
        return "";
    }

    @Override
    public String classIdentification() {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(PUBLIC+INTERFACE+getClassName()+EXTENDS);
        if(isMultiId()){
            stringBuffer.append("MultipleIdDao");
        }else if(isNoId()){
            stringBuffer.append("NoIdDao");
        }else{
            stringBuffer.append("SingleIdDao");
        }
        stringBuffer.append(getBaseEntityGeneric());
        return stringBuffer.toString();
    }

    @Override
    public String classbody() {
        return "";
    }
}