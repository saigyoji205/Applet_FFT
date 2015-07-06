import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FileAccess {

    private static File filePath;

    public void setFilePath(File filePath) {
        FileAccess.filePath = filePath;
    }

    public void getRaw() throws IOException{
        // ファイルの読み込み
        FileReader fr =new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);

        // 脳波の電位差を格納するリスト
        ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();

        // 読み込んだファイルを１行ずつ処理
        String line;
        StringTokenizer token;
        Double brain_voltage; // 電位差
        int flag = 0;
        while((line = br.readLine()) != null) {
            // 区切り文字","で分割する
            token = new StringTokenizer(line, ",");
            // 分割した文字を画面出力する
            ArrayList<Double> sub = new ArrayList<Double>();
            while (token.hasMoreTokens()) {
                try {
                    // 分割した文字から数値データを抽出
                    brain_voltage = Double.parseDouble(token.nextToken());
                    sub.add(brain_voltage);
                    flag = 1;
                } catch (NumberFormatException ne) {
                    // 例外処理(数値変換不可の場合)
                }
            }
            if (flag == 1) {
                list.add(sub);
            }
            flag = 0;
        }
        System.out.println(list);
    }
}