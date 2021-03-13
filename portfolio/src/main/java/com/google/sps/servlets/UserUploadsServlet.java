package com.google.sps.servlets;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user-uploads")
public class UserUploadsServlet extends HttpServlet{
    private static final String PROJECT_ID = "emunoz-sps-spring21";
    private static final String BUCKET_NAME = "emunoz-sps-spring21.appspot.com";


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        // List all of the uploaded files.
        Storage storage = StorageOptions.newBuilder().setProjectId(PROJECT_ID).build().getService();
        Bucket bucket = storage.get(BUCKET_NAME);
        Page<Blob> blobs = bucket.list();

        // Add option to return to main page
        response.getWriter().println("<h1>User Submissions</h1>");
        response.getWriter().println("<h2>Click <a href=\"/\">here<a/> to return to the main page</h2>");

        // Output <img> elements as HTML.
        response.setContentType("text/html;");
        for (Blob blob : blobs.iterateAll()) {
            String imgTag = String.format("<img src=\"%s\" />", blob.getMediaLink());
            response.getWriter().println(imgTag);
            response.getWriter().println("<br>");
        }
    }

}