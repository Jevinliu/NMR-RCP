package edu.xmu.nmr.app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    
    /**
     * 获取当前的额系统时间，到毫秒级别
     * 
     * @return
     */
    public static String getCurrentTime() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        return sdf.format(now);
    }
}
