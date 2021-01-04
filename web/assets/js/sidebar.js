
const hamburgerBtn = document.querySelector('#hamburgerBtn');
const sidebar = document.querySelector('.sidebar');

hamburgerBtn.addEventListener('click', () => {
    sidebar.classList.toggle('open');
});


jQuery(document).ready(function ($) {

    $('.first-dropdown').click(function () {
        $('div.dropdown-list-first').toggleClass("show");
        $('a.first-dropdown').toggleClass("show-rotate");
    });



    let path = window.location.pathname.split("/").pop();
    console.log(path);

    switch (path) {
        case 'SigninMain':
        case 'MainIndex':
            path = 'MainIndex';
            break;
        case 'ReportCenral':
            path = 'ReportCenral';
            $('a.first-dropdown').toggleClass("active-lists");
            break;
        case 'ReportCentralDetail':
            path = 'ReportCentralDetail';
            $('a.first-dropdown').toggleClass("active-lists");
            break;
        case 'ReportCentralDetailReport':
            path = 'ReportCentralDetail';
            $('a.first-dropdown').toggleClass("active-lists");
            break;
        case 'ReportProvincial':
            path = 'ReportProvincial';
            $('a.first-dropdown').toggleClass("active-lists");
            break;
        case 'ReportProvincialDetail':
            path = 'ReportProvincialDetail';
            $('a.first-dropdown').toggleClass("active-lists");
            break;
        case 'ReportProvincialDetailReport':
            path = 'ReportProvincialDetail';
            $('a.first-dropdown').toggleClass("active-lists");
            break;
    }
    let target = $('div a[href="' + path + '"]');

    target.addClass('active');
    target.css("color", "#ffffff");
});



