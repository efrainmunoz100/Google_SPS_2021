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
import java.util.ArrayList;
import java.util.List;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;


/** 
 * Takes an image uploaded by a user, uploads it to Google Cloud Storage, analyzes the image using Google CLoud 
 * Vision, and shows the user their image.
 */
@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet{

    private static final String PROJECT_ID = "emunoz-sps-spring21";
    private static final String BUCKET_NAME = "emunoz-sps-spring21.appspot.com";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Part filePart = request.getPart("image");
        String fileName = filePart.getSubmittedFileName();
        InputStream fileInput = filePart.getInputStream();
        byte[] imageBytes = fileInput.readAllBytes();

        String visionURL = uploadToCloudStorage(fileName, imageBytes);

        List<EntityAnnotation> imageLabels = getImageLabels(imageBytes);

        /** Respond with the uploaded image, an image recognition readout, and a link to see all the 
         * images uploaded by other users */
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>Image Upload</h1>");
        out.println("<p>Here is the image you uploaded: </p>");
        out.println("<a href=\"" + visionURL + "\">");
        out.println("<img src=\"" + visionURL + "\" />");
        out.println("</a>");
        out.println("<br/>");
        out.println("For fun, the image was run through image recognition, and this is what the computer thinks is in the picture: ");
        for (EntityAnnotation label : imageLabels){
            out.println("<li>" + label.getDescription() + " " + label.getScore());
        }
        out.println("<br/>");
        out.println("<h2>Click <a href=\"/user-uploads\">here</a> to see all images uploaded by users. </h2>");
    }

  /** Uploads a file to Cloud Storage and returns the uploaded file's URL. */
  private static String uploadToCloudStorage(String fileName, byte[] fileInput) {
    Storage storage =
        StorageOptions.newBuilder().setProjectId(PROJECT_ID).build().getService();
    BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

    Blob blob = storage.create(blobInfo, fileInput);
    return blob.getMediaLink();
  }

  /**
   * Uses the Google Cloud Vision API to generate a list of labels that apply to the image
   * represented by the binary data stored in imgBytes.
   */
  private List<EntityAnnotation> getImageLabels(byte[] imageBytes) throws IOException {
        ByteString byteString = ByteString.copyFrom(imageBytes);
        Image image = Image.newBuilder().setContent(byteString).build();

        Feature feature = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
        AnnotateImageRequest request =
            AnnotateImageRequest.newBuilder().addFeatures(feature).setImage(image).build();
        List<AnnotateImageRequest> requests = new ArrayList<>();
        requests.add(request);

        ImageAnnotatorClient client = ImageAnnotatorClient.create();
        BatchAnnotateImagesResponse batchResponse = client.batchAnnotateImages(requests);
        client.close();
        List<AnnotateImageResponse> imageResponses = batchResponse.getResponsesList();
        AnnotateImageResponse imageResponse = imageResponses.get(0);

        if (imageResponse.hasError()) {
            System.err.println("Error getting image labels: " + imageResponse.getError().getMessage());
            return null;
        }

        return imageResponse.getLabelAnnotationsList();
  }
}