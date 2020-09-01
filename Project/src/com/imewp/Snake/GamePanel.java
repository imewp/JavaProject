package com.imewp.Snake;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//游戏的面板
public class GamePanel extends JPanel implements KeyListener, ActionListener {
    //定义蛇的数据结构
    int length;//蛇的长度
    int[] snakeX = new int[600];  //蛇的坐标 25*25
    int[] snakeY = new int[500];  //蛇的坐标 25*25
    String fx;
    //游戏当前的状态：开始，停止
    boolean isStart = false;    //默认是停止

    //定时器
    Timer timer = new Timer(100, this); //100毫秒执行一次

    //构造器
    public GamePanel() {
        init();

        //获得焦点和键盘事件
        this.setFocusable(true);//获得焦点事件
        this.addKeyListener(this);//获得键盘监听事件
        timer.start();  //游戏一开始定时器就启动
    }

    //初始化方法
    public void init() {
        length = 3;
        snakeX[0] = 100;
        snakeY[0] = 100;    //脑袋的坐标
        snakeX[1] = 75;
        snakeY[1] = 100;    //第一个身体的坐标
        snakeX[2] = 50;
        snakeY[2] = 100;    //第二个身体的坐标
        fx = "R";     //蛇头初始方向向右

    }


    //绘制面板，我们的游戏中的所有东西，都是用这个画笔来画
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);    //清屏

        //绘制静态的面板
        Data.header.paintIcon(this, g, 25, 11);    //头部的广告栏
        this.setBackground(Color.WHITE);
        g.fillRect(25, 75, 850, 600);   //默认的游戏界面

        //把小蛇画上去
        if (fx.equals("R")) {
            Data.right.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (fx.equals("L")) {
            Data.left.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (fx.equals("U")) {
            Data.up.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (fx.equals("D")) {
            Data.down.paintIcon(this, g, snakeX[0], snakeY[0]);
        }

        for (int i = 1; i < length; i++) {
            Data.body.paintIcon(this, g, snakeX[i], snakeY[i]);
        }

        //游戏状态
        if (isStart == false) {
            g.setColor(Color.white);
            g.setFont(new Font("微软雅黑", Font.BOLD, 35));
            g.drawString("按下空格开始游戏!", 300, 300);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //键盘监听事件
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();   //获得键盘按键是哪一个
        if (keyCode == KeyEvent.VK_SPACE) { //空格
            isStart = !isStart;     //取反
            repaint();
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    // 事件监听--需要通过固定事件来刷新，1s=10次
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isStart) {    //如果游戏是开始状态，就让小蛇动起来！

            //右移
            for (int i = length - 1; i > 0; i--) {  //后一节移到前一节的位置
                snakeY[i] = snakeY[i - 1];
                snakeX[i] = snakeX[i - 1];
            }
            snakeX[0] = snakeX[0] + 25;

            //边界判断
            if (snakeX[0] > 850)
                snakeX[0] = 25;

            repaint();  //重画页面
        }
        timer.start();  //定时器开始
    }
}
