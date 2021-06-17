package ir.tdaapp.carpro.carpro.Presenters;

import android.content.Context;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.carpro.carpro.Models.Repository.Server.UserEnabledStateRepository;
import ir.tdaapp.carpro.carpro.Models.Services.SplashFragmentService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.UserModel;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;

public class SplashPresenter {

    Context context;
    SplashFragmentService service;
    UserEnabledStateRepository repository;
    Disposable getDisposable;

    public SplashPresenter(Context context, SplashFragmentService service) {
        this.context = context;
        this.service = service;
        this.repository = new UserEnabledStateRepository();
    }

    public void start(){
        service.onPresenterStart();
    }

//    public void getStatusUser(int id){
//
//        Single<String> data = repository.checkStatus(id);
//        getDisposable = data.subscribeWith(new DisposableSingleObserver<String>() {
//            @Override
//            public void onSuccess(@NonNull String s) {
//                service.onLoading(false);
//                service.onResponseRecive(s);
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                service.onError(e.getMessage());
//            }
//        });
//
//    }

}
