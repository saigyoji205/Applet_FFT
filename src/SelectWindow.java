import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class SelectWindow extends JFrame implements ActionListener {
    JButton bt1;
    JButton bt2;
    JLabel label;
    JFileChooser fc;
    FileAccess fileAccess;

    public SelectWindow() {
        setTitle("FFT for 9ch");
        setSize(600, 300);
        Container ct = getContentPane();
        ct.setLayout(new FlowLayout());
        bt1 = new JButton("Open File FileChooser");
        bt2 = new JButton("First Fourier Transform");
        label = new JLabel("File Name");

        this.add(bt1);
        this.add(bt2);
        this.add(label);

        bt1.addActionListener(this);
        bt2.addActionListener(this);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        fc = new JFileChooser();
        // [Open File FileChooser]をクリックした場合
        if (e.getSource() == bt1) {
            // File open FileChooserを表示して、戻り値をresultに保存
            int result = fc.showOpenDialog(SelectWindow.this);

            // (APPROVE_OPTION)FileChooserの"開く"ボタンをクリックした場合
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    setPath();
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
        // [First Fourier Transform]をクリックした場合
        if (e.getSource() == bt2) {
            try {
                doFFT();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            /*
            int result = fc.showSaveDialog(SelectWindow.this);

            // (APPROVE_OPTION)FileChooserの保存ボタンをクリックした場合
            if (result == JFileChooser.APPROVE_OPTION) {
                doFFT();
            }

            // (CANCEL_OPTION) FileChooser取消しボタンをクリックした場合
            else if (result == JFileChooser.CANCEL_OPTION) {
                System.out.println("取消し");
            }

            // (ERROR_OPTION) FileChooserでエラーが発生した場合
            else if (result == JFileChooser.ERROR_OPTION) {
                System.out.println("エラー発生");
            }
            */
        }
    }

    public void doFFT() throws IOException{
        System.out.println("保存ボタンがクリックされました");
        // FileChooserのテキストフィールドに入力されたファイル名のフルパスの取得
        File filePath = fc.getSelectedFile();
        fileAccess = new FileAccess();

        System.out.println("fileName =" + filePath);

        fileAccess.getRaw();
    }

    public void setPath() throws IOException {
        // FileChooserが参照しているディレクトリ・パス名の取得
        File directoryName = fc.getCurrentDirectory();
        fileAccess = new FileAccess();

        System.out.println("directoryName = " + directoryName);

        // FileChooserで選択されたファイルのフルパスの取得
        File filePath = fc.getSelectedFile();
        System.out.println("fileName= " + filePath);

        label.setText(filePath.getName());

        fileAccess.setFilePath(filePath);

    }

    public static void main(String[] args) {
        SelectWindow window = new SelectWindow();
    }
}