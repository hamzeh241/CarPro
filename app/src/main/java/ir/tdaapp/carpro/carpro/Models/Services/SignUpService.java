package ir.tdaapp.carpro.carpro.Models.Services;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;

public interface SignUpService {

    void onPresenterStart();

    void onError(String result);

    void onResponseReceived(ApiDefaultResponse response);

    void onResponseReciveCode(ApiDefaultResponse response);

    void onStoragePermissionGranted();

    void onStoragePermissionDenied();

    void onImagesUploaded(String filename, Uri uri);

    void onImageUploading(boolean loading);


    void onDataSendingState(boolean loading);

    void onLoading(boolean state);

    void onFinish();

}
