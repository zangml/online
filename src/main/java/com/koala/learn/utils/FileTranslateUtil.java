package com.koala.learn.utils;

import javafx.scene.control.Cell;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.ArrayList;


public class FileTranslateUtil {

    public static File csv2xls(File file) throws IOException {
        File out = new File(file.getAbsolutePath().replace("csv", "xls"));
        ArrayList arList = null;
        ArrayList al = null;
        String thisLine;
        FileInputStream fis = new FileInputStream(file);
        DataInputStream myInput = new DataInputStream(fis);
        int i = 0;
        arList = new ArrayList();
        while ((thisLine = myInput.readLine()) != null) {
            al = new ArrayList();
            String strar[] = thisLine.split(",");
            for (int j = 0; j < strar.length; j++) {
                al.add(strar[j]);
            }
            arList.add(al);
            i++;
        }
        try {
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("new sheet");
            for (int k = 0; k < arList.size(); k++) {
                ArrayList ardata = (ArrayList) arList.get(k);
                HSSFRow row = sheet.createRow((short) 0 + k);
                for (int p = 0; p < ardata.size(); p++) {
                    HSSFCell cell = row.createCell((short) p);
                    String data = ardata.get(p).toString();
                    if (data.startsWith("=")) {

                        data = data.replaceAll("\"", "");
                        data = data.replaceAll("=", "");
                        cell.setCellValue(data);
                    } else if (data.startsWith("\"")) {
                        data = data.replaceAll("\"", "");

                        cell.setCellValue(data);
                    } else {
                        data = data.replaceAll("\"", "");

                        cell.setCellValue(data);
                    }
                }
            }
            FileOutputStream fileOut = new FileOutputStream(out);
            hwb.write(fileOut);
            fileOut.close();
            System.out.println("Your excel file has been generated");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static void main(String[] args) throws IOException {
        File file=new File("/Users/zangmenglei/data/diabetes.csv");
        csv2xls(file);
    }
}
