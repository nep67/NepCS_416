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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nep
 */
@WebServlet(urlPatterns = {"/DBAccessServlet"})

public class DBAccessServlet extends HttpServlet {
    
    @Resource(name = "jdbc/HW2DB")
    private javax.sql.DataSource datasource;

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
            throws ServletException,IOException {
        
        
         
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try{
           
            out.println("<!DOCTYPE html>");
           
            
            out.println("<html>");
            out.println("<head>");
            out.println("<title>DB ACCESS</title>");
            out.println("</head>");
            out.println("<body>");
            
            String sql = "select * from votes";
            String musicTypeInsert = "INSERT INTO votes( musictype, numvotes)"
                                   + " VALUES (?, 1)";
                                   
            String updateDB = "UPDATE votes"
                            + " SET numvotes=?"
                            + " WHERE musictype=?";
            
             ArrayList<String> genreList = new ArrayList();
             ArrayList<Integer> voteList = new ArrayList();
            
             String[] genres  = request.getParameterValues("genre");
             String genreName = request.getParameter("new_music_type");
             String voteNum   = request.getParameter("checkboxsubmit");
             String newtype   = request.getParameter("new_music_type");
             
             String prev_servlet = (String)request.getAttribute("javax.servlet.forward.request_uri");
               if (prev_servlet != null) {
                // Remove the context.
                prev_servlet = prev_servlet.substring(request.getContextPath().length());
            }
            
            Connection connection = datasource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                genreList.add(resultSet.getString("musictype"));
                voteList.add(resultSet.getInt("numvotes"));
            }
            
           request.setAttribute("names_col", genreList);
           request.setAttribute("votes_col", voteList);
           
           // Session reference for gathering users' votes per session.
            HttpSession session = request.getSession();
            // Context reference for gathering all votes.
            ServletContext context = request.getServletContext();
           
            if (prev_servlet != null && prev_servlet.equals("/StartPageServlet")){
                    // If no parameters were sent.
                    if (genres == null && newtype == null) {
                        // Forward the request back to the StartPageServlet.
                        // This handles the very first page request to StartPageServlet.
                        connection.close();
                        request.getRequestDispatcher("StartPageServlet").forward(request, response);
                        return;
                    }
            }
           
            
            
            
            //****************************************************************************
            // INSERT INTO TABLE FROM TEXTBOX
               
            if (genreName != null ) {
                
                
                PreparedStatement addStatement = connection.prepareStatement(musicTypeInsert);
                addStatement.setString(1, genreName);
                
                addStatement.close();
            
                connection.close();
             
             
           
           //**************************************************************************
           //SESSION AND CONTEXT
           
            
            // Session count
                Integer sessionCount = (Integer)session.getAttribute("sessionCount");
                if (sessionCount == null){
                    sessionCount = new Integer(1);
                    session.setAttribute("sessionCount", sessionCount);
                }else{
                    session.setAttribute("sessionCount", new Integer(sessionCount.intValue()+1));
                }
            
                // Context count
            
            
                Integer contextCount = (Integer)context.getAttribute("contextCount");
                if (contextCount == null){
                    contextCount = new Integer(1);
                    context.setAttribute("contextCount", contextCount);
                }else{
                    context.setAttribute("contextCount", contextCount.intValue()+1);
                }
             
            
           }
            
            if (prev_servlet.equals("/index"))
            {
               request.getRequestDispatcher("Display.jsp").forward(request, response);
            }
           //********************************************************************************** 
           //UPDATE CHECKBOX VOTES
           
           if(voteNum != null)
           {
                    
                   
                    //request.getRequestDispatcher("Display.jsp").forward(request, response);
          
            
           }
            
            
            out.println("</body>");
            out.println("</html>");
            
            for(int i = 0; i < genreList.size(); i++)
            {
                out.println(genreList.get(i));
            }
       
        } catch (Exception e) {
            out.println("error occurred " + e.getMessage());
        } finally {
            out.close();
           
        
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

  