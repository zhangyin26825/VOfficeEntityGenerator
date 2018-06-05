package com.voffice.idea.plugin.ui;

import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.ui.components.JBPanel;

/**
 * @author zhangyin
 * @create 2018-05-27 19:23
 **/
public class PluginJPanel extends JBPanel {
    private JBPopup jbPopup;

    public void setJBPopup(JBPopup jbPopup){
        this.jbPopup=jbPopup;
    }

    public  void cancel(){
        jbPopup.cancel();
    }
}
