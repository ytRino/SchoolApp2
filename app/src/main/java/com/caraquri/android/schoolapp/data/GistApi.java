package com.caraquri.android.schoolapp.data;

import com.caraquri.android.schoolapp.data.data.Gist;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface GistApi {

  @Headers("Accept: application/vnd.github.v3+json")
  @GET("users/yiino-cq/gists") Call<List<Gist>> getGist();

  @Headers("Accept: application/vnd.github.v3+json")
  @GET("users/{user}/gists") List<Gist> getGist(@Path("user") String user);
}
