package listener;

import gui.ImagePane;
import gui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 该类负责发送显示的表情包
 */
public class SelectionImageListener implements ActionListener {
    public MainFrame mainFrame;
    private ImagePane imagePane;

    public SelectionImageListener(MainFrame mainFrame, ImagePane imagePane){
        this.mainFrame = mainFrame;
        this.imagePane = imagePane;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        for(JButton button : imagePane.getButtons()){
            if(button.equals(e.getSource())){
                Icon imageIcon = button.getIcon();
                //保存图片路径
                mainFrame.save(button.getName()+"\n");
                //将图片显示出来
//                    mainFrame.getTextShow().insertIcon(imageIcon);
                mainFrame.read();
                break;
            }
        }
    }
}
