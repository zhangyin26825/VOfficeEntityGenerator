package com.voffice.idea.plugin.file.impl;

import com.intellij.psi.PsiDirectory;
import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.file.field.FieldGenerator;
import com.voffice.idea.plugin.jdbc.TableInfo;
import com.voffice.idea.plugin.jdbc.ColumnInfo;

public class MultipleKeyJavaFile extends  EntityJavaFile {

    public MultipleKeyJavaFile(TableInfo tableInfo, FieldGenerator fieldGenerator){
        super(tableInfo,fieldGenerator);
        this.fieldGenerator=fieldGenerator;
        addLombokImport();
        addMaqvMysqlImport();
        addEnumClass();
    }

//    @Override
//    public PsiDirectory getDirectory() {
//        return DirectoryManager.getPsiPoDirectory();
//    }

    @Override
    public String getClassName() {
        return getBaseEntityName()+"Key";
    }

    @Override
    public String classIdentification() {
        return PUBLIC+CLASS+getClassName()+IMPLEMENTS+"DBId";
    }

    @Override
    public String classbody() {
        StringBuffer stringBuffer = new StringBuffer();
        for (ColumnInfo columnInfo : getPriKey()) {
            stringBuffer.append(fieldGenerator.generatorFieldDesc(columnInfo));
        }
        return stringBuffer.toString();
    }
}
