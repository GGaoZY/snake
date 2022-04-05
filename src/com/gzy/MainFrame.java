package com.gzy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class MainFrame  extends JFrame {
    private Snake snake;//蛇
    private JPanel jPanel; //游戏棋盘
    private Timer timer;//定时器，在规定的时间内调用蛇移动的方法
    private Node food;//食物

    public MainFrame() throws HeadlessException {
        //初始化窗体参数
        initFrame();
        //初始化游戏棋盘
        initGamePanel();
        //初始化蛇
        initSnake();
        //初始化食物
        initFood();
        //初始化定时器
        initTimer();
        //设置键盘监听，让蛇随着上下左右方向移动
        setKeyListener();
    }

    //初始化食物
    private void initFood() {
        food=new Node();
        food.random();
    }

    //设置键盘监听
    private void setKeyListener() {
        addKeyListener(new KeyAdapter() {
            //当键盘按下时，会自动掉此方法
            @Override
            public void keyPressed(KeyEvent e) {
                //键盘中的每一个键都有一个编号
                switch (e.getKeyCode()){

                    case KeyEvent.VK_UP: //上键
                        if (snake.getDirection()!=Direction.DOWN){
                            snake.setDirection(Direction.UP);
                        }
                        break;
                    case KeyEvent.VK_DOWN: //下键
                        if (snake.getDirection()!=Direction.UP){
                            snake.setDirection(Direction.DOWN);
                        }
                        break;
                    case KeyEvent.VK_LEFT: //左键
                        if (snake.getDirection()!=Direction.RIGHT){
                            snake.setDirection(Direction.LEFT);
                        }
                        break;
                    case KeyEvent.VK_RIGHT: //右键
                        if (snake.getDirection()!=Direction.LEFT){
                            snake.setDirection(Direction.RIGHT);
                        }
                        break;
                }
            }
        });
    }

    //初始化定时器
    private void initTimer() {
        //创建定时器对象
        timer=new Timer();

        //初始化定时任务
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                snake.move();
                //判断蛇头是否和食物重合
                Node head = snake.getBody().getFirst();
                if (head.getX()== food.getX()&&head.getY()== food.getY()){
                    snake.eat(food);
                    food.random();
                }
                //重绘游戏棋盘
                jPanel.repaint();
            }
        };

        //每100毫秒，执行一次定时任务
        timer.scheduleAtFixedRate(timerTask,0,100);
    }

    private void initSnake() {
        snake=new Snake();
    }

    //初始化游戏棋盘
    private void initGamePanel() {
        jPanel=new JPanel(){
            //绘制游戏棋盘中的内容
            @Override
            public void paint(Graphics g) {
                //清空棋盘
                g.clearRect(0,0,600,600);

                //Graphics g 可以看做是一个画笔，它提供了很多方法可以来绘制一些基本的图形（直线、矩形）
                //绘制40条横线
                for (int i = 0; i <=40; i++) {
                    g.drawLine(0,i*15,600,i*15);
                }
                //绘制40条竖线   第n条：  （n*15，0）   （n*15，600）
                for (int i = 0; i <=40; i++) {
                    g.drawLine(i*15,0,i*15,600);
                }

                //绘制蛇
                LinkedList<Node> body = snake.getBody();
                for (Node node : body) {
                    g.fillRect(node.getX()*15,node.getY()*15,15,15);
                }

                //绘制食物
                g.fillRect(food.getX()*15, food.getY()*15,15,15);
            }
        };

        //把棋盘添加到窗体中
        add(jPanel);
    }

    //初始化窗体参数
    private void initFrame() {
        //设置窗体宽高
        setSize(610,640);
        //设置窗体的位置
        setLocation(400,400);
        //设置关闭按钮的作用（退出程序）
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗体大小不可变
        setResizable(false);
    }

    public static void main(String[] args) {
        //创建窗体对象，并显示
        new MainFrame().setVisible(true);
    }
}
