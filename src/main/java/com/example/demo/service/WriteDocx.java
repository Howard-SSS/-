package com.example.demo.service;

import com.example.demo.baike.Baidu;
import com.example.demo.baike.Wiki;
import com.example.demo.empty.Word;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WriteDocx {

    private Properties properties = null;

    public WriteDocx(){
        try {
            InputStream in = WriteDocx.class.getClassLoader().getResourceAsStream("application.properties");
            properties = new Properties();
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void write(XWPFDocument xwpfDocument, Word word) {
        XWPFParagraph paragraph = xwpfDocument.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontFamily("宋体");
        run.setFontSize(18);
        run.setText("Ch" + word.getNum());
        run.addBreak();
        run.setText(word.getTime() + " ");
        run.setText(word.getName());
        run.addBreak();
        run.setText(word.getUrl());
        run.addBreak();
        run.setText(word.getDescribe());
        run.addBreak();

        //提取括号里的中文名
        Pattern p = Pattern.compile("\\((.*)\\)");
        Matcher m = p.matcher(word.getName());
        m.find();
        String cn_name = m.group(1);

        Map<String, String> map = null;
        map = new Wiki().match(cn_name, properties.getProperty("wiki"), 2);
        if(map.size() == 0)
            map = new Baidu().match(cn_name, properties.getProperty("baidu"), 2);
        otherWrite(run, map);
    }

    private void write(XWPFDocument xwpfDocument, List<Word> words) {
        words.forEach((word) -> this.write(xwpfDocument, word));
    }

    public void write(File file, List<Word> words) throws IOException {
        XWPFDocument xwpfDocument = new XWPFDocument();
        write(xwpfDocument, words);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        xwpfDocument.write(fileOutputStream);
        fileOutputStream.close();
        xwpfDocument.close();
    }

    public static void otherWrite(XWPFRun xwpfRun, Map<String, String> map) {
        map.forEach((k, v) -> {
            xwpfRun.setText(k);
            xwpfRun.addBreak();
            xwpfRun.setText(v);
            xwpfRun.addBreak();
        });
    }
}
