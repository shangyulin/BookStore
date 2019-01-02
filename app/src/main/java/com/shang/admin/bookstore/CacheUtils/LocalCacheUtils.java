package com.shang.admin.bookstore.CacheUtils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LocalCacheUtils {

    private static final String OBJ_CACHE = Environment.getExternalStorageDirectory() + "/" + "obj_cache.txt";

    private static final String JSON_CACHE = Environment.getExternalStorageDirectory() + "/" + "json_cache.txt";

    // 将一个对象保存到本地
    public static void saveObject(Object obj){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(new File(OBJ_CACHE));
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            Log.d("giant", "写入成功");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (oos != null) oos.close();
                if (fos != null) fos.close(); //最后关闭输出流
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void save(String str){
        try {
            FileOutputStream fos = new FileOutputStream(new File(JSON_CACHE));
            fos.write(str.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object readObject(){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(new File(OBJ_CACHE));
            ois = new ObjectInputStream(fis);
            Object object = ois.readObject();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (ois != null) ois.close();
                if (fis != null) fis.close(); //最后关闭输出流
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String read(){
        StringBuffer sb = new StringBuffer();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(JSON_CACHE));
            byte[] b = new byte[1024];
            int len;
            while ((len = fis.read(b)) != -1){
                sb.append(new String(b, 0, len));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
