package com.adaptive.ui.id3Tree;

import java.util.Comparator;

/**
 * TreeSet存储自定义类需要传的比较方法
 * Created by yeta on 2017/4/12/012.
 */
public class Comparisons implements Comparator {
    @Override
    public int compare(Object o1, Object o2) throws ClassCastException {
        String str1 = (String)o1;
        String str2 = (String)o2;
        return str1.compareTo(str2);
    }
}
