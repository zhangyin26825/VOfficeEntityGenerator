package com.voffice.idea.plugin.file.serviceimpl;

import com.voffice.idea.plugin.file.JavaCodeElement;
import com.voffice.idea.plugin.file.MysqlJavaFileCreate;
import com.voffice.idea.plugin.jdbc.TableInfo;
import com.voffice.idea.plugin.mysqlconfig.MySqlPersistent;
import org.apache.commons.lang.StringUtils;

/**
 * @author zhangyin
 * @create 2018-06-04 14:12
 **/
public class Controller  extends MysqlJavaFileCreate implements JavaCodeElement {

    public Controller(TableInfo tableInfo) {
        super(tableInfo);
        addImport("org.springframework.beans.factory.annotation.Autowired");
        addImport("org.springframework.web.bind.annotation.RestController");
        addImport("org.springframework.web.bind.annotation.RequestMapping");
        addImport("org.springframework.validation.annotation.Validated");
        String servicePackage = MySqlPersistent.getMySqlConfig().getServicePackage() + "." + getBaseEntityName().toLowerCase() + "." + getBaseEntityName() + "Service";
        addImport(servicePackage);
    }

    @Override
    public String getClassName() {
        return getBaseEntityName()+"Controller";
    }

    @Override
    public String getPackageName() {
        String s = MySqlPersistent.getMySqlConfig().getControllerPackage() + "." + getBaseEntityName().toLowerCase();
        return s;
    }

    @Override
    public String classCommentCode() {
        return comment("");
    }

    @Override
    public String classAnnotation() {
        return "@RestController\n" +
                "@RequestMapping(\"/{apiVersion}/api/"+getBaseEntityName().toLowerCase()+"\")\n" +
                "@Validated\n";
    }

    @Override
    public String classIdentification() {
        return PUBLIC+CLASS+ getClassName();
    }

    @Override
    public String classbody() {
        return "@Autowired\n"
                +getBaseEntityName()+"Service "+ StringUtils.uncapitalize(getBaseEntityName()+"Service; ");
    }
}
