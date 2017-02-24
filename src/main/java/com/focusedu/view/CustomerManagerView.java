package com.focusedu.view;

import com.focusedu.model.Customer;
import com.focusedu.service.CustomerService;
import com.focusedu.view.customer.CustomerInfoView;
import org.springframework.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * CustomerManagerView
 *
 * @author liuruichao
 * @date 15/9/14 下午2:46
 */
public class CustomerManagerView extends JFrame {
    private JLabel cusIdLbl, emailLbl, moneyLbl, integralLbl, queryLbl, isBindLbl;
    private JTextField cusIdIn, emailIn, moneyIn, integralIn, queryIn, isBindIn;
    private JButton queryBtn, updateBtn, showInfo;
    private JPanel topPanel, centerPanel, cusIdPanel, emailPanel, moneyPanel, integralPanel, isBindPanel;

    private CustomerService customerService = new CustomerService();

    public CustomerManagerView() {
        setTitle("测试专用");
        setSize(700, 500);
        setLayout(new BorderLayout());

        topPanel = new JPanel();
        queryLbl = new JLabel("id/email");
        queryIn = new JTextField(25);
        queryBtn = new JButton("query");
        updateBtn = new JButton("update");
        showInfo = new JButton("info");
        topPanel.add(queryLbl);
        topPanel.add(queryIn);
        topPanel.add(queryBtn);
        topPanel.add(updateBtn);
        topPanel.add(showInfo);

        queryBtn.addActionListener(e -> {
            if (e.getActionCommand().equals("query")) {
                int cusId = 0;
                String query = queryIn.getText();
                Customer customer = null;
                try {
                    cusId = Integer.valueOf(query);
                } catch (Exception e1) {
                }
                try {
                    customer = customerService.queryCustomer(cusId, query);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                cusIdIn.setText(customer.cusId + "");
                emailIn.setText(customer.email);
                moneyIn.setText(customer.money + "");
                integralIn.setText(customer.integral + "");
                isBindIn.setText(customer.isBind + "");
                repaint();
            }
        });

        updateBtn.addActionListener(e -> {
            if (e.getActionCommand().equals("update")) {
                try {
                    int cusId = Integer.valueOf(cusIdIn.getText());
                    String email = emailIn.getText();
                    double money = Double.valueOf(moneyIn.getText());
                    int integral = Integer.valueOf(integralIn.getText());
                    int isBind = Integer.valueOf(isBindIn.getText());
                    customerService.updateCustomer(cusId, money, integral, isBind);
                    JOptionPane.showMessageDialog(this, "修改成功！");
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(this, "参数错误！");
                }
            }
        });

        showInfo.addActionListener(e -> {
            if (StringUtils.isEmpty(cusIdIn.getText().trim())) {
                JOptionPane.showMessageDialog(this, "不知道说啥了。");
            }
            int cusId = Integer.valueOf(cusIdIn.getText());
            new CustomerInfoView(cusId, customerService);
        });

        centerPanel = new JPanel(new GridLayout(5, 1));
        cusIdPanel = new JPanel();
        cusIdLbl = new JLabel("用户id:");
        cusIdIn = new JTextField(25);
        cusIdIn.setText("");
        emailPanel = new JPanel();
        emailLbl = new JLabel("email:");
        emailIn = new JTextField(25);
        emailIn.setText("");
        moneyPanel = new JPanel();
        moneyLbl = new JLabel("余额:");
        moneyIn = new JTextField(25);
        moneyIn.setText("");
        integralPanel = new JPanel();
        integralLbl = new JLabel("积分:");
        integralIn = new JTextField(25);
        integralIn.setText("");
        isBindPanel = new JPanel();
        isBindLbl = new JLabel("是否绑定:");
        isBindIn = new JTextField(25);
        isBindIn.setText("");

        cusIdPanel.add(cusIdLbl);
        cusIdPanel.add(cusIdIn);

        emailPanel.add(emailLbl);
        emailPanel.add(emailIn);

        moneyPanel.add(moneyLbl);
        moneyPanel.add(moneyIn);

        integralPanel.add(integralLbl);
        integralPanel.add(integralIn);

        isBindPanel.add(isBindLbl);
        isBindPanel.add(isBindIn);

        centerPanel.add(cusIdPanel);
        centerPanel.add(emailPanel);
        centerPanel.add(moneyPanel);
        centerPanel.add(integralPanel);
        centerPanel.add(isBindPanel);

        cusIdIn.setEnabled(false);
        emailIn.setEnabled(false);

        add("North", topPanel);
        add("Center", centerPanel);
        // 设置基本属性
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
