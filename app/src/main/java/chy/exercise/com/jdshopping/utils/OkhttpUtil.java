package chy.exercise.com.jdshopping.utils;

import android.os.Environment;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 卿为谁人醉 on 2018/4/10.
 */

public class OkhttpUtil {
    private OkhttpUtil okHttpUtils;
    static OkHttpClient client;
    public static OkHttpClient getInstance(){
        if (client == null) {
            synchronized (OkhttpUtil.class){
                if (client == null) {
                    File file=new File(Environment.getExternalStorageDirectory()+"client");
                    client=new OkHttpClient().newBuilder()
                            .cache(new Cache(file,10*1024*1024))
                            .connectTimeout(3000, TimeUnit.SECONDS)
                            .readTimeout(3000,TimeUnit.SECONDS)
                            .writeTimeout(3000,TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return client;
    }
    public static void doGet(String url, Callback callback){
        OkHttpClient instance = getInstance();
        Request request=new Request.Builder()
                .url(url)
                .build();
        instance.newCall(request).enqueue(callback);
    };
    public static void doPost(String url, Map<String,String> params, Callback callback){
        OkHttpClient instance = getInstance();
        FormBody.Builder form=new FormBody.Builder();
        for (String in:params.keySet() ) {
            form.add(in,params.get(in));
        }
        Request request=new Request.Builder()
                .url(url)
                .post(form.build())
                .build();
        instance.newCall(request).enqueue(callback);
    };
}

