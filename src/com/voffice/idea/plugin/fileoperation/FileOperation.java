package com.voffice.idea.plugin.fileoperation;

import com.intellij.lang.Language;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.lang.xml.XMLLanguage;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileTypes.PlainTextLanguage;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.voffice.idea.plugin.G;

public class FileOperation {

    /**
     *  新建文件 如果目录存在同名的文件，删除原文件
     * @param fileName   文件名  包含后缀
     * @param fileSourcesCode  文件代码
     * @param directory    对应的目录
     * @return
     */
    public static PsiFile createFileExistDel(String fileName, String fileSourcesCode, PsiDirectory directory){

        if(directory==null){
            throw new RuntimeException("创建文件指定的目录为空，新建的文件为"+fileName);
        }

        Language language=getLanguage(fileName);
        PsiFile psiFile = PsiFileFactory.getInstance(G.getProject()).createFileFromText(fileName, language, fileSourcesCode);
        //如果这个目录下已经存在同名的文件，那么删除
        PsiFile[] files = directory.getFiles();
        for (PsiFile file : files) {
            if(file.getName().equals(fileName)){
                WriteCommandAction.runWriteCommandAction(G.getProject(), () -> {
                    file.delete();
                });

            }
        }
        WriteCommandAction.runWriteCommandAction(G.getProject(), () -> {
            if(language==JavaLanguage.INSTANCE) {
                JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(G.getProject());
                styleManager.optimizeImports(psiFile);
            }
            CodeStyleManager.getInstance(G.getProject()).reformat(psiFile);
            directory.add(psiFile);
        });
        return psiFile;
    }

    /**
     *  新建文件 如果目录存在同名的文件，删除原文件
     * @param fileName   文件名  包含后缀
     * @param fileSourcesCode  文件代码
     * @param directory    对应的目录
     * @return
     */
    public static PsiFile createFileExistCancel(String fileName, String fileSourcesCode, PsiDirectory directory){
        Language language=getLanguage(fileName);
        PsiFile psiFile = PsiFileFactory.getInstance(G.getProject()).createFileFromText(fileName, language, fileSourcesCode);
        //如果这个目录下已经存在同名的文件，那么删除
        PsiFile[] files = directory.getFiles();
        for (PsiFile file : files) {
            if(file.getName().equals(fileName)){
               return file;
            }
        }
        WriteCommandAction.runWriteCommandAction(G.getProject(), () -> {
            if(language==JavaLanguage.INSTANCE) {
                JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(G.getProject());
                styleManager.optimizeImports(psiFile);
            }
            CodeStyleManager.getInstance(G.getProject()).reformat(psiFile);
            directory.add(psiFile);
        });
        return psiFile;
    }



    private static Language getLanguage(String fileName){
        Language language;
        if(fileName.endsWith("java")){
            language= JavaLanguage.INSTANCE;
        }else if(fileName.endsWith("xml")){
            language= XMLLanguage.INSTANCE;
        }else {
            language= PlainTextLanguage.INSTANCE;
        }
        return  language;
    }

    private static PsiJavaDocumentedElement getPsiJavaDocumentedElement(PsiJavaFile javaFile){
        PsiElement[] children = javaFile.getChildren();
        for (PsiElement child : children) {
            if(child instanceof PsiJavaDocumentedElement){
                return (PsiJavaDocumentedElement)child;
            }
        }
        return null;
    }

    /**
     *  给一个java类 增加属性
     * @param psiJavaFile
     * @param fieldDesc
     */
    public  static void   addJavaField(PsiJavaFile psiJavaFile,String fieldDesc){
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(G.getProject());
        PsiJavaParserFacade parserFacade = javaPsiFacade.getParserFacade();
        PsiField psiField = parserFacade.createFieldFromText(fieldDesc, psiJavaFile);
        WriteCommandAction.runWriteCommandAction(G.getProject(), () -> {
            getPsiJavaDocumentedElement(psiJavaFile).add(psiField);
        });
    }

    /**
     *  给一个java类  增加导入
     * @param psiJavaFile
     * @param psiClass
     */
    public static void   addJavaImport(PsiJavaFile psiJavaFile,PsiClass psiClass){
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(G.getProject());
        PsiJavaParserFacade parserFacade = javaPsiFacade.getParserFacade();
        WriteCommandAction.runWriteCommandAction(G.getProject(), () -> {
            PsiImportStatement importStatement = javaPsiFacade.getElementFactory().createImportStatement(psiClass);
            psiJavaFile.getImportList().add(importStatement);
        });

    }
}
