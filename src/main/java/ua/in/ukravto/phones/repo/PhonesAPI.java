package ua.in.ukravto.phones.repo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ua.in.ukravto.phones.domain.Employee;
import ua.in.ukravto.phones.domain.EmployeeOrganization;
import ua.in.ukravto.phones.domain.ResponseAPI;
import ua.in.ukravto.phones.domain.ResponseListAPI;

public interface PhonesAPI {

    @POST("user/token")
    Call<ResponseAPI<String>> logIn(@Query("email") String email, @Query("deviceid") String idDevice);

    @GET("organization/all")
    Call<ResponseListAPI<EmployeeOrganization>> getListOrganizations(@Header("token") String token);

    @GET("phone/all")
    Call<ResponseListAPI<Employee>> listEmployeePhones(@Header("token") String token);
}
