package com.voffice.idea.plugin.jdbc;

import java.util.List;

public class TableInfo {

    private String tableName;

    private String tableComment;

    private List<ColumnInfo> columnInfos;


    public TableInfo(TableSql tableSql, List<ColumnInfo> columnInfos) {
        this.tableName = tableSql.getTableName();
        this.tableComment = tableSql.getTableComment();
        this.columnInfos = columnInfos;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public List<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public void setColumnInfos(List<ColumnInfo> columnInfos) {
        this.columnInfos = columnInfos;
    }
}
