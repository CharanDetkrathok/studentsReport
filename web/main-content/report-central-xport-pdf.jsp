<%-- 
    Document   : report-central-xport-pdf
    Created on : Dec 27, 2020, 9:22:43 PM
    Author     : Sammy Guergachi <sguergachi at gmail.com>
--%>

<%@page import="java.util.List"%>
<%@page import="student.models.ReportCentralInfo"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Students Report</title>
<link rel="shortcut icon" href="./assets/imgs/ru.png"> 
<link href="https://fonts.googleapis.com/css2?family=Kanit:wght@200&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">

<style>
    @media print {

        table.gridtable     { page-break-after: auto; }
        table.gridtable   tr    { page-break-inside:avoid;page-break-after: auto; }
        table.gridtable   td    { page-break-inside:avoid; page-break-after:auto }
        table.gridtable   thead { display:table-header-group }
        table.gridtable   tfoot { display:table-footer-group }

        html, body{
            background-image: none;
        }     

        data-detail, table {
            width: 100%; 
            height: 100%;
            margin-left: -200px;   
            padding: 0;

        }

        #h2-text-detail-report, 
        #p-text,
        #container-content-central-detail, 
        #sidebar {
            display: none;
        }

    }

    @page {
        size: A4;        
    }

</style>


<link rel="stylesheet" href="./assets/css/app-style.css">

<div id="content-central-detail" class="content-central-detail-2">

    <div id="container-content-central-detail" class="container-content-central-detail-2">
        <div class="container-btn-submit-data-detail">
            <!--<a type="button" onclick="history.back()" class="btn-export-back-2">-->
            <a type="button" href="/studentsReport/ReportCentralDetail" class="btn-export-back-2">
                << กลับสู่หน้าหลัก
            </a> 
            <button type="button" class="btn-export-back-3" onClick="window.print();" value="Print">
                <img class="img-export" src="./assets/imgs/pdf_logo1.png" />
                บันทึกไฟล์ PDF
            </button>
        </div>
    </div>

</div>

