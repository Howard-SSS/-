package com.example.demo.service;

import com.example.demo.empty.Word;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WriteDocx {
    private static void write(XWPFDocument xwpfDocument, Word word){
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
    }
    private static void write(XWPFDocument xwpfDocument, List<Word> words){
        words.forEach((word) -> WriteDocx.write(xwpfDocument, word));
    }
    public static void write(File file, List<Word> words) throws IOException {
        XWPFDocument xwpfDocument = new XWPFDocument();
        write(xwpfDocument, words);
        xwpfDocument.write(new FileOutputStream(file));
        xwpfDocument.close();
    }
}
