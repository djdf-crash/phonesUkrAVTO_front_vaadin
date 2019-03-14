package ua.in.ukravto.phones.repo;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.in.ukravto.phones.repo.utils.ConstantAPI;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class RestClientBuilder {

    private static PhonesAPI service;

    public static PhonesAPI getService(){
        if (service == null){
            java.net.Proxy proxy = new Proxy(Proxy.Type.HTTP,  new InetSocketAddress("proxy.ukravto.loc", 3128));
            OkHttpClient client = new OkHttpClient.Builder().proxy(proxy).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(ConstantAPI.SERVICE_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(PhonesAPI.class);
        }

        return service;
    }
}
