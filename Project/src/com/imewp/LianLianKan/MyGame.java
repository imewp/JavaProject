package com.imewp.LianLianKan;


import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class MyGame extends JFrame implements ActionListener {
    private JPanel functionPanel;   //功能区面板
    private JPanel gamePanel;       //游戏区面板

    private final static int ROW = 7;   //游戏区按钮行数
    private final static int COLUMN = 8;    //游戏区按钮列数
    private JButton[] dots = new JButton[ROW * COLUMN]; //游戏区的按钮数组

    private JLabel user;    //显示用户名标签
    private JLabel score;   //显示用户得分标签
    private JButton hint;   //提示按钮
    private JComboBox level;
    private JTextArea talkList;    //聊天列表
    private JTextField speak;       //用户发言

    // 构造方法
    public MyGame() {
        this.setBounds(200, 100, 790, 500);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setTitle("连连看");

        gamePanel = new JPanel();    //生成游戏区面板对象
        gamePanel.setBackground(Color.blue);    //设置面板背景颜色
        gamePanel.setPreferredSize(new Dimension(590, 500));    //设置面板大小
        functionPanel = new JPanel();   //生成功能区面板
        functionPanel.setBackground(Color.yellow);
        functionPanel.setPreferredSize(new Dimension(200, 500));

        addmyMenu();    //调用生成菜单方法
        addGamePanel(); //调用生成游戏区方法
        addFunctionPanel(); //调用生成功能区方法

        this.getContentPane().add(functionPanel, BorderLayout.EAST); //添加面板
        this.getContentPane().add(gamePanel, BorderLayout.WEST);

        this.setResizable(false);
        this.setVisible(true);  //设置窗体可见

        //内部匿名类
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int re = JOptionPane.showConfirmDialog(null,
                        "是否退出？",
                        "提示",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (re == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    return;
                }
            }
        });

        Timer timer = new Timer(1000, this);
        timer.start();
    }

    //生成菜单的方法
    private void addmyMenu() {
        JMenuBar menuBar = new JMenuBar();  //菜单条
        this.setJMenuBar(menuBar);      //把菜单条加到窗体上

        JMenu menuGame = new JMenu("游戏");   //“游戏”菜单
        menuBar.add(menuGame);  //把菜单加到菜单条上
        //给“游戏”菜单加上  菜单项
        menuGame.add(new JMenuItem("打开"));
        menuGame.add(new JMenuItem("保存"));
        menuGame.addSeparator();                    //分隔线
        menuGame.add(new JMenuItem("退出"));

        JMenu menuSet = new JMenu("设置");
        menuBar.add(menuSet);
        menuSet.add(new JCheckBoxMenuItem("音乐开关"));


        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem rbm1 = new JRadioButtonMenuItem("初级难度");
        JRadioButtonMenuItem rbm2 = new JRadioButtonMenuItem("中级难度");
        JRadioButtonMenuItem rbm3 = new JRadioButtonMenuItem("高级难度");
        group.add(rbm1);
        group.add(rbm2);
        group.add(rbm3);
        menuSet.add(rbm1);
        menuSet.add(rbm2);
        menuSet.add(rbm3);

        menuSet.add(new JMenuItem("背景色"));
        menuSet.add(new JMenuItem("提示"));

        JMenu menuHelp = new JMenu("帮助");
        menuBar.add(menuHelp);

        JMenuItem about = new JMenuItem("关于", new ImageIcon("Project/src/com/imewp/LianLianKan/image/about.png"));
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        menuHelp.add(new JMenuItem("帮助内容"));
        menuHelp.addSeparator();    //添加一条分割线
        menuHelp.add(about);
    }

    //游戏区
    private void addGamePanel() {
        gamePanel = new JPanel();    //生成游戏区面板对象
        gamePanel.setBackground(Color.red);    //设置面板背景颜色
        gamePanel.setPreferredSize(new Dimension(590, 500));    //设置面板大小
        gamePanel.setLayout(new GridLayout(ROW, COLUMN));        //设置布局
        for (int i = 0; i < ROW * COLUMN; i++) {
            Random random = new Random();
            int newNumber = random.nextInt(9) + 1;
            ImageIcon image = new ImageIcon("Project/src/com/imewp/LianLianKan/image/" + newNumber + ".jpg");
            dots[i] = new JButton("", image);
            dots[i].setActionCommand(i + "");
            dots[i].addActionListener(new ButtonEvent());
            gamePanel.add(dots[i]);
        }
    }

    //功能区
    private void addFunctionPanel() {
        functionPanel = new JPanel();   //生成功能区面板
        functionPanel.setBackground(Color.yellow);
        functionPanel.setPreferredSize(new Dimension(200, 500));
        functionPanel.setLayout(new BorderLayout());

        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        p1.setPreferredSize(new Dimension(180, 200));
        p2.setPreferredSize(new Dimension(180, 300));
        p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
        p2.setLayout(new BorderLayout());
        p3.setLayout(new BorderLayout());

        user = new JLabel("当前玩家：imewp");
        user.setFont(new Font("隶书", Font.BOLD, 20));
        user.setForeground(new Color(232, 12, 123));

        score = new JLabel("玩家得分：322");
        score.setFont(new Font("隶书", Font.BOLD, 20));
        score.setForeground(Color.red);

        hint = new JButton("提示");
        hint.setPreferredSize(new Dimension(100, 40));
        hint.addMouseListener(new MouseEvents());
        hint.addActionListener(new MultiEvents());

        level = new JComboBox(new String[]{"初级难度", "中级难度", "高级难度"});
        level.setSelectedIndex(1);
        level.setPreferredSize(new Dimension(100, 40));
        talkList = new JTextArea(5, 5);
        talkList.setBorder(new BevelBorder(BevelBorder.LOWERED));
        talkList.setText("游戏进行中\n" + "玩家 imewp：大家好，一起来玩吧");

        speak = new JTextField(5);
        speak.addKeyListener(new KeyEvents());

        p1.add(user);
        p1.add(score);
        p1.add(hint);
        p1.add(level);
        p2.add(talkList, BorderLayout.CENTER);
        p2.add(p3, BorderLayout.SOUTH);
        p3.add(speak, BorderLayout.CENTER);
        p3.add(new JButton("发言"), BorderLayout.EAST);
        functionPanel.add(p1, BorderLayout.NORTH);
        functionPanel.add(p2, BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int r = (int) (Math.random() * 255);
        int g = (int) (Math.random() * 255);
        int b = (int) (Math.random() * 255);s

        score.setForeground(new Color(r, g, b));
    }

    //内部类：监听器类
    private class ButtonEvent implements ActionListener {

        //游戏区按钮监听器
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                JButton button = (JButton) e.getSource();
                int offset = Integer.parseInt(button.getActionCommand());
                int row, col;
                row = offset / COLUMN + 1;
                col = offset % COLUMN;
                JOptionPane.showMessageDialog(null,
                        "行数为：" + row + "，列数为：" + col,
                        "按钮",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    //监听器类的使用
   /* private class MouseEvents implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            hint.setBackground(Color.red);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            hint.setBackground(Color.gray);
        }
    }*/

    //适配器类的使用
    private class MouseEvents extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            hint.setBackground(Color.red);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            hint.setBackground(Color.gray);
        }
    }

    private class MultiEvents implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == hint) {
                JOptionPane.showMessageDialog(null, "注册多个事件", "按钮", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class KeyEvents extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            String s1 = "当前输入的字符是：" + e.getKeyChar();
            String s2 = "编码是：" + e.getKeyCode();
            talkList.setText(s1 + "\n" + s2);
        }
    }


    //主方法
    public static void main(String[] args) {
        new MyGame();
    }


}
