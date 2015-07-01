import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SelectWindow extends JFrame implements ActionListener {
    JButton bt1;
    JButton bt2;
    JFileChooser fc;
    FileAccess fileAccess;

    public SelectWindow() {
        setTitle("FFT");
        setSize(600, 600);
        Container ct = getContentPane();
        ct.setLayout(new FlowLayout());
        bt1 = new JButton("Open File FileChooser");
        bt2 = new JButton("Save File FileChooser");

        this.add(bt1);
        this.add(bt2);

        bt1.addActionListener(this);
        bt2.addActionListener(this);

        fc = new JFileChooser();

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // [Open File FileChooser]をクリックした場合
        if (e.getSource() == bt1) {
            // File open FileChooserを表示して、戻り値をresultに保存
            int result = fc.showOpenDialog(SelectWindow.this);

            // (APPROVE_OPTION)FileChooserの"開く"ボタンをクリックした場合
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    getPath();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            // (CANCEL_OPTION)FileChooserの"取消し"ボタンをクリックした場合
            else if (result == JFileChooser.CANCEL_OPTION) {
                System.out.println("取消し");
            }
            // (ERROR_OPTION)FileChooserでエラーが発生した場合
            else if (result == JFileChooser.ERROR_OPTION) {
                System.out.println("エラー発生");
            }
        }
        // [Save File FileChooserをクリックした場合
        if (e.getSource() == bt2) {
            int result = fc.showSaveDialog(SelectWindow.this);

            // (APPROVE_OPTION)FileChooserの保存ボタンをクリックした場合
            if (result == JFileChooser.APPROVE_OPTION) {
                savePath();
            }

            // (CANCEL_OPTION) FileChooser取消しボタンをクリックした場合
            else if (result == JFileChooser.CANCEL_OPTION) {
                System.out.println("取消し");
            }

            // (ERROR_OPTION) FileChooserでエラーが発生した場合
            else if (result == JFileChooser.ERROR_OPTION) {
                System.out.println("エラー発生");
            }
        }
    }

    public void savePath() {
        System.out.println("保存ボタンがクリックされました");
        try {
            // FileChooserのテキストフィールドに入力されたファイル名のフルパスの取得
            File filePath = fc.getSelectedFile();
            System.out.println("fileName =" + filePath);

            // 保存先のフルパスの指定
            FileWriter fw = new FileWriter(filePath);

            //以下コード省略
            fw.close();
        } catch (IOException evt) {}
    }

    public void getPath() throws IOException {
        // FileChooserが参照しているディレクトリ・パス名の取得
        File directoryName = fc.getCurrentDirectory();
        fileAccess = new FileAccess();

        System.out.println("directoryName = " + directoryName);

        // FileChooserで選択されたファイルのフルパスの取得
        File filePath = fc.getSelectedFile();
        System.out.println("fileName= " + filePath);

        try {
            FileReader fr = new FileReader(filePath);
            // 以下コード省略
            fr.close();
        } catch (IOException evt) {}

        fileAccess.setFilePath(filePath);
        System.out.println(fileAccess.getRaw());
    }

    public static void main(String[] args) {
        FileChooser fc1 = new FileChooser();
        SelectWindow window = new SelectWindow();
    }
}