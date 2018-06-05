package com.voffice.idea.plugin.ui.listselect;

import com.intellij.openapi.ui.popup.JBPopup;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class DataListSelect extends JPanel{

    private JTextField textField1;
    private JButton exitButton;
    private JList<String> list1;
    private JScrollBar scrollBar1;
    private JScrollPane jScrollPane;

    private JBPopup jbPopup;
    private List<ListValue> dataList;
    private ListValue selectValue;
    private SelectedCallback selectedCallback;

    private void initComponent(){
        textField1=new JTextField();
        exitButton=new JButton("退出");
        list1=new JList<String>();
        jScrollPane=new JScrollPane(list1);
        list1.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

//        scrollBar1=new JScrollBar(JScrollBar.VERTICAL);
//        scrollBar1.add(list1);
        this.setLayout(new BorderLayout());
        this.add("North", textField1);
        this.add("Center", jScrollPane);
        this.add("South", exitButton);
    }


    public DataListSelect(List<ListValue> dataList,SelectedCallback selectedCallback) {
        initComponent();

        this.dataList = dataList;
        this.selectedCallback = selectedCallback;
        List<String> collect = dataList.stream().map(l -> l.getStringValue()).collect(Collectors.toList());
        System.out.println("选择列表的长度为"+collect.size());
        DataSelectListModel dataSelectListModel=new DataSelectListModel(collect);
        list1.setModel(dataSelectListModel);

        list1.setCellRenderer(new DefaultListCellRenderer());
        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                endSelect();
            }


        });
        list1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    endSelect();
                    return;
                }
//                if(e.getKeyCode()==KeyEvent.VK_DOWN){
//                    int selectedIndex = list1.getSelectedIndex();
//                    if(selectedIndex+1<list1.getModel().getSize()) {
//                        list1.setSelectedIndex(selectedIndex + 1);
//                    }
//                    return;
//                }
                if(e.getKeyCode()==KeyEvent.VK_UP){
                    int selectedIndex = list1.getSelectedIndex();
                    if(selectedIndex==0) {
                        textField1.requestFocus();
                        textField1.setFocusable(true);
                        textField1.requestFocusInWindow();
                    }
                    return;
                }
            }
        });
        textField1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textField1.setFocusable(true);
            }
        });

        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if(e.getKeyCode()==KeyEvent.VK_DOWN){
                    if(list1.getModel().getSize()>0) {
                        list1.setSelectedIndex(0);
                        list1.requestFocus();
                        list1.setFocusable(true);
                        list1.requestFocusInWindow();
                    }
                    return;
                }
                if(StringUtils.isEmpty(textField1.getText())){
                    return;
                }
                System.out.println("输入框文本内容变更"+textField1.getText());
                if(StringUtils.isNotEmpty(textField1.getText())){
                    List<String> collect1 = dataList.stream().map(l -> l.getStringValue()).filter(t -> t.contains(textField1.getText())).collect(Collectors.toList());
                    DataSelectListModel dataSelectListModel=new DataSelectListModel(collect1);
                    list1.setModel(dataSelectListModel);
                    list1.repaint();
                }else{
                    List<String> collect = dataList.stream().map(l -> l.getStringValue()).collect(Collectors.toList());
                    DataSelectListModel dataSelectListModel=new DataSelectListModel(collect);
                    list1.setModel(dataSelectListModel);
                    list1.repaint();
                }
            }
        });
        textField1.requestFocus();
        textField1.setFocusable(true);
        textField1.requestFocusInWindow();
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jbPopup.cancel();
            }
        });
        this.repaint();
        list1.repaint();
    }

    private void endSelect() {
        String selectedValue = list1.getSelectedValue();
        selectValue = dataList.stream().filter(l -> l.getStringValue().equals(selectedValue)).findFirst().get();
        jbPopup.cancel();
        selectedCallback.handleSelectValue(selectValue);
    }


    public void setJbPopup(JBPopup jbPopup) {
        this.jbPopup = jbPopup;
    }
}
