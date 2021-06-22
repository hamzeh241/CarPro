package ir.tdaapp.carpro.carpro.Models.Services;

import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.li_volley.Enum.ResaultCode;

public interface LoginFragmentService {

  void onPresenterStart();

  void onResponseReceived(ApiDefaultResponse response);

  void onError(ResaultCode code);

  void onLoading(boolean state);
}
