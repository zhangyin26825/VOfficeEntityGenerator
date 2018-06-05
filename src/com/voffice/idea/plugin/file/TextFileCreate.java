package com.voffice.idea.plugin.file;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.fileoperation.FileOperation;
import org.apache.log4j.Logger;

/**
 * 文本文件创建
 */
public interface TextFileCreate extends  FileCreate{

    public static Logger logger = Logger.getLogger(TextFileCreate.class);

    /**
     * 获取文件的内容
     * @return
     */
    public String   getFileContent();

    /**
     * 获取文件的名字
     * @return
     */
    public String   getFileName();

    /**
     * 文件放置的目录
     * @return
     */
    public PsiDirectory  getDirectory();

    @Override
    default PsiFile create(){
        logger.info("获取到的文件内容为"+getFileContent());
        logger.info("获取到的文件名称为"+getFileName());
        logger.info("获取到的文件目录为"+getDirectory());
        return FileOperation.createFileExistDel(getFileName(), getFileContent(), getDirectory());
    }
}
