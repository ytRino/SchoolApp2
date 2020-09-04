package com.caraquri.android.schoolapp.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.caraquri.android.schoolapp.R;
import com.caraquri.android.schoolapp.data.GistApi;
import com.caraquri.android.schoolapp.data.data.Gist;
import com.caraquri.android.schoolapp.databinding.NetworkFragmentBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkFragment extends Fragment {

  private static final String uri = "https://api.github.com/users/yiino-cq/gists";

  private NetworkFragmentBinding binding;

  public NetworkFragment() {
    super(R.layout.network_fragment);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    binding = NetworkFragmentBinding.bind(view);

    binding.httpUrlConnection.setOnClickListener(v -> {
      new Thread(() -> {
        getByHttpUrlConnection();
      }).start();
    });

    binding.okhttp.setOnClickListener(v -> {
      getByOkHttpAsync();
    });

    binding.retrofit.setOnClickListener(v -> {
      getByRetrofit();
    });
  }

  private void getByHttpUrlConnection() {
    try {
      URL url = new URL(uri);
      HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
      connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
      connection.setRequestMethod("GET");
      // 通信
      connection.connect();

      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        Log.v("thread", Thread.currentThread().toString());
        // 通信に成功 テキストを取得
        InputStream in = connection.getInputStream();
        String encoding = connection.getContentEncoding();
        if (null == encoding) {
          encoding = "UTF-8";
        }
        final InputStreamReader inReader = new InputStreamReader(in, encoding);
        final BufferedReader bufReader = new BufferedReader(inReader);
        Gson gson = new Gson();
        List<Gist> gist = gson.fromJson(bufReader, new TypeToken<List<Gist>>() {}.getType());
        bufReader.close();
        inReader.close();
        in.close();
        // アウトプット
        Log.v("log", "result: " + gist);
        binding.log.setText(System.currentTimeMillis() + ", " + gist.toString());
      }
    } catch (Exception e) {
      Log.w("log", "エラー!", e);
    }
  }

  private void getByOkHttp() {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url(uri)
        .addHeader("Accept", "application/vnd.github.v3+json")
        .build();

    try {
      Response response = client.newCall(request).execute();
      if (response.isSuccessful()) {
        String body = response.body().string();
        Gson gson = new Gson();
        List<Gist> gist = gson.fromJson(body, new TypeToken<List<Gist>>() {}.getType());
        // アウトプット
        Log.v("log", "result: " + gist);
        binding.log.setText(System.currentTimeMillis() + ", " + gist.toString());
      }
    } catch (IOException e) {

    }
  }

  private void getByOkHttpAsync() {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url(uri)
        .addHeader("Accept", "application/vnd.github.v3+json")
        .build();

    client.newCall(request).enqueue(new Callback() {
      @Override public void onResponse(@NotNull Call call, @NotNull Response response)
          throws IOException {
        if (response.isSuccessful()) {
          String body = response.body().string();
          Gson gson = new Gson();
          List<Gist> gist = gson.fromJson(body, new TypeToken<List<Gist>>() {}.getType());
          // アウトプット
          Log.v("log", "result: " + gist);
          binding.log.setText(System.currentTimeMillis() + ", " + gist.toString());
        }
      }

      @Override public void onFailure(@NotNull Call call, @NotNull IOException e) { }
    });
  }

  private void getByRetrofit() {
    OkHttpClient client = new OkHttpClient();
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(new Gson()))
        .build();
    GistApi api = retrofit.create(GistApi.class);
    api.getGist().enqueue(new retrofit2.Callback<List<Gist>>() {
      @Override public void onResponse(retrofit2.Call<List<Gist>> call,
          retrofit2.Response<List<Gist>> response) {
        if (response.isSuccessful()) {
          List<Gist> gist = response.body();
          // アウトプット
          Log.v("log", "result: " + gist);
          binding.log.setText(System.currentTimeMillis() + ", " + gist.toString());
        }
      }

      @Override public void onFailure(retrofit2.Call<List<Gist>> call, Throwable t) { }
    });
  }
}
