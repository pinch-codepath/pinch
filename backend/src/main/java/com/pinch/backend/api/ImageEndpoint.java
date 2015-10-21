package com.pinch.backend.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import com.pinch.backend.model.UploadUrl;

@Api(
        name = "imageEndpoint",
        version = "v1",
        resource = "image",
        namespace = @ApiNamespace(
                ownerDomain = "backend.pinch.com",
                ownerName = "backend.pinch.com",
                packagePath = ""
        )
)
public class ImageEndpoint {

    @ApiMethod(name = "generateImageUploadUrl")
    public UploadUrl generateImageUploadUrl() {
        BlobstoreService blobstoreService =
                BlobstoreServiceFactory.getBlobstoreService();
        return new UploadUrl(blobstoreService.createUploadUrl("/upload"));
    }
}
