package com.example.demo.controller;

import com.example.demo.empty.Word;
import com.example.demo.service.ReadXlsx;
import com.example.demo.service.WriteDocx;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class MainController {

    @RequestMapping("/start")
    public String mainMapper() throws IOException, InvalidFormatException {
        File xlsFile = new File("C:\\Users\\Administrator\\Desktop\\x.xlsx"),
                docxFile = new File("C:\\Users\\Administrator\\Desktop\\1.docx");
        ReadXlsx readXlsx = new ReadXlsx();
        WriteDocx writeDocx = new WriteDocx();

        List<Word> read = readXlsx.read(xlsFile);
        writeDocx.write(docxFile, read);
        return "finish";
    }
}