<div class="data-detail-2">
    <%
        List<ReportCentralInfo> reportExcel = (List<ReportCentralInfo>) request.getAttribute("reportExcel");
        List<ReportCentralInfo> faculty = (List<ReportCentralInfo>) request.getAttribute("faculty");
    %>
    <table class="table-data-detail">
        <thead>              
            <tr>
                <th colspan="18" class="th-header">
                    รายงานจำนวนผู้สมัครเรียนระดับปริญญาตรี ส่วนกลาง <br> ประจำภาค <%= faculty.get(0).ENROLL_SEMESTER%> ปีการศึกษา <%= faculty.get(0).ENROLL_YEAR%>
                </th>
            </tr>
            <tr>
                <th rowspan="2" colspan="1" class="th-faculty">
                    คณะ
                </th>  

                <th colspan="3" class="th-text">
                    สมัครทาง Internet
                </th>  

                <th colspan="1" class="th-text">
                    สมัครด้วยตนเอง
                </th>  

                <th colspan="1" class="th-text">
                    สมัครทางไปรษณี
                </th>  

                <th rowspan="2" colspan="1" class="th-text">
                    ยอดรวม<br> 
                    นักศึกษา
                    ชำระเงิน
                </th>  
            </tr>
            <tr>
                <th colspan="1" class="th-text">
                    ยอดสมัคร
                </th>
                <th colspan="1" class="th-text">
                    ยอดชำระเงิน
                </th>
                <th colspan="1" class="th-text">
                    ยอดออกรหัสแล้ว
                </th>
                <th colspan="1" class="th-text">
                    ยอดสมัคร
                </th>
                <th colspan="1" class="th-text">
                    ยอดสมัคร
                </th>
            </tr>   
        </thead>                       
        <tbody>
            <%
                int TOTAL_SUM1 = 0, TOTAL_SUM2 = 0, TOTAL_SUM3 = 0, TOTAL_SUM4 = 0, TOTAL_SUM5 = 0, TOTAL_SUM6 = 0;
                for (int indexOutside = 0; indexOutside < faculty.size(); indexOutside++) {
            %>
            <tr class="">                        
                <td class="data-detail-faculty"><%= faculty.get(indexOutside).FACULTY_NAME_THAI%></td>
                <td class="data-detail-faculty-sum">                            
                    <% if (faculty.get(indexOutside).SUM1 == "0") { %>
                    -
                    <%} else {%>

                    <fmt:formatNumber type="number" maxFractionDigits="3" value="<%= faculty.get(indexOutside).SUM1%>" />
                    <%}%>
                </td>
                <td class="data-detail-faculty-sum">
                    <% if (faculty.get(indexOutside).SUM2 == "0") { %>
                    -
                    <%} else {%>

                    <fmt:formatNumber type="number" maxFractionDigits="3" value="<%= faculty.get(indexOutside).SUM2%>" />
                    <%}%>
                </td>
                <td class="data-detail-faculty-sum">
                    <% if (faculty.get(indexOutside).SUM3 == "0") { %>
                    -
                    <%} else {%>

                    <fmt:formatNumber type="number" maxFractionDigits="3" value="<%= faculty.get(indexOutside).SUM3%>" />
                    <%}%>
                </td>
                <td class="data-detail-faculty-sum">
                    <% if (faculty.get(indexOutside).SUM4 == "0") { %>
                    -
                    <%} else {%>

                    <fmt:formatNumber type="number" maxFractionDigits="3" value="<%= faculty.get(indexOutside).SUM4%>" />
                    <%}%>
                </td>
                <td class="data-detail-faculty-sum">
                    <% if (faculty.get(indexOutside).SUM5 == "0") { %>
                    -
                    <%} else {%>

                    <fmt:formatNumber type="number" maxFractionDigits="3" value="<%= faculty.get(indexOutside).SUM5%>" />
                    <%}%>
                </td>
                <td class="data-detail-faculty-sum">
                    <% if (faculty.get(indexOutside).TOTAL == "0") { %>
                    -
                    <%} else {%>

                    <fmt:formatNumber type="number" maxFractionDigits="3" value="<%= faculty.get(indexOutside).TOTAL%>" />
                    <%}%>
                </td>
                <%
                    for (int indexInside = 0; indexInside < reportExcel.size(); indexInside++) {
                        if (faculty.get(indexOutside).FACULTY_NO.equals(reportExcel.get(indexInside).FACULTY_NO)) {

                %>
            </tr>                    
            <tr>
                <td class="data-detail-major"><%= reportExcel.get(indexInside).MAJOR_NAME_THAI%></td>                   
                <td class="data-detail-major-values">
                    <% if (reportExcel.get(indexInside).CNT_NET_ALL.intValue() > 0) {%>

                    <fmt:formatNumber type="number" maxFractionDigits="3" value="<%=reportExcel.get(indexInside).CNT_NET_ALL.intValue()%>" />
                    <%} else {%>
                    -
                    <%}%>
                </td>
                <td class="data-detail-major-values">
                    <% if (reportExcel.get(indexInside).CNT_NET_RECEIPT.intValue() > 0) {%>

                    <fmt:formatNumber type="number" maxFractionDigits="3" value="<%=reportExcel.get(indexInside).CNT_NET_RECEIPT.intValue()%>" />
                    <%} else {%>
                    -
                    <%}%>
                </td>
                <td class="data-detail-major-values">
                    <% if (reportExcel.get(indexInside).CNT_NET_STD.intValue() > 0) {%>

                    <fmt:formatNumber type="number" maxFractionDigits="3" value="<%=reportExcel.get(indexInside).CNT_NET_STD.intValue()%>" />
                    <%} else {%>
                    -
                    <%}%>
                </td>
                <td class="data-detail-major-values">
                    <% if (reportExcel.get(indexInside).CNT_CENT_ALL.intValue() > 0) {%>

                    <fmt:formatNumber type="number" maxFractionDigits="3" value="<%=reportExcel.get(indexInside).CNT_CENT_ALL.intValue()%>" />
                    <%} else {%>
                    -
                    <%}%>
                </td>
                <td class="data-detail-major-values">
                    <% if (reportExcel.get(indexInside).CNT_POST_ALL.intValue() > 0) {%>

                    <fmt:formatNumber type="number" maxFractionDigits="3" value="<%=reportExcel.get(indexInside).CNT_POST_ALL.intValue()%>" />
                    <%} else {%>
                    -
                    <%}%>
                </td>
                <td class="data-detail-major-values-last">
                    <% if (reportExcel.get(indexInside).TOTAL_COLUMN.equals("0")) {%>
                    -
                    <%} else {
                        TOTAL_SUM6 = TOTAL_SUM6 + Integer.valueOf(reportExcel.get(indexInside).TOTAL_COLUMN);
                    %>

                    <fmt:formatNumber type="number" maxFractionDigits="3" value="<%=reportExcel.get(indexInside).TOTAL_COLUMN%>" />
                    <%}%>
                </td>
            </tr>
            <% }// end if 
                }// end for reportExcel%>


            <%  
                    TOTAL_SUM1 = TOTAL_SUM1 + Integer.valueOf(faculty.get(indexOutside).SUM1);
                    TOTAL_SUM2 = TOTAL_SUM2 + Integer.valueOf(faculty.get(indexOutside).SUM2);
                    TOTAL_SUM3 = TOTAL_SUM3 + Integer.valueOf(faculty.get(indexOutside).SUM3);
                    TOTAL_SUM4 = TOTAL_SUM4 + Integer.valueOf(faculty.get(indexOutside).SUM4);
                    TOTAL_SUM5 = TOTAL_SUM5 + Integer.valueOf(faculty.get(indexOutside).SUM5);
                } // end for faculty%>
            <tr>
                <td class="data-detail-faculty">รวมทั้งสื้น</td>                   
                <td class="data-detail-faculty-total"><fmt:formatNumber type="number" maxFractionDigits="3" value="<%=TOTAL_SUM1%>" /></td>
                <td class="data-detail-faculty-total"><fmt:formatNumber type="number" maxFractionDigits="3" value="<%=TOTAL_SUM2%>" /></td>
                <td class="data-detail-faculty-total"><fmt:formatNumber type="number" maxFractionDigits="3" value="<%=TOTAL_SUM3%>" /></td>
                <td class="data-detail-faculty-total"><fmt:formatNumber type="number" maxFractionDigits="3" value="<%=TOTAL_SUM4%>" /></td>
                <td class="data-detail-faculty-total"><fmt:formatNumber type="number" maxFractionDigits="3" value="<%=TOTAL_SUM5%>" /></td>
                <td class="data-detail-faculty-total"><fmt:formatNumber type="number" maxFractionDigits="3" value="<%=TOTAL_SUM6%>" /></td>
            </tr>
            <tr>
                <td class="footer-date"  colspan="18">ข้อมูล ณ วันที่ <%=request.getAttribute("footerDate")%> </td>         
            </tr>
            <tr>
                <td class="footer-date-2"  colspan="18"> <span class="footer-date-2-1">มหาวิทยาลัยรามคำแหง</span> <span class="footer-date-2-2">สถาบันคอมพิวเตอร์</span> </td>
            </tr>
        </tbody>
    </table>
</div>


