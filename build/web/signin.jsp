<%-- 
    Document   : login
    Created on : Nov 3, 2020, 9:39:45 AM
    Author     : Sammy Guergachi <sguergachi at gmail.com>
--%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Students Report</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="./assets/imgs/ru.png"> 
        <link href="https://fonts.googleapis.com/css2?family=Kanit:wght@200&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">

        <link rel="stylesheet" href="./assets/css/signin.css">

    </head>
    <body>
        <div class="container" id="container">
            <div class="card" id="card">
                <div class="header" id="header">
                    <img  src="./assets/imgs/logo.png" class="header-img-logo" id="header-img-logo"/>
                    <h1 class="header-text" id="header-text"><span class="header-text-th">เข้าสู่ระบบ</span></h1>
                </div>
                <div class="content" id="content">
                    <form action="/studentsReport/SigninMain" method="POST" name="validateSignin" onsubmit="return (validationSignin());">
                        <div class="content-input-username" id="content-input-username">
                            <input class="input-username" id="input-username" maxlength="15" type="text" name="username" placeholder="Username"/>                        
                        </div>
                        <div class="content-input-password" id="content-input-password">
                            <input class="input-password" id="input-password" maxlength="15" type="password" name="password" placeholder="Password"/>                        
                        </div>
                        <div id="content-username-password-null" class="content-username-password-null">
                            <p id="username-password-null-message" class="username-password-null-message"></p>
                        </div>
                        <div class="content-input-button" id="content-input-button">
                            <button type="submit" class="button-signin-submit" id="button-signin-submit">Sign in</button>
                            <button type="reset" class="button-signin-reset" id="button-signin-reset" onclick="return (clearColor());">Clear</button>                        
                        </div>
                    </form>
                </div>
                <div class="footer" id="footer">

                </div>
            </div>
        </div>

        <div class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-close-button">
                        <span class="close-button">&times;</span>
                        <img  src="./assets/imgs/logo.png" class="close-button-logo"/>
                        <h3>สถานะการเข้าสู่ระบบ</h3>

                    </div>
                </div>  
                <div class="modal-text">
                    <h4 class="modal-text-display">Username หรือ Password ไม่ถูกต้อง!</h4>
                </div>  
            </div>
        </div>

    </body>

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
        
        let signinFailed = '<%=request.getAttribute("signinFailed")%>';
        let isSigninFailed = signinFailed === 'failed' ? modal.classList.toggle("show-modal") : null;

        closeButton.addEventListener("click", toggleModal);
        window.addEventListener("click", windowOnClick);

    </script>
    <script type="text/javascript" src="./assets/js/signin.js"></script>

</html>
