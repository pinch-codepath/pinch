package com.pinch.console;

import com.pinch.backend.imageEndpoint.ImageEndpoint;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class UploadImage {
    public static void main(String[] args) throws IOException {
        ImageEndpoint endpoint = Endpoints.getInstance().imageEndpoint;
        String url = endpoint.generateImageUploadUrl().execute().getUrl();
        System.out.println(url);
        HttpClient httpClient = HttpClientBuilder.create().build();
        File file = new File("/Users/sgarg/Downloads/glide.jpg");
        HttpEntity entity = MultipartEntityBuilder
                .create()
                .addBinaryBody("file", file, ContentType.create("application/octet-stream"), file.getName())
                .build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity urlEntity = response.getEntity();
        InputStream in = urlEntity.getContent();
        String str = "";
        while (true) {
            int ch = in.read();
            if (ch == -1)
                break;
            str += (char) ch;
        }
        System.out.println(str);
    }
}
