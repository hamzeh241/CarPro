package ir.tdaapp.carpro.carpro.Presenters;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.carpro.carpro.Models.Repository.Server.MemberRepository;
import ir.tdaapp.carpro.carpro.Models.Services.MemberService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Models.ViewModels.UserModel;

public class MemberPresenter {

  Context context;
  MemberService service;
  MemberRepository repository;
  Disposable getMembers, setMembers, changeStatus;


  public MemberPresenter(Context context, MemberService service) {
    this.context = context;
    this.service = service;
    repository = new MemberRepository();
  }

  public void start() {
    service.onPresenterStart();
    getMembers();
  }

  private void getMembers() {
    service.onLoading(true);

    Single<List<UserModel>> data = repository.getUsers();

    getMembers = data.subscribeWith(new DisposableSingleObserver<List<UserModel>>() {
      @Override
      public void onSuccess(@NonNull List<UserModel> userModels) {
        service.onLoading(false);
        setMembers(userModels);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onLoading(false);
        service.onError(e.getMessage());
      }
    });
  }

  private void setMembers(List<UserModel> userModels) {
    Observable<UserModel> observable = Observable.fromIterable(userModels);

    setMembers = observable.subscribe(model -> {
      service.onDataReceived(model);
    }, throwable -> {
    }, () -> {
      service.onFinish();
    });
  }

  public void changeUserProStatus(int adminId, int userId, boolean status) {
    JSONObject object = new JSONObject();
    try {
      object.put("UserId", userId);
      object.put("Status", status);
      object.put("AdminId", adminId);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    Single<ApiDefaultResponse> data = repository.changeUserState(object);

    changeStatus = data.subscribeWith(new DisposableSingleObserver<ApiDefaultResponse>() {
      @Override
      public void onSuccess(@NonNull ApiDefaultResponse apiDefaultResponse) {
        if (apiDefaultResponse.isResult())
          service.onStatusChangeSuccessful(true);
        else service.onStatusChangeSuccessful(false);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onStatusChangeSuccessful(false);
      }
    });
  }
}

