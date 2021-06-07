package ir.tdaapp.carpro.carpro.Models.Services;

import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;

public interface SignupFragmentService {

  void onPresenterStart();

  void onResponseReceived(ApiDefaultResponse response);

  void onError(String message);

  void onLoading(boolean state);
}
