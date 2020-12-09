package com.caraquri.android.app4.di;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.room.Room;
import com.caraquri.android.app4.data.TodoDao;
import com.caraquri.android.app4.data.TodoDatabase;
import com.caraquri.android.app4.data.TodoRepository;
import com.caraquri.android.app4.data.TodoRepositoryImpl;
import com.caraquri.android.app4.executor.AppExecutors;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import java.util.concurrent.Executors;
import javax.inject.Singleton;

/**
 * Repository関連の依存を提供するモジュール。
 * TodoDaoやTodoDatabaseはコンストラクタで生成できないため、@Providesで提供方法を書く。
 */
@Module
@InstallIn(SingletonComponent.class)
abstract class RepositoryModule {

  // TodoRepositoryImplはコンストラクタを使う生成方法がわかるので
  // TodoRepositoryImplのコンストラクタに@InjectをつけてDaggerがそのコンストラクタを利用して
  // TodoRepositoryImplを生成するようにする。
  // コンストラクタ引数のTodoDaoやAppExecutorsがDaggerから利用できれば自動で呼んでくれる。
  // ↓のように自分でprovideメソッドを書くことも可能。
  //@Provides TodoRepositoryImpl provideTodoRepositoryImpl(TodoDao todoDao, AppExecutors appExecutors) {
  //  return new TodoRepositoryImpl(todoDao, appExecutors);
  //}

  // 実際に提供したいのはTodoRepositoryなので、生成したTodoRepositoryImplを渡す
  // TodoRepositoryは実装クラスであるTodoRepositoryImplが実体のため@Bindsを使うことでメソッドの中身を省略できる
  @Binds @Singleton public abstract TodoRepository provideTodoRepository(
      TodoRepositoryImpl repository);

  // ↑は↓と同じ意味
  //@Provides @Singleton
  //public TodoRepository provideTodoRepository(TodoRepositoryImpl repository) {
  //  return repository;
  //}

  @Provides public static TodoDao provideTodoDao(TodoDatabase db) {
    return db.todoDao();
  }

  // Hileの@ApplicationContextを使うとApplicationContextが得られる
  // @Singletonはコンポーネントごとに常にひとつのインスタンスのみを生成する指定
  @Provides @Singleton public static TodoDatabase provideTodoDatabase(
      @ApplicationContext Context context) {
    return Room.databaseBuilder(context, TodoDatabase.class, "todo")
        .fallbackToDestructiveMigration()
        .build();
  }

  // AppExecutorはTodoRepositoryImpl同様コンストラクタで生成できるが、
  // AppExecutorには2つのExecutorが必要。
  // ただし@Providesを使い複数のExecutorを提供しても、どのExecutorを使えばいいのdaggerにはわからない。
  // そのため
  // (1) Executorは@Providesで書かず、代わりにAppExecutorsのコンストラクタを呼ぶ@Providesを書くか、
  // (2) Qualifierという仕組みで複数の同じクラスのインスタンスを区別し提供する
  // のどちらかをする必要がある。
  // @ApplicationContextもQualifierの一種。
  // (1)の例
  @Provides @Singleton public static AppExecutors provideAppExecutors(
      @ApplicationContext Context context) {
    return new AppExecutors(Executors.newSingleThreadExecutor(),
        ContextCompat.getMainExecutor(context));
  }

  // (2)の例
  // @NamedというQualifierを使って2つのExecutorを区別する。
  // @Namedに渡した値でインスタンスが区別される。
  //
  // (2.1) この場合は↑のAppExecutorsのprovideメソッドは不要で、
  // AppExecutorsのコンストラクタに@Injectをつけて、
  // 引数のExecutorにそれぞれ対応する@Namedをつける。
  // @Inject public AppExecutors(@Named("IoExecutor") Executor io, @Named("MainExecutor") Executor main)
  //
  // (2.2) もしくはAppExecutorのコンストラクタには@Injectや@Namedをつけず、
  // AppExecutorsのprovideメソッドにExecutorを渡す
  // @Provides @Singleton public AppExecutors provideAppExecutors(
  //      @Named("IoExecutor") Executor io, @Named("MainExecutor") Executor main) {
  //   return new AppExecutors(io, main);
  // }
  //@Provides @Singleton @Named("IoExecutor") //
  //public Executor provideIoExecutor() {
  //  return Executors.newSingleThreadExecutor();
  //}
  //
  //@Provides @Singleton @Named("MainExecutor") //
  //public Executor provideMainThreadExecutor(@ApplicationContext Context context) {
  //  return ContextCompat.getMainExecutor(context);
  //}
}
