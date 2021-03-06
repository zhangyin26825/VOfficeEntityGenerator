package com.voffice.idea.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.ui.popup.ComponentPopupBuilder;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.voffice.idea.plugin.G;
import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.file.MysqlFileCreateUtil;
import com.voffice.idea.plugin.jdbc.ColumnInfo;
import com.voffice.idea.plugin.jdbc.JdbcUtil;
import com.voffice.idea.plugin.jdbc.TableInfo;
import com.voffice.idea.plugin.jdbc.TableSql;
import com.voffice.idea.plugin.ui.listselect.DataListSelect;
import com.voffice.idea.plugin.ui.listselect.ListValue;
import com.voffice.idea.plugin.ui.listselect.SelectedCallback;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EntityGeneratorAction extends AnAction {

    private static Logger logger = Logger.getLogger(EntityGeneratorAction.class);

    @Override
    public void actionPerformed(AnActionEvent e) {
        DirectoryManager.init(G.getProject());
        JdbcUtil jdbcUtil=new JdbcUtil();
        //查询所有的表
        List<TableSql> tableSqls = jdbcUtil.queryAllTableName();

        if(tableSqls==null){
            logger.error("查询数据库的表的信息为空");
        }
        SelectedCallback selectedCallback=new SelectedCallback() {
            @Override
            public void handleSelectValue(ListValue listValue) {
                if(listValue instanceof  TableSql){
                    TableSql tableSql = (TableSql) listValue;

                    List<ColumnInfo> columnInfos = jdbcUtil.queryColumns(tableSql.getTableName());
                    TableInfo tableInfo=new TableInfo(tableSql,columnInfos);
                    MysqlFileCreateUtil mysqlFileCreateUtil =new MysqlFileCreateUtil(tableInfo);
                    mysqlFileCreateUtil.createFiles();
                }
            }
        };
        List<ListValue> listValues=new ArrayList<>(tableSqls);
        DataListSelect dataListSelect=new DataListSelect(listValues,selectedCallback);
        ComponentPopupBuilder componentPopupBuilder = JBPopupFactory.getInstance().createComponentPopupBuilder(dataListSelect, null);
        componentPopupBuilder.setProject(G.getProject());
        componentPopupBuilder.setMinSize(new Dimension(200,400));
        JBPopup jbPopup = componentPopupBuilder.createPopup();
        jbPopup.showInBestPositionFor(e.getDataContext());
        dataListSelect.setJbPopup(jbPopup);
    }

    @Override
    public void update(AnActionEvent e) {
        G.run(e.getProject());
    }
}
