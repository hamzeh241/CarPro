package ir.tdaapp.carpro.carpro.Models.Services;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.li_volley.Enum.ResaultCode;

public interface SignUpService {

    void onPresenterStart();

    void onError(ResaultCode code);

    void onResponseReceived(ApiDefaultResponse response);

    void onStoragePermissionGranted();

    void onStoragePermissionDenied();

    void onImagesUploaded(String filename, Uri uri);

    void onImageUploading(boolean loading);

    void onDataSendingState(boolean loading);

    void onLoading(boolean state);

    void onFinish();

}
