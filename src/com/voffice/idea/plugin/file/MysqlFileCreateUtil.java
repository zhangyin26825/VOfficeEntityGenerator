package com.voffice.idea.plugin.file;

import com.intellij.psi.PsiJavaFile;
import com.voffice.idea.plugin.file.enumclass.EnumJavaFile;
import com.voffice.idea.plugin.file.field.FieldGenerator;
import com.voffice.idea.plugin.file.field.impl.EntityFieldGenerator;
import com.voffice.idea.plugin.file.impl.*;
import com.voffice.idea.plugin.file.mapperxml.MapperXmlFile;
import com.voffice.idea.plugin.jdbc.TableInfo;
import com.voffice.idea.plugin.jdbc.ColumnInfo;

public class MysqlFileCreateUtil {

    TableInfo tableInfo;

    FieldGenerator fieldGenerator;


    public MysqlFileCreateUtil(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
        fieldGenerator=new EntityFieldGenerator();
    }

    public  void  createFiles(){
        EntityJavaFile entityJavaFile = new EntityJavaFile(tableInfo,fieldGenerator);
        TableJavaFile tableJavaFile = new TableJavaFile(tableInfo);
        MapperJavaFile mapperJavaFile = new MapperJavaFile(tableInfo);
        DaoJavaFile daoJavaFile = new DaoJavaFile(tableInfo);
        DaoImplJavaFile daoImplJavaFile = new DaoImplJavaFile(tableInfo);
        PsiJavaFile entity = (PsiJavaFile)entityJavaFile.create();
        PsiJavaFile table = (PsiJavaFile)tableJavaFile.create();
        PsiJavaFile mapper = (PsiJavaFile)mapperJavaFile.create();
        PsiJavaFile dao = (PsiJavaFile)daoJavaFile.create();
        PsiJavaFile daoImpl = (PsiJavaFile)daoImplJavaFile.create();
        //创建所有的枚举类
        for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
            if(fieldGenerator.isEnum(columnInfo)){
                EnumJavaFile enumJavaFile = new EnumJavaFile(columnInfo);
                PsiJavaFile psiFile = (PsiJavaFile)enumJavaFile.create();
            }
        }
        if(entityJavaFile.isMultiId()){
            MultipleKeyJavaFile multipleKeyJavaFile = new MultipleKeyJavaFile(tableInfo, fieldGenerator);
            multipleKeyJavaFile.create();
        }
        MapperXmlFile mapperXmlFile = new MapperXmlFile(tableInfo, fieldGenerator);
        mapperXmlFile.create();
    }
}
