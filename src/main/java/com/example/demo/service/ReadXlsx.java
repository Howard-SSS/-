package com.example.demo.service;

import com.example.demo.empty.Word;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadXlsx {

    public static List<Word> read(String filePath, int page) throws IOException, InvalidFormatException {
        File file = new File(filePath);
        return read(file, page);
    }
    public static List<Word> read(String filePath) throws IOException, InvalidFormatException {
        return read(filePath, 0);
    }
    public static List<Word> read(File file, int page) throws IOException, InvalidFormatException {
        ArrayList<Word> words = new ArrayList<>();
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        XSSFSheet sheet = xssfWorkbook.getSheetAt(page);
        Iterator<Row> rowIterator = sheet.rowIterator();
        while(rowIterator.hasNext()){
            Row next = rowIterator.next();
            Word word = new Word();
            if(next.getCell(0) == null)
                continue;
            word.setTime(next.getCell(0).getStringCellValue());
            word.setName(next.getCell(1).getStringCellValue());
            word.setDescribe(next.getCell(2).getStringCellValue());
            word.setUrl(next.getCell(3).getStringCellValue());
            words.add(word);
        }
        xssfWorkbook.close();
        return words;
    }
    public static List<Word> read(File file) throws IOException, InvalidFormatException {
        return read(file, 0);
    }
}
