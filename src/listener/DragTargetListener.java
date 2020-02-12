package listener;

import gui.MainFrame;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;

/**
 * @author Prongs
 * @date 2020/2/8 11:07
 */
public class DragTargetListener implements DropTargetListener {
    private MainFrame mainFrame;

    public DragTargetListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {

    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {

    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {

    }

    @Override
    public void dragExit(DropTargetEvent dte) {

    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        boolean isAccept = false;

        try {
            /*
             * 1. 文件: 判断拖拽目标是否支持文件列表数据（即拖拽的是否是文件或文件夹, 支持同时拖拽多个）
             */
            if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                // 接收拖拽目标数据
                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                isAccept = true;

                // 以文件集合的形式获取数据
                List<File> files = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                // 把文件路径输出到文本区域
                if (files.size() > 0) {
                    StringBuilder filePaths = new StringBuilder();
                    for (File file : files) {
                        filePaths.append(file.getAbsolutePath());
                        JButton button = mainFrame.getFileButton(filePaths.toString(),false);
                        mainFrame.getTextInput().insertComponent(button);
                        MainFrame.type = 1;
                        mainFrame.setFile(new File(filePaths.toString()));
                    }
                }
            }

            /*
             * 2. 文本: 判断拖拽目标是否支持文本数据（即拖拽的是否是文本内容, 或者是否支持以文本的形式获取）
             */
            if (dtde.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                // 接收拖拽目标数据
                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                isAccept = true;

                // 以文本的形式获取数据
                String text = dtde.getTransferable().getTransferData(DataFlavor.stringFlavor).toString();
                // 输出到文本区域
                mainFrame.getTextInput().setText(text + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 如果此次拖拽的数据是被接受的, 则必须设置拖拽完成（否则可能会看到拖拽目标返回原位置, 造成视觉上以为是不支持拖拽的错误效果）
        if (isAccept) {
            dtde.dropComplete(true);
        }
    }

}
