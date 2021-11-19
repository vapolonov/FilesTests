package ru.apolonov;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.List;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilesTests {

    @Test
    @DisplayName("Скачивание xls файла")
    void xlsFileDownloadTest() throws FileNotFoundException {
        open("https://ru.justexw.com/download/%d0%ba%d0%b0%d0%bb%d0%b5%d0%bd%d0%b4%d0%b0%d1%80%d1%8c-2018");
        File xls = $(".btn").download();
        XLS parsedXls = new XLS(xls);
        boolean checkPassed = parsedXls.excel
                .getSheetAt(2)
                .getRow(2)
                .getCell(5)
                .getStringCellValue().contains("March 2018");
        assertTrue(checkPassed);
    }

    @Test
    @DisplayName("Скачивание pfd-файла")
    void pdfFileDownloadTest() throws IOException {
        open("https://kub-24.ru/prajs-list-shablon-prajs-lista-2020-v-excel-word-pdf/");
        File pdf = $(byText("шаблон прайс-листа в PDF")).download();
        PDF parsedPdf = new PDF(pdf);
        Assertions.assertEquals(parsedPdf.title, "Прайс-лист &quot;Прайс-лист_02.03.2020&quot;");
        Assertions.assertEquals(parsedPdf.numberOfPages, 1);
    }

    @Test
    @DisplayName("Парсинг CSV файлов")
    void parseCsvFileTest() throws IOException, CsvException {
        ClassLoader classloader = this.getClass().getClassLoader();
        try (InputStream is = classloader.getResourceAsStream("research2019-csv.csv");
             Reader reader = new InputStreamReader(is)) {
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> strings = csvReader.readAll();
            assertEquals(strings.size(), 14947);
        }
    }
/*
    @Test
    @DisplayName("Скачивание xlsx файла")
    void xlsxFileDownloadTest() throws FileNotFoundException {
        open("https://www.stats.govt.nz/large-datasets/csv-files-for-download/");
        File xlsx = $(byText("Research and development survey: 2020")).download();
        XLS parsedXlsx = new XLS(xlsx);
        boolean checkPassed = parsedXlsx.excel
                .getSheetAt(0)
                .getRow(0)
                .getCell(0)
                .getStringCellValue().contains("Research and development survey: 2020");
        assertTrue(checkPassed);
    }
 */
}
