package com.focusedu;

import com.focusedu.view.CustomerManagerView;
import com.focusedu.view.RedisManagerView;
import com.focusedu.view.ZookeeperManagerView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


/**
 * 主窗口
 *
 * @author liuruichao
 * @date 15/9/12 下午2:05
 */
public class MainView extends JFrame {
    private JButton customerBtn, redisBtn, zkBtn;

    public MainView() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        setTitle("选择");
        setLayout(new GridLayout(2, 1));
        customerBtn = new JButton("CustomerManager");
        redisBtn = new JButton("RedisManager");
        zkBtn = new JButton("ZkManager");
        add(customerBtn);
        add(redisBtn);
        add(zkBtn);

        initEvent();

        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void initEvent() {
        customerBtn.addActionListener(e -> {
            if (e.getActionCommand().equals("CustomerManager")) {
                new CustomerManagerView();
            }
        });
        redisBtn.addActionListener(e -> {
            if (e.getActionCommand().equals("RedisManager")) {
                try {
                    new RedisManagerView();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        zkBtn.addActionListener(e -> {
            if (e.getActionCommand().equals("ZkManager")) {
                new ZookeeperManagerView();
            }
        });
    }

    public static void main(String[] args) throws Exception {
        new MainView();
    }
}
