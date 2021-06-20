package com.example.demo.baike;

import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Wiki {
    private static String url = "https://wiki.mbalib.com/wiki/";

    public static String getPageBody(String word) {
        Pattern p = Pattern.compile("\\((.*)\\)");
        Matcher m = p.matcher(word);
        m.find();
        String zh_name = m.group(1);
        String body = HttpRequest.get(url + zh_name).timeout(2000).execute().body();
        //去除<a>标签
        return HtmlUtil.unwrapHtmlTag(body, "a");
    }
}
