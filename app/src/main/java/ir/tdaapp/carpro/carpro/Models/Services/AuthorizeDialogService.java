package ir.tdaapp.carpro.carpro.Models.Services;

import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;

public interface AuthorizeDialogService {

  void onLoading(boolean state);

  void onError(String message);

  void onDataReceived(ApiDefaultResponse response);

  void onPresenterStart();
}
