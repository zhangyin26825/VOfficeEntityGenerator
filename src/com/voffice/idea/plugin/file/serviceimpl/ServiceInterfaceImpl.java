package com.voffice.idea.plugin.file.serviceimpl;

import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.file.JavaCodeElement;
import com.voffice.idea.plugin.file.MysqlJavaFileCreate;
import com.voffice.idea.plugin.jdbc.TableInfo;
import com.voffice.idea.plugin.mysqlconfig.MySqlPersistent;
import org.apache.commons.lang.StringUtils;

/**
 * @author zhangyin
 * @create 2018-06-04 14:04
 **/
public class ServiceInterfaceImpl extends MysqlJavaFileCreate implements JavaCodeElement {

    public ServiceInterfaceImpl(TableInfo tableInfo) {
        super(tableInfo);
        addImport("org.springframework.beans.factory.annotation.Autowired");
        addImport("org.springframework.stereotype.Service");
        String servicePackage = MySqlPersistent.getMySqlConfig().getServicePackage() + "." + getBaseEntityName().toLowerCase() + "." + getBaseEntityName() + "Service";
        String s = DirectoryManager.getDaoPackage() + "." + getBaseEntityName() + "Dao";
        addImport(s);
        addImport(servicePackage);
    }

    @Override
    public String getClassName() {
        return getBaseEntityName()+"ServiceImpl";
    }

    @Override
    public String getPackageName() {
        String servicepackage = MySqlPersistent.getMySqlConfig().getServicePackage() +"."+ getBaseEntityName().toLowerCase()+".impl";
        return servicepackage;
    }

    @Override
    public String classCommentCode() {
        return "";
    }

    @Override
    public String classAnnotation() {
        return "@Service\n";
    }

    @Override
    public String classIdentification() {
        return PUBLIC +CLASS+ getClassName()+IMPLEMENTS+getBaseEntityName()+"Service";
    }

    @Override
    public String classbody() {
        return "@Autowired\n"
                +getBaseEntityName()+"Dao "+ StringUtils.uncapitalize(getBaseEntityName()+"Dao; ");

    }
}
