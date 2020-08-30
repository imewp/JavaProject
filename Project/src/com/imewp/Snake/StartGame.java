package com.imewp.Snake;

import javax.swing.*;

//游戏的主启动类
public class StartGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("贪吃蛇");

        frame.setBounds(10, 10, 900, 720);
        frame.setResizable(false);  //窗口大小不可变
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //正常游戏界面都应该在面板上！
        frame.add(new GamePanel());
        frame.setVisible(true);
    }
}
