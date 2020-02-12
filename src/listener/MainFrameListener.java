package listener;

import gui.MainFrame;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Prongs
 * @date 2020/2/4 15:03
 */
public class MainFrameListener implements ActionListener, DocumentListener, KeyListener {

    private MainFrame mainFrame;

    public MainFrameListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void send() {
        //如果输入内容为空不发送
        if ((MainFrame.type == 0) && ("".equals(mainFrame.getComm()))) {
            return;
        }
        //获取输入区的内容
        String comm = mainFrame.getComm();
        if (MainFrame.type == 1) {
            //发送内容是文件，保存格式化的文件记录
            comm = mainFrame.sendFile();
            MainFrame.type = 0;
        }
        //每发送一次消息就保存一次，读取一次
        mainFrame.save(comm);
        mainFrame.getTextInput().setText(null);
        mainFrame.read();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String send = "发送";
        String file = "文件";
        if (send.equals(e.getActionCommand())) {
            send();
        } else if (file.equals(e.getActionCommand())) {
            mainFrame.choseFile();
        }
    }

//    /**
//     * “文件”按钮的事件处理
//     */
//    private void choseFile(){
//        //1、创建文件对话框
//        FileDialog dialog = new FileDialog(mainFrame.getFrame());
//        dialog.setVisible(true);
//        //2、获取选择文件
//        sendFile=new File(dialog.getDirectory()+dialog.getFile());
//        //3、只有选中了文件，才置type为1
//        if (sendFile.exists()){
//            type = 1;
//            //看文件类型，能直接显示的直接显示
//            if(sendFile.getName().endsWith("png") || sendFile.getName().endsWith("jpeg") || sendFile.getName().endsWith("gif") || sendFile.getName().endsWith("jpg")){
//                mainFrame.getTextInput().insertIcon(new ImageIcon(sendFile.getPath()));
//            } else {
//                //不能直接显示的添加组件，点击相应的组件即可显示文件内容
//                JButton button = mainFrame.getFileButton(sendFile.getName(),false);
//                mainFrame.getTextInput().insertComponent(button);
//            }
//        }
//        //4、显示到输入区
//        //吹水：这里我来
//
//    }
//
//    /**
//     * 发送文件：
//     * 1、复制文件
//     * 2、返回文件路径（用于保存传送记录）
//     * @return
//     */
//    public String sendFile(){
//        //1、判断该用户是否存在历史文件目录，无则创建
//        String dirStr="D:\\users_files\\"+mainFrame.getUsername();
//        File dir=new File(dirStr);
//        if (!dir.exists()){
//            dir.mkdirs();
//        }
//        //2、创建目的文件
//        File aimedFile=new File(dirStr+"\\"+sendFile.getName());
//        //4、复制
//        copy(sendFile, aimedFile);
//        //5、返回格式化的文件发送记录（如下，以“.”切割，后半段为文件路径）
//        return "$$FILE."+aimedFile.getName()+"\n";
//    }
//
//    /**
//     * 文件传输方法
//     * @param srcFile 被复制的文件路径
//     * @throws IOException
//     */
//    public void copy(File srcFile,File aimedFile) {
//        //创建输入流对象 将源文件File对象传入其形参
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(srcFile);
//            //获取目标路径的File对象，关键在于目标路径问题，注意复制访问
//            //只能是文件不能是文件夹 否则拒绝访问，getpath()得到具体带盘符的路径
//            //getName()得到的是文件的名称，两者字符串拼接就是具体的目标路径（目标文件//路径）
//            //创建输出流对象
//            FileOutputStream bos = new FileOutputStream(aimedFile);
//            //读写复制
//            int len;
//            byte[] b = new byte[1024];
//            while ((len = fis.read(b)) != -1) {
//                bos.write(b, 0, len);
//                bos.flush();
//            }
//            bos.close();
//            fis.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 输入区监听（选中文件后可能删除）
     *
     * @param e
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        //如果有删除就置type为0
        MainFrame.type = 0;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_ENTER) && (e.isControlDown())) {
            String temp = mainFrame.getComm();
            mainFrame.getTextInput().setText(temp + "\n");
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            send();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
