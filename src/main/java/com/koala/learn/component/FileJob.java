package com.koala.learn.component;

import com.koala.learn.Const;
import com.koala.learn.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author HLX
 * @create 2021-04-14 4:25 下午
 */


@Component
@Lazy(value = false)
public class FileJob {
	private static final Logger logger= LoggerFactory.getLogger(UploadService.class);

	@Scheduled(cron="0 45 10 ? * FRI")//每周 星期天3点执行 0 0 3 ? * 1
	public void cronJob(){
		System.out.println("准备执行删除操作"+new Date());
		//  Const.UPLOAD_DATASET
		//deleteFiles("/Users/houlixin/Desktop/te/");
		deleteFiles(Const.UPLOAD_DATASET);

	}
	public static void deleteFiles(String pathDir){

		File file=new File(pathDir);
		if(file.isFile()) { // 判断是否是文件夹
			file.delete();
		}else{
			File[] files = file.listFiles();
			for(int i=0;i<files.length;i++){
				File file1 = new File(files[i].getAbsolutePath());
				//File file = new File( "/Users/houlixin/Desktop/te/test.sh" );
				BasicFileAttributes attrs;
				try {
					attrs = Files.readAttributes(file1.toPath(), BasicFileAttributes.class);
					FileTime time = attrs.creationTime();
					Date date_create = new Date(time.toMillis());
					Date date_now = new Date();

					if(((date_now.getTime()/1000-date_create.getTime()/1000>15768000) && files[i].getName().startsWith("out"))||files[i].getName().startsWith("model_cla")||files[i].getName().startsWith("model_reg")){//相差的时间不能大于一年31536000
						files[i].delete();
						logger.info("删除时间："+date_now+"/删除的文件为"+files[i].getAbsolutePath());
					}

//					String pattern = "yyyy-MM-dd HH:mm:ss";
//					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//
//					String formatted = simpleDateFormat.format( new Date( time.toMillis() ) );
//
//					System.out.println( "文件创建日期和时间是: " + formatted );
				} catch (IOException e) {
					e.printStackTrace();
				}
//				if(files[i].getName().startsWith("out")){
//					files[i].delete();
//				}
			}
		}
	}

	public static void main(String[] args) {
		deleteFiles("/Users/houlixin/Desktop/te/");
//		File file = new File( "/Users/houlixin/Desktop/te/test.sh" );
//		BasicFileAttributes attrs;
//		try {
//			attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
//			FileTime time = attrs.creationTime();
//
//			String pattern = "yyyy-MM-dd HH:mm:ss";
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//
//			String formatted = simpleDateFormat.format( new Date( time.toMillis() ) );
//
//			System.out.println( "文件创建日期和时间是: " + formatted );
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}
