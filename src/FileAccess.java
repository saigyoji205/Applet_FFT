import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class FileAccess {

    private static File filePath;

    public void setFilePath(File filePath) {
        this.filePath = filePath;
    }

    public int getRaw() throws IOException{
        // ファイルの読み込み
        FileReader fr =new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);

        // 読み込んだファイルを１行ずつ処理
        String line;
        StringTokenizer token;
        Double brain_voltage; // 電位差
        int i = 0;  // 行数カウンタ
        int flag = 0;
        while((line = br.readLine()) != null) {
            // 区切り文字","で分割する
            token = new StringTokenizer(line, ",");
            // 分割した文字を画面出力する
            while (token.hasMoreTokens()) {
                try {
                    // 分割した文字から数値データを抽出
                    brain_voltage = Double.parseDouble(token.nextToken());
                    System.out.println(brain_voltage);
                    flag = 1;
                }catch(NumberFormatException ne){
                    // 例外処理(数値変換不可の場合)
                }
            }
            System.out.println("**********");
            if(flag == 1) {
                i++;
            }
            flag = 0;
        }
        return i;
    }
}