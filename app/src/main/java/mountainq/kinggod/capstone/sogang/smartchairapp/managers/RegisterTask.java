package mountainq.kinggod.capstone.sogang.smartchairapp.managers;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by dnay2 on 2017-03-25.
 */

public class RegisterTask extends AsyncTask<Integer, Integer, Integer> {
    private static final String TAG = "RegisterTask";
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();
    @Override
    protected Integer doInBackground(Integer... params) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        Log.d("test", "task running");
    }

    protected String postMethod (String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .header("content-type", "application/json")
                .post(body)
                .build();
        Log.d(TAG, "post request : " + request.body().toString());
        okhttp3.Response response = client.newCall(request).execute();
        return response.body().string();
    }

    protected String getMethod (String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
//        Log.d(TAG, "get request : " + request.body().toString());
        okhttp3.Response response = client.newCall(request).execute();
        return response.body().string();
    }

    protected String putMethod (String url, String header, String jsonBody) throws IOException{
        RequestBody body = RequestBody.create(JSON, jsonBody);
        Log.d(TAG, "put request ");
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        okhttp3.Response response = client.newCall(request).execute();
        return response.body().string();
    }


}
