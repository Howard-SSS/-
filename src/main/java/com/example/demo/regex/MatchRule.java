package com.example.demo.regex;

import cn.hutool.http.HtmlUtil;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.apache.xmlbeans.impl.regex.Match;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchRule {

    private Properties properties = null;
    private Pattern pattern;

    public MatchRule(){
        load();
    }
    public boolean compile(String key){
        String r = properties.getProperty(key);
        if(r == null)
            return false;
        pattern = Pattern.compile(r);
        return true;
    }
    public Map<String,String> match(String content){
        if(properties == null)
            throw new NullPointerException("can't find file(application.properties)");
        Map<String, String> ret = new HashMap<>();
        Matcher matcher = pattern.matcher(content);
        for(int i=0;i<2;i++){
            if(!matcher.find())
                break;
            String k = matcher.group(0);
            // 去除<p>标签
            String v = HtmlUtil.unwrapHtmlTag(matcher.group(1), "p");
            ret.put(k,v);
        }
        return ret;
    }
    public void load(){
        try {
            properties.load(new FileInputStream(new File("application.properties")));
        }catch (IOException e){}
    }

}
