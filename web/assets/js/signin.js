
function isCheckUsernameNull() {

    document.querySelector('#input-username').style.boxShadow = 'inset 0px 0px 2px 1px rgba(255, 0, 0, 0.5)';

    return false;
}

function isCheckUsernameNotNull() {

    document.querySelector('#input-username').style.boxShadow = '0px 0px 2px 1px rgba(140, 140, 140, 0.4)';

    return true;

}

function isCheckPasswordNull() {

    document.querySelector('#input-password').style.boxShadow = 'inset 0px 0px 2px 1px rgba(255, 0, 0, 0.5)';

    return false;
}

function isCheckPasswordNotNull() {

    document.querySelector('#input-password').style.boxShadow = '0px 0px 2px 1px rgba(140, 140, 140, 0.4)';

    return true;

}

function isCheckUsernameNullAndPasswordNotNull() {

    document.querySelector('#input-username').style.boxShadow = 'inset 0px 0px 2px 1px rgba(255, 36, 27, 0.5)';

    document.querySelector('#input-password').style.boxShadow = 'inset 0px 0px 2px 1px rgba(0, 153, 61, 0.79)';

}

function isCheckUsernameNotNullAndPasswordNull() {

    document.querySelector('#input-username').style.boxShadow = 'inset 0px 0px 2px 1px rgba(0, 153, 61, 0.79)';

    document.querySelector('#input-password').style.boxShadow = 'inset 0px 0px 2px 1px rgba(255, 36, 27, 0.5)';

}

function clearColor() {

    document.querySelector('#input-username').style.boxShadow = '0px 0px 2px 1px rgba(140, 140, 140, 0.4),inset 0px 0px 2px 1px rgba(255, 255, 255, 1)';

    document.querySelector('#input-password').style.boxShadow = '0px 0px 2px 1px rgba(140, 140, 140, 0.4),inset 0px 0px 5px 1px rgba(255, 255, 255, 1)';

    document.querySelector('#username-password-null-message').innerHTML = "";

    return true;
}

function signinMessege() {

    document.querySelector('#username-password-null-message').innerHTML = "*** กรุณากรอกข้อมูลให้ครบถ้วน ***";
    document.querySelector('#username-password-null-message').style.fontFamily = "'Kanit', sans-serif";
    document.querySelector('#username-password-null-message').style.color = "rgba(255, 0, 0, 0.5)";
    document.querySelector('#username-password-null-message').style.fontSize = "1rem";
    document.querySelector('#username-password-null-message').style.fontWeight = "bold";

}


function validationSignin() {

    let userName = document.querySelector('.input-username').value;
    let passWord = document.querySelector('.input-password').value;

    let isUsername = true;
    let isPassword = true;

    isUsername = userName === "" ? isCheckUsernameNull() : isCheckUsernameNotNull();
    isPassword = passWord === "" ? isCheckPasswordNull() : isCheckPasswordNotNull();


    if (isUsername === true && isPassword === true) {

        document.querySelector('#username-password-null-message').innerHTML = "";

        return true;

    } else if (isUsername === true && isPassword === false) {

        isCheckUsernameNotNullAndPasswordNull();
        signinMessege();

        return false;

    } else if (isUsername === false && isPassword === true) {

        isCheckUsernameNullAndPasswordNotNull();
        signinMessege();

        return false;

    } else {

        signinMessege();

        return false;

    }


}

