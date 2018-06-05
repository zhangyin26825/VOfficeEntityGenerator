package com.voffice.idea.plugin.ui.idea;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBMovePanel;
import com.voffice.idea.plugin.ui.ObjectListModel;
import com.voffice.idea.plugin.ui.PluginJPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;


/**
 * @author zhangyin
 * @create 2018-05-27 18:23
 **/
public class MultiChoose<T> extends PluginJPanel {



    private JBList<T> jbList;

    public MultiChoose(AnActionEvent e, List<T> list, Function<T,String> function) {
        this.setLayout(new BorderLayout());
        ObjectListModel<T> objectListModel=new ObjectListModel<>(list,function);
        JBList<T> jbList = new JBList(list);
        jbList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jbList.setCellRenderer(new DefaultListCellRenderer());

        JBList<T> jbList1=new JBList(Collections.emptyList());
        jbList1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jbList1.setCellRenderer(new DefaultListCellRenderer());

        JBMovePanel jbMovePanel=new JBMovePanel(jbList,jbList1);



//        this.add("Center",jbList);
        this.add("Center",jbMovePanel);
        this.repaint();
        jbList.repaint();
    }

}
