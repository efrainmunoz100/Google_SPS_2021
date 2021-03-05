package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Handles requests sent to the /hello URL. Try running a server and navigating to /hello! */
@WebServlet("/hello")
public class HelloWorldServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;");
    response.getWriter().println("<h1>Hello world!</h1>");
    response.getWriter().println("<p> This is Efrain coming at you live from the studio! <p>");
    response.getWriter().println("<a href= \"/Images/sleepy_cat.jpg\"><img \"src=\"/Images/sleepy_cat.jpg\" style=\"width:250px;height:auto;\"/><a>");
    response.getWriter().println("<p> This is my cat Tito, also coming at you live from the studio (but a little sleepy).<p>");
  }
}
