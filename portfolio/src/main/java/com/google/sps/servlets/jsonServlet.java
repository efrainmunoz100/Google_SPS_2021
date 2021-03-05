package com.google.sps.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Handles requests sent to the /josnServlet URL, and returns a json object with a list of my favorite quotes. */
@WebServlet("/jsonServlet")
public class jsonServlet extends HttpServlet {

List<String> quoteList =  Arrays.asList(
    "Reality can be whatever I want it to be.",
    "Do you know... The Muffin Man? \n The Muffin Man? \n THE MUFFIN MAN!",
    "I can't believe I put on my good crocs for this.",
    "Beds are lowkey slept on. ",
    "If you can't handle me at my worst, imagine how I feel",
    "I may be stupid \n \n",
    "Hold on I'm trying to remember the second number of the alphabet",
    "I'm not like the other girls \n I'm worse", 
    "Be a hero: Eat the straw so the turtles don't have to :)");

Gson gson = new Gson();
String json = gson.toJson(quoteList);

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json;");
    
    response.getWriter().println(json);
  }
}
