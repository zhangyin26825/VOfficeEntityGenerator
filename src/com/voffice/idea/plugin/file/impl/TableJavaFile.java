package com.voffice.idea.plugin.file.impl;

import com.intellij.psi.PsiDirectory;
import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.jdbc.ColumnInfo;
import com.voffice.idea.plugin.jdbc.TableInfo;
import com.voffice.idea.plugin.file.MysqlJavaFileCreate;

public class TableJavaFile extends MysqlJavaFileCreate {

    public TableJavaFile(TableInfo tableInfo){
        super(tableInfo);
        addMaqvMysqlImport();

    }

//    @Override
//    public PsiDirectory getDirectory() {
//        return DirectoryManager.getPsiTableDirectory();
//    }

    @Override
    public String getClassName() {
        return getBaseEntityName()+"Table";
    }

    @Override
    public String getPackageName() {
        return DirectoryManager.getTablePackage();
    }

    @Override
    public String classCommentCode() {
        return comment("DBTable for "+tableName," ",AUTHOR);
    }

    @Override
    public String classAnnotation() {
        return "";
    }

    @Override
    public String classIdentification() {
        return PUBLIC+INTERFACE+getClassName();
    }

    @Override
    public String classbody() {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(" DBTable TABLE_NAME = new DBTable(\"")
                .append(tableName)
                .append("\", \"")
                .append(getTableAlias()).append("\")"+SEMICOLON+NEWLINE);
        stringBuffer.append(NEWLINE);
        for (ColumnInfo columnInfo : columnInfos) {
            stringBuffer.append("DBColumn ").append(columnInfo.getColumnName())
                    .append("= new DBColumn(TABLE_NAME, \"").append(columnInfo.getColumnName()).append("\");"+NEWLINE);
        }
        return stringBuffer.toString();
    }
}
