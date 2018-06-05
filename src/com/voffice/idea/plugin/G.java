package com.voffice.idea.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiJavaFile;
import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.generated.GeneratedEntityTable;
import com.voffice.idea.plugin.util.FindUseUtil;
import org.apache.log4j.Logger;

import java.util.Set;

public class G {

    private static Logger logger = Logger.getLogger(G.class);

    private static Project p;

    public static Project getProject() {
        return p;
    }

    private static boolean inited=false;

    public static  void run(Project project){
        p=project;
        if(inited){
            return;
        }
        inited=true;
        try {
            logger.info("进入项目初始化");
            FindUseUtil.init(project);
//            /**
//             * 找到所有已经生成过实体类的表
//             */
//            logger.info("寻找生成过实体类的表");
//            GeneratedEntityTable.initGeneratedEntityTable();
//
//            Set<PsiJavaFile> entityClasses = GeneratedEntityTable.getEntityClasses();
//            logger.info("找到实体类的数量为"+entityClasses.size());
            /**
             * 根据生成的实体类的信息  初始化相关文件所在的目录
             */
            logger.info("初始化相关的目录");
//            DirectoryManager.initPoPackage(entityClasses,project);
//            DirectoryManager.init(p);
        } catch (Exception e) {
//            inited=false;
        }

    }
}
