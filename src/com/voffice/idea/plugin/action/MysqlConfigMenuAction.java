package com.voffice.idea.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.popup.ComponentPopupBuilder;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.mysqlconfig.ui.MysqlConfigPanel;

import java.awt.*;

public class MysqlConfigMenuAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        //配置mysql数据库的页面
        MysqlConfigPanel panel=new MysqlConfigPanel();
        ComponentPopupBuilder componentPopupBuilder = JBPopupFactory.getInstance().createComponentPopupBuilder(panel, null);
        componentPopupBuilder.setMinSize(new Dimension(100,250));
        JBPopup jbPopup = componentPopupBuilder.createPopup();
        jbPopup.showInBestPositionFor(e.getDataContext());
        panel.setJbPopup(jbPopup);
    }

    @Override
    public void update(AnActionEvent e) {

    }
}
