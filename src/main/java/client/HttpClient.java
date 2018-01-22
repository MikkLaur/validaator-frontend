package client;

import com.squareup.okhttp.*;
import org.json.JSONObject;;

import java.io.IOException;

public class HttpClient {
    private static OkHttpClient httpClient = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String URL = "http://0.0.0.0:4567";
    private HttpClient() {

    }

    static long registerUser(String name, String personalId, String dateOfBirth) {
        String message = new JSONObject()
                .put("name", name)
                .put("personal_id", personalId)
                .put("date_of_birth", dateOfBirth)
                .toString();
        RequestBody body = RequestBody.create(JSON, message);

        Request request = new Request.Builder()
                .url(URL.concat("/api/users"))
                .post(body)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            long result = Long.parseLong(response.body().string());
            response.body().close();
            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    static long registerStop(String stopName) {
        String message = new JSONObject()
                .put("name", stopName)
                .toString();
        RequestBody body = RequestBody.create(JSON, message);

        Request request = new Request.Builder()
                .url(URL.concat("/api/stops"))
                .post(body)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            long result = Long.parseLong(response.body().string());
            response.body().close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    static String getAllUsers() {
        Request request = new Request.Builder()
                .url(URL.concat("/api/users"))
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            String result = response.body().string();
            response.body().close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getAllStops() {
        Request request = new Request.Builder()
                .url(URL.concat("/api/stops"))
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            String result = response.body().string();
            response.body().close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void sendValidation(String userId, String stopId) {
        String message = new JSONObject()
                .put("user_id", userId)
                .put("stop_id", stopId)
                .toString();
        RequestBody body = RequestBody.create(JSON, message);

        Request request = new Request.Builder()
                .url(URL.concat("/api/tickets"))
                .post(body)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            response.body().close();
            //return Long.parseLong(response.body().string());

        } catch (IOException e) {
            e.printStackTrace();
        }
        //return -1;
    }
}
