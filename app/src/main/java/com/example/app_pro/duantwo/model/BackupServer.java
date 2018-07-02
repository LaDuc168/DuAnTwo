package com.example.app_pro.duantwo.model;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by LaVanDuc on 1/30/2018.
 */

public class BackupServer {

    public static String ReadHangHoa(){
        File file=null;
        try {
                file=new File(Environment.getExternalStorageDirectory().getPath(),"hanghoa.txt");
            FileInputStream fis=new FileInputStream(file);
            BufferedReader re=new BufferedReader(new InputStreamReader(fis));
            StringBuilder builder=new StringBuilder();
            String s="";
            while ((s=re.readLine())!=null)
            {
                builder.append(s);
            }
            re.close();
            return builder.toString();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return "";
    }

    public static String ReadNhapHang(){
        File file=null;
        try {
                file=new File(Environment.getExternalStorageDirectory().getPath(),"nhaphang.txt");
            FileInputStream fis=new FileInputStream(file);
            BufferedReader re=new BufferedReader(new InputStreamReader(fis));
            StringBuilder builder=new StringBuilder();
            String s="";
            while ((s=re.readLine())!=null)
            {
                builder.append(s);
            }
            re.close();
            return builder.toString();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return "";
    }

    public static String ReadXuatHang(){
        File file=null;
        try {
                file=new File(Environment.getExternalStorageDirectory().getPath(),"xuathang.txt");
            FileInputStream fis=new FileInputStream(file);
            BufferedReader re=new BufferedReader(new InputStreamReader(fis));
            StringBuilder builder=new StringBuilder();
            String s="";
            while ((s=re.readLine())!=null)
            {
                builder.append(s);
            }
            re.close();
            return builder.toString();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return "";
    }

    public static String ReadNguoiDung(){
        File file=null;
        try {
                file=new File(Environment.getExternalStorageDirectory().getPath(),"nguoidung.txt");
            FileInputStream fis=new FileInputStream(file);
            BufferedReader re=new BufferedReader(new InputStreamReader(fis));
            StringBuilder builder=new StringBuilder();
            String s="";
            while ((s=re.readLine())!=null)
            {
                builder.append(s);
            }
            re.close();
            return builder.toString();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return "";
    }
}
