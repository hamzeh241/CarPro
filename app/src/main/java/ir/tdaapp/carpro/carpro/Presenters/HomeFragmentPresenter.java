package ir.tdaapp.carpro.carpro.Presenters;

import android.content.Context;
import android.util.Log;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.carpro.carpro.Models.Repository.Server.UserEnabledStateRepository;
import ir.tdaapp.carpro.carpro.Models.Services.HomeFragmentService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Views.Fragments.HomeFragment;

import static ir.tdaapp.carpro.carpro.Views.Fragments.HomeFragment.TAG;

public class HomeFragmentPresenter {
    
    Context context;
    HomeFragmentService service;
    UserEnabledStateRepository repository;
    Disposable getDisposable;

    public HomeFragmentPresenter(Context context, HomeFragmentService service) {
        this.context = context;
        this.service = service;
        this.repository = new UserEnabledStateRepository();
    }

    public void start(){
        service.onPresenterStart();
    }

    public void getUserStatus(int id){

        Single<ApiDefaultResponse> data = repository.checkStatus(id);

            getDisposable = data.subscribeWith(new DisposableSingleObserver<ApiDefaultResponse>() {
                @Override
                public void onSuccess(@NonNull ApiDefaultResponse model) {
                    service.onResponseRecive(model);
                    service.onLoading(false);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    service.onLoading(false);
                    service.onError(e.getMessage());

                }
            });

    }
    public void checkIsAdmin(int id){

        Single<ApiDefaultResponse> data = repository.checkAdmin(id);

            getDisposable = data.subscribeWith(new DisposableSingleObserver<ApiDefaultResponse>() {
                @Override
                public void onSuccess(@NonNull ApiDefaultResponse model) {
                    service.onResponseReciveAdmin(model);
                    service.onLoading(false);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    service.onLoading(false);
                    service.onError(e.getMessage());

                }
            });

    }
}
