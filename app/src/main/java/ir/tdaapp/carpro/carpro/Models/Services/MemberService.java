package ir.tdaapp.carpro.carpro.Models.Services;

import ir.tdaapp.carpro.carpro.Models.ViewModels.UserModel;

public interface MemberService {

  void onLoading(boolean state);

  void onError(String message);

  void onFinish();

  void onDataReceived(UserModel model);

  void onStatusChangeSuccessful(boolean state);

  void onPresenterStart();
}
