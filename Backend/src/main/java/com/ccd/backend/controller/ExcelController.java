package com.ccd.backend.controller;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.Units;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xddf.usermodel.text.XDDFAutoFit;
import org.apache.poi.xddf.usermodel.text.XDDFBodyProperties;
import org.apache.poi.xddf.usermodel.text.XDDFTextBody;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFChart;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTDouble;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTTitle;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBodyProperties;
import org.openxmlformats.schemas.drawingml.x2006.main.STTextUnderlineType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {
//    @Autowired
//    ExcelService excelService;

    @PostMapping("/downloadStandard")
    @ResponseBody
    public void uploadExcel(@RequestParam("file") MultipartFile file, HttpServletResponse response, @RequestParam("titleSize") String titleSize,
                            @RequestParam("axisNumSize") String axisNumSize, @RequestParam("axisTitleSize") String axisTitleSize,
                            @RequestParam("legendFontSize") String legendFontSize,
                            @RequestParam("chartWidth") String chartWidth, @RequestParam("chartHeight") String chartHeight,
                            @RequestParam("chartX") String chartX, @RequestParam("chartY") String chartY) throws Exception {
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
        slideShow.setPageSize(new java.awt.Dimension(960, 540));
        ArrayList<XSLFSlide> slidesList = new ArrayList<>();
        for(int j =3; j < 36; j++){
            slidesList.add(slideShow.createSlide());
        }
        ArrayList<XSLFChart> pptCharts = new ArrayList<>();
        for(int j =3; j < 36; j++){
            pptCharts.add(slideShow.createChart());
        }
        for(int j =0; j < 33; j++){

            slidesList.get(j).addChart(pptCharts.get(j), new java.awt.geom.Rectangle2D.Double(Double.valueOf(chartX)* Units.EMU_PER_CENTIMETER, Double.valueOf(chartY)*Units.EMU_PER_CENTIMETER, Double.valueOf(chartWidth)*Units.EMU_PER_CENTIMETER, Double.valueOf(chartHeight)*Units.EMU_PER_CENTIMETER));
        }
        for(int j =0; j < 33; j++){
            chartList.get(j).getCTChart().getTitle().getTx().getRich().getPArray(0).getRArray(0).getRPr().setSz(Integer.parseInt(titleSize)*100);
            pptCharts.get(j).importContent(chartList.get(j));
            pptCharts.get(j).saveWorkbook((XSSFWorkbook) workbook);
//            CTDouble ctdouble = CTDouble.Factory.newInstance();
//            ctdouble.setVal(0.5);
//            CTDouble ctdoublex = CTDouble.Factory.newInstance();
//            ctdoublex.setVal(0.5);
//
//            pptCharts.get(j).getCTChart().getPlotArea().getLayout().getManualLayout().setW(ctdouble);

//            CTBoolean ctBooleanFalse = CTBoolean.Factory.newInstance();
//            ctBooleanFalse.setVal(false);
//            pptCharts.get(j).getCTChart().getTitle().setOverlay(ctBooleanFalse);
//            pptCharts.get(j).getCTChart().getPlotArea().getLayout().getManualLayout().setY(ctdouble);
//
//            pptCharts.get(j).getCTChart().getPlotArea().getLayout().getManualLayout().setX(ctdoublex);
//            pptCharts.get(j).getCTChart().getTitle().getTx().getRich().getPArray(0).getRArray(0).getRPr().setU(STTextUnderlineType.Enum.forInt(100));
//            pptCharts.get(j).getCTChart().getTitle().getLayout().getManualLayout().setX(ctdouble);
            List<? extends XDDFChartAxis> axies = pptCharts.get(j).getAxes();
            XDDFValueAxis bottomAxis = (XDDFValueAxis) axies.get(0);
            XDDFValueAxis leftAxis = (XDDFValueAxis) axies.get(1);

            // font size for left axis labels (ticks)
            bottomAxis.getOrAddTextProperties().setFontSize(Double.valueOf(axisNumSize));
            leftAxis.getOrAddTextProperties().setFontSize(Double.valueOf(axisNumSize));
            //set legend font size
            XDDFChartLegend legend = pptCharts.get(j).getOrAddLegend();
            legend.setPosition(LegendPosition.TOP_RIGHT);
            XDDFTextBody legendTextBody = new XDDFTextBody(legend);
            legendTextBody.getXmlObject().addNewBodyPr();
            legendTextBody.addNewParagraph().addDefaultRunProperties().setFontSize(Double.valueOf(legendFontSize));
            legend.setTextBody(legendTextBody);
            // reflect the underlying the xml objects in order to access fields that are not implemented in apache poi
            org.openxmlformats.schemas.drawingml.x2006.chart.CTValAx ctValAx = null;
            org.openxmlformats.schemas.drawingml.x2006.chart.CTValAx ctCatAx = null;
            org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea ctPlotArea = null;
            java.lang.reflect.Field ctValRef;
            java.lang.reflect.Field ctCatRef;
            java.lang.reflect.Field chartLegendRef;
            java.lang.reflect.Field ctPlotAreaRef;
            try {
                ctValRef = XDDFValueAxis.class.getDeclaredField("ctValAx");
                ctCatRef = XDDFValueAxis.class.getDeclaredField("ctValAx");
                ctValRef.setAccessible(true);
                ctCatRef.setAccessible(true);
                ctValAx = (org.openxmlformats.schemas.drawingml.x2006.chart.CTValAx) ctValRef.get(leftAxis);
                ctCatAx = (org.openxmlformats.schemas.drawingml.x2006.chart.CTValAx) ctCatRef.get(bottomAxis);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            // set title properties for left axis
            CTTitle ctLTitle = ctValAx.getTitle();
            for(int k = 0; k < ctLTitle.getTx().getRich().getPArray(0).getRArray().length; k++){
                ctLTitle.getTx().getRich().getPArray(0).getRArray(k).getRPr().setSz(Integer.valueOf(axisTitleSize)*100);
            }
            // set title properties for bottom axis
            CTTitle ctBTitle = ctCatAx.getTitle();
            for(int l = 0; l < ctBTitle.getTx().getRich().getPArray(0).getRArray().length; l++){
                ctBTitle.getTx().getRich().getPArray(0).getRArray(l).getRPr().setSz(Integer.valueOf(axisTitleSize)*100);
            }

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

    @PostMapping("/downloadCompact")
    @ResponseBody
    public void downloadCompactPPT(@RequestParam("file") MultipartFile file, HttpServletResponse response, @RequestParam("titleSize") String titleSize,
                            @RequestParam("axisNumSize") String axisNumSize, @RequestParam("axisTitleSize") String axisTitleSize,
                            @RequestParam("legendFontSize") String legendFontSize,
                            @RequestParam("chartWidth") String chartWidth, @RequestParam("chartHeight") String chartHeight,
                            @RequestParam("chartX") String chartX, @RequestParam("chartY") String chartY) throws Exception {
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
        slideShow.setPageSize(new java.awt.Dimension(960, 540));
        ArrayList<XSLFSlide> slidesList = new ArrayList<>();
        for(int j =0; j < 6; j++){
            slidesList.add(slideShow.createSlide());
        }
        ArrayList<XSLFChart> pptCharts = new ArrayList<>();
        for(int j =3; j < 36; j++){
            pptCharts.add(slideShow.createChart());
        }
        for(int j =0; j < 6; j++){
            if(j == 0){
                slidesList.get(j).addChart(pptCharts.get(0), new java.awt.geom.Rectangle2D.Double(0d* Units.EMU_PER_CENTIMETER, 4.5*Units.EMU_PER_CENTIMETER, 17*Units.EMU_PER_CENTIMETER,12.6*Units.EMU_PER_CENTIMETER));
                slidesList.get(j).addChart(pptCharts.get(1), new java.awt.geom.Rectangle2D.Double(17d* Units.EMU_PER_CENTIMETER, 4.5*Units.EMU_PER_CENTIMETER, 17*Units.EMU_PER_CENTIMETER,12.6*Units.EMU_PER_CENTIMETER));
            }

            if(j == 1){
                slidesList.get(j).addChart(pptCharts.get(6), new java.awt.geom.Rectangle2D.Double(0d* Units.EMU_PER_CENTIMETER, 4.5*Units.EMU_PER_CENTIMETER, 17*Units.EMU_PER_CENTIMETER,12.6*Units.EMU_PER_CENTIMETER));
                slidesList.get(j).addChart(pptCharts.get(7), new java.awt.geom.Rectangle2D.Double(17d* Units.EMU_PER_CENTIMETER, 4.5*Units.EMU_PER_CENTIMETER, 17*Units.EMU_PER_CENTIMETER,12.6*Units.EMU_PER_CENTIMETER));
            }
            if(j == 2){
                slidesList.get(j).addChart(pptCharts.get(8), new java.awt.geom.Rectangle2D.Double(0d* Units.EMU_PER_CENTIMETER, 4.5*Units.EMU_PER_CENTIMETER, 17*Units.EMU_PER_CENTIMETER,12.6*Units.EMU_PER_CENTIMETER));
                slidesList.get(j).addChart(pptCharts.get(9), new java.awt.geom.Rectangle2D.Double(17d* Units.EMU_PER_CENTIMETER, 4.5*Units.EMU_PER_CENTIMETER, 17*Units.EMU_PER_CENTIMETER,12.6*Units.EMU_PER_CENTIMETER));
            }
            if(j == 3){
                slidesList.get(j).addChart(pptCharts.get(10), new java.awt.geom.Rectangle2D.Double(0d* Units.EMU_PER_CENTIMETER, 4.5*Units.EMU_PER_CENTIMETER, 17*Units.EMU_PER_CENTIMETER,12.6*Units.EMU_PER_CENTIMETER));
                slidesList.get(j).addChart(pptCharts.get(11), new java.awt.geom.Rectangle2D.Double(17d* Units.EMU_PER_CENTIMETER, 4.5*Units.EMU_PER_CENTIMETER, 17*Units.EMU_PER_CENTIMETER,12.6*Units.EMU_PER_CENTIMETER));
            }
            if(j == 4){
                slidesList.get(j).addChart(pptCharts.get(14), new java.awt.geom.Rectangle2D.Double(0d* Units.EMU_PER_CENTIMETER, 4.5*Units.EMU_PER_CENTIMETER, 17*Units.EMU_PER_CENTIMETER,12.6*Units.EMU_PER_CENTIMETER));
                slidesList.get(j).addChart(pptCharts.get(15), new java.awt.geom.Rectangle2D.Double(17d* Units.EMU_PER_CENTIMETER, 4.5*Units.EMU_PER_CENTIMETER, 17*Units.EMU_PER_CENTIMETER,12.6*Units.EMU_PER_CENTIMETER));
            }
            if(j == 5){
                slidesList.get(j).addChart(pptCharts.get(31), new java.awt.geom.Rectangle2D.Double(0d* Units.EMU_PER_CENTIMETER, 4.5*Units.EMU_PER_CENTIMETER, 17*Units.EMU_PER_CENTIMETER,12.6*Units.EMU_PER_CENTIMETER));
                slidesList.get(j).addChart(pptCharts.get(32), new java.awt.geom.Rectangle2D.Double(17d* Units.EMU_PER_CENTIMETER, 4.5*Units.EMU_PER_CENTIMETER, 17*Units.EMU_PER_CENTIMETER,12.6*Units.EMU_PER_CENTIMETER));
            }

//            slidesList.get(j).addChart(pptCharts.get(j), new java.awt.geom.Rectangle2D.Double(Double.valueOf(chartX)* Units.EMU_PER_CENTIMETER, Double.valueOf(chartY)*Units.EMU_PER_CENTIMETER, Double.valueOf(chartWidth)*Units.EMU_PER_CENTIMETER, Double.valueOf(chartHeight)*Units.EMU_PER_CENTIMETER));
        }
        for(int j =0; j < 33; j++){

            chartList.get(j).getCTChart().getTitle().getTx().getRich().getPArray(0).getRArray(0).getRPr().setSz(Integer.parseInt(titleSize)*100);

            pptCharts.get(j).importContent(chartList.get(j));
            pptCharts.get(j).saveWorkbook((XSSFWorkbook) workbook);

            List<? extends XDDFChartAxis> axies = pptCharts.get(j).getAxes();
            XDDFValueAxis bottomAxis = (XDDFValueAxis) axies.get(0);
            XDDFValueAxis leftAxis = (XDDFValueAxis) axies.get(1);

            // font size for left axis labels (ticks)
            bottomAxis.getOrAddTextProperties().setFontSize(Double.valueOf(axisNumSize));
            leftAxis.getOrAddTextProperties().setFontSize(Double.valueOf(axisNumSize));
            //set legend font size
            XDDFChartLegend legend = pptCharts.get(j).getOrAddLegend();
            legend.setPosition(LegendPosition.TOP_RIGHT);
            XDDFTextBody legendTextBody = new XDDFTextBody(legend);
            legendTextBody.getXmlObject().addNewBodyPr();
            legendTextBody.addNewParagraph().addDefaultRunProperties().setFontSize(Double.valueOf(legendFontSize));
            legend.setTextBody(legendTextBody);
            // reflect the underlying the xml objects in order to access fields that are not implemented in apache poi
            org.openxmlformats.schemas.drawingml.x2006.chart.CTValAx ctValAx = null;
            org.openxmlformats.schemas.drawingml.x2006.chart.CTValAx ctCatAx = null;
            org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea ctPlotArea = null;
            java.lang.reflect.Field ctValRef;
            java.lang.reflect.Field ctCatRef;
            java.lang.reflect.Field chartLegendRef;
            java.lang.reflect.Field ctPlotAreaRef;
            try {
                ctValRef = XDDFValueAxis.class.getDeclaredField("ctValAx");
                ctCatRef = XDDFValueAxis.class.getDeclaredField("ctValAx");
                ctValRef.setAccessible(true);
                ctCatRef.setAccessible(true);
                ctValAx = (org.openxmlformats.schemas.drawingml.x2006.chart.CTValAx) ctValRef.get(leftAxis);
                ctCatAx = (org.openxmlformats.schemas.drawingml.x2006.chart.CTValAx) ctCatRef.get(bottomAxis);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            CTDouble ctdouble = CTDouble.Factory.newInstance();
            ctdouble.setVal(-2);
            ctCatAx.getScaling().setMin(ctdouble);
            // set title properties for left axis
            CTTitle ctLTitle = ctValAx.getTitle();
            for(int k = 0; k < ctLTitle.getTx().getRich().getPArray(0).getRArray().length; k++){
                ctLTitle.getTx().getRich().getPArray(0).getRArray(k).getRPr().setSz(Integer.valueOf(axisTitleSize)*100);
            }
            // set title properties for bottom axis
            CTTitle ctBTitle = ctCatAx.getTitle();
            for(int l = 0; l < ctBTitle.getTx().getRich().getPArray(0).getRArray().length; l++){
                ctBTitle.getTx().getRich().getPArray(0).getRArray(l).getRPr().setSz(Integer.valueOf(axisTitleSize)*100);
            }

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
