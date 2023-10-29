package com.example.myhisabadmin.Network;

import com.example.myhisabadmin.Model.AccountHeadAdd.AccountHeadAdd;
import com.example.myhisabadmin.Model.AdminUserList.AdminUserList;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("api/admin/login")
    Call<JsonObject> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/auth/accountheadstore")
    Call<AccountHeadAdd> addhead(@Field("name") String name, @Field("type") String type);

    @GET("api/admin/userlist")
    Call<AdminUserList> userlist();

    @GET("api/admin/userlist")
    Call<AdminUserList> userlistpage(@Query("page")int page);
}
