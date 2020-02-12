package gui;

import listener.SelectionImageListener;


import javax.swing.*;
import java.awt.*;

/**
 * 该类是表情面板的窗口类
 */
public class ImagePane {
    //小窗口
    private JDialog dialog ;
    //获取图片对象
    private Image[]image = new Image[30];
    //接收图片对象
    private ImageIcon[] imageIcons = new ImageIcon[30];
    //按钮
    private JButton[] buttons = new JButton[30];
    //获取当前工程路径
    private String path = System.getProperty("user.dir") + "/src/image/";

    private MainFrame mainFrame;

    public ImagePane(MainFrame mainFrame){
        this.mainFrame = mainFrame;
    }

    /**
     * 初始化方法
     */
    public void init(){
        dialog = new JDialog(mainFrame.getFrame());
        dialog.setLayout(new GridLayout(6,5));
        for(int i = 0; i < 30;i++){
            String fileName = path + (i + 1) + ".png";
            image[i] = Toolkit.getDefaultToolkit().getImage(fileName);
            imageIcons[i] = new ImageIcon(image[i]);
            buttons[i] = new JButton();
            buttons[i].setName(fileName);
            buttons[i].setSize(30,30);
            buttons[i].setOpaque(false);
            buttons[i].setBorderPainted(false);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setFocusPainted(false);
            buttons[i].setBorder(null);
            buttons[i].setIcon(imageIcons[i]);
            dialog.add(buttons[i]);
        }


        dialog.setSize(300,250);
        dialog.setLocation(840, 400);
        dialog.setVisible(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    }

    /**
     * 注册监听器
     * @param selectionImageListener 监听器类参数
     */
    public void addListener(SelectionImageListener selectionImageListener){
        for(int i = 0; i < 30 ;i++){
            buttons[i].addActionListener(selectionImageListener);
        }
    }

    /**
     * 获取按钮组
     * @return 返回按钮数组
     */
    public JButton[] getButtons(){
        return buttons;
    }
}
