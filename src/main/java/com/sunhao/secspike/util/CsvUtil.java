package com.sunhao.secspike.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/4/5  15:33
 */
public class CsvUtil {

    public static int userIdUpperLimit = 1000;//用户编号上限

    public static int userIdLowwerLimit = 1;//用户编号

    public static int goodsIdUpperLimit = 19;//商品编号

    public static int goodsIdLowwerLimit = 1;//商品编号

    public static int count = 1000000;//生成一万组数据

    public static void main(String[] args) {
        try {
            /* 写入Txt文件 */
            File writename = new File("C:\\Software\\apache-jmeter-5.2.1\\csvset\\yace.txt");
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            while(count > 0){
                out.write(getRandom(userIdLowwerLimit,userIdUpperLimit)+","+getRandom(goodsIdLowwerLimit,goodsIdUpperLimit)+"\n");
                count--;
            }
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getRandom(int min, int max){
        Random random = new Random();
        int s = min + (int)(Math.random() * (max-min+1));
        return s;
    }
}
