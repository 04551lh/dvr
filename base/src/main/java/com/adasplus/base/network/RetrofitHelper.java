package com.adasplus.base.network;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author:刘净辉
 * Date : 2019/9/16 16:06
 */
public class RetrofitHelper {

    private static final String TAG = "RetrofitHelper";

    private final static int CONNECT_TIMEOUT = 3000;
    private final static int READ_TIMEOUT = 3000;
    private final static int WRITE_TIMEOUT = 3000;

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(HttpConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(new Gson()))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(getOkHttpClient());

    private static CookieJar cookieJar = new CookieJar() {
        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url.host(), cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    };

    private static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                PrettyFormatStrategy prettyFormatStrategy = PrettyFormatStrategy.newBuilder()
                        .tag(TAG)
                        .build();
                Logger.addLogAdapter(new AndroidLogAdapter(prettyFormatStrategy));
                Logger.i(message);
                Logger.clearLogAdapters();
            }
        });

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();
    }

    protected <T> Observable.Transformer<BaseResponse<T>, T> applySchedulers() {

        return (Observable.Transformer<BaseResponse<T>, T>) transformer;
    }

    Observable.Transformer transformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            Observable observable1 = ((Observable) observable)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Func1() {
                        @Override
                        public Object call(Object response) {
                            return flatResponse((BaseResponse<Object>) response);
                        }
                    });
            return observable1;
        }
    };

    public static <T> T createServiceFrom(Class<T> serviceClass) {
        return builder.build().create(serviceClass);
    }

    private <T> Observable<T> flatResponse(final BaseResponse<T> response) {

        Observable.OnSubscribe<T> onSubscribe = new Observable.OnSubscribe<T>() {

            @Override
            public void call(rx.Subscriber<? super T> subscriber) {
                int statuesCode = response.getStatusCode();
                Log.e(TAG,"StatusCode---->"+statuesCode);
                try{
                    if (statuesCode == 0){
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(response.getResult());
                        }
                    }else {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(new Throwable(String.valueOf(statuesCode)));
                        }
                    }
                }catch (Exception e){
                    Log.e(TAG, "-----:"+e.getMessage());
                }

            }
        };

        return Observable.create(onSubscribe);
    }

    public static class GsonConverterFactory extends Converter.Factory {
        public static GsonConverterFactory create() {
            return create(new Gson());
        }

        static GsonConverterFactory create(Gson gson) {
            return new GsonConverterFactory(gson);
        }

        private final Gson gson;

        private GsonConverterFactory(Gson gson) {
            if (gson == null) throw new NullPointerException("gson == null");
            this.gson = gson;
        }

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                                Retrofit retrofit) {
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new GsonResponseBodyConverter<>(gson, adapter);
        }

        @Override
        public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                              Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new GsonRequestBodyConverter<>(gson, adapter);
        }
    }

    public static class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final Gson gson;
        private final TypeAdapter<T> adapter;

        GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            JsonReader jsonReader = gson.newJsonReader(value.charStream());
            try {
                return adapter.read(jsonReader);
            } finally {
                value.close();
            }
        }
    }

    public static class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private static final Charset UTF_8 = Charset.forName("UTF-8");

        private final Gson gson;
        private final TypeAdapter<T> adapter;

        GsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            Buffer buffer = new Buffer();
            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            JsonWriter jsonWriter = gson.newJsonWriter(writer);
            adapter.write(jsonWriter, value);
            jsonWriter.close();
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        }
    }
}
