package com.focusedu.view.customer;

import com.focusedu.base.DbResult;
import com.focusedu.service.CustomerService;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * CustomerInfoView
 *
 * @author liuruichao
 * @date 15/9/16 下午1:58
 */
public class CustomerInfoView extends JFrame {
    private int cusId;
    private CustomerService customerService;
    private DbResult dbResult;
    Map<String, JTextField> paramsIn;

    public CustomerInfoView(int cusId, CustomerService customerService) {
        paramsIn = new HashMap<>();
        this.customerService = customerService;
        dbResult = customerService.getCustomerById(cusId);
        if (dbResult == null) {
            JOptionPane.showMessageDialog(this, "没有查到此用户头像。");
            return;
        }

        setLayout(new GridLayout(dbResult.getColumnNames().size() + 1, 1));
        if (dbResult.next()) {
            for (int i = 0; i < dbResult.getColumnNames().size(); i++) {
                JPanel panel = new JPanel(new GridLayout(1, 2));
                String columnName = dbResult.getColumnNames().get(i);
                Object columnValue = dbResult.get(columnName);
                panel.add(new JLabel(columnName));
                JTextField textField = new JTextField();
                textField.setText(columnValue == null ? "null" : columnValue.toString());
                panel.add(textField);
                paramsIn.put(columnName, textField);
                add(panel);
            }
            JButton updateBtn = new JButton("update");
            add(updateBtn);
            updateBtn.addActionListener(e -> {
                if (e.getActionCommand().equals("update")) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    for (String key : paramsIn.keySet()) {
                        JTextField field = paramsIn.get(key);
                        String newValue = field.getText();
                        params.put(key, newValue);
                    }
                    int count = customerService.updateCustomer(params);
                    if (count > 0) {
                        JOptionPane.showMessageDialog(this, "修改成功。");
                    } else {
                        JOptionPane.showMessageDialog(this, "修改失败。");
                    }
                }
            });
        }

        setSize(500, Toolkit.getDefaultToolkit().getScreenSize().height);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
