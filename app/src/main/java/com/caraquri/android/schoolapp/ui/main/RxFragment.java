package com.caraquri.android.schoolapp.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.caraquri.android.schoolapp.R;
import com.caraquri.android.schoolapp.databinding.RxFragmentBinding;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.util.concurrent.TimeUnit;

public class RxFragment extends Fragment {

  // Subject: 外部からデータを直接流すことができるストリーム
  private final PublishSubject<Long> viewClickSubject = PublishSubject.create();

  // Subscribeしたストリームの停止などに使うDisposableをまとめて管理するDisposable
  private final CompositeDisposable disposables = new CompositeDisposable();

  public RxFragment() {
    super(R.layout.rx_fragment);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    RxFragmentBinding binding = RxFragmentBinding.bind(view);

    binding.button.setOnClickListener(v -> viewClickSubject.onNext(System.currentTimeMillis()));

    // クリック時にクリックした時間が流れてくるストリーム
    Disposable d = viewClickEvents()
        // イベントが流れてくるとサーバにリクエストを送るストリームを作成し
        // そのデータを次のストリームに流すオペレータ
        .flatMap(time -> requestData(time))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(data -> {
          TextView textView = createTextView(data);
          binding.log.addView(textView);
        });
    disposables.add(d);
  }

  @Override public void onDestroyView() {
    // 処理中のストリームのsubscriptionを破棄しsubscribeに登録した処理が行われないようにする
    disposables.clear();
    super.onDestroyView();
  }

  private Observable<Long> viewClickEvents() {
    return viewClickSubject;
  }

  private Observable<String> requestData(long time) {
    // サーバリクエスト(ダミー)
    // Observable.just(): 渡した値を即座に流すストリームを作成
    return Observable.just("サーバデータ:" + time)
        // delay(): 指定時間データを遅延して流すオペレータ
        // I/O用のスレッドで実行されるようSchedulerを指定
        .delay(1200, TimeUnit.MILLISECONDS, Schedulers.io());
  }

  private TextView createTextView(String text) {
    TextView textView = new TextView(getContext());
    textView.setText(text);
    return textView;
  }
}
