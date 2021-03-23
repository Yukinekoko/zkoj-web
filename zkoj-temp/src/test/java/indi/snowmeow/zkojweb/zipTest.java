package indi.snowmeow.zkojweb;

import indi.snowmeow.zkojweb.util.ZipFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class zipTest {
    public static void main(String[] args) {
        File file = new File("C:/Users/jin/IdeaProjects/src.zip");
        ZipFileUtil zipFile = new ZipFileUtil(file);
        if (!zipFile.isZip()){
            System.out.println("不是zip");
        }
        if (zipFile.isEncrypted()){
            System.out.println("请把压缩包解密后");
        }
        if (zipFile.isLegal()){
            Map<String, String> files= zipFile.readFiles();
            ArrayList<String> inputNames = zipFile.getInputNames();
            ArrayList<String> outputNames = zipFile.getOutputNames();
            String input = "";
            String output = "";
            System.out.println("--------------------------------");
            for (int j = 0; j < inputNames.size(); j++) {
                String inputName = inputNames.get(j);
                String inputNumber = inputName.substring(6,inputName.length() - 4);

                //output temp = name.substring(7,name.length()-4);
                for (int i = 0; i < outputNames.size(); i++){
                    String name = outputNames.get(i);
                    String number = name.substring(7,name.length() - 4);
                    if (number.equals(inputNumber)){
                        input = files.get(inputName);
                        System.out.println(inputName+"："+input);
                        output = files.get(name);
                        System.out.println(name +"："+output);
                        //Sql 插入数据库
                        outputNames.remove(i);
                        break;
                    }
                }
            }
            for (int i =0;i<outputNames.size(); i++){
                //Sql 插入数据库
                System.out.println(outputNames.get(i));
            }

        }
    }
}
