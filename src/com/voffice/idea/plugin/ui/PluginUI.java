package com.voffice.idea.plugin.ui;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.popup.ComponentPopupBuilder;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author zhangyin
 * @create 2018-05-27 16:42
 **/
public class PluginUI   {


    public static void handle(AnActionEvent e,PluginJPanel pluginJPanel,int width,int height) {
        ComponentPopupBuilder componentPopupBuilder =
                JBPopupFactory.getInstance()
                        .createComponentPopupBuilder(pluginJPanel, null);
        componentPopupBuilder.setMinSize(new Dimension(width,width));
        JBPopup jbPopup = componentPopupBuilder.createPopup();
        jbPopup.showInBestPositionFor(e.getDataContext());
        pluginJPanel.setJBPopup(jbPopup);
    }
    public static void handle(AnActionEvent e,PluginJPanel pluginJPanel){
        handle(e,pluginJPanel,200,300);
    }



}
