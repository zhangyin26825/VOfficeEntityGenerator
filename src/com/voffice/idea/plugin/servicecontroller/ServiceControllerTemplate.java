package com.voffice.idea.plugin.servicecontroller;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiPackage;
import com.voffice.idea.plugin.G;
import com.voffice.idea.plugin.file.JavaCodeElement;
import com.voffice.idea.plugin.file.serviceimpl.Controller;
import com.voffice.idea.plugin.file.serviceimpl.ServiceInterface;
import com.voffice.idea.plugin.file.serviceimpl.ServiceInterfaceImpl;
import com.voffice.idea.plugin.jdbc.TableInfo;
import com.voffice.idea.plugin.mysqlconfig.MySqlPersistent;
import com.voffice.idea.plugin.util.FindUseUtil;

/**
 * @author zhangyin
 * @create 2018-06-04 11:47
 **/
public class ServiceControllerTemplate implements JavaCodeElement {
    private TableInfo tableInfo;

    public ServiceControllerTemplate(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
        initDiretory();
        createClass(tableInfo);
    }

    private void createClass(TableInfo tableInfo) {
        ServiceInterface serviceInterface = new ServiceInterface(tableInfo);
        serviceInterface.create();
        ServiceInterfaceImpl serviceImpl = new ServiceInterfaceImpl(tableInfo);
        serviceImpl.create();
        Controller controller = new Controller(tableInfo);
        controller.create();
    }

    private void initDiretory(){
        String className = convertTableNameToClassName(tableInfo.getTableName());
        String lowerClassName = className.toLowerCase();
        {
            String servicePackageString = MySqlPersistent.getMySqlConfig().getServicePackage();
            PsiPackage servicePackage = FindUseUtil.findPackage(servicePackageString);
            PsiDirectory serviceDirectory = servicePackage.getDirectories()[0];
            WriteCommandAction.runWriteCommandAction(G.getProject(), () -> {

                PsiDirectory subdirectory1 = serviceDirectory.findSubdirectory(lowerClassName);
                if(subdirectory1==null) {
                    PsiDirectory subdirectory = serviceDirectory.createSubdirectory(lowerClassName);
                    subdirectory.createSubdirectory("impl");
                }
            });
        }
        {
            String controllerPackageString = MySqlPersistent.getMySqlConfig().getControllerPackage();
            PsiPackage controllerPackage = FindUseUtil.findPackage(controllerPackageString);
            PsiDirectory controllerDirectory = controllerPackage.getDirectories()[0];
            WriteCommandAction.runWriteCommandAction(G.getProject(), () -> {
                PsiDirectory subdirectory = controllerDirectory.findSubdirectory(lowerClassName);
                if (subdirectory == null) {
                    controllerDirectory.createSubdirectory(lowerClassName);
                }
            });
        }
    }

}
