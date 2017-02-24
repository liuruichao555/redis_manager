package com.focusedu.view;

import com.focusedu.service.ZookeeperService;
import org.apache.zookeeper.KeeperException;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.List;

/**
 * ZookeeperManagerView
 *
 * @author liuruichao
 * @date 15/10/23 下午1:46
 */
public class ZookeeperManagerView extends JFrame {
    private int maxWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int maxHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    private JTree tree;
    private JTextArea valueText;
    private Font defaultFont = new Font("黑体", Font.BOLD, 32);

    public ZookeeperManagerView() {
        setTitle("zookeeper");
        setSize(maxWidth, maxHeight);
        setLayout(new BorderLayout());

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("/");
        tree = new JTree(rootNode);
        try {
            java.util.List<String> list = ZookeeperService.getChildren("/");
            for (String key : list) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(key);
                rootNode.add(node);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tree.setFont(defaultFont);
        tree.setRowHeight(35);
        tree.addTreeSelectionListener(e -> {
            TreePath path = e.getPath();
            Object[] objs = path.getPath();
            StringBuffer zkPath = new StringBuffer();
            for (int i = 0; i < objs.length; i++) {
                if (i > 1) {
                    zkPath.append("/");
                }
                zkPath.append(objs[i]);
            }
            if (zkPath.toString().equals("/")) {
                return;
            }
            try {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
                        .getLastSelectedPathComponent();
                String value = ZookeeperService.getValue(zkPath.toString());
                if ("\"\"".equals(value)) {
                    List<String> keys = null;
                    try {
                        keys = ZookeeperService.getChildren(zkPath.toString());
                    } catch (KeeperException e1) {
                        e1.printStackTrace();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    if (path != null && keys != null && keys.size() > 0) {
                        for (String key : keys) {
                            node.add(new DefaultMutableTreeNode(key));
                        }
                    }
                } else {
                    valueText.setText(value);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        valueText = new JTextArea();
        valueText.setSize((maxWidth / 2 - 10), maxHeight);
        valueText.setLineWrap(true);
        valueText.setFont(defaultFont);
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        getContentPane().add("Center", centerPanel);
        centerPanel.add(tree);
        centerPanel.add(valueText);

        setVisible(true);
    }
}
