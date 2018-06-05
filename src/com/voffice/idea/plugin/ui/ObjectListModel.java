package com.voffice.idea.plugin.ui;

import javax.swing.*;
import java.util.List;
import java.util.function.Function;

/**
 * @author zhangyin
 * @create 2018-05-27 18:57
 **/
public class ObjectListModel<T> extends AbstractListModel<String> {

    private List<T> list;

    private Function<T,String> function;

    public ObjectListModel(List<T> list, Function<T,String> function){
        this.list=list;
        this.function=function;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public String getElementAt(int index) {
        return function.apply(list.get(index));
    }
}
