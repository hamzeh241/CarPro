package ir.tdaapp.carpro.carpro.Models.Services;

public interface SplashFragmentService {

    void onPresenterStart();

    void onResponseRecive(String status);

    void onLoading(boolean load);

    void onError(String message);

    void onFinish();

}
