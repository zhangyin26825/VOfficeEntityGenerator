package com.voffice.idea.plugin.generated;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import com.voffice.idea.plugin.Constant;
import com.voffice.idea.plugin.util.FindUseUtil;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 已经生成过实体类的表
 */
public class GeneratedEntityTable {

    private static Logger logger = Logger.getLogger(GeneratedEntityTable.class);


    /**
     *  实体类
     */
    private  static Set<PsiJavaFile> entityClasses;

    /**
     * 寻找所有已经生成过实体类的表
     */
    public static void initGeneratedEntityTable(){
        if(entityClasses==null) {
            entityClasses = FindUseUtil.findUseages(Constant.Table);
            logger.info("寻找到使用@Table注解的类的数量为" + entityClasses.size());
        }
    }

    /**
     * 获取已经生成过的实体类的表名
     * @return
     */
    public static Set<String>   getGeneratedTables(){
        Set<String> tableNames = entityClasses.stream().map(e -> getTableNameFromEntityClass(e)).collect(Collectors.toSet());
        return tableNames;
    }

    /**
     * 从一个实体类获取表名
     * @param entityClass
     * @return
     */
    private  static String getTableNameFromEntityClass(PsiJavaFile entityClass){
//        PsiClass psiClass = entityClass.getClasses()[0];
//        PsiAnnotation[] annotations = psiClass.getAnnotations();
//        Optional<String> name = Arrays.asList(annotations).stream().filter(p -> p.getQualifiedName().equals(Constant.Table))
//                .map(a -> a.findAttributeValue("name").getText())
//                .findFirst();
//        if(name.isPresent()){
//            return  name.get().replaceAll("\"","" );
//        }
        return  "";
    }

    public static Set<PsiJavaFile> getEntityClasses() {
        return entityClasses;
    }
}
