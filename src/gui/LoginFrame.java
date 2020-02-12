package gui;

import listener.EnterFrameListener;

import javax.swing.*;
import java.awt.*;

/**
 * 登录窗口类
 *
 * @author 邓宇良
 */
public class LoginFrame {
    /**
     * 窗口主体
     */
    private JFrame loginFrame;
    /**
     * 用户名标签
     */
    private JLabel nameLabel;
    /**
     * 密码标签
     */
    private JLabel passwordLabel;
    /**
     * 用户名输入框
     */
    private JTextField nameField;
    /**
     * 密码输入框
     */
    private JTextField passwordField;
    /**
     * 登录按钮
     */
    private JButton enterButton;
    /**
     * 注册按钮
     */
    private JButton registerButton;

    /**
     * 布局容器（可以不用看）
     */
    private Box boxName;
    private Box boxPassword;
    private Box boxButton;
    private Box boxAll;
    private JPanel panelAll;

    /**
     * 账户名
     */
    private String userName;

    private EnterFrameListener listener = new EnterFrameListener(this);
    private String path = System.getProperty("user.dir");

    /**
     * 初始化窗口
     */
    public void init() {
        loginFrame = new JFrame("文件传输助手");
        nameLabel = new JLabel("用户名：");
        passwordLabel = new JLabel("密    码：");
        nameField = new JTextField(20);
        passwordField = new JTextField(20);
        enterButton = new JButton("这不是按钮");
        enterButton.setActionCommand("登录");
        enterButton.setBorder(null);
        enterButton.setBackground(Color.white);
        registerButton = new JButton("加油，代码不只有诗，还有bug！");
        registerButton.setActionCommand("注册");
        registerButton.setBorder(null);
        registerButton.setBackground(Color.white);

        boxName = Box.createHorizontalBox();
        boxName.add(nameLabel);
        boxName.add(Box.createVerticalStrut(40));
        boxName.add(nameField);

        boxPassword = Box.createHorizontalBox();
        boxPassword.add(passwordLabel);
        boxPassword.add(Box.createVerticalStrut(40));
        boxPassword.add(passwordField);

        boxButton = Box.createHorizontalBox();
        boxButton.add(enterButton);
        boxButton.add(Box.createHorizontalStrut(40));
        boxButton.add(registerButton);

        boxAll = Box.createVerticalBox();
        boxAll.add(boxName);
        boxAll.add(Box.createVerticalStrut(20));
        boxAll.add(boxPassword);
        boxAll.add(Box.createVerticalStrut(20));
        boxAll.add(boxButton);
        boxAll.add(Box.createVerticalStrut(20));

        panelAll = new JPanel();
        panelAll.add(boxAll);

        loginFrame.setVisible(true);
        loginFrame.add(panelAll);
        loginFrame.setSize(450, 250);
        loginFrame.setLocation(300, 300);
        loginFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "\\src\\image\\file.png"));
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //统一注册监听器
        addListener();
    }

    /**
     * 为以下组件注册监听器
     * 密码输入框
     * 登录按钮
     * 注册按钮
     */
    public void addListener() {
        passwordField.addActionListener(listener);
        passwordField.addKeyListener(listener);
        enterButton.addActionListener(listener);
        registerButton.addActionListener(listener);
    }

    /**
     * 获取输入的用户名以及密码
     *
     * @return 返回“用户名-密码”格式的字符串
     */
    public String getMessage() {
        return nameField.getText() + "-" + passwordField.getText();
    }

    /**
     * 获取账户名
     * @return 账户名称
     */
    public String getUserName() {
        userName = nameField.getText();
        return userName;
    }

    /**
     * 关闭登录窗口
     */
    public void close() {
        loginFrame.setVisible(false);
    }

    public static void main(String[] args) {
        //下面的语句可以删除
//        String message = "运行项目前确定已经将register.txt文件放在D盘中!按确定继续，取消停止运行";
//        int flag = JOptionPane.showConfirmDialog(null, message, "温馨提示：", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
//        if (flag == 0) {
            new LoginFrame().init();
//        }
    }
}
