package ir.tdaapp.carpro.carpro.Models.Services;

import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.li_volley.Enum.ResaultCode;

public interface HomeFragmentService {

    void onPresenterStart();

    void onResponseReceived(ApiDefaultResponse status);

    void onResponseReceivedAdmin(ApiDefaultResponse status);

    void onLoading(boolean load);

    void onError(ResaultCode code);

    void onFinish();
    
    
}
