package com.example.demo.baike;

import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpRequest;

public class Wiki {
    private static String url = "https://wiki.mbalib.com/wiki/";
    public static String getPageBody(String word){
        String body = HttpRequest.get(url + word).timeout(2000).execute().body();
        //去除<a>标签
        return HtmlUtil.unwrapHtmlTag(body,"a");
    }
}
