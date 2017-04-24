package com.adaptive.ui.id3Tree;

/**
 * 与熵有关的类
 * Created by yeta on 2017/4/13/013.
 */
public class Entropy {
    public static double getEntropy(int p_num, int total_num){
        if(p_num == 0 || total_num == 0) {
            return 0;
        }
        double p = p_num * Double.parseDouble("1.0") / total_num;
        return -p * (Math.log(p) / Math.log(2));
    }
}
