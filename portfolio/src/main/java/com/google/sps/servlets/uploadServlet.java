package com.google.sps.servlets;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/** 
 * Takes an image uploaded by a user, and uploads it to Google Cloud Storage, then shows the user their uploaded
 * image.
 */
@WebServlet("/upload")
@MultipartConfig
public class uploadServlet extends HttpServlet{

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Part filePart = request.getPart("image");
        String fileName = filePart.getSubmittedFileName();
        InputStream fileInput = filePart.getInputStream();

        String fileURL = uploadToCloudStorage(fileName, fileInput);

        /** Respond with the uploaded image, and a link to see all the images uploaded by other users */
        PrintWriter out = response.getWriter();
        out.println("<h1>Image Upload</h1>");
        out.println("<p>Here is the image you uploaded: </p>");
        out.println("<a href=\"" + fileURL + "\">");
        out.println("<img src=\"" + fileURL + "\" />");
        out.println("</a>");
        out.println("<br/>");
        out.println("<p>Click <a href=\"/userUploads\">here</a> to see all images uploaded by users. </p>");
    }

      /** Uploads a file to Cloud Storage and returns the uploaded file's URL. */
  private static String uploadToCloudStorage(String fileName, InputStream fileInputStream) {
    String projectId = "emunoz-sps-spring21";
    String bucketName = "emunoz-sps-spring21.appspot.com";
    Storage storage =
        StorageOptions.newBuilder().setProjectId(projectId).build().getService();
    BlobId blobId = BlobId.of(bucketName, fileName);
    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

    // Upload the file to Cloud Storage.
    Blob blob = storage.create(blobInfo, fileInputStream);

    // Return the uploaded file's URL.
    return blob.getMediaLink();
  }
}