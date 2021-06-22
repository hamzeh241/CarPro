package ir.tdaapp.carpro.carpro.Models.Services;

import ir.tdaapp.carpro.carpro.Models.ViewModels.UserModel;
import ir.tdaapp.li_volley.Enum.ResaultCode;

public interface MemberService {

  void onLoading(boolean state);

  void onError(ResaultCode code);

  void onFinish();

  void onDataReceived(UserModel model);

  void onStatusChangeSuccessful(String message,boolean state);

  void onPresenterStart();
}
