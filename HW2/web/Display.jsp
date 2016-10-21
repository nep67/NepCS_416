<%-- 
    Document   : Display
    Created on : Oct 20, 2016, 5:25:43 PM
    Author     : Nep
--%>

<%@ page errorPage="ErrPage.jsp"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Display Page</title>
    </head>
    <body>
        <% 
                // List objects to hold query data.
                ArrayList<String> genreNames = (ArrayList<String>)request.getAttribute("names_col");
                ArrayList<Integer> genreVotes = (ArrayList<Integer>)request.getAttribute("votes_col");
                
                //session variable.
                Integer myVotes = (Integer)session.getAttribute("sessionCount");               
                      
                for (int i=0; i<genreNames.size(); i++) {
                    String line = genreNames.get(i);
                    line += " has " + genreVotes.get(i) + " votes.<br/>";
                    out.println(line);
                }
                //Session for total votes
                if (myVotes != null) {
                    out.println("My total votes: " + myVotes);
                }
                else {
                    out.println("My total votes: 0");
                }
            %>
    </body>
</html>
