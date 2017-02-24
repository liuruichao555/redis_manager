package com.focusedu.view;

import com.focus_pay.shop.controller.dto.PermissionDto;
import com.focusedu.model.Customer;
import com.focusedu.service.CustomerService;
import com.focusedu.utils.CommonUtils;
import com.focusedu.utils.RedisUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

/**
 * RedisManagerView
 *
 * @author liuruichao
 * @date 15/9/14 下午2:46
 */
public class RedisManagerView extends JFrame {
    private JTextArea valueText;
    private JList keyList;
    private JComboBox dbSelect;
    private JPanel centerPanel, topPanel;
    private JScrollPane scrollPanel;
    private RedisUtils redisUtils;
    private JTextField queryIn, hostIn, portIn;
    private JLabel hostDesc, ipDesc, dbDesc, queryDesc;
    private JButton queryBtn, updateBtn, connectBtn;
    private int maxWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int maxHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    private Font defaultFont = new Font("黑体", Font.BOLD, 32);
    private CustomerService customerService = new CustomerService();

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public RedisManagerView() throws IOException {
        setTitle("redis管理");
        setSize(maxWidth, maxHeight);
        setLayout(new BorderLayout());

        initTop();

        keyList = new JList();
        scrollPanel = new JScrollPane(keyList);
        scrollPanel.setSize((maxWidth / 2 - 10), maxHeight);
        keyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        keyList.addListSelectionListener(e -> {
            Object obj = ((JList) e.getSource()).getSelectedValue();
            if (e.getValueIsAdjusting()) {
                String key = obj.toString();
                // 查询数据
                Object valueObj = redisUtils.get(key);
                if (valueObj instanceof PermissionDto) {
                    // 会员相关
                    String jsonStr = gson.toJson(valueObj);
                    valueText.setText(jsonStr);
                } if (valueObj instanceof ArrayList) {
                    String jsonStr = gson.toJson(valueObj);
                    valueText.setText(jsonStr);
                } else {
                    // 其他
                    valueText.setText(valueObj.toString());
                }
            }
        });

        valueText = new JTextArea();
        valueText.setSize((maxWidth / 2 - 10), maxHeight);
        valueText.setLineWrap(true);
        valueText.setFont(defaultFont);

        centerPanel = new JPanel(new GridLayout(1, 2));
        getContentPane().add("Center", centerPanel);
        centerPanel.add(scrollPanel);
        centerPanel.add(valueText);

        // 设置基本属性
        setVisible(true);
    }

    private void initTop() {
        // 选择redis数据库
        dbSelect = new JComboBox();
        dbSelect.setEnabled(false);
        dbSelect.addItem("===请选择===");
        for (int i = 0; i <= 15; i++) {
            dbSelect.addItem(i);
        }
        // 监听选择事件
        dbSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("comboBoxChanged")) {
                    try {
                        clearResult();
                        redisUtils.select((Integer) dbSelect.getSelectedItem());
                        // 改变数据库事件
                        keyList.removeAll();
                        keyList.setListData(CommonUtils.convertCollectionToArr(redisUtils.keys()));
                    } catch (Exception e1) {
                        keyList.removeAll();
                        keyList.setListData(new Object[]{});
                    }
                }
            }
        });

        // 模糊查询
        queryIn = new JTextField("", 30);
        queryBtn = new JButton("query");
        queryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("query")) {
                    String queryValue = queryIn.getText();
                    keyList.removeAll();
                    Set<String> keys = redisUtils.keys(queryValue);
                    if (keys == null || keys.size() <= 0) {
                        // email查询
                        String email = queryValue;
                        try {
                            Customer customer = customerService.queryCustomer(0, email);
                            if (customer != null && customer.cusId > 0) {
                                keys = redisUtils.keys(customer.cusId + "");
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                    keyList.setListData(CommonUtils.convertCollectionToArr(keys));
                    clearResult();
                }
            }
        });

        updateBtn = new JButton("update");
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("update")) {
                    String value = valueText.getText();
                    String key = keyList.getSelectedValue().toString();
                    Object obj = value;
                    try {
                        PermissionDto permissionDto = gson.fromJson(value, PermissionDto.class);
                        obj = permissionDto;
                    } catch (Exception e1) {
                    }
                    redisUtils.set(key, obj);
                }
            }
        });

        topPanel = new JPanel(new GridLayout(2, 1));

        JPanel redisConfigPanel = new JPanel();
        hostIn = new JTextField("121.41.49.105", 30);
        portIn = new JTextField("6379", 5);
        connectBtn = new JButton("connect");
        connectBtn.addActionListener(e -> {
            if (e.getActionCommand().equals("connect")) {
                String host = hostIn.getText().trim();
                String port = portIn.getText().trim();
                if (StringUtils.isEmpty(host) || StringUtils.isEmpty(port)) {
                    JOptionPane.showMessageDialog(this, "redis ip地址或端口没填写。傻波。");
                    return;
                }
                if (redisUtils != null) {
                    redisUtils.destroy();
                }
                redisUtils = new RedisUtils(host, Integer.valueOf(port));
                try {
                    redisUtils.getDbSize();
                    dbSelect.setEnabled(true);
                    clearResult();
                    dbSelect.setSelectedItem("===请选择===");
                } catch (Exception ex) {
                    dbSelect.setEnabled(false);
                    JOptionPane.showMessageDialog(this, "连接失败，检查ip和端口，并且检查是否运行redis。");
                }
            }
        });
        hostDesc = new JLabel("ip:");
        ipDesc = new JLabel("port:");
        dbDesc = new JLabel("db:");
        queryDesc = new JLabel("key:");
        redisConfigPanel.add(hostDesc);
        redisConfigPanel.add(hostIn);
        redisConfigPanel.add(ipDesc);
        redisConfigPanel.add(portIn);
        redisConfigPanel.add(connectBtn);

        JPanel actionPanel = new JPanel();
        actionPanel.add(dbDesc);
        actionPanel.add(dbSelect);
        actionPanel.add(queryDesc);
        actionPanel.add(queryIn);
        actionPanel.add(queryBtn);
        actionPanel.add(updateBtn);

        topPanel.add(redisConfigPanel);
        topPanel.add(actionPanel);
        add("North", topPanel);
    }

    private void clearResult() {
        valueText.setText("");
    }
}
