package listener;

import gui.ImagePane;
import gui.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 这是负责调出表情面板的事件监听器类
 */
public class ImageListener implements ActionListener {
    //获取表情面板对象
    private ImagePane imagePane ;//= new ImagePane() ;

    private MainFrame mainFrame;


    /**
     * 带参构造器
     * @param mainFrame 获取主窗口的显示域
     */
    public ImageListener(MainFrame mainFrame){
        this.mainFrame = mainFrame;
    }

    /**
     * 事件监听方法
     * @param e 事件参数
     */
    @Override
    public void actionPerformed(ActionEvent e) {
            imagePane = new ImagePane(mainFrame);
            imagePane.init();
            imagePane.addListener(new SelectionImageListener(mainFrame,imagePane));
    }
}
