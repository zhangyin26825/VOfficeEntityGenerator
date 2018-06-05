package com.voffice.idea.plugin.file.serviceimpl;

import com.voffice.idea.plugin.file.JavaCodeElement;
import com.voffice.idea.plugin.file.MysqlJavaFileCreate;
import com.voffice.idea.plugin.jdbc.TableInfo;
import com.voffice.idea.plugin.mysqlconfig.MySqlPersistent;

/**
 * @author zhangyin
 * @create 2018-06-04 13:56
 **/
public class ServiceInterface extends MysqlJavaFileCreate implements JavaCodeElement {

    public ServiceInterface(TableInfo tableInfo) {
        super(tableInfo);
    }

    @Override
    public String getClassName() {
        return getBaseEntityName()+"Service";
    }

    @Override
    public String getPackageName() {
        String servicepackage = MySqlPersistent.getMySqlConfig().getServicePackage() +"."+ getBaseEntityName().toLowerCase();
        return servicepackage;
    }

    @Override
    public String classCommentCode() {
        return "";
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
        return "";
    }
}
