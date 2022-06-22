package com.zhang.DataGenerator;

import com.zhang.blogmanager1.entity.user.Users;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static com.zhang.DataGenerator.Words.*;

public class Generator {
    private static long random(long begin,long end){
    long rtn = begin + (long)(Math.random() * (end - begin));
    if(rtn == begin || rtn == end){
        return random(begin,end);
    }
    return rtn;
    }
    private static int getNum(int start,int end) { return (int)(Math.random()*(end-start+1)+start); }

    public static Users userGen() throws Exception {
        //名字
        int len1 = name1.length - 1;
        int len2 = name2.length - 1;
        int random1 = (int) (Math.random() * len1);
        int random2 = (int) (Math.random() * len2);
        int random2_1 = (int) (Math.random() * len2);
        String name = "";
        int randomN = (int) (Math.random() * 2);
        if (randomN == 1) {
            name = name1[random1] + name2[random2];
        } else {
            name = name1[random1] + name2[random2] + name2[random2_1];
        }
        //密码
        StringBuffer password = new StringBuffer();
        String num = "0123456789";
        String english = "qwertyuiopasdfghjklzxcvbnm";
        String englishBig = "QWERTYUIOPASDFGHJKLZXCVBNM";
        String symbol = "!@#$%^&*_+-{}<>.*";
        String stringSum = num + english + englishBig + symbol;
        int length = stringSum.length();
        int passwordLength = 8; //定义密码长度,写死8
        for (int i = 0; i < passwordLength; i++) {
            Random random = new Random();
            int a = random.nextInt(length + 1);
            char one = stringSum.charAt(a);
            password.append(one);
        }
        //注册时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start = format.parse("2000-01-01");
        Date end = format.parse("2020-01-01");
        long date = random(start.getTime(),end.getTime());
        Date registertime = new Date(date);
        //类别
        int usertype = 1;
        //性别
        int sex = (int) (Math.random() * 2);
        //生日
        start = format.parse("1987-01-01");
        end = format.parse("2008-01-01");
        date = random(start.getTime(),end.getTime());
        Date birth = new Date(date);
        //年龄
        int age = 2022 - birth.getYear() - 1900;
        //邮箱
        length = getNum(5,10);
        StringBuffer email = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = (int)(Math.random()*base.length());
            email.append(base.charAt(number));
        }
        email.append(email_suffix[(int)(Math.random()*email_suffix.length)]);

        return new Users(0, name, password.toString(), registertime, usertype, sex, age, birth, email.toString());
    }

    public static void main(String[] args) throws Exception {
        Users user = userGen();
        System.out.println(user);
    }
}
