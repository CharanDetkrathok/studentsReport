<%-- 
    Document   : signin-register
    Created on : Dec 16, 2020, 9:22:43 AM
    Author     : Sammy Guergachi <sguergachi at gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Students Report</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link rel="shortcut icon" href="./assets/imgs/ru.png"> 

        <link rel="stylesheet" href="./assets/css/signin-register-style.css">

        <link href="https://fonts.googleapis.com/css?family=Nunito:400,600,700,800&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Kanit:wght@200&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="cont">
            <form action="/studentsReport/SigninMain" method="POST"> <!-- onsubmit="return (validationSignin());" -->
                <div class="form sign-in">
                    <h2>เข้าสู่ระบบ</h2>
                    <label>
                        <span>Email : <span class="lower-case">@ru.ac.th<span></span>
                        <input type="email" name="username" class="" id="" required="true">
                    </label>
                    <label>
                        <span>Password</span>
                        <input type="password" name="password" class="" id="" required="true">
                    </label>
                    <button class="submit" type="submit">Sign In</button>
                </div>
            </form>
            <div class="sub-cont">
                <div class="img">
                    <div class="img-text m-up">
                        <h2>เข้าสู่ระบบ...?</h2>
                        <p>- เข้าสู่ระบบ...ด้วย "@ru.ac.th"</p>
                        <p>- เริ่มต้นใช้งาน โปรดลงทะเบียน คลิกที่ปุ่ม ด้านล่าง</p>
                    </div>
                    <div class="img-text m-in">                        
                        <h2>ลงทะเบียน...?</h2>
                        <p>- ใช้ "@ru.ac.th" ในการลงทะเบียนเท่านั้น!</p>
                        <p>- เข้าสู่ระบบ คลิกที่ปุ่ม ด้านล่าง</p>                        
                    </div>
                    <div class="img-btn">
                        <span class="m-up">ลงทะเบียน</span>
                        <span class="m-in">เข้าสู่ระบบ</span>
                    </div>
                </div> 
                <!--action="studentsReport/RegisterMain"-->
                <form action="/studentsReport/RegisterMain" method="POST" onsubmit="return validationSignin()"> <!-- onsubmit="return (validationSignin());" -->
                    <div class="form sign-up">
                        <h2>ลงทะเบียน</h2>
                        <label>
                            <span>Email : <span class="lower-case">@ru.ac.th<span></span>
                            <input type="email" name="username" class="username" id="username" required="true">
                        </label>
                        <label>
                            <span>Password</span>
                            <input type="password" name="password" class="password" id="password" required="true">
                        </label>
                        <label>
                            <span>Confirm Password</span>
                            <input type="password" name="confirmpassword" class="confirmpassword" id="confirmpassword" required="true">
                        </label>
                        <button type="sumit" class="submit" >Sign Up Now</button>
                    </div>
                </form>
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
                    <h4 class="modal-text-display modal-text-display-0">Username หรือ Password ไม่ถูกต้อง !</h4>
                    <h4 class="modal-text-display modal-text-display-1">กรุณาทำการลงทะเบียนก่อน !</h4>
                    <h4 class="modal-text-display modal-text-display-2">กรุณาใช้ &nbsp;<strong style="color: green;"> "@ru.ac.th" </strong>&nbsp; ในการเข้าสู่ระบบ !</h4>
                </div>  
            </div>
        </div>

        <script type="text/javascript">

            document.querySelector('.img-btn').addEventListener('click', function ()
            {
                document.querySelector('.cont').classList.toggle('s-signup');
            }
            );

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

            //start เข้าสู่ระบบไม่สำเร็จ
            let signinFailed = '<%=request.getAttribute("signinFailed")%>';
            let isSigninFailed = signinFailed === 'failed' ? modal.classList.toggle("show-modal") : null;

            closeButton.addEventListener("click", toggleModal);
            window.addEventListener("click", windowOnClick);

            //start เข้าสู่ระบบไม่สำเร็จ (เพราะ User ยังไม่เคยลงทะเบียน)
            function notRegisterModal() {
                document.querySelector('.modal-close-button').lastElementChild.innerHTML = "เข้าสู่ระบบ";
                document.querySelector('h4.modal-text-display-0').innerHTML = "กรุณาทำการลงทะเบียนก่อน เข้าสู่ระบบ !";
                document.querySelector('h4.modal-text-display-1').innerHTML = "คุณสามารถ คลิกที่ปุ่ม ลงทะเบียนได้เลยค่ะ...";
                document.querySelector('h4.modal-text-display-2').innerHTML = "";
                modal.classList.toggle("show-modal");
            }
            let notRegister = '<%=request.getAttribute("notRegister")%>';
            let isNotRegister = notRegister === 'notRegister' ? notRegisterModal() : null;


            //start ลงทะเบียน (ผู้ใช้เคยลงทะเบียนแล้ว)
            function registerAlreadyModal() {
                document.querySelector('.modal-close-button').lastElementChild.innerHTML = "ลงทะเบียน";
                document.querySelector('h4.modal-text-display-0').innerHTML = "User นี้เคยลงทะเบียนแล้ว !";
                document.querySelector('h4.modal-text-display-1').innerHTML = "คุณสามารถ &nbsp;<span style='color:green;'> Sign In </span>&nbsp; เข้าสู่ระบบได้เลยค่ะ...";
                document.querySelector('h4.modal-text-display-2').innerHTML = "";
                modal.classList.toggle("show-modal");
            }
            let registerAlready = '<%=request.getAttribute("registerAlready")%>';
            let isRegisterAlready = registerAlready === 'registerAlready' ? registerAlreadyModal() : null;

            // หากคุณลืม รหัสผ่านใช้งาน โปรดติดต่อ 023-108-845
            //start ลงทะเบียน (ลงทะเบียนเรียบร้อย) 
            function registerCompleateModal() {
                document.querySelector('.modal-close-button').lastElementChild.innerHTML = "ลงทะเบียน";
                document.querySelector('h4.modal-text-display-0').innerHTML = "ลงทะเบียนเรียบร้อย";
                document.querySelector('h4.modal-text-display-1').innerHTML = "คุณสามารถ &nbsp;<span style='color:green;'> Sign In </span>&nbsp; เข้าสู่ระบบได้เลยค่ะ...";
                document.querySelector('h4.modal-text-display-2').innerHTML = "";
                modal.classList.toggle("show-modal");
            }
            let registerCompleate = '<%=request.getAttribute("registerCompleate")%>';
            let isRegisterCompleate = registerCompleate === 'registerCompleate' ? registerCompleateModal() : null;

            // หากคุณลืม รหัสผ่านใช้งาน Offic 365 โปรดติดต่อ 023-108-845
            //start ลงทะเบียน (ลงทะเบียนไม่สำเร็จ Database Error) 
            function registerdb_err_failedModal() {
                document.querySelector('.modal-close-button').lastElementChild.innerHTML = "ลงทะเบียน";
                document.querySelector('h4.modal-text-display-0').innerHTML = "ลงทะเบียนไม่สำเร็จ";
                document.querySelector('h4.modal-text-display-1').innerHTML = "หากไม่สามารถทำการลงทะเบียนได้ โปรดติดต่อ 2231";
                document.querySelector('h4.modal-text-display-2').innerHTML = "";
                modal.classList.toggle("show-modal");
            }
            let registerdb_err_failed = '<%=request.getAttribute("registerdb_err_failed")%>';
            let isRegisterdb_err_failed = registerdb_err_failed === 'registerdb_err_failed' ? registerdb_err_failedModal() : null;

            // หากคุณลืม รหัสผ่านใช้งาน Offic 365 โปรดติดต่อ 023-108-845
            //start ลงทะเบียน (ลงทะเบียนไม่สำเร็จ Api Error) 
            function registerFailedModal() {
                document.querySelector('.modal-close-button').lastElementChild.innerHTML = "ลงทะเบียน";
                document.querySelector('h4.modal-text-display-0').innerHTML = "ลงทะเบียนไม่สำเร็จ";
                document.querySelector('h4.modal-text-display-1').innerHTML = "ใช้ Email และ Password เดียวกันกับ &nbsp;<strong style='color: red;'>RU Portfolio</strong>&nbsp;!";
                document.querySelector('h4.modal-text-display-2').innerHTML = "หากไม่สามารถทำการลงทะเบียนได้ โปรดติดต่อ 2231";
                modal.classList.toggle("show-modal");
            }
            let registerFailed = '<%=request.getAttribute("registerFailed")%>';
            let isRegisterFailed = registerFailed === 'registerFailed' ? registerFailedModal() : null;

            // ตรวจสอบ password และ confirmpassword
            function validationSignin() {
                let username = document.querySelector('.username').value;
                let password = document.querySelector('.password').value;
                let confirmpassword = document.querySelector('.confirmpassword').value;

                let isValidation = confirmpassword === password ? true : false;
                if (isValidation === false) {
                    document.querySelector('.modal-close-button').lastElementChild.innerHTML = "ลงทะเบียน";
                    document.querySelector('h4.modal-text-display-0').innerHTML = "คุณกรอก Password และ Confirm Password ไม่ตรงกัน !";
                    document.querySelector('h4.modal-text-display-1').innerHTML = "ใช้ Email และ Password เดียวกันกับ &nbsp;<strong style='color: red;'>RU Portfolio</strong>&nbsp;!";
                    document.querySelector('h4.modal-text-display-2').innerHTML = "";
                    modal.classList.toggle("show-modal");

                }

                return isValidation;
            }

        </script>

    </body>
</html>

