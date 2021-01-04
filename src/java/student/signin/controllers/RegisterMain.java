package student.signin.controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import student.models.Database;
import student.models.SigninModel;
import student.models.AuthenLogin;

public class RegisterMain extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmpassword = request.getParameter("confirmpassword");

        AuthenLogin getAuthen = new AuthenLogin();
        String strAuthen = getAuthen.autLogin(username, password);

//        System.out.println("strAuthen => " + strAuthen);

        if (!strAuthen.equals("0")) { //success
            
            JSONObject jsonObj = new JSONObject(strAuthen);

            String mail = jsonObj.getString("mail");

            Database db = new Database();
            SigninModel getDataSignin = new SigninModel(db);
            boolean isSignin = getDataSignin.SigninChecking(mail);

            if (isSignin) {

                request.setAttribute("registerAlready", "registerAlready");
                RequestDispatcher rs = request.getRequestDispatcher("signin-register.jsp");
                rs.forward(request, response);

                System.out.println("มีข้อมูลผู้ใช้งานแล้ว ลงทะเบียนซ้ำ ไม่ได้");

            } else {
                boolean isRegister = getDataSignin.RegisterUser(username);
                if (isRegister) {

                    HttpSession sessionUsername = request.getSession(true);
                    sessionUsername.setAttribute("username", username);
                    request.setAttribute("registerCompleate", "registerCompleate");

                    RequestDispatcher rs = request.getRequestDispatcher("signin-register.jsp");
                    rs.forward(request, response);

                    System.out.println("ลงทะเบียนเรียบร้อย");

                } else {
                    request.setAttribute("registerdb_err_failed", "registerdb_err_failed");
                    RequestDispatcher rs = request.getRequestDispatcher("signin-register.jsp");
                    rs.forward(request, response);
                    System.out.println("Database Error ลงทะเบียนไม่สำเร็จ");
                }
            }
            db.close();

        } else {

            request.setAttribute("registerFailed", "registerFailed");
            RequestDispatcher rs = request.getRequestDispatcher("signin-register.jsp");
            rs.forward(request, response);
            System.out.println("ไม่พบอีเมล์หรือรหัสผ่านที่ตรงกัน");

        }
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
        } catch (JSONException ex) {
            Logger.getLogger(RegisterMain.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (JSONException ex) {
            Logger.getLogger(RegisterMain.class.getName()).log(Level.SEVERE, null, ex);
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
