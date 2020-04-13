package com.ivanapp.ivanapp.httpClient;

import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


public class HttpClient extends AbstractClient {

    private ConcurrentHashMap<String, List<Cookie>> cookieStore = new ConcurrentHashMap<>();

    public HttpClient() {
    }

    public HttpClient(String charsetName) {
        super(charsetName);
    }

    @Override
    public Response get(String url, Map<String, String> headers) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .cookieJar(this.newCookieJar(cookieStore))
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .url(url);
        System.out.println("检测User-Agent："+headers+"---url："+url);
        if (headers != null) {
            headers.forEach(requestBuilder::addHeader);
            System.out.println("设置的请求头User-Agent："+headers);
        }
        Request request = requestBuilder.build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response;
        } else {
            return null;
        }
    }

    @Override
    public Response post(String url, Map<String, String> headers, Map<String, String> forms) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .cookieJar(this.newCookieJar(cookieStore))
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .url(url);
        if (headers != null) {
            headers.forEach(requestBuilder::addHeader);
        }
        if (forms != null) {
//            FormBody.Builder formBody = new FormBody.Builder();
//            forms.forEach(formBody::add);
//            requestBuilder.post(formBody.build());
            StringBuilder stringBuffer = new StringBuilder();
            for (String key : forms.keySet()) {
                stringBuffer.append(key).append("=").append(forms.get(key)).append("&");
            }
            RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8")
                    , stringBuffer.toString());
            requestBuilder.post(body);
        }
        Request request = requestBuilder.build();
        System.out.println("请求的信息："+request.headers().get("User-Agent"));
//        String s=request.headers().get("User-Agent");
//        System.out.println("request的User-Agent："+s);
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            System.out.println("响应的信息："+response);
            return response;
        } else {
            return null;
        }
    }

    @Override
    public ConcurrentHashMap<String, List<Cookie>> getCookieStore() {
        return cookieStore;
    }
}
