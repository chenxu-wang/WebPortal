package com.ccd.backend.controller;

import com.ccd.backend.service.ExcelService;
import com.ccd.backend.service.UserService;
import com.ccd.backend.utils.Maps;
import com.ccd.backend.utils.Result;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.Units;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFChart;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class ExcelController {
    @Autowired
    ExcelService excelService;

    @PostMapping("/importExcel")
    @ResponseBody
    public void uploadExcel(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {
        String powerPointPath = "./src/main/resources/Testppt.pptx";
        response.setContentType("application/vnd.ms-powerpoint");
        response.setHeader("Content-Disposition", "attachment;filename=\"slideshow.ppt\"");
        //get file name
        String fileName = file.getOriginalFilename();
        //get suffix
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        Workbook workbook = WorkbookFactory.create(new BufferedInputStream(file.getInputStream()));
        ArrayList<XSSFChart> chartList = new ArrayList<>();
        for(int i = 3; i < 36; i++ ){
            Sheet sheet = workbook.getSheetAt(i);
            XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
            chartList.add(drawing.getCharts().get(0));        }
        // create new PowerPoint slide show
        XMLSlideShow slideShow = new XMLSlideShow();
        ArrayList<XSLFSlide> slidesList = new ArrayList<>();
        for(int j =3; j < 36; j++){
            slidesList.add(slideShow.createSlide());
        }
        ArrayList<XSLFChart> pptCharts = new ArrayList<>();
        for(int j =3; j < 36; j++){
            pptCharts.add(slideShow.createChart());
        }
        for(int j =0; j < 33; j++){

            slidesList.get(j).addChart(pptCharts.get(j), new java.awt.geom.Rectangle2D.Double(2.6d* Units.EMU_PER_CENTIMETER, 2d*Units.EMU_PER_CENTIMETER, 20d*Units.EMU_PER_CENTIMETER, 15d*Units.EMU_PER_CENTIMETER));
        }
        for(int j =0; j < 33; j++){
            pptCharts.get(j).importContent(chartList.get(j));
            pptCharts.get(j).saveWorkbook((XSSFWorkbook) workbook);
        }
        // Write the output to a file

            ServletOutputStream outputStream = response.getOutputStream();
            // write output stream
            slideShow.write(outputStream);
            outputStream.close();
            outputStream.flush();

        try (FileOutputStream fileOut = new FileOutputStream(powerPointPath)) {
            slideShow.write(fileOut);
        }

        workbook.close();
        slideShow.close();

    }
}
