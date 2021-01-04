package student.main.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.System.out;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.HeaderFooter;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Alignment;
import jxl.write.Border;
import jxl.write.BorderLineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.commons.io.IOUtils;
import student.models.DatabaseProvincail;
import student.models.ReportProvincialInfo;
import student.models.ReportProvincialModel;

public class ReportProvincialExportExcel extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();

        if (session.getAttribute("username") != null) {

            String tempEnroll = request.getParameter("enroll");

            String[] enroll = tempEnroll.split("\\/");
            String year = enroll[0];
            String semester = enroll[1];

            DatabaseProvincail db = new DatabaseProvincail();

            ReportProvincialModel getReportProvincial = new ReportProvincialModel(db);

            List<ReportProvincialInfo> reportExcel = getReportProvincial.findAll(year, semester);

            List<ReportProvincialInfo> provincial = getReportProvincial.findAllFaculty(year, semester);

            String FILE_NAME = "";
            FILE_NAME = exportExcelReport(reportExcel, provincial, response);

            if (FILE_NAME != "") {
                request.setAttribute("export-excel-provincial", "success");
            } else {
                request.setAttribute("export-excel-provincial", "failed");
            }
            db.close();

            RequestDispatcher rs = request.getRequestDispatcher("ReportProvincial");
            rs.forward(request, response);

        } else {

            RequestDispatcher rs = request.getRequestDispatcher("SigninMain");
            rs.forward(request, response);

        }

    }

    public String exportExcelReport(List<ReportProvincialInfo> report, List<ReportProvincialInfo> provin, HttpServletResponse response) throws ParseException, IOException {

        WritableWorkbook wworkbook;

//        String FILE_PATH = "/opt/tomcat8.5/webapps/studentsReport/ReportsExcelProvincial/";
        String FILE_PATH = "/opt/tomcat8.5/webapps/studentsReport/";
        String TEMP_FILE_NAME = getDateToFileName();
        String FILE_NAME = "รายงานจำนวนผู้สมัครใหม่ระดับปริญญาตรี_ส่วนภูมิภาค(สมัครทาง_Internet_และสมัครด้วยตนเอง)" + TEMP_FILE_NAME + ".xls";
//        String FILE_NAME = "รายงานจำนวนผู้สมัครใหม่ระดับปริญญาตรี_ส่วนภูมิภาค(สมัครทาง_Internet_และสมัครด้วยตนเอง).xls";

        File myDir = new File(FILE_PATH + FILE_NAME);

        wworkbook = Workbook.createWorkbook(myDir);

        try {
            //ชื่อของ Sheet
            WritableSheet sheet = wworkbook.createSheet("ส่วนภูมิภาค", 0);

            WritableSheet sheetSetHeader = wworkbook.getSheet(0);
            sheetSetHeader.getSettings().setPrintTitlesRow(0, 2);

            HeaderFooter headerPages = new HeaderFooter();
            headerPages.getRight().append("หน้าที่ ");
            headerPages.getRight().appendPageNumber();
            headerPages.getRight().append(" จาก ");
            headerPages.getRight().appendTotalPages();
            sheet.getSettings().setHeader(headerPages);

            HeaderFooter footerPages = new HeaderFooter();
            footerPages.getLeft().append("มหาวิทยาลัยรามคำแหง");
            footerPages.getRight().append("สถาบันคอมพิวเตอร์");
            sheet.getSettings().setFooter(footerPages);

            // สร้าง font และ format
            WritableFont firstHeaderFont = new WritableFont(WritableFont.createFont("TH SarabunPSK"), 17, WritableFont.BOLD);
            WritableFont headerFont = new WritableFont(WritableFont.createFont("TH SarabunPSK"), 16, WritableFont.BOLD);

            // สร้าง format จัดกึ่งกลาง มีขอบทั้ง 4 ด้านแบบ ขอบบาง *** หัวตาราง
            WritableCellFormat firstHeaderFormat = new WritableCellFormat(firstHeaderFont);
            firstHeaderFormat.setAlignment(Alignment.CENTRE);
            firstHeaderFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            firstHeaderFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            firstHeaderFormat.setBackground(jxl.format.Colour.GRAY_25);

            // สร้าง format จัดกึ่งกลาง มีขอบทั้ง 4 ด้านแบบ ขอบบาง *** หัวตาราง
            WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
            headerFormat.setAlignment(Alignment.CENTRE);
            headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            headerFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            headerFormat.setBackground(jxl.format.Colour.GRAY_25);

            // สร้าง format จัดชิดซ้าย และชิดขอบล่าง มีขอบทั้ง 4 ด้านแบบ ขอบบาง *** หัวตาราง
            WritableCellFormat headerFormat2 = new WritableCellFormat(headerFont);
            headerFormat2.setAlignment(Alignment.CENTRE);
            headerFormat2.setVerticalAlignment(VerticalAlignment.BOTTOM);
            headerFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
            headerFormat2.setBackground(jxl.format.Colour.GRAY_25);

            // สร้าง format จัดกึ่งกลาง และชิดขอบล่าง มีขอบทั้ง 4 ด้านแบบ ขอบบาง *** หัวตาราง
            WritableCellFormat headerFormat3 = new WritableCellFormat(headerFont);
            headerFormat3.setAlignment(Alignment.CENTRE);
            headerFormat3.setVerticalAlignment(VerticalAlignment.BOTTOM);
            headerFormat3.setBorder(Border.ALL, BorderLineStyle.THIN);

            // สร้าง format จัดชิดซ้าย และกึ่งกลาง มีขอบทั้ง 4 ด้านแบบ ขอบบาง *** ชื่อคณะ
            WritableCellFormat mainContent = new WritableCellFormat(headerFont);
            mainContent.setAlignment(Alignment.GENERAL);
            mainContent.setVerticalAlignment(VerticalAlignment.CENTRE);
            mainContent.setBorder(Border.ALL, BorderLineStyle.THIN);
            mainContent.setBackground(jxl.format.Colour.GRAY_25);

            WritableCellFormat mainLastContent = new WritableCellFormat(headerFont);
            mainLastContent.setAlignment(Alignment.LEFT);
            mainLastContent.setVerticalAlignment(VerticalAlignment.CENTRE);
            mainLastContent.setBorder(Border.ALL, BorderLineStyle.THIN);
            mainLastContent.setBackground(jxl.format.Colour.GRAY_25);

            // สร้าง format จัดกึ่งกลาง มีเส้นใต้ 2 เส้น มีขอบทั้ง 4 ด้านแบบ ขอบบาง *** ผลรวม
            WritableFont lastHeaderFormatFont = new WritableFont(WritableFont.createFont("TH SarabunPSK"), 16, WritableFont.BOLD);
            WritableCellFormat lastHeaderFormatSum = new WritableCellFormat(lastHeaderFormatFont);
            lastHeaderFormatSum.setAlignment(Alignment.CENTRE);
            lastHeaderFormatSum.setVerticalAlignment(VerticalAlignment.BOTTOM);
            lastHeaderFormatSum.setBorder(Border.ALL, BorderLineStyle.THIN);
            lastHeaderFormatSum.setBackground(jxl.format.Colour.GRAY_25);
            lastHeaderFormatSum.setWrap(true);

            // สร้าง format จัดกึ่งกลาง มีเส้นใต้ 2 เส้น มีขอบทั้ง 4 ด้านแบบ ขอบบาง *** ผลรวม
            NumberFormat decimalNo = new NumberFormat("#,##0;(#,##0)a");
            WritableFont mainContentSumFont = new WritableFont(WritableFont.createFont("TH SarabunPSK"), 16, WritableFont.BOLD, false, UnderlineStyle.DOUBLE_ACCOUNTING);
            WritableCellFormat mainCeontentSum = new WritableCellFormat(mainContentSumFont, decimalNo);
            mainCeontentSum.setAlignment(Alignment.RIGHT);
            mainCeontentSum.setVerticalAlignment(VerticalAlignment.BOTTOM);
            mainCeontentSum.setBorder(Border.ALL, BorderLineStyle.THIN);

            WritableCellFormat mainCeontentSum_feildHeaderColor = new WritableCellFormat(mainContentSumFont, decimalNo);
            mainCeontentSum_feildHeaderColor.setAlignment(Alignment.RIGHT);
            mainCeontentSum_feildHeaderColor.setVerticalAlignment(VerticalAlignment.BOTTOM);
            mainCeontentSum_feildHeaderColor.setBorder(Border.ALL, BorderLineStyle.THIN);
            mainCeontentSum_feildHeaderColor.setBackground(jxl.format.Colour.GRAY_25);

            // สร้าง format cellสุดท้าย ให้มีสีพื้นหลังเป็น สีเทา *** ผลรวมหัวข้อหลัก
            WritableFont lastMainCeontentSumFont = new WritableFont(WritableFont.createFont("TH SarabunPSK"), 16, WritableFont.BOLD, false, UnderlineStyle.DOUBLE_ACCOUNTING);
            WritableCellFormat lastMainCeontentSum = new WritableCellFormat(lastMainCeontentSumFont, decimalNo);
            lastMainCeontentSum.setAlignment(Alignment.RIGHT);
            lastMainCeontentSum.setVerticalAlignment(VerticalAlignment.BOTTOM);
            lastMainCeontentSum.setBorder(Border.ALL, BorderLineStyle.THIN);
            lastMainCeontentSum.setBackground(jxl.format.Colour.GRAY_25);

            // สร้าง format cellสุดท้าย ให้มีสีพื้นหลังเป็น สีเทา *** ผลรวม
            WritableFont lastContentFont = new WritableFont(WritableFont.createFont("TH SarabunPSK"), 16, WritableFont.NO_BOLD);
            WritableCellFormat lastContent = new WritableCellFormat(lastContentFont);
            lastContent.setAlignment(Alignment.CENTRE);
            lastContent.setVerticalAlignment(VerticalAlignment.BOTTOM);
            lastContent.setBorder(Border.ALL, BorderLineStyle.THIN);
            lastContent.setBackground(jxl.format.Colour.GRAY_25);

            // สร้าง format cell contentText            
            WritableFont contentTextFont = new WritableFont(WritableFont.createFont("TH SarabunPSK"), 16, WritableFont.NO_BOLD);
            WritableCellFormat contentText = new WritableCellFormat(contentTextFont);
            contentText.setAlignment(Alignment.LEFT);
            contentText.setVerticalAlignment(VerticalAlignment.CENTRE);
            contentText.setBorder(Border.ALL, BorderLineStyle.THIN);

            // สร้าง format cell contentText
            WritableFont contentNumFont = new WritableFont(WritableFont.createFont("TH SarabunPSK"), 16, WritableFont.NO_BOLD);
            WritableCellFormat contentNumText = new WritableCellFormat(contentNumFont, decimalNo);
            contentNumText.setAlignment(Alignment.RIGHT);
            contentNumText.setVerticalAlignment(VerticalAlignment.CENTRE);
            contentNumText.setBorder(Border.ALL, BorderLineStyle.THIN);

            // สร้าง format cell ใส่เครื่องหมาย (-) กรณีที่ค่าเป็น 0
            WritableFont lastContentNumFont = new WritableFont(WritableFont.createFont("TH SarabunPSK"), 16, WritableFont.NO_BOLD);
            WritableCellFormat lastContentNum = new WritableCellFormat(lastContentNumFont, decimalNo);
            lastContentNum.setAlignment(Alignment.RIGHT);
            lastContentNum.setVerticalAlignment(VerticalAlignment.CENTRE);
            lastContentNum.setBorder(Border.ALL, BorderLineStyle.THIN);
            lastContentNum.setBackground(jxl.format.Colour.GRAY_25);

            // สร้าง format จัดกึ่งกลาง มีเส้นใต้ 2 เส้น มีขอบทั้ง 4 ด้านแบบ ขอบบาง *** ผลรวม
            NumberFormat decimalNoTemp = new NumberFormat("-#Text");
            WritableFont ContentSumFontTemp = new WritableFont(WritableFont.createFont("TH SarabunPSK"), 16, WritableFont.BOLD);
            WritableCellFormat CeontentSumTemp = new WritableCellFormat(ContentSumFontTemp, decimalNoTemp);
            CeontentSumTemp.setAlignment(Alignment.RIGHT);
            CeontentSumTemp.setVerticalAlignment(VerticalAlignment.BOTTOM);
            CeontentSumTemp.setBorder(Border.ALL, BorderLineStyle.THIN);

            // สร้าง format จัดกึ่งกลาง มีเส้นใต้ 2 เส้น มีขอบทั้ง 4 ด้านแบบ ขอบบาง *** ผลรวม
            WritableCellFormat lastCeontentSumTemp2 = new WritableCellFormat(ContentSumFontTemp, decimalNoTemp);
            lastCeontentSumTemp2.setAlignment(Alignment.RIGHT);
            lastCeontentSumTemp2.setVerticalAlignment(VerticalAlignment.BOTTOM);
            lastCeontentSumTemp2.setBorder(Border.ALL, BorderLineStyle.THIN);
            lastCeontentSumTemp2.setBackground(jxl.format.Colour.GRAY_25);

            //--------- Header -------------------------------------------------
            //รวม column หรือ cell
            sheet.mergeCells(0, 0, 6, 0); //หัวตาราง
            sheet.mergeCells(0, 1, 1, 2); //สาขาวิทยบริการฯ/ คณะ/ สาขาวิชา
            sheet.mergeCells(2, 1, 4, 1); //สมัครทาง Internet
            sheet.mergeCells(6, 1, 6, 2); //ยอดรวม นักศึกษาชำระเงิน

            // เตรียมข้อมูลสำหรับหยอด ลง cell หรือ column ที่ต้องการ
            Label haeder1 = new Label(0, 0, "รายงานจำนวนผู้สมัครใหม่ระดับปริญญาตรี ส่วนภูมิภาค ประจำภาค " + report.get(0).ENROLL_SEMESTER + " ปีการศึกษา " + report.get(0).ENROLL_YEAR, firstHeaderFormat);
            Label header2 = new Label(0, 1, "สาขาวิทยบริการฯ/ คณะ/ สาขาวิชา", headerFormat2);
            Label header3 = new Label(2, 1, "สมัครทาง Internet", headerFormat);
            Label header4 = new Label(5, 1, "สมัครด้วยตนเอง", headerFormat);
            Label header5 = new Label(6, 1, "ยอดรวม \n นักศึกษาชำระเงิน", lastHeaderFormatSum);
            Label header6 = new Label(2, 2, "ยอดสมัคร", headerFormat);
            Label header7 = new Label(3, 2, "ยอดชำระเงิน", headerFormat);
            Label header8 = new Label(4, 2, "ยอดออกรหัสแล้ว", headerFormat);
            Label header9 = new Label(5, 2, "ยอดสมัคร", headerFormat);

            // จัดความกว้างของ cell
            sheet.setColumnView(0, 25);
            sheet.setColumnView(1, 25);
            sheet.setColumnView(2, 12);
            sheet.setColumnView(3, 15);
            sheet.setColumnView(4, 15);
            sheet.setColumnView(5, 15);
            sheet.setColumnView(6, 17);

            // หยอดข้อมูลลง cell
            sheet.addCell(haeder1);
            sheet.addCell(header2);
            sheet.addCell(header3);
            sheet.addCell(header4);
            sheet.addCell(header5);
            sheet.addCell(header6);
            sheet.addCell(header7);
            sheet.addCell(header8);
            sheet.addCell(header9);
            //--------- End Header ---------------------------------------------

            int headerCounter = 3;
            int lastCounter = 4;
            int intFirstSum = lastCounter;
            int intLastSum = lastCounter + 1;
            int[] arrTotalSum = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

            String strTempSum = "";

            Label mainCeontent;
            Label mainLastCeontent;
            Label leftHeader1;
            Label leftHeader2;

            Formula mainCeontentSum1;
            Formula mainCeontentSum2;
            Formula mainCeontentSum3;
            Formula mainCeontentSum4;
            Formula mainCeontentSum5;
            Formula mainCeontentSum6;
            Formula contentNumSum;
            Formula total1;
            Formula total2;
            Formula total3;
            Formula total4;
            Formula total5;
            Formula total6;

            jxl.write.Number contentNum1;
            jxl.write.Number contentNum2;
            jxl.write.Number contentNum3;
            jxl.write.Number contentNum4;

            for (int indexOutside = 0; indexOutside < provin.size(); indexOutside++) {

                intFirstSum = lastCounter + 1;
                intLastSum = lastCounter;
                boolean feildHeaderColor = true;

                mainCeontent = new Label(0, headerCounter, "สาขาวิทยบริการฯ " + provin.get(indexOutside).REGINAL_NAME_THAI, headerFormat2);
                sheet.mergeCells(0, headerCounter, 1, headerCounter);
                sheet.addCell(mainCeontent);

                for (int indexInside = 0; indexInside < report.size(); indexInside++) {
                    if (provin.get(indexOutside).REGINAL_NO.equals(report.get(indexInside).REGINAL_NO)) {

                        strTempSum = "SUM(D" + (lastCounter + 1) + ",F" + (lastCounter + 1) + ")";

                        leftHeader1 = new Label(0, lastCounter, report.get(indexInside).FACULTY_NAME_THAI, contentText);
                        leftHeader2 = new Label(1, lastCounter, report.get(indexInside).MAJOR_NAME_THAI, contentText);
                        sheet.addCell(leftHeader1);
                        sheet.addCell(leftHeader2);

                        if (report.get(indexInside).CNT_ALL.intValue() > 0) {
                            contentNum1 = new jxl.write.Number(2, lastCounter, report.get(indexInside).CNT_ALL.intValue(), contentNumText);
                        } else {
                            contentNum1 = new jxl.write.Number(2, lastCounter, report.get(indexInside).CNT_ALL.intValue(), CeontentSumTemp);
                        }

                        if (report.get(indexInside).CNT_RECEIPT.intValue() > 0) {
                            contentNum2 = new jxl.write.Number(3, lastCounter, report.get(indexInside).CNT_RECEIPT.intValue(), contentNumText);
                        } else {
                            contentNum2 = new jxl.write.Number(3, lastCounter, report.get(indexInside).CNT_RECEIPT.intValue(), CeontentSumTemp);
                        }

                        if (report.get(indexInside).CNT_STD_CODE.intValue() > 0) {
                            contentNum3 = new jxl.write.Number(4, lastCounter, report.get(indexInside).CNT_STD_CODE.intValue(), contentNumText);
                        } else {
                            contentNum3 = new jxl.write.Number(4, lastCounter, report.get(indexInside).CNT_STD_CODE.intValue(), CeontentSumTemp);
                        }
                        
                        
//                        if (report.get(indexInside).CNT_CENT_ALL.intValue() > 0) {
//                            contentNum4 = new jxl.write.Number(5, lastCounter, report.get(indexInside).CNT_CENT_ALL.intValue(), contentNumText);
//                        } else {
//                            contentNum4 = new jxl.write.Number(5, lastCounter, report.get(indexInside).CNT_CENT_ALL.intValue(), contentNumText);
//                        }
                        if (report.get(indexInside).CNT_CENT_ALL != null) {
                            contentNum4 = new jxl.write.Number(5, lastCounter, report.get(indexInside).CNT_CENT_ALL.intValue(), contentNumText);
                        } else {
                            contentNum4 = new jxl.write.Number(5, lastCounter, 0, CeontentSumTemp);
                        }

                        sheet.addCell(contentNum1);
                        sheet.addCell(contentNum2);
                        sheet.addCell(contentNum3);
                        sheet.addCell(contentNum4);

                        int sumTotalInRow = Integer.parseInt(sheet.getCell(2, lastCounter).getContents()) + Integer.parseInt(sheet.getCell(4, lastCounter).getContents());

                        if (sumTotalInRow > 0) {
                            contentNumSum = new Formula(6, lastCounter, strTempSum, lastContentNum);
                        } else {
                            contentNumSum = new Formula(6, lastCounter, strTempSum, lastCeontentSumTemp2);
                        }

                        sheet.addCell(contentNumSum);

                        lastCounter++;
                        intLastSum++;
                    }
                }

                String strSumC = "SUM(C" + intFirstSum + ":C" + intLastSum + ")";
                String strSumD = "SUM(D" + intFirstSum + ":D" + intLastSum + ")";
                String strSumE = "SUM(E" + intFirstSum + ":E" + intLastSum + ")";
                String strSumF = "SUM(F" + intFirstSum + ":F" + intLastSum + ")";
                String strSumG = "SUM(D" + (headerCounter + 1) + ",F" + (headerCounter + 1) + ")";

                mainCeontentSum2 = new Formula(2, headerCounter, strSumC, mainCeontentSum_feildHeaderColor);
                mainCeontentSum3 = new Formula(3, headerCounter, strSumD, mainCeontentSum_feildHeaderColor);
                mainCeontentSum4 = new Formula(4, headerCounter, strSumE, mainCeontentSum_feildHeaderColor);
                mainCeontentSum5 = new Formula(5, headerCounter, strSumF, mainCeontentSum_feildHeaderColor);
                mainCeontentSum6 = new Formula(6, headerCounter, strSumG, lastMainCeontentSum);

                sheet.addCell(mainCeontentSum2);
                sheet.addCell(mainCeontentSum3);
                sheet.addCell(mainCeontentSum4);
                sheet.addCell(mainCeontentSum5);
                sheet.addCell(mainCeontentSum6);

                arrTotalSum[indexOutside] = (headerCounter + 1);

                if (headerCounter <= lastCounter) {
                    headerCounter = lastCounter;
                    lastCounter++;
                }
            }

            mainLastCeontent = new Label(0, (lastCounter - 1), "รวมทั้งสื้น", mainLastContent);
            sheet.addCell(mainLastCeontent);
            sheet.mergeCells(0, (lastCounter - 1), 1, (lastCounter - 1));

            total2 = new Formula(2, (lastCounter - 1), "SUM(C" + arrTotalSum[0] + ",C" + arrTotalSum[1] + ",C" + arrTotalSum[2] + ",C" + arrTotalSum[3] + ",C" + arrTotalSum[4] + ",C" + arrTotalSum[5] + ",C" + arrTotalSum[6] + ",C" + arrTotalSum[7] + ",C" + arrTotalSum[8] + ",C" + arrTotalSum[9] + ",C" + arrTotalSum[10] + ",C" + arrTotalSum[11] + ",C" + arrTotalSum[12] + ",C" + arrTotalSum[13] + ",C" + arrTotalSum[14] + ",C" + arrTotalSum[15] + ",C" + arrTotalSum[16] + ",C" + arrTotalSum[17] + ",C" + arrTotalSum[18] + ",C" + arrTotalSum[19] + ",C" + arrTotalSum[20] + ",C" + arrTotalSum[21] + ",C" + arrTotalSum[22] + ")", lastMainCeontentSum);
            total3 = new Formula(3, (lastCounter - 1), "SUM(D" + arrTotalSum[0] + ",D" + arrTotalSum[1] + ",D" + arrTotalSum[2] + ",D" + arrTotalSum[3] + ",D" + arrTotalSum[4] + ",D" + arrTotalSum[5] + ",D" + arrTotalSum[6] + ",D" + arrTotalSum[7] + ",D" + arrTotalSum[8] + ",D" + arrTotalSum[9] + ",D" + arrTotalSum[10] + ",D" + arrTotalSum[11] + ",D" + arrTotalSum[12] + ",D" + arrTotalSum[13] + ",D" + arrTotalSum[14] + ",D" + arrTotalSum[15] + ",D" + arrTotalSum[16] + ",D" + arrTotalSum[17] + ",D" + arrTotalSum[18] + ",D" + arrTotalSum[19] + ",D" + arrTotalSum[20] + ",D" + arrTotalSum[21] + ",D" + arrTotalSum[22] + ")", lastMainCeontentSum);
            total4 = new Formula(4, (lastCounter - 1), "SUM(E" + arrTotalSum[0] + ",E" + arrTotalSum[1] + ",E" + arrTotalSum[2] + ",E" + arrTotalSum[3] + ",E" + arrTotalSum[4] + ",E" + arrTotalSum[5] + ",E" + arrTotalSum[6] + ",E" + arrTotalSum[7] + ",E" + arrTotalSum[8] + ",E" + arrTotalSum[9] + ",E" + arrTotalSum[10] + ",E" + arrTotalSum[11] + ",E" + arrTotalSum[12] + ",E" + arrTotalSum[13] + ",E" + arrTotalSum[14] + ",E" + arrTotalSum[15] + ",E" + arrTotalSum[16] + ",E" + arrTotalSum[17] + ",E" + arrTotalSum[18] + ",E" + arrTotalSum[19] + ",E" + arrTotalSum[20] + ",E" + arrTotalSum[21] + ",E" + arrTotalSum[22] + ")", lastMainCeontentSum);
            total5 = new Formula(5, (lastCounter - 1), "SUM(F" + arrTotalSum[0] + ",F" + arrTotalSum[1] + ",F" + arrTotalSum[2] + ",F" + arrTotalSum[3] + ",F" + arrTotalSum[4] + ",F" + arrTotalSum[5] + ",F" + arrTotalSum[6] + ",F" + arrTotalSum[7] + ",F" + arrTotalSum[8] + ",F" + arrTotalSum[9] + ",F" + arrTotalSum[10] + ",F" + arrTotalSum[11] + ",F" + arrTotalSum[12] + ",F" + arrTotalSum[13] + ",F" + arrTotalSum[14] + ",F" + arrTotalSum[15] + ",F" + arrTotalSum[16] + ",F" + arrTotalSum[17] + ",F" + arrTotalSum[18] + ",F" + arrTotalSum[19] + ",F" + arrTotalSum[20] + ",F" + arrTotalSum[21] + ",F" + arrTotalSum[22] + ")", lastMainCeontentSum);
            total6 = new Formula(6, (lastCounter - 1), "SUM(G" + arrTotalSum[0] + ",G" + arrTotalSum[1] + ",G" + arrTotalSum[2] + ",G" + arrTotalSum[3] + ",G" + arrTotalSum[4] + ",G" + arrTotalSum[5] + ",G" + arrTotalSum[6] + ",G" + arrTotalSum[7] + ",G" + arrTotalSum[8] + ",G" + arrTotalSum[9] + ",G" + arrTotalSum[10] + ",G" + arrTotalSum[11] + ",G" + arrTotalSum[12] + ",G" + arrTotalSum[13] + ",G" + arrTotalSum[14] + ",G" + arrTotalSum[15] + ",G" + arrTotalSum[16] + ",G" + arrTotalSum[17] + ",G" + arrTotalSum[18] + ",G" + arrTotalSum[19] + ",G" + arrTotalSum[20] + ",G" + arrTotalSum[21] + ",G" + arrTotalSum[22] + ")", lastMainCeontentSum);

            sheet.addCell(total2);
            sheet.addCell(total3);
            sheet.addCell(total4);
            sheet.addCell(total5);
            sheet.addCell(total6);

            String displayDate = getDate();

            Label footer = new Label(0, lastCounter, "ข้อมูล ณ วันที่ " + displayDate + "      ", contentNumText);
            sheet.addCell(footer);
            sheet.mergeCells(0, lastCounter, 6, lastCounter);

            //ออกไฟล์ Excel
            wworkbook.write();
            //จบการออกไฟล์ 
            wworkbook.close();

            System.out.println("สำเร็จ");
        } catch (Exception e) {
            System.out.println("ไม่สำเร็จ");
            System.out.println(e);
        }

        InputStream in = null;
        OutputStream outstream = null;
        try {
            response.reset();
            in = new FileInputStream(FILE_PATH + FILE_NAME);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(FILE_NAME, "UTF-8") + "\"");
            outstream = response.getOutputStream();
            IOUtils.copyLarge(in, outstream);
        } catch (IOException e) {
            out.write(0);
        } finally {
            IOUtils.closeQuietly(outstream);
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }

        return FILE_NAME;
    }

    public String getDate() throws ParseException {

        LocalDate date = LocalDate.parse(LocalDate.now().toString());
        DateTimeFormatter toThai = DateTimeFormatter.ofPattern("dd/MM/yyyy").withChronology(ThaiBuddhistChronology.INSTANCE);

        return toThai.format(date);
    }

    public String getDateToFileName() throws ParseException {

        LocalDate date = LocalDate.parse(LocalDate.now().toString());
        DateTimeFormatter toThai = DateTimeFormatter.ofPattern("dd-MM-yyyy").withChronology(ThaiBuddhistChronology.INSTANCE);

        return toThai.format(date);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ReportProvincialExportExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ReportProvincialExportExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
