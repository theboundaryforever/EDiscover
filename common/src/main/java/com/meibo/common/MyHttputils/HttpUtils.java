package com.meibo.common.MyHttputils;


import android.os.Handler;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/23 0023.
 * 网络请求框架封装类
 */

public class HttpUtils {

    private static volatile HttpUtils instance;
    private static final String  TAG = "HttpUtils";
    private static final String SIGNATURE = "os=androidApp&signcert=875878748as3dNLJK32fbn2vmnvmnhhj8";
    public Handler handler = new Handler();

    private HttpUtils(){}

    /**双重检索，单例模式*/
    public static HttpUtils getInstance(){
        if(instance == null){
            synchronized(HttpUtils.class){
                if(instance == null){
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }


    /**网络请求get方法*/
    public void get(final String url,Map<String,String> map,final ICallback callback){

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        map.put("os","androidAppaa");
        map.put("requestDate",df.format(new Date()));
        map.put("signature",Utils.md5(SIGNATURE));

        //对url和参数做拼接
        StringBuffer sbu = new StringBuffer();
        sbu.append(url);
        if(url.contains("?")){
            //如果？不在最后一位
            if(sbu.indexOf("?") != sbu.length()-1){
                sbu.append("&");
            }
        }else{
            sbu.append("?");
        }
        for(Map.Entry<String,String> entry : map.entrySet()){
            sbu.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        if(sbu.indexOf("&") != -1){
            sbu.deleteCharAt(sbu.lastIndexOf("&"));
        }
        //拼接完成
        Log.i(TAG,"get url"+sbu);
        //new 一个okhttpClient实例
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
                .build();
        final Request request = new Request.Builder()
                .get()
                .url(sbu.toString())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求异常出错
                Log.e(TAG,"onFailure"+e.getCause().getStackTrace()+","+e.getMessage());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功
                final String result = response.body().toString();
                //final Object object = GsonUtils.getInstance().fromJson(result,clss);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                    callback.onSuccess(url,result);
                    }
                });
            }
        });
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**pos请求json字符串*/
    public void post(final String url, Map<String,Object> map, final ICallback callback) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        map.put("requestDate",df.format(new Date()));


        //排序方法
        List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        StringBuilder s1 = new StringBuilder();
        //排序后
        for(Map.Entry<String, Object> m : infoIds){
            s1.append(m.getKey()).append("=").append(map.get(m.getKey())).append("&");
        }
        s1.append(SIGNATURE);
        map.put("signature",Utils.getMD5(s1.toString()));
        map.put("os","androidApp");

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
                .build();
        //url类型
        /*FormBody.Builder builder = new FormBody.Builder();
        for(Map.Entry<String,Object> entry:map.entrySet()) {
            builder.add(entry.getKey(),entry.getValue());
        }
        FormBody formBody = builder.build();*/

        //json类型
        RequestBody body = RequestBody.create(JSON,getjsonData(map).toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Log.d("网络请求post","js="+getjsonData(map).toString());
        Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //请求失败
                Log.e(TAG,"onFailure:"+e.getCause().getStackTrace() + e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功
                final String result = response.body().string();
                //final Object o = GsonUtils.getInstance().fromJson(result, cls);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(url,result);
                    }
                });
            }
        });
    }



    /**自定义网络请求返回值接口*/
    public interface ICallback{
        void onSuccess(String url, String result);
    }

    /**
     * 应用拦截器
     */
    public class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request original = chain.request();
            HttpUrl url = original.url().newBuilder()
                    .addQueryParameter("source", "android")
                    .build();
            //添加请求头
            Request request = original.newBuilder()
                    .url(url)
                    .build();
            return chain.proceed(request);
        }
    }

    public JSONObject getjsonData(Map<String, Object> params) {
        JSONObject js=new JSONObject();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            try {
                js.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return js;
    }


}
