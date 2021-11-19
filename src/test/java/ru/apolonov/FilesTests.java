package ru.apolonov;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
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
    }
}
