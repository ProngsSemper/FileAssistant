package listener;

import gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类负责显示历史记录
 */
public class HistoryListener implements ActionListener {
    private JFrame frame;
    private JScrollPane scrollShow;
    private JTextPane textPane;
    private String userName;
    private MainFrame mainFrame;

    public HistoryListener(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        this.userName = mainFrame.getUsername();
    }
    /**
     * 初始化一个显示框
     */
    public void init() {
        frame = new JFrame(userName + ".历史记录");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "\\src\\image\\file1.png"));
        textPane = new JTextPane();
        textPane.setFont(new Font(null,Font.PLAIN,20));
        scrollShow = new JScrollPane(textPane);
        frame.add(scrollShow);
        frame.setBackground(Color.WHITE);
        //大小
        frame.setSize(500, 500);
        //位置
        frame.setLocation(300, 150);
        //是否可见
        frame.setVisible(true);
        //关闭进程时关闭窗口
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    /**
     * 消息的读取 因为两个listener都要用 就写这里了增强代码复用性
     */
    public void read() {
        //读取之前先把内容清空，再重新显示
        textPane.setText(null);
        try {
            File file = new File("D:\\" + userName + ".txt");
            //文件不存在则创建文件
            if (!file.exists()) {
                file.createNewFile();
            }
            // 读取文件
            FileReader fileReader = new FileReader(file);
            BufferedReader in = new BufferedReader(fileReader);
            String s;

            //positions集合保留插入位置，images集合保留每张图片路径
            java.util.List<Integer> positions = new ArrayList<>();
            java.util.List<String> images = new ArrayList<>();

            //filePositions集合保留文件的插入位置，files集合保存文件名
            java.util.List<Integer> filePositions = new ArrayList<>();
            java.util.List<String> files = new ArrayList<>();
            //非文本内容的标识集合，0表示图片，1表示文件
            List<Integer> nonText = new ArrayList<>();
            //把读取到的内容放到显示消息的组件上
            while ((s = in.readLine()) != null) {
                //如果是图片的就则不需要显示路径
                if (s.endsWith(".png") && (!s.startsWith("$$FILE."))) {
                    //记录位置
                    int position = textPane.getText().length() + nonText.size();
                    //记录位置、路径信息
                    nonText.add(0);
                    images.add(s);
                    positions.add(position);
                    textPane.setText(textPane.getText());
                } else if (s.startsWith("$$FILE.")) {
                    int position = textPane.getText().length() + nonText.size();
                    nonText.add(1);
                    filePositions.add(position);
                    files.add(s.substring(7));
                }
                //普通文字信息
                else {
                    textPane.setText(textPane.getText() + s + "\n");
                }
            }
            int imgIndex = 0;
            int fileIndex = 0;
            for (Integer flag : nonText) {
                if (flag == 0) {
                    //图片
                    textPane.setCaretPosition(positions.get(imgIndex));
                    textPane.insertIcon(new ImageIcon(images.get(imgIndex)));
                    imgIndex = imgIndex + 1;
                } else if (flag == 1) {
                    if (files.get(fileIndex).endsWith(".jpg") || files.get(fileIndex).endsWith(".jpeg") || files.get(fileIndex).endsWith(".png") || files.get(fileIndex).endsWith(".gif")) {
                        textPane.setCaretPosition(filePositions.get(fileIndex));
                        textPane.insertIcon(new ImageIcon("D:\\users_files\\" + userName + "\\" + files.get(fileIndex)));
                    } else {
                        textPane.setCaretPosition(filePositions.get(fileIndex));
                        textPane.insertComponent(mainFrame.getFileButton(files.get(fileIndex),true));
                    }
                    fileIndex = fileIndex+1;
                }
            }
            //将光标位置放回文末
            textPane.setCaretPosition(textPane.getText().length());
            // 释放资源
            in.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        init();
        read();
    }
}
