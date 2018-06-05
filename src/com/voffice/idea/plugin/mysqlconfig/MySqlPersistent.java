package com.voffice.idea.plugin.mysqlconfig;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.Nullable;

@State(
        name = "maqv-gen-mysql",
        storages = {
                @Storage(
                        id = "maqv-gen-mysql",
                        file = "$APP_CONFIG$/maqv-gen-mysql.xml"
                )
        }
)
public class MySqlPersistent implements PersistentStateComponent<MySqlPersistent.MySqlConfig> {


    public static MySqlConfig getMySqlConfig() {
        MySqlPersistent service = ServiceManager.getService(MySqlPersistent.class);
        if(service.getState()==null){
            service.loadState(new MySqlConfig());
        }
        return  service.getState();
    }
    public static void  saveMySqlConfig(MySqlConfig mySqlConfig){
        MySqlPersistent service = ServiceManager.getService(MySqlPersistent.class);
        service.loadState(mySqlConfig);
    }

    MySqlConfig mySqlConfig;

    @Nullable
    @Override
    public MySqlConfig getState() {
        return mySqlConfig;
    }

    @Override
    public void loadState(MySqlConfig sqlConfig) {
        mySqlConfig=sqlConfig;
    }


    public static class MySqlConfig{
        private  String driverName= "com.mysql.jdbc.Driver";
        private  String jdbcURL= "jdbc:mysql://rm-wz970j84167q2388a.mysql.rds.aliyuncs.com:3306/sz-local?zeroDateTimeBehavior=convertToNull";
        private  String username= "voffice_sz";
        private  String password= "Db2017Admin";
        private  String enumSuffix="enum,status,type,flg";
        private  String entityPackage="";
        private  String ServicePackage="";
        private  String controllerPackage="";


        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getJdbcURL() {
            return jdbcURL;
        }

        public void setJdbcURL(String jdbcURL) {
            this.jdbcURL = jdbcURL;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEnumSuffix() {
            return enumSuffix;
        }

        public void setEnumSuffix(String enumSuffix) {
            this.enumSuffix = enumSuffix;
        }

        public String getEntityPackage() {
            return entityPackage;
        }

        public void setEntityPackage(String entityPackage) {
            this.entityPackage = entityPackage;
        }

        public String getServicePackage() {
            return ServicePackage;
        }

        public void setServicePackage(String servicePackage) {
            ServicePackage = servicePackage;
        }

        public String getControllerPackage() {
            return controllerPackage;
        }

        public void setControllerPackage(String controllerPackage) {
            this.controllerPackage = controllerPackage;
        }

        public String getTableSchema(){
            int i = jdbcURL.indexOf("?");
            int i1 = jdbcURL.lastIndexOf("/");
            return  jdbcURL.substring(i1+1, i);
        }
    }
}
