package com.example.website.Api;

import com.example.website.model.AnswerResponse;
import com.example.website.model.Avatar;
import com.example.website.model.DefaultResponse;
import com.example.website.model.LoginResponse;
import com.example.website.model.PostResponse;
import com.example.website.model.QuestionResponse;
import com.example.website.model.TagsListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @POST("api/get_nonce/?controller=user&method=register")
    Call<DefaultResponse> nonce();
    @FormUrlEncoded
    @POST("api/user/register/")
    Call<DefaultResponse> create_user(
            @Field("username") String username,
            @Field("email") String email,
            @Field("nonce") String nonce,
            @Field("display_name") String name,
            @Field("notify") String notify,
            @Field("user_pass") String pass

    );
    @POST("api/get_nonce/?controller=user&method=generate_auth_cookie")
    Call<LoginResponse> Nonce();
    @FormUrlEncoded
    @POST("api/user/generate_auth_cookie/")
    Call<LoginResponse> cookie(
            @Field("nonce") String nonce,
            @Field("username") String user,
            @Field("password") String pass
    );
    @FormUrlEncoded
    @POST("api/user/validate_auth_cookie/")
    Call<LoginResponse> Valid(
            @Field("cookie") String cookie
    );

    @POST("api/get_recent_posts/")
    Call<PostResponse> post(
            @Query("page") int x
    );

    @POST("api/get_tag_index/")
    Call<TagsListResponse> tags();

    @GET("newapi/wp/v2/question/")
    Call<List<QuestionResponse>> ques(
            @Query("page") int y
    );

    @GET("newapi/wp/v2/answer")
    Call<List<AnswerResponse>> ans();

    @POST("api/user/get_avatar/")
    Call<Avatar> avatar(
    @Query("user_id") int id,
    @Query("type") String type
                );

    @GET("api/get_tag_posts/")
    Call<PostResponse> tagPost(
            @Query("slug") String slug
    );

}
