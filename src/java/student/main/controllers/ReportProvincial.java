package student.main.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.format.DateTimeFormatter;
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

public class ReportProvincial extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();

        if (session.getAttribute("username") != null) {

            DatabaseProvincail db = new DatabaseProvincail();

            ReportProvincialModel getReportProvincial = new ReportProvincialModel(db);

            List<ReportProvincialInfo> enroll = getReportProvincial.findEnrollYearAndSemester();
            request.setAttribute("enroll", enroll);

            RequestDispatcher rs = request.getRequestDispatcher("main-content/report-provincial.jsp");
            rs.forward(request, response);

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
            Logger.getLogger(ReportProvincial.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ReportProvincial.class.getName()).log(Level.SEVERE, null, ex);
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
