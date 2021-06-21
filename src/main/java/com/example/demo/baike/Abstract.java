package com.example.demo.baike;

import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpRequest;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Abstract {

    /**
     *
     * @param cn_name 格式 en_name(cn_name)
     * @return
     */
    public String getPageBody(String cn_name){
        String body = HttpRequest.get(getUrl() + cn_name).timeout(3000).execute().body();
        //去除<a><img>标签
        return HtmlUtil.unwrapHtmlTag(body, "a","img");
    }

    public Map<String, String> match(String cn_name, String regex, int num){
        String pageBody = getPageBody(cn_name);
        Pattern pattern = Pattern.compile(regex);
        Map<String, String> map = new TreeMap<>();
        Matcher matcher = pattern.matcher(pageBody);
        while(num-- > 0){
            if (!matcher.find())
                break;
            String k = matcher.group(1);
            // 去除标签
            String v = HtmlUtil.cleanHtmlTag(matcher.group(2));
            map.put(k, v);
        }
        return map;
    }

    public abstract String getUrl();
}
