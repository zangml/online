package com.koala.learn.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by koala on 2018/3/6.
 */
public class PythonUtils {

    private static final String HOST="39.105.62.140";
    private static final int PORT_FILE=20000;
    private static final int PORT_PY=20001;

    private static final Logger logger= LoggerFactory.getLogger(PythonUtils.class);

    public static String execPy(String py){
        StringBuilder sb = new StringBuilder();
        Process process;
        try {
            process = Runtime.getRuntime().exec(py);

//            process.waitFor();
            BufferedReader stdOut=new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s;
            while((s=stdOut.readLine())!=null){
                sb.append(s);
            }
            stdOut.close();
        } catch (IOException e) {
            sb.append(e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String execPyRemote(String py) throws IOException {

        Socket socket =new Socket();
        socket.connect(new InetSocketAddress(HOST, PORT_PY), 3000);

        logger.info("已发起服务器连接，并进入后续流程～");
        logger.info("客户端信息：" + socket.getLocalAddress() + " P:" + socket.getLocalPort());
        logger.info("服务器信息：" + socket.getInetAddress() + " P:" + socket.getPort());

        // 得到Socket输出流
        OutputStream outputStream = socket.getOutputStream();

        // 得到Socket输入流
        InputStream inputStream = socket.getInputStream();

        PrintStream socektPrintStream=new PrintStream(outputStream);
        // 发送到服务器
        socektPrintStream.println(py);
        // 接收服务器返回
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        String serveInfo = bufferedReader.readLine();
        logger.info("收到信息：" + serveInfo);

        // 资源释放
        outputStream.close();
        inputStream.close();
        socektPrintStream.close();
        bufferedReader.close();
        socket.close();
        return serveInfo;

    }
    public static void transFile(File file) throws IOException {
        Socket socket = new Socket();

        socket.connect(new InetSocketAddress(HOST, PORT_FILE), 3000);

        DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
        BufferedInputStream din = new BufferedInputStream(new FileInputStream(file));
        int len;
        byte buf[] = new byte[8192];
        dout.writeUTF(file.getAbsolutePath());
        dout.flush();
        while ((len = din.read(buf)) != -1) {
            dout.write(buf, 0, len);
        }
        logger.info("客户端文件传输完毕");
        dout.close();
        din.close();
        socket.close();
    }
    public static void main(String[] args) throws IOException {
        //String out = execPy("python /Users/zangmenglei/Python/LR.py penalty=l2  tol=0.0001 random_state=None fit_intercept=True solver=sag multi_class=ovr class_weight=None C=1.0 train=/Users/zangmenglei/train.csv test=/Users/zangmenglei/test.csv");
        //System.out.println(out);
//        String out = execPy("python3 /usr/local/sk/feature_all.py len_piece=20 freq=25600 wave_layer=3 avg=True std=True var=True skew=True kur=True ptp=True min_freq=1600 max_freq=3000 ar_num=10 mfcc=10 path=/Users/zangmenglei/data/djms.csv opath=/Users/zangmenglei/data/djmsfea_all1.csv");

        transFile(new File("/Users/zangmenglei/PHM/572/random0.8diabetes_afterPrewindow-L1-S1test.csv"));
    }
}


