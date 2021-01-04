package student.main.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import student.models.Database;
import student.models.ReportCantralModel;
import student.models.ReportCentralInfo;

public class ReportCentralDetailReport extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();

        if (session.getAttribute("username") != null) {

            String tempEnroll = request.getParameter("enroll");

            String[] enroll = tempEnroll.split("\\/");
            String year = enroll[0];
            String semester = enroll[1];

            Database db = new Database();

            ReportCantralModel getReportCentral = new ReportCantralModel(db);

            List<ReportCentralInfo> reportExcel = getReportCentral.findAll(year, semester);

            List<ReportCentralInfo> faculty = getReportCentral.findAllFaculty(year, semester);

            ArrayList<ReportCentralInfo> facultyExcel = new ArrayList<ReportCentralInfo>();

            String footerDate = getDate();

            request.setAttribute("reportExcel", reportExcel);
            request.setAttribute("faculty", facultyExcel);
            request.setAttribute("footerDate", footerDate);

            for (int indexOutside = 0; indexOutside < faculty.size(); indexOutside++) {
                int sum1 = 0, sum2 = 0, sum3 = 0, sum4 = 0, sum5 = 0;

                ReportCentralInfo tempReport = new ReportCentralInfo();

                for (int indexInside = 0; indexInside < reportExcel.size(); indexInside++) {
                    if (faculty.get(indexOutside).FACULTY_NO.equals(reportExcel.get(indexInside).FACULTY_NO)) {
                        sum1 += reportExcel.get(indexInside).CNT_NET_ALL.intValue();
                        sum2 += reportExcel.get(indexInside).CNT_NET_RECEIPT.intValue();
                        sum3 += reportExcel.get(indexInside).CNT_NET_STD.intValue();
                        sum4 += reportExcel.get(indexInside).CNT_CENT_ALL.intValue();
                        sum5 += reportExcel.get(indexInside).CNT_POST_ALL.intValue();
                        reportExcel.get(indexInside).setTOTAL_COLUMN(String.valueOf(reportExcel.get(indexInside).CNT_NET_RECEIPT.intValue() + reportExcel.get(indexInside).CNT_CENT_ALL.intValue() + reportExcel.get(indexInside).CNT_POST_ALL.intValue()));
                    }
                }
                tempReport.setENROLL_YEAR(faculty.get(indexOutside).ENROLL_YEAR);
                tempReport.setENROLL_SEMESTER(faculty.get(indexOutside).ENROLL_SEMESTER);
                tempReport.setFACULTY_NO(faculty.get(indexOutside).FACULTY_NO);
                tempReport.setFACULTY_NAME_THAI(faculty.get(indexOutside).FACULTY_NAME_THAI);
                tempReport.setMAJOR_NO(faculty.get(indexOutside).MAJOR_NO);
                tempReport.setMAJOR_NAME_THAI(faculty.get(indexOutside).MAJOR_NAME_THAI);
                tempReport.setCNT_NET_ALL(faculty.get(indexOutside).CNT_NET_ALL);
                tempReport.setCNT_NET_RECEIPT(faculty.get(indexOutside).CNT_NET_RECEIPT);
                tempReport.setCNT_NET_STD(faculty.get(indexOutside).CNT_NET_STD);
                tempReport.setCNT_POST_ALL(faculty.get(indexOutside).CNT_POST_ALL);
                tempReport.setCNT_CENT_ALL(faculty.get(indexOutside).CNT_CENT_ALL);
                tempReport.setSUM1(String.valueOf(sum1));
                tempReport.setSUM2(String.valueOf(sum2));
                tempReport.setSUM3(String.valueOf(sum3));
                tempReport.setSUM4(String.valueOf(sum4));
                tempReport.setSUM5(String.valueOf(sum5));
                int tempTotalSum = sum2 + sum4 + sum5;
                tempReport.setTOTAL(String.valueOf(tempTotalSum));

                facultyExcel.add(tempReport);
            }

            request.setAttribute("year", year);
            request.setAttribute("semester", semester);
            RequestDispatcher rs = request.getRequestDispatcher("main-content/report-central-detail-report.jsp");
            rs.forward(request, response);

            db.close();

        } else {

            RequestDispatcher rs = request.getRequestDispatcher("SigninMain");
            rs.forward(request, response);

        }

    }

    public String getDate() throws ParseException {

        LocalDate date = LocalDate.parse(LocalDate.now().toString());
        DateTimeFormatter toThai = DateTimeFormatter.ofPattern("dd/MM/yyyy").withChronology(ThaiBuddhistChronology.INSTANCE);

        return toThai.format(date);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ReportCentralDetailReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ReportCentralDetailReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
