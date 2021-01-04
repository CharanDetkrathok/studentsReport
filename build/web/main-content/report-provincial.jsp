<%-- 
    Document   : report- provincial
    Created on : Nov 9, 2020, 9:40:24 AM
    Author     : Sammy Guergachi <sguergachi at gmail.com>
--%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="./assets/css/provin.css">
<link rel="stylesheet" href="./assets/css/modals.css">

<jsp:include page="../navigation/navigation-side-bar.jsp"/>

<div class="main-content">

    <h2 class="h2-text">ออกรายงาน "ส่วนภูมิภาค"</h2>
    <p class="p-text">รายงานจำนวนผู้สมัครเรียนระดับปริญญาตรี</p>
    <p class="p-text-central-provincial">นำออกไฟล์รายงาน "ส่วนภูมิภาค" Excel file...</p>

    <div class="container-content">
        <div class="container-btn-submit">
            <form action="/studentsReport/ReportProvincialExportExcel" method="POST" onsubmit="return validationSigninCheck()">       
                <label for="">
                    <p class="p-text-selector"> เลือกปีการศึกษา และภาคการศึกษา </p>
                    <p class="p-text-selector-check"></p>
                </label>   
                <div class="container-selector">    
                    <select name="enroll" id="select-itemp">
                        <option class="select-option" value="">ปี/ภาค การศึกษา</option>
                        <c:forEach items="${enroll}" var = "enroll" varStatus="count">   
                            <c:choose>
                                <c:when test = "${enroll.ENROLL_YEAR >= '2563' && enroll.ENROLL_SEMESTER == '1'}">
                                    <option class="select-option" value="${enroll.ENROLL_YEAR}/${enroll.ENROLL_SEMESTER}">${enroll.ENROLL_YEAR} / ${enroll.ENROLL_SEMESTER}</option>
                                </c:when>
                            </c:choose>                              
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" name="submit-data-report" class="btn-export-detail-report">
                    <img class="img-export" src="./assets/imgs/excel-logo.png" />
                    Export รายงาน
                </button>
            </form>
        </div>        

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
        
    function validationSigninCheck() {
        let selectOption = document.querySelector('#select-itemp').value;

        console.log(selectOption);

        let isValidation = selectOption !== '' ? true : false;
        if (isValidation === false) {
            console.log(isValidation);

            document.querySelector('.p-text-selector-check').innerHTML = "กรุณาทำการเลือก ปี/ภาค การศึกษา ก่อนค่ะ !";

        } else {
            document.querySelector('.p-text-selector-check').innerHTML = "";
        }

        return isValidation;
    }
    
    let exExProvin = '<%=request.getAttribute("export-excel-provincial")%>';
    let isExExProvin = exExProvin === 'success' ? document.querySelector('.p-text-selector-check').innerHTML = "" : null;
     
</script>
