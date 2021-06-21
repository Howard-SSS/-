package com.example.demo;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() throws IOException {
        XWPFDocument xwpfDocument = new XWPFDocument();
        XWPFParagraph paragraph = xwpfDocument.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontFamily("宋体");
        run.setFontSize(14);
        run.setText("你会11");
        run.addBreak();
        XWPFRun run1 = paragraph.createRun();
        run1.setFontFamily("Times New Roman");
        run1.setFontSize(18);
        run1.setText("你会11");
        FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\心里的潇洒情\\Desktop\\1.docx"));
        xwpfDocument.write(fileOutputStream);
        fileOutputStream.close();
        xwpfDocument.close();
    }

}
