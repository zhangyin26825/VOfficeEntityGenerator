package com.voffice.idea.plugin.file.enumclass;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.file.JavaCodeElement;
import com.voffice.idea.plugin.file.JavaFileCreate;
import com.voffice.idea.plugin.fileoperation.FileOperation;
import com.voffice.idea.plugin.jdbc.ColumnInfo;
import org.apache.commons.lang.StringUtils;

public class EnumJavaFile extends JavaFileCreate implements JavaCodeElement {


    ColumnInfo columnInfo;

    public EnumJavaFile(ColumnInfo columnInfo){
        this.columnInfo=columnInfo;
        addImport("com.maqv.mybatis.converter.CommonEnum");
    }

//    @Override
//    public PsiDirectory getDirectory() {
//        return DirectoryManager.getPsiTypeDirectory();
//    }

    @Override
    public String getClassName() {
        return StringUtils.capitalize(getFieldName(columnInfo.getColumnName()));
    }

    @Override
    public String getPackageName() {
        return DirectoryManager.getTypePackage();
    }

    @Override
    public String classCommentCode() {
        return comment("@author Ben.Ma <xiaobenma020@gmail.com>");
    }

    @Override
    public String classAnnotation() {
        return "";
    }

    @Override
    public String classIdentification() {
        return PUBLIC+ENUM+getClassName()+IMPLEMENTS+"CommonEnum<"+getClassName()+">";
    }

    @Override
    public PsiFile create() {
        return FileOperation.createFileExistCancel(getFileName(), getFileContent(), getDirectory());
    }

    public String  generateFieldDesc(){
        String comment = comment(columnInfo.getColumnComment(), columnInfo.getColumnType());
        StringBuffer sb=new StringBuffer(comment);
        if(StringUtils.equals("PRI", columnInfo.getColumnKey())){
            sb.append("@Id(");
        }else{
            sb.append("@Column(");
        }
        sb.append("name=\""+columnInfo.getColumnName()+"\"");
        if(StringUtils.equals("YES", columnInfo.getIsNullable())){
            sb.append(", nullable = true");
        }
        if(StringUtils.equals("auto_increment", columnInfo.getExtra())){
            sb.append(", autoIncrease = true");
        }
        sb.append(")"+NEWLINE);
        sb.append(PRIVATE).append(getClassName())
                .append(" ")
                .append(getFieldName(columnInfo.getColumnName())).append(SEMICOLON+NEWLINE);
        return sb.toString();
    }

    @Override
    public String classbody() {
        return " ;\n" +
                "\n" +
                "\n" +
                "    "+getClassName()+"(int value) {\n" +
                "        this.value = value;\n" +
                "    }\n" +
                "\n" +
                "    private int value;\n" +
                "\n" +
                "    @Override\n" +
                "    public int getValue() {\n" +
                "        return value;\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    public static "+getClassName()+" get(Integer value) {\n" +
                "        if (null == value) {\n" +
                "            return null;\n" +
                "        }\n" +
                "        for ("+getClassName()+" status : values()) {\n" +
                "            if (value.equals(status.getValue())) {\n" +
                "                return status;\n" +
                "            }\n" +
                "        }\n" +
                "        return null;\n" +
                "    }";
    }
}
