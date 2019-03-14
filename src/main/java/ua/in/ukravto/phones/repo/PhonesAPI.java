package ua.in.ukravto.phones.repo;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ua.in.ukravto.phones.domain.ResponseToken;

public interface PhonesAPI {

    @POST("user/token")
    Call<ResponseToken<String>> logIn(@Query("email") String email, @Query("deviceid") String idDevice);
}
