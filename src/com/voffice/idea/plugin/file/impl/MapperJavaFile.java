package com.voffice.idea.plugin.file.impl;

import com.intellij.psi.PsiDirectory;
import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.jdbc.TableInfo;
import com.voffice.idea.plugin.file.MysqlJavaFileCreate;

public class MapperJavaFile extends MysqlJavaFileCreate {

    public MapperJavaFile(TableInfo tableInfo){
        super(tableInfo);
        addMapperImport();
        addBaseEntityImport();
    }

//    @Override
//    public PsiDirectory getDirectory() {
//        return DirectoryManager.getPsiMapperDirectory();
//    }

    @Override
    public String getClassName() {
        return getBaseEntityName()+"Mapper";
    }

    @Override
    public String getPackageName() {
        return DirectoryManager.getMapperPackage();
    }

    @Override
    public String classCommentCode() {
        return comment(" Mapper for "+tableName," ",AUTHOR);
    }

    @Override
    public String classAnnotation() {
        return "";
    }

    @Override
    public String classIdentification() {
        return PUBLIC+INTERFACE+getClassName()+EXTENDS+"Mapper"+getBaseEntityGeneric();
    }

    @Override
    public String classbody() {
        return "";
    }
}
