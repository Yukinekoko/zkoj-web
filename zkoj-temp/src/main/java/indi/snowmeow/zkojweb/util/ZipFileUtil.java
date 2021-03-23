package indi.snowmeow.zkojweb.util;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZipFileUtil {
    private static byte[] ZIP = new byte[] { 80, 75, 3, 4 };
    //static boolean isZip =false;
    private static File file;
    byte[] buffer = new byte[8];
    ArrayList<String> inputNames=new ArrayList<String>();
    ArrayList<String> outputNames=new ArrayList<String>();

    public ArrayList<String> getInputNames() {
        return inputNames;
    }

    public ArrayList<String> getOutputNames() {
        return outputNames;
    }

    public ZipFileUtil(File file) {

        this.file = file;
        try {
            FileInputStream input= new FileInputStream(file);
            input.read(buffer, 0, 7);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public boolean isEncrypted(){
        //加密判断 如果第6byte 是奇数 为 加密过的
        boolean isEncrypted=false;
        if (buffer[6]%2==1)
            isEncrypted=true ;
        return isEncrypted;
    }

    public boolean isZip() {
        boolean isZip=false;
        byte [] temp = new byte[4];

        for (int i = 0; i < 4; i++) {
            temp[i] = buffer[i];
        }
        isZip = Arrays.equals(ZIP,temp);

        return isZip;
    }
    //压缩包文件命名是否合法

    public boolean isLegal(){
        boolean isLegal = false;
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(file));
            ZipInputStream zin = new ZipInputStream(in);
            ZipEntry ze ;

            while ((ze= zin.getNextEntry())!= null){
                String name = ze.getName();
                name =name.toUpperCase();
                String temp ="";
                boolean isInput =  name.contains("INPUT_");
                boolean isOutput = name.contains("OUTPUT_");
                if (!isInput&&!isOutput) {
                    System.out.println("文件命名格式有误,应用INPUT_ 以及OUTPUT_ 进行命名");
                    isLegal=false;
                    return isLegal;
                }else {
                    if (isInput)
                        temp = name.substring(6,name.length());
                    if (isOutput)
                        temp = name.substring(7,name.length());
                    char isnumber[] = temp.toCharArray();
                    //ascii >47 <58 是数字
                    for (int i =0;i<isnumber.length; i++){
                        if (!(isnumber[i]>47&&isnumber[i] <58)){
                            System.out.println("文件命名应该以数字结尾");
                            isLegal=false;
                            return isLegal;
                        }
                    }
                }
                if (isInput)
                    this.inputNames.add(name);
                if (isOutput)
                    this.outputNames.add(name);
            }

            // 一个input 对应一个output 否则return output可以单独出现。
            for (int i = 0; i < this.inputNames.size(); i++) {
                String name = this.inputNames.get(i);
                String number = name.substring(6,name.length());
                System.out.println(number);
                for (int j = 0;j<this.outputNames.size(); j++){
                    String outputName = this.outputNames.get(j);
                    String outputNumber = outputName.substring(7,outputName.length() );

                    if (number.equals(outputNumber)){
                        System.out.println(outputNumber);
                        break;
                    }
                    if (j==this.outputNames.size() - 1){
                        System.out.println( "input_"+number+"没有对应的输出");
                        isLegal=false;
                        return isLegal;

                    }
                }
            }

            in.close();
            zin.close();
            isLegal=true;

        } catch (ZipException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isLegal;
    }

    public Map<String, String> readFiles(){
        Map<String, String> files = new HashMap<String, String>();
        try {
            ZipFile zipFile = new ZipFile(this.file);
            InputStream in = new BufferedInputStream(new FileInputStream(this.file));
            ZipInputStream zin = new ZipInputStream(in);
            ZipEntry ze;
            while ((ze = zin.getNextEntry()) != null) {
                long size = ze.getSize();
                String fileName = ze.getName().toUpperCase();
                System.out.println("filename: \n" + fileName + ": " + size + " bytes");
                String sample = "";
                if (size>0){
                    BufferedReader br = new BufferedReader(new InputStreamReader(zipFile.getInputStream(ze)));
                    String line;
                    while ((line = br.readLine())!=null){
                        sample=sample+line+"\n";
                    }
                    files.put(fileName,sample);
                    br.close();
                }
                else{
                    files.put(fileName,sample);
                }
            }
            zipFile.close();
            in.close();
            zin.close();
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;
    }
}
