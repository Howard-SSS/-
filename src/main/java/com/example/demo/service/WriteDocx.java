package com.example.demo.service;

import com.example.demo.baike.Wiki;
import com.example.demo.empty.Word;
import com.example.demo.regex.MatchRule;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WriteDocx {

    private void write(XWPFDocument xwpfDocument, Word word) {
        XWPFParagraph paragraph = xwpfDocument.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("Ch" + word.getNum());
        run.addBreak();
        run.setText(word.getTime() + " ");
        run.setText(word.getName());
        run.addBreak();
        run.setText(word.getUrl());
        run.addBreak();
        run.setText(word.getDescribe());
        run.addBreak();

        MatchRule matchRule = new MatchRule();
        matchRule.compile("wiki");
        Map<String, String> map = matchRule.match(Wiki.getPageBody(word.getName()));
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
