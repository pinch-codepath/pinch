package com.pinch.android.remote;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.pinch.backend.eventEndpoint.model.Event;

import java.io.IOException;

public class InsertEventTask extends AsyncTask<Void, Void, Void> {

    InsertEventResultsListener listener;
    Event event;
    Bitmap imageBitmap;
    long organizationId;

    public InsertEventTask(InsertEventResultsListener listener, long organizationId, Event event, Bitmap imageBitmap) {
        this.listener = listener;
        this.event = event;
        this.organizationId = organizationId;
        this.imageBitmap = imageBitmap;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Event returnedEvent = Endpoints.getInstance().eventEndpoint.insert(organizationId, event).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void done) {
        listener.onEventInsert();
    }

    private String uploadImage() throws IOException {

        return null;

//        ImageEndpoint endpoint = Endpoints.getInstance().imageEndpoint;
//        String url = endpoint.generateImageUploadUrl().execute().getUrl();
//        System.out.println(url);
//          org.apache.http.client.HttpClient httpClient = HttpClientBuilder.create().build();
//          File file = new File("/Users/sgarg/Downloads/glide.jpg");
//          HttpEntity entity = MultipartEntityBuilder
//                  .create()
//                  .addBinaryBody("file", file, ContentType.create("application/octet-stream"), file.getName())
//                  .build();
//        HttpPost httpPost = new HttpPost(url);
//        httpPost.setEntity(entity);
//        HttpResponse response = httpClient.execute(httpPost);
//        HttpEntity urlEntity = response.getEntity();
//        InputStream in = urlEntity.getContent();
//        String str = "";
//                while (true) {
//            int ch = in.read();
//            if (ch == -1)
//                break;
//            str += (char) ch;
//        }
//        System.out.println(str);
//        return null;
    }

    public interface InsertEventResultsListener {
        void onEventInsert();
    }
}

