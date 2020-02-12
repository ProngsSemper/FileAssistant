package gui;

import listener.*;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.io.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.awt.Color.BLACK;

/**
 * 文件助手主体类
 *
 * @author 邓宇良
 */
public class MainFrame {
    /**
     * 主窗口
     */
    private JFrame mainFrame;
    /**
     * 示文本域
     */
    private JTextPane textShow;
    /**
     * 显示框滚动条
     */
    private JScrollPane scrollShow;
    /**
     * 输入框的滚动条
     */
    private JScrollPane scrollInput;
    /**
     * 输入文本域
     */
    private JTextPane textInput;
    /**
     * 发送按钮
     */
    private JButton sendButton;

    /**
     * 钮  表情、文件、历史记录
     */
    private JButton expression;
    private JButton file;
    private JButton historyRecord;

    /**
     * 布局
     */
    private Box boxText = Box.createHorizontalBox();
    private Box boxButton = Box.createHorizontalBox();
    private Box boxAll = Box.createVerticalBox();

    /**
     * 监听器
     */
    private ImageListener imageListener;
    private MainFrameListener listener = new MainFrameListener(this);

    private String username;

    private String path = System.getProperty("user.dir");

    /**
     * 发送内容格式，0——文字，1——文件
     */
    public static int type=0;

    /**
     * 要发送的文件
     */
    private static File sendFile;

