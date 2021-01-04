<%-- 
    Document   : report-central-detail
    Created on : Dec 2, 2020, 11:21:47 AM
    Author     : Sammy Guergachi <sguergachi at gmail.com>
--%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="./assets/css/central.css">
<jsp:include page="../navigation/navigation-side-bar.jsp"/>

<div class="main-content">

    <h2 class="h2-text">ข้อมูลรับสมัคร "ส่วนกลาง" </h2>
    <p class="p-text">รายงานจำนวนผู้สมัครเรียนระดับปริญญาตรี</p>

    <div class="container-content">
        <div class="container-btn-submit">
            <!--action="/studentsReport/#"-->
            <form action="/studentsReport/ReportCentralDetailReport" method="POST" onsubmit="return validationSigninCheck()">       
                <label for="">
                    <p class="p-text-selector"> เลือกปีการศึกษา และภาคการศึกษา </p>
                    <p class="p-text-selector-check"></p>
                </label>   
                <div class="container-selector">    
                    <select name="enroll" id="select-itemp">
                        <option class="select-option" value="">ปี/ภาค การศึกษา</option>
                        <c:forEach items="${enroll}" var = "enroll" varStatus="count">   
                            <c:choose>
                                <c:when test = "${enroll.ENROLL_YEAR >= '2563' && enroll.ENROLL_SEMESTER == '2'}">
                                    <option class="select-option" value="${enroll.ENROLL_YEAR}/${enroll.ENROLL_SEMESTER}">${enroll.ENROLL_YEAR} / ${enroll.ENROLL_SEMESTER}</option>
                                </c:when>
                            </c:choose>                              
                        </c:forEach>
                    </select>                    
                </div>
                
                <button type="submit" name="submit-data-report" class="btn-export-detail">
                    <img class="img-export" src="./assets/imgs/folder.png" />
                    เรียกดูรายละเอียด
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
</script>
