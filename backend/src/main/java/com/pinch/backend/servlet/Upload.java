package com.pinch.backend.servlet;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.repackaged.com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Upload extends HttpServlet {
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    private Logger logger = Logger.getLogger(Upload.class.getName());

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            List<BlobKey> blobs = blobstoreService.getUploads(req).get("file");
            BlobKey blobKey = blobs.get(0);

            ImagesService imagesService = ImagesServiceFactory
                    .getImagesService();
            ServingUrlOptions servingOptions = ServingUrlOptions.Builder
                    .withBlobKey(blobKey);

            String servingUrl = imagesService.getServingUrl(servingOptions);

            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json");

            Gson gson = new Gson();
            Map<String, String> map = new HashMap<>();
            map.put("servingUrl", servingUrl);
            map.put("blobKey", blobKey.getKeyString());
            PrintWriter out = res.getWriter();
            out.print(gson.toJson(map));
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to upload: " + e.getMessage());
        }
    }
}
