package ir.tdaapp.carpro.carpro.Models.Services;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarChipsListModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailsEntryModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarModel;
import ir.tdaapp.li_volley.Enum.ResaultCode;

public interface CarDetailsService {

  void onPresenterStart();

  void onItemReceived(CarDetailModel item);

  void onConfirmCarSuccessful(boolean result, String message);

  void onRejectCarSuccessful(boolean result, String message);

  void onEditCarSuccessful(boolean result, String message);

  void onError(ResaultCode code);

  void onLoading(boolean state);

  void onCategoriesReceived(List<CarChipsListModel> model);

  void onProductionYearsReceived(List<CarDetailsEntryModel> model);

  void onBrandsReceived(List<CarDetailsEntryModel> model, int selected);

  void onModelsReceived(List<CarDetailsEntryModel> model);

  void onEngineStatusReceived(List<CarDetailsEntryModel> model);

  void onChassisStatusReceived(List<CarDetailsEntryModel> model);

  void onBodyStatusReceived(List<CarDetailsEntryModel> model);

  void onInsuranceDeadlineReceived(List<CarDetailsEntryModel> model);

  void onGearBoxReceived(List<CarDetailsEntryModel> model);

  void onDocumentReceived(List<CarDetailsEntryModel> model);

  void onSellTypeReceived(List<CarDetailsEntryModel> model);

  void onColorsReceived(List<CarDetailsEntryModel> model);


  void onImagesUploaded(ArrayList<String> arrayList, List<Uri> uris);

  void onImageUploading(boolean loading);

  void onStoragePermissionGranted();

  void onStoragePermissionDenied();
}
