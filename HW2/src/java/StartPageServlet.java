/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.*;



/**
 *
 * @author Nep
 */

@WebServlet(name = "StartPageServlet", urlPatterns = {"/StartPageServlet"})
public class StartPageServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String prev_servlet = (String)request.getAttribute("javax.servlet.forward.request_uri");
        if (prev_servlet == null){
            request.getRequestDispatcher("DBAccessServlet").forward(request, response);
            return;
        }
        else {
            // Determine if an error message has been set by the last page.
            String error_message = (String)request.getAttribute("error_message");
            
            // Once the DBAccessServlet returns the table data, continue to format
            // and print the HTML voting page.
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
            
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Music Genre Poll</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("Vote what your favorite type of music is:<br/>");
                out.println("<form name=\"vote_input\" action=\"DBAccessServlet\" method=\"GET\">");

                ArrayList qresults = (ArrayList)request.getAttribute("names_col");
                if (qresults != null) {
                    String line_start = "<input type=\"checkbox\" name=\"genre\" value=\"";
                    for (int i=0; i<qresults.size(); i++) {
                        out.println(line_start + qresults.get(i) + "\">" + qresults.get(i) + "<br/>");
                    }            
                }
            
                out.println("<input type=\"hidden\" name=\"checkboxsubmit\">");
                out.println("<input type=\"submit\" value=\"Submit vote\">");
                out.println("</form>");
                out.println("<br/><br/>");                
                out.println("Or add a new one<br/><br/>");
                out.println("<form name=\"new_type_input\" action=\"DBAccessServlet\" method=\"GET\">");
                out.println("New music type: <input type=\"text\" name=\"new_music_type\"><br/>");
                out.println("<input type=\"submit\" value=\"Add type and vote\"/>");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");
            }
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
        processRequest(request, response);
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
        processRequest(request, response);
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