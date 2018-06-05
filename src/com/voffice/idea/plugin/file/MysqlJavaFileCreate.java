package com.voffice.idea.plugin.file;

import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.jdbc.ColumnInfo;
import com.voffice.idea.plugin.jdbc.TableInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 针对 mysql 数据库生成相关java类
 */
public  abstract  class MysqlJavaFileCreate  extends JavaFileCreate{

    protected String AUTHOR="@author Ben.Ma <xiaobenma020@gmail.com>";

    protected String tableName;

    protected String tableComment;

    protected List<ColumnInfo> columnInfos;

    protected MysqlJavaFileCreate(TableInfo tableInfo){
        this.tableName=tableInfo.getTableName();
        this.tableComment=tableInfo.getTableComment();
        this.columnInfos=tableInfo.getColumnInfos();
    }

    protected String getTableAlias() {
        return getAlias(tableName);
    }

    protected String  getBaseEntityName(){
        return  convertTableNameToClassName(tableName);
    }

    protected String  getBaseEntityGeneric(){
        return "<"+getBaseEntityName()+">";
    }

    /**
     * 多主键
     * @return
     */
    public Boolean  isMultiId(){
        List<ColumnInfo> collect = getPriKey();
        if(collect.size()<2){
            return false;
        }
        return  true;
    }

    /**
     * 无主键
     * @return
     */
    public Boolean  isNoId(){
        List<ColumnInfo> collect = getPriKey();
        return  collect.isEmpty();
    }

    public  List<ColumnInfo> getPriKey(){
        List<ColumnInfo> collect = columnInfos.stream().filter(c -> "PRI".equals(c.getColumnKey())).collect(Collectors.toList());
        return collect;
    }

    protected  void  addBaseEntityImport(){
        addImport(DirectoryManager.getPoPackage()+"."+getBaseEntityName());
    }

    protected  void  addLombokImport(){
        addImport("lombok.Getter");
        addImport("lombok.Setter");
    }

    protected  void addMaqvMysqlImport(){
        addImport("com.maqv.mysql.annotation.Column");
        addImport("com.maqv.mysql.annotation.Id");
        addImport("com.maqv.mysql.annotation.Table");
        addImport("com.maqv.mysql.db.DBColumn");
        addImport("com.maqv.mysql.db.DBTable");
        addImport("com.maqv.mysql.db.DBId");
    }

    protected  void addDaoImport(){
        addImport("com.maqv.mybatis.core.dao.MultipleIdDao");
        addImport("com.maqv.mybatis.core.dao.SingleIdDao");
        addImport("com.maqv.mybatis.core.dao.NoIdDao");
        addImport("com.maqv.mybatis.core.dao.impl.SingleIdDaoImpl");
        addImport("com.maqv.mybatis.core.dao.impl.MultipleIdDaoImpl");
        addImport("com.maqv.mybatis.core.dao.impl.NoIdDaoImpl");
    }

    protected  void  addEnumImport(){
        addImport("com.maqv.mybatis.converter.CommonEnum");
    }

    protected  void addSpringImport(){
        addImport("org.springframework.beans.factory.annotation.Autowired");
        addImport("org.springframework.stereotype.Repository");
        addImport("org.springframework.stereotype.Service");
        addImport("org.springframework.stereotype.Controller");
        addImport("org.springframework.stereotype.Component");
    }

    protected  void  addMapperImport(){
        addImport("com.maqv.mybatis.core.dao.mapper.Mapper");
    }
}
