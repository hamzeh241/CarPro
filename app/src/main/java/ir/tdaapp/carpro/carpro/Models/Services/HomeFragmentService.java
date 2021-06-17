package ir.tdaapp.carpro.carpro.Models.Services;

import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;

public interface HomeFragmentService {

    void onPresenterStart();

    void onResponseRecive(ApiDefaultResponse status);

    void onResponseReciveAdmin(ApiDefaultResponse status);

    void onLoading(boolean load);

    void onError(String message);

    void onFinish();
    
    
}
