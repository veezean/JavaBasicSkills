package com.veezean.skills.test333;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/9/13
 */
public class Main {


    public static void main(String[] args) {
        String source = "9u+Phzu8ay7J+33pp9fC46oWqZdfTmT7ywajbFcSQAZit0+qeZiNx0EzyfPjrruGSHB0adjzoaSxltqSPL4i16j9umUZPBD0RV/eVxDKrKOBP6errfMHMcE1hRLkXAKLJ04Vqli1XEt+k9WyxTFGscuEe8W5QF3S2wn1He4CDqEBuAV2KDBWG2mx5Bi06Hrp9QCBCqzhALwsgnS3D7xHKyjPu8djb//eNhjmvRSr4OeEFdkrPC4B99X0V+v5fTIGRaujZihOjEHUyLd1EY/CUDBc6FnE+H36SjNzb1atmURkeynYxphaGhazjihFvBbmXStsDKgj/o+yiel6JpSX4Ose4S7a0qDxIKd3kY6bKWOwY94HcrMD032UlzSo4bLn4jaxIs+hqLS7AEf7BBw8XSnBsr7f9ZawZMEkqbuaXQA=";
        try {
            String result = URLEncoder.encode(source, "UTF-8");
            System.out.println(result);


            String decodeSource = "U_ID%3d1174%26U_NAME%3d%e7%8e%8b%e4%bc%9f%e4%bb%bb%26U_NICKNAME%3d%e7%8e%8b%e4%bc%9f%e4%bb%bb%26U_DEFAULTSEL%3d0";
            result = URLDecoder.decode(decodeSource, "UTF-8");
            System.out.println(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
