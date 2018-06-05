package com.voffice.idea.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.popup.*;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import com.intellij.ui.components.JBList;
import com.voffice.idea.plugin.ui.PluginUI;
import com.voffice.idea.plugin.ui.idea.MultiChoose;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author zhangyin
 * @create 2018-05-27 16:38
 **/
public class TestAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        List<String> list=new ArrayList<>();
        list.add("Hello");
        list.add("world");
        //单选
//        ListPopupStep<String> listPopupStep=new BaseListPopupStep("testList",list){
//            @Override
//            public PopupStep onChosen(Object selectedValue, boolean finalChoice) {
//                System.out.println("选中的值为"+selectedValue);
//                return FINAL_CHOICE;
//            }
//        };
//        ListPopup listPopup = JBPopupFactory.getInstance().createListPopup(listPopupStep);
//        listPopup.showInBestPositionFor(e.getDataContext());

        JBList<String> jbList=new JBList<>(list);
        jbList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        PopupChooserBuilder popupChooserBuilder = new PopupChooserBuilder(jbList);
        popupChooserBuilder.setItemChoosenCallback(new Runnable() {
            @Override
            public void run() {
                List<String> selectedValuesList = jbList.getSelectedValuesList();
                if (!selectedValuesList.isEmpty()) {
                    selectedValuesList.forEach(System.out::println);
                }
            }
        });
        popupChooserBuilder.setTitle("多选");

        JBPopup popup = popupChooserBuilder.createPopup();

        popup.showInBestPositionFor(e.getDataContext());


//        MultiChoose multiChoose = new MultiChoose(e, list, Function.identity());
//        PluginUI.handle(e,multiChoose);
    }
}