    /**
     * 初始化窗口
     */
    public void init(String name) {

        username = name;

        mainFrame = new JFrame("文件传输助手");



        textShow = new JTextPane();
        textShow.setEnabled(false);
        textShow.setFont(new Font(null, Font.PLAIN, 20));
        textShow.setSize(500, 200);
        textShow.setBackground(Color.black);
        scrollShow = new JScrollPane(textShow);

        textInput = new JTextPane();
        textInput.setSize(500, 200);
        textInput.setBackground(Color.WHITE);
        scrollInput = new JScrollPane(textInput);

        sendButton = new JButton("发送");
        sendButton.setBackground(Color.WHITE);
        sendButton.setBorderPainted(false);

        boxText.add(scrollInput);
        boxText.add(sendButton);

        expression = new JButton();
        Icon icon1 = new ImageIcon(path + "\\src\\image\\em.png");
        expression.setIcon(icon1);
        expression.setBorder(null);
        expression.setBackground(Color.white);
        file = new JButton();
        Icon icon2 = new ImageIcon(path + "\\src\\image\\file.png");
        file.setIcon(icon2);
        file.setBorder(null);
        file.setBackground(Color.white);
        file.setActionCommand("文件");
        historyRecord = new JButton();
        Icon icon3 = new ImageIcon(path + "\\src\\image\\his.png");
        historyRecord.setIcon(icon3);
        historyRecord.setBackground(Color.white);
        boxButton.add(Box.createHorizontalStrut(5));
        boxButton.add(expression);
        boxButton.add(Box.createHorizontalStrut(20));
        boxButton.add(file);
        boxButton.add(Box.createHorizontalStrut(20));
        boxButton.add(historyRecord);
        boxButton.add(Box.createHorizontalStrut(20));
        boxButton.add(Box.createHorizontalGlue());
        boxButton.repaint();

        boxAll.add(boxButton);
        boxAll.add(Box.createVerticalStrut(5));
        boxAll.add(boxText);

        mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(path + "\\src\\image\\file.png"));
        mainFrame.setLayout(new BorderLayout(30, 5));
        mainFrame.add(scrollShow, BorderLayout.CENTER);
        mainFrame.add(boxAll, BorderLayout.SOUTH);
        mainFrame.setBackground(Color.WHITE);
        //大小
        mainFrame.setSize(550, 500);
        //位置
        mainFrame.setLocation(300, 150);
        //是否可见
        mainFrame.setVisible(true);
        //关闭进程时关闭进程
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        imageListener = new ImageListener(this);
        addListener();
    }

    public String getComm() {
        return textInput.getText();
    }

    public String getUsername() {
        return username;
    }

    public JTextPane getTextInput() {
        return textInput;
    }


    public JFrame getFrame() {
        return mainFrame;
    }

    public void addListener() {
        sendButton.addActionListener(listener);
        file.addActionListener(listener);
        expression.addActionListener(imageListener);
        textInput.getDocument().addDocumentListener(listener);
        textInput.addKeyListener(listener);
        historyRecord.addActionListener(new HistoryListener(this));
        DragTargetListener dtl = new DragTargetListener(this);
        new DropTarget(textInput, DnDConstants.ACTION_COPY_OR_MOVE, dtl, true);
    }

    /**
     * 消息的读取 因为两个listener都要用 就写这里了增强代码复用性
     */
    public void read() {
        //读取之前先把内容清空，再重新显示
        textShow.setText(null);
        try {
            File file = new File("D:\\" + username + ".txt");
            //文件不存在则创建文件
            if (!file.exists()) {
                file.createNewFile();
            }
            // 读取文件
            FileReader fileReader = new FileReader(file);
            BufferedReader in = new BufferedReader(fileReader);
            String s;

            //positions集合保留插入位置，images集合保留每张图片路径
            List<Integer> positions = new ArrayList<>();
            List<String> images = new ArrayList<>();

            //filePositions集合保留文件的插入位置，files集合保存文件名
            List<Integer> filePositions = new ArrayList<>();
            List<String> files = new ArrayList<>();
            //非文本内容的标识集合，0表示图片，1表示文件
            List<Integer> nonText = new ArrayList<>();
            //把读取到的内容放到显示消息的组件上
            while ((s = in.readLine()) != null) {
                //如果是图片的就则不需要显示路径
                if (s.endsWith(".png") && (!s.startsWith("$$FILE."))) {
                    //记录位置
                    int position = textShow.getText().length() + nonText.size();
                    //记录位置、路径信息
                    nonText.add(0);
                    images.add(s);
                    positions.add(position);
                    textShow.setText(textShow.getText());
                } else if (s.startsWith("$$FILE.")) {
                    int position = textShow.getText().length() + nonText.size();
                    nonText.add(1);
                    filePositions.add(position);
                    files.add(s.substring(7));
                }
                //普通文字信息
                else {
                    textShow.setText(textShow.getText() + s + "\n");
                }
            }
            int imgIndex = 0;
            int fileIndex = 0;
            for (Integer flag : nonText) {
                if (flag == 0) {
                    //图片
                    textShow.setCaretPosition(positions.get(imgIndex));
                    textShow.insertIcon(new ImageIcon(images.get(imgIndex)));
                    imgIndex = imgIndex + 1;
                } else if (flag == 1) {
                    if (files.get(fileIndex).endsWith(".jpg") || files.get(fileIndex).endsWith(".jpeg") || files.get(fileIndex).endsWith(".png") || files.get(fileIndex).endsWith(".gif")) {
                        textShow.setCaretPosition(filePositions.get(fileIndex));
                        textShow.insertIcon(new ImageIcon("D:\\users_files\\" + username + "\\" + files.get(fileIndex)));
                    } else {
                        textShow.setCaretPosition(filePositions.get(fileIndex));
                        textShow.insertComponent(getFileButton(files.get(fileIndex),true));
                    }
                    fileIndex = fileIndex+1;
                }
            }
            //将光标位置放回文末
            textShow.setCaretPosition(textShow.getText().length());
            // 释放资源
            in.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存方法
     *
     * @param newMessage 未写入历史记录的信息
     */
    public void save(String newMessage) {
        try {
            //消息保存路径
            String historyPath = "D:\\" + getUsername() + ".txt";
            File file = new File(historyPath);
            // 写入文件
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write(DateFormat.getDateTimeInstance().format(new Date()) + "\n" + newMessage + "\r\n");
            // 释放资源
            out.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取被插入在显示域上的按钮
     *
     * @param fileName 文件名
     * @param flag 判断是显示在显示域(true)还是输入区(false)
     * @return 返回一个按钮引用对象
     */
    public JButton getFileButton(String fileName,boolean flag) {
        JButton button = new JButton();
        button.setText(fileName);
        button.setIcon(new ImageIcon(path + "\\src\\image\\file3.png"));
        if(flag){
            button.addActionListener(new FileListener(username, fileName));
        }
        else{
            button.addActionListener(new FileListener("", fileName));
        }
        button.setBackground(BLACK);
        button.setBorder(null);
        button.setSize(50, 30);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setAutoscrolls(true);
        return button;
    }


    //----------------------------------测试区----------------------------------------
    //ps:  为了能够将拖拽的效果实现，我把发送文件的choseFile、sendFile、copy三个方法搬到了MainFrame中，然后新增了一个
    //     setFile方法为sendFile赋值，因为拖拽文件之后获取到的文件路径要使用，而原来的文件保存方法是调用sendFile()方法，
    //     但是核心还是sendFile变量保存路径
    /**
     * “文件”按钮的事件处理
     */
    public File choseFile(){
        //1、创建文件对话框
        FileDialog dialog = new FileDialog(getFrame());
        dialog.setVisible(true);
        //2、获取选择文件
        sendFile=new File(dialog.getDirectory()+dialog.getFile());
        //3、只有选中了文件，才置type为1
        if (sendFile.exists()){
            type = 1;
            //看文件类型，能直接显示的直接显示
            if(sendFile.getName().endsWith("png") || sendFile.getName().endsWith("jpeg") || sendFile.getName().endsWith("gif") || sendFile.getName().endsWith("jpg")){
                getTextInput().insertIcon(new ImageIcon(sendFile.getPath()));
            } else {
                //不能直接显示的添加组件，点击相应的组件即可显示文件内容
                JButton button = getFileButton(sendFile.getPath(),false);
                getTextInput().insertComponent(button);
            }
        }

        return sendFile;
        //4、显示到输入区
        //吹水：这里我来

    }

    /**
     * 发送文件：
     * 1、复制文件
     * 2、返回文件路径（用于保存传送记录）
     * @return  返回选择的文件对象的路径
     */
    public String sendFile(){
        //1、判断该用户是否存在历史文件目录，无则创建
        String dirStr="D:\\users_files\\"+getUsername();
        File dir=new File(dirStr);
        if (!dir.exists()){
            dir.mkdirs();
        }
        //2、创建目的文件
        File aimedFile=new File(dirStr+"\\"+sendFile.getName());
        //4、复制
        copy(sendFile, aimedFile);
        //5、返回格式化的文件发送记录（如下，以“.”切割，后半段为文件路径）
        return "$$FILE."+aimedFile.getName()+"\n";
    }

    /**
     * 文件传输方法
     * @param srcFile 被复制的文件路径
     * @throws IOException
     */
    public void copy(File srcFile,File aimedFile) {
        //创建输入流对象 将源文件File对象传入其形参
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(srcFile);
            //获取目标路径的File对象，关键在于目标路径问题，注意复制访问
            //只能是文件不能是文件夹 否则拒绝访问，getpath()得到具体带盘符的路径
            //getName()得到的是文件的名称，两者字符串拼接就是具体的目标路径（目标文件//路径）
            //创建输出流对象
            FileOutputStream bos = new FileOutputStream(aimedFile);
            //读写复制
            int len;
            byte[] b = new byte[1024];
            while ((len = fis.read(b)) != -1) {
                bos.write(b, 0, len);
                bos.flush();
            }
            bos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFile(File sendFile){
        this.sendFile = sendFile;
    }



}
