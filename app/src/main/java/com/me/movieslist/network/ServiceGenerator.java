package com.me.movieslist.network;

import com.me.movieslist.config.UrlConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator {

    private final static String API_KEY = "api_key";
    private final static String API_KEY_VAL = "7908fb521aab3ef9b6c8212714d628db";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(UrlConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    private static Interceptor apiKeyQueryParameterInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter(API_KEY, API_KEY_VAL)
                    .build();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    };

    public static <S> S createService(Class<S> serviceClass) {

        if (!httpClient.interceptors().contains(apiKeyQueryParameterInterceptor)) {
            httpClient.addInterceptor(apiKeyQueryParameterInterceptor);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }
}

