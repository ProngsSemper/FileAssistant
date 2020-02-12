package listener;

import gui.LoginFrame;
import gui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

/**
 * 事件监听者实现类
 *
 * @author 邓宇良
 */
public class EnterFrameListener implements ActionListener, KeyListener {

    /**
     * 获取被监听对象
     */
    private LoginFrame loginFrame;
    /**
     * 用户信息保存文件的位置
     */
    private File path = new File("D:\\register.txt");

    /**
     * 构造器
     *
     * @param loginFrame
     */
    public EnterFrameListener(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    /**
     * 响应事件方法
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String login = "登录";
        String register = "注册";
        if (login.equals(e.getActionCommand())) {
            enter();
        } else if (register.equals(e.getActionCommand())) {
            register();
        }
    }

    /**
     * 不用实现的方法
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * 监听回车键
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            enter();
        }

    }

    /**
     * 登录
     */
    public void enter() {
        //System.out.println("点击了登录~");
        try {
            if (!path.exists()) {
                path.createNewFile();
            }
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String text;
            boolean flag = true;
            while ((text = bufferedReader.readLine()) != null) {
                if (loginFrame.getMessage().equals(text)) {
                    MainFrame mainFrame = new MainFrame();
                    //加个参数是为了把用户名传递到主窗口
                    mainFrame.init(loginFrame.getUserName());
                    //读取消息记录
                    mainFrame.read();
                    flag = false;
                    loginFrame.close();
                    break;
                }
            }
            if (flag) {
                JOptionPane.showMessageDialog(null, "用户名不存在或密码错误！", "温馨提示", JOptionPane.WARNING_MESSAGE);
            }
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("register.txt文件不存在！");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取register.txt文件失败！");

        }

    }

    /**
     * 注册
     */
    public void register() {
        //System.out.println("点击了注册--");
        String name_password = loginFrame.getMessage();
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader buffereader = new BufferedReader(fileReader);
            String text;
            boolean flag = true;
            while ((text = buffereader.readLine()) != null) {
                //System.out.println(text);
                int index1 = text.indexOf("-");
                //System.out.println(index1);
                if (text.substring(0, index1).equals(name_password.substring(0, index1))) {
                    JOptionPane.showMessageDialog(null, "用户名已存在！请重新输入！", "温馨提示", JOptionPane.WARNING_MESSAGE);
                    flag = false;
                    break;
                }
            }
            if (flag) {
                System.out.println("开始写入");
                FileWriter fileWriter = new FileWriter(path, true);
                try {
                    fileWriter.write(name_password + "\r\n");
                    JOptionPane.showMessageDialog(null, "注册成功！请点击登录！", "温馨提示", JOptionPane.WARNING_MESSAGE);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                fileWriter.close();
                fileReader.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("文件不存在！");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取register.txt 文件失败！");
        }
    }

}
