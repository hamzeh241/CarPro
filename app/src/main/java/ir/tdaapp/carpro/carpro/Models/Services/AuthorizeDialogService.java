package ir.tdaapp.carpro.carpro.Models.Services;

import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.li_volley.Enum.ResaultCode;

public interface AuthorizeDialogService {

  void onLoading(boolean state);

  void onError(ResaultCode code);

  void onDataReceived(ApiDefaultResponse response);

  void onPresenterStart();
}
