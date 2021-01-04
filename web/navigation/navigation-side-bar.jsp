<%-- 
    Document   : navigation-side-bar
    Created on : Nov 6, 2020, 2:23:43 PM
    Author     : Sammy Guergachi <sguergachi at gmail.com>
--%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Students Report</title>
        <link rel="shortcut icon" href="./assets/imgs/ru.png"> 
        <link href="https://fonts.googleapis.com/css2?family=Kanit:wght@200&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">

        <link rel="stylesheet" href="./assets/css/app-style.css">
        <link rel="stylesheet" href="./assets/css/modals-signout.css">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
    </head>
    <body>
        <span id="hamburgerBtn">&#9776</span>
        <div id="sidebar" class="sidebar">
            <div class="user-status">
                <div class="img-sidebar-user">
                    <img class="img-sidebar-users" src="./assets/imgs/logo.png" />
                </div>
                <div class="user">  
                    ${username}
                </div> 
                <div class="signout">
                    <button id="signout-button" class="signout-button">
                        <i class="icon-off"></i>&nbsp; ออกจากระบบ
                    </button>
                </div>
            </div>
            <div id="bar">
                <a class="btn-sidebar"  href="MainIndex">
                    <img class="img-sidebar" src="./assets/imgs/home-logo.png" />
                    หน้าแรก
                </a>
                <a class="btn-sidebar first-dropdown">
                    <img class="img-sidebar" src="./assets/imgs/lists3.png" />
                    รายงานรับสมัครนักศึกษาใหม่ &nbsp;&nbsp; ระดับปริญญาตรี
                    <img class="img-sidebar-cursor" src="./assets/imgs/cursor.png" />
                </a>
                <div class="dropdown-list-first">
                    <!--<hr>-->
                    <a class="btn-sidebar lists-item" href="ReportCentralDetail">
                        <img class="img-export" src="./assets/imgs/pdf_logo3.png" />
                        ข้อมูลรับสมัคร ส่วนกลาง PDF
                    </a>
                    <!--<hr>-->
                    <a class="btn-sidebar lists-item" href="ReportProvincialDetail">
                        <img class="img-export" src="./assets/imgs/pdf_logo3.png" />
                        ข้อมูลรับสมัคร ส่วนภูมิภาค PDF
                    </a>
                    <!--<hr>-->
                    <a class="btn-sidebar lists-item" href="ReportCenral">
                        <img class="img-sidebar" src="./assets/imgs/excel-logo.png" />
                        ข้อมูลรับสมัคร ส่วนกลาง Excel
                    </a>
                    <!--<hr>-->
                    <a class="btn-sidebar lists-item" href="ReportProvincial">
                        <img class="img-sidebar" src="./assets/imgs/excel-logo.png" />
                        ข้อมูลรับสมัคร ส่วนภูมิภาค Excel
                    </a>
                    <!--<hr>-->
                </div>
            </div>
        </div>

        <div class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-close-button">
                        <span class="close-button">&times;</span>
                        <img  src="./assets/imgs/logo.png" class="close-button-logo"/>
                        <h3>ออกจากระบบ</h3>

                    </div>
                </div> 
                <div class="modal-text">
                    <h4>คุณต้องการออกจากระบบใช่หรือไม่?</h4>
                    <a id="close-btn" class="signout-btn-2">
                        ไม่ใช่
                    </a>
                    <a href="SignoutMain" class="signout-btn-1">
                        ใช่
                    </a>                    
                </div>  
            </div>
        </div>

    </body>
    <script type="text/javascript">

        var btn = document.getElementById("signout-button");
        var closeBtn = document.getElementById("close-btn");
        var modal = document.getElementById(".modal");
        var closeButton = document.getElementById("close-button");

        btn.onclick = function () {
            modal.classList.toggle("show-modal");
        }

        function toggleModal() {
            modal.classList.toggle("show-modal");
        }

        function windowOnClick(event) {
            if (event.target === modal) {
                toggleModal();
            }
        }
        closeBtn.addEventListener("click", toggleModal);
        closeButton.addEventListener("click", toggleModal);
        window.addEventListener("click", windowOnClick);
    </script>
    <script type="text/javascript" src="./assets/js/sidebar.js"></script>
</html>
