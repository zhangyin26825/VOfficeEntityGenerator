package com.voffice.idea.plugin.file;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiPackage;
import com.voffice.idea.plugin.util.FindUseUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 创建java文件
 */
public abstract class JavaFileCreate  implements TextFileCreate,JavaCodeElement {


    public String JavaSuffix=".java";

    private Set<String> importLists;

    protected JavaFileCreate(){
        importLists=new HashSet<>();
    }

    public abstract String  getClassName();

    public abstract String  getPackageName();

    public String  getQualifiedName(){
        return  getPackageName()+"."+getClassName();
    }

    @Override
    public boolean overwriteOldFiles() {
        return true;
    }

    /**
     * 类注释
     * @return
     */
    public abstract String  classCommentCode();

    /**
     * 类注解
     * @return
     */
    public abstract String  classAnnotation();

    /**
     * 类标识信息
     * @return
     */
    public abstract String  classIdentification();

    /**
     * 类里面的信息
     * @return
     */
    public abstract String  classbody();

    @Override
    public String getFileName(){
        return  getClassName()+JavaSuffix;
    }

    protected  void  addImport(String importDesc){
        importLists.add(importDesc);
    }


    @Override
    public PsiDirectory getDirectory() {
        String packageName = getPackageName();
        PsiPackage aPackage = FindUseUtil.findPackage(packageName);
        return aPackage.getDirectories()[0];
    }



    @Override
    public String getFileContent() {
        StringBuffer sb=new StringBuffer();
        sb.append(packageCode()+NEWLINE);
        sb.append(NEWLINE);
        sb.append(importCode());
        sb.append(NEWLINE);
        sb.append(classCommentCode());
        sb.append(NEWLINE);
        sb.append(classAnnotation());
        sb.append(classIdentification()+LEFT_BRACES+NEWLINE);
        sb.append(NEWLINE);
        sb.append(classbody());
        sb.append(NEWLINE);
        sb.append(RIGHT_BRACES);
        return sb.toString();
    }

    /**
     * 包
     * @return
     */
    private  String  packageCode(){
        return PACKAGE+getPackageName()+SEMICOLON;
    }

    /**
     * 导入
     * @return
     */
    private String importCode() {
        StringBuffer stringBuffer=new StringBuffer();
        for (String importDesc : importLists) {
            stringBuffer.append(IMPORT+importDesc+SEMICOLON+NEWLINE);
        }
        return stringBuffer.toString();
    }



}
