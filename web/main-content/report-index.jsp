<%-- 
    Document   : report-index
    Created on : Nov 9, 2020, 11:12:01 AM
    Author     : Sammy Guergachi <sguergachi at gmail.com>
--%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="./assets/css/index.css">
<link rel="stylesheet" href="./assets/css/modals.css">
<jsp:include page="../navigation/navigation-side-bar.jsp"/>

<div class="main-content">
    
    <div class="container-content">
        <img class="img-excl-index" src="./assets/imgs/ru.png" />
    </div>
    
    <h2 class="h2-text-index">ยินดีต้อนรับเข้าสู่ระบบ</h2>
    <h2 class="h2-text-index">ออกรายงาน และแสดงผลข้อมูล</h2>
    <h2 class="h2-text-index">เพื่อเข้าศึกษาในระดับปริญญาตรี</h2>
    
<!--    <p class="p-text">ระบบออกรายงาน และแสดงผลข้อมูล</p>
    <p class="p-text">การสมัครเพื่อเข้าศึกษา ระดับปริญญาตรี</p>
    <p class="p-text">ส่วนกลาง และส่วนภูมิภาค</p>-->
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