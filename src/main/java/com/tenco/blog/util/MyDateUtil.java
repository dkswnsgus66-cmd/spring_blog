package com.tenco.blog.util;


import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;

// SRP 단일 책임 원칙
// 날짜 시간 관련된 유틸리티 클래스
public class MyDateUtil {


    // 1. TimeStamp 포메터
    // 클래스 이름으로 접근하게만듬 static
    // 시간은 스트링으로 반환
    public static String timeStamoFormat(Timestamp timestamp){
        // TimeStamp --> Date 형태로 변환 날짜 시간 --> 날짜로 형변환
        Date currentDate = new Date(timestamp.getTime());
        return DateFormatUtils.format(currentDate,"yyyy-MM-dd HH:mm");
    }

}
