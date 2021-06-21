package com.example.demo.service;

import com.example.demo.baike.Baidu;
import com.example.demo.baike.Wiki;
import com.example.demo.empty.Word;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
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
        run.setFontFamily("Times New Roman");
        run.setFontSize(14);// 小四
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
        otherWrite(paragraph, map);
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

    public static void otherWrite(XWPFParagraph xwpfParagraph, Map<String, String> map) {
        map.forEach((k, v) -> {
            XWPFRun title = xwpfParagraph.createRun();
            title.setBold(true);
            title.setFontFamily("宋体");
            title.setFontSize(14);// 小四
            title.setText(k);
            title.addBreak();
            title.setUnderline(UnderlinePatterns.THICK);
            XWPFRun content = xwpfParagraph.createRun();
            content.setFontFamily("宋体");
            content.setFontSize(14);
            content.setText(v);
            content.addBreak();
        });
    }
}
