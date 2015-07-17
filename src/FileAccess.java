import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

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
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);

        // 脳波の電位差を格納するリスト
        ArrayList<Double[]> list = new ArrayList<>();

        // 読み込んだファイルを１行ずつ処理
        String line;
        StringTokenizer token;
        Double brain_voltage; // 電位差
        Double windowFunc; //　窓関数
        int N = 0;
        int flag = 0; // 格納判定(1なら格納)
        while((line = br.readLine()) != null) {
            // 区切り文字","で分割する
            token = new StringTokenizer(line, ",");
            // 分割した文字を画面出力する
            ArrayList<Double> sub = new ArrayList<>();
            while (token.hasMoreTokens()) {
                try {
                    // 分割した文字から数値データを抽出
                    brain_voltage = Double.parseDouble(token.nextToken());
                    windowFunc = (0.5 - 0.5 * Math.cos((2*Math.PI*N)/512))*brain_voltage; //窓関数
                    sub.add(windowFunc);
                    flag = 1;
                } catch (NumberFormatException ne) {
                    // 例外処理(数値変換不可の場合)
                }
            }
            if (flag == 1) {
                list.add(sub.toArray(new Double[0])); // 変更箇所
                N++;
            }
            flag = 0;
        }

        /* Fast Fourier Transform */
        Double[][] data = list.toArray(new Double[0][0]); // listを2次元配列化
        double[] x = new double[512];
        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD); //FFTインスタンス作成

        System.out.println(list.size());
        System.out.println(data.length);
        System.out.println(x.length);
        for(int j = 0; j < list.size(); j+=512) {
            for (int i = 0; i < x.length; i++) {
                if(i+j >= list.size()){
                    x[i] = 0.0;
                }
                else {
                    x[i] = data[i + j][0];
                }
                System.out.println((i + j)+ ":" + x[i]);
            }
            System.out.println("******************");
            try {
                Complex[] y = fft.transform(x, TransformType.FORWARD);
                for (int i = 0; i < y.length; i++) {
                    System.out.println(y[i].toString()+":"+y[i].getReal()+":"+y[i].getImaginary()+":"+y[i].abs());
                }
            } catch (MathIllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}