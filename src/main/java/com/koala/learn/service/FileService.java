package com.koala.learn.service;
import com.koala.learn.Const;
import com.koala.learn.commen.ServerResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

@Service
public class FileService {

    /**
     * 检查文件大小，限制文件大小在5M以内
     */

    public ServerResponse checkFileSize(MultipartFile file){
        if (file.getSize()>1024*1024*5l){
            return ServerResponse.createByErrorMessage("文件过大，请限制在5M以内");
        }
        return ServerResponse.createBySuccess();
    }
    /**
     * 将数据保存到根文件目录下，并更换随机名字
     * @param file
     * @return 返回保存后的文件路径地址
     * @throws IOException
     */
    public File addFile(MultipartFile file, String fileRoot, String prefix) throws IOException {

        String testFileName=file.getOriginalFilename();
        String newTestName=getFileName(prefix,testFileName);
        File test = new File(fileRoot,newTestName);
        file.transferTo(test);
        return test;
    }
    public File addFile(String base64Str,String fileRoot,String prefix) throws IOException {

        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Str);
        String newTestName=getFileName(prefix,"base64.csv");
        File test = new File(fileRoot,newTestName);
        FileOutputStream out = new FileOutputStream(test);
        out.write(buffer);
        out.close();
        return test;
    }
    /**
     * 生成随机名字
     * @param prefix
     * @param uploadFileName
     * @return
     */

    private  String getFileName(String prefix,String uploadFileName){
        StringBuilder res=new StringBuilder();
        Random random=new Random();
        int suffIndex=uploadFileName.lastIndexOf(".");
        String suffix=uploadFileName.substring(suffIndex);
        String prefixStr=prefix+"_"+System.nanoTime()+random.nextInt(100);
        return res.append(prefixStr).append(suffix).toString();
    }

    /**
     * <p>将文件转成base64 字符串</p>
     * @param path 文件路径
     */
    public static String encodeBase64File(String path) throws Exception {
        File  file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }

    /**
     * <p>将base64字符解码保存文件</p>
     */
    public static void decoderBase64File(String base64Code,String targetPath) throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    /**
     * 定期删除文件
     * @param
     * @throws Exception
     */

    @Scheduled(cron="0 0 3 ? * 1")//每周星期天3点执行
    public void cronJob(){
        deleteFiles(Const.UPLOAD_DATASET);
    }
    private void deleteFiles(String pathDir){

        File file=new File(pathDir);
        if(file.isFile()) { // 判断是否是文件夹
            file.delete();
        }else{
            File[] files = file.listFiles();
            for(int i=0;i<files.length;i++){
                if(files[i].getName().startsWith("out")){
                    files[i].delete();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
//        File testFile=new File("/Users/zangmenglei/data/diabetes.csv");
        String base64Code=encodeBase64File("/Users/zangmenglei/data/test_api/test_regression.csv");
        System.out.println(base64Code);
//        decoderBase64File(base64Code,"/Users/zangmenglei/data/train_classifierBase64.csv");
    }
}
