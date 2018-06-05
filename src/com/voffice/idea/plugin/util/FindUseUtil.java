package com.voffice.idea.plugin.util;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PackageScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.util.Query;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FindUseUtil {


    private static Logger logger = Logger.getLogger(FindUseUtil.class);

    private  static Project project;

    private static JavaPsiFacade javaPsiFacade;

    public static  void init(Project p){
        logger.info(p.getName());
        project=p;
        javaPsiFacade=JavaPsiFacade.getInstance(project);
    }

    /**
     * 根据完整的类名 找到idea 中对应的 PsiClass
     * @param qualifiedName
     * @return
     */
    public static PsiClass  findClass(String qualifiedName){
        PsiClass psiClass = javaPsiFacade.findClass(qualifiedName, GlobalSearchScope.allScope(project));
        return psiClass;
    }

    /**
     * 根据完整的类名  找到idea 中的对应的 PsiPackage
     * @param qualifiedName
     * @return
     */
    public static PsiPackage findPackage(String qualifiedName){
        return javaPsiFacade.findPackage(qualifiedName);
    }

    /**
     *  根据完整的类名  找到 所有使用 这个类的对应的 java类
     * @param qualifiedName
     * @return
     */
    public  static Set<PsiJavaFile> findUseages(String qualifiedName){
        PsiClass psiClass = findClass(qualifiedName);
        logger.info(qualifiedName+"这个类的psiClass 是否为空"+(psiClass==null));
        if (psiClass == null) {
            return Collections.emptySet();
        }
        /**
         * 这里搜索出来的是  使用过这个类的 java代码片段  可能是属性  可能是方法的参数  注解的属性  等等
         * 用 getContainingFile   找到具体的 java类
         *
         */
        Query<PsiReference> search = ReferencesSearch.search(psiClass, PackageScope.everythingScope(project));
        Set<PsiJavaFile> set=new HashSet<>();
        logger.info("find user找到的引用数量为"+search.findAll().size());
        System.out.println("find user找到的引用数量为"+search.findAll().size());
        search.forEach(p->{
            PsiElement element = p.getElement();
            PsiFile containingFile = element.getContainingFile();
            if(containingFile instanceof  PsiJavaFile){
                set.add((PsiJavaFile)containingFile);
            }
        });
        Set<PsiJavaFile> collect = set.stream().filter(p -> !p.getPackageName().equals("com.maqv.mysql.converter")).collect(Collectors.toSet());
        logger.info("find use 获得的java类的数量为"+collect.size());
        return collect;
    }



}
