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
import student.models.DatabaseProvincail;
import student.models.ReportProvincialInfo;
import student.models.ReportProvincialModel;

public class ReportProvincialDetailReport extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();

        if (session.getAttribute("username") != null) {

            String tempEnroll = request.getParameter("enroll");

            String[] enroll = tempEnroll.split("\\/");
            String year = enroll[0];
            String semester = enroll[1];

            DatabaseProvincail db = new DatabaseProvincail();

            ReportProvincialModel getReportProvincial = new ReportProvincialModel(db);

            List<ReportProvincialInfo> reportExcel = getReportProvincial.findAll(year, semester);

            List<ReportProvincialInfo> provincial = getReportProvincial.findAllFaculty(year, semester);

            ArrayList<ReportProvincialInfo> provincialExcel = new ArrayList<ReportProvincialInfo>();

            String footerDate = getDate();

            request.setAttribute("reportExcel", reportExcel);
            request.setAttribute("provincialExcel", provincialExcel);
            request.setAttribute("footerDate", footerDate);

            for (int indexOutside = 0; indexOutside < provincial.size(); indexOutside++) {
                int sum1 = 0, sum2 = 0, sum3 = 0, sum4 = 0;

                ReportProvincialInfo tempReport = new ReportProvincialInfo();

                for (int indexInside = 0; indexInside < reportExcel.size(); indexInside++) {
                    if (provincial.get(indexOutside).REGINAL_NO.equals(reportExcel.get(indexInside).REGINAL_NO)) {
                        sum1 += reportExcel.get(indexInside).CNT_ALL.intValue();
                        sum2 += reportExcel.get(indexInside).CNT_RECEIPT.intValue();
                        sum3 += reportExcel.get(indexInside).CNT_STD_CODE.intValue();
                        reportExcel.get(indexInside).setTOTAL_COLUMN(String.valueOf(reportExcel.get(indexInside).CNT_RECEIPT.intValue() + 0));
                    }
                }

                tempReport.setENROLL_YEAR(provincial.get(indexOutside).ENROLL_YEAR);
                tempReport.setENROLL_SEMESTER(provincial.get(indexOutside).ENROLL_SEMESTER);
                tempReport.setREGINAL_NO(provincial.get(indexOutside).REGINAL_NO);
                tempReport.setREGINAL_NAME_THAI(provincial.get(indexOutside).REGINAL_NAME_THAI);
                tempReport.setFACULTY_NO(provincial.get(indexOutside).FACULTY_NO);
                tempReport.setFACULTY_NAME_THAI(provincial.get(indexOutside).FACULTY_NAME_THAI);
                tempReport.setMAJOR_NO(provincial.get(indexOutside).MAJOR_NO);
                tempReport.setMAJOR_NAME_THAI(provincial.get(indexOutside).MAJOR_NAME_THAI);
                tempReport.setCNT_ALL(provincial.get(indexOutside).CNT_ALL);
                tempReport.setCNT_RECEIPT(provincial.get(indexOutside).CNT_RECEIPT);
                tempReport.setCNT_STD_CODE(provincial.get(indexOutside).CNT_STD_CODE);
                tempReport.setCNT_CENT_ALL(provincial.get(indexOutside).CNT_CENT_ALL);
                tempReport.setSUM1(String.valueOf(sum1));
                tempReport.setSUM2(String.valueOf(sum2));
                tempReport.setSUM3(String.valueOf(sum3));
                tempReport.setSUM4(String.valueOf(sum4));
                tempReport.setTOTAL(String.valueOf(sum2 + sum4));

                provincialExcel.add(tempReport);
            }
            
            request.setAttribute("year", year);
            request.setAttribute("semester", semester);
            RequestDispatcher rs = request.getRequestDispatcher("main-content/report-provincial-detail-report.jsp");
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
            Logger.getLogger(ReportProvincialDetailReport.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ReportProvincialDetailReport.class.getName()).log(Level.SEVERE, null, ex);
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
