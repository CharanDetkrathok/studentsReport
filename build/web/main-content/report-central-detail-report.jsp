<%-- 
    Document   : report-central-detail-report
    Created on : Dec 24, 2020, 10:49:37 AM
    Author     : Sammy Guergachi <sguergachi at gmail.com>
--%>

<%@page import="java.util.List"%>
<%@page import="student.models.ReportCentralInfo"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet" href="./assets/css/central.css">

<style>
    @media print {

        * {
            box-sizing: border-box;
            font-family: "Sarabun", sans-serif;
            color: black;
            -webkit-print-color-adjust: exact !important;  /* Chrome, Safari */
            print-color-adjust: exact !important;
            color-adjust: exact !important; /*Firefox*/
            margin: 0 auto;

        }



        table.gridtable     { page-break-after: auto; }
        table.gridtable   tr    { page-break-inside:avoid;page-break-after: auto; }
        table.gridtable   td    { page-break-inside:avoid; page-break-after:auto }
        table.gridtable   thead { display:table-header-group }
        table.gridtable   tfoot { display:table-footer-group }

        html, body{
            background-image: none;
        }     

        .data-detail, table {
            width: 100%; 
            height: 100%;
            margin: 0 auto;   
            padding: 0;      
        }

        .footer-date {
            font-size: 12px;
        }

        thead {
            font-size: 13px;
        }

        td {
            font-size: 12px;
        }

        #content-central-detail {
            margin: 0 auto;   
            padding: 0;
        }

        #h2-text-detail-report, 
        #p-text,
        #container-content-central-detail, 
        #h2-text-detail-report,
        #p-text,
        #sidebar {
            display: none;
        }

        @page { 
            size: A4;
        }
        
    }
</style>

<jsp:include page="../navigation/navigation-side-bar.jsp"/>



<div id="content-central-detail" class="content-central-detail">

    <h2 id="h2-text-detail-report" class="h2-text-detail-report">รายงานแยกสาขาวิชา "ส่วนกลาง"</h2>
    <p id="p-text" class="p-text">รายงานจำนวนผู้สมัครเรียนระดับปริญญาตรี</p>

    <div id="container-content-central-detail" class="container-content-central-detail">
        <div class="container-btn-submit-data-detail">
            <a type="button" href="/studentsReport/ReportCentralDetail" class="btn-export-back">
                << กลับสู่หน้าหลัก
            </a> 
            <form method="post" action="/studentsReport/ReportCentralExportPDF">
                <input type="hidden" name="year" value="${year}">
                <input type="hidden" name="semester" value="${semester}">
                <button type="button" class="btn-export-back-1" onClick="window.print();" value="Print">
                    <img class="img-export" src="./assets/imgs/pdf_logo1.png" />
                    บันทึกไฟล์ PDF
                </button>
            </form>
        </div>
    </div>

    <div class="data-detail">
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
                        <% if (faculty.get(indexOutside).TOTAL == "0") {
                        %>
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
                    }%>


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

                <!--                <tr>
                                    <td class="footer-date-2"  colspan="18"> <span class="footer-date-2-1">มหาวิทยาลัยรามคำแหง</span> <span class="footer-date-2-2">สถาบันคอมพิวเตอร์</span> </td>
                                </tr>-->
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript">
    var modal = document.querySelector(".modal");
    var closeButton = document.querySelector(".close-button");

    function toggleModal() {
        modal.classList.toggle("show-modal");
    }

    function windowOnClick(event) {
        if (event.target === modal) {
            toggleModal();
        }
    }

    closeButton.addEventListener("click", toggleModal);
    window.addEventListener("click", windowOnClick);
</script>

