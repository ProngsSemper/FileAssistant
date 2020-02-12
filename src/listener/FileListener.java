package listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * 该类负责查看文本文件
 */
public class FileListener implements ActionListener {
    private JFrame frame;
    private JScrollPane scrollShow;
    private JTextPane textPane;
    private String fileName;
    private String path = "D:\\users_files\\";
    private boolean flag = true;

    public FileListener(String userName, String fileName){
        this.fileName = fileName;
        if(!userName.equals("")) {
            path = path + userName + "\\";
        } else {
            flag = false;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            init();
            txt();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 初始化一个显示框
     */
    public void init() {
        frame = new JFrame(fileName);
        textPane = new JTextPane();
        textPane.setFont(new Font(null,Font.PLAIN,20));
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "\\src\\image\\file1.png"));
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
    public void txt() throws FileNotFoundException {
        try {
            FileReader fileReader;
            if(flag){
                fileReader = new FileReader(path + fileName);
            } else{
                System.out.println(fileName);
                fileReader = new FileReader(fileName);
            }
            BufferedReader in = new BufferedReader(fileReader);
            String s;
            while ((s = in.readLine()) != null){
                textPane.setText(textPane.getText() + s + "\n");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
