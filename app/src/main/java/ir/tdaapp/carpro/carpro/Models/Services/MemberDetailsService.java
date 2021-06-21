package ir.tdaapp.carpro.carpro.Models.Services;

import java.util.List;

import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailsEntryModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.UserModel;

public interface MemberDetailsService {

  void onLoading(boolean state);

  void onPresenterStart();

  void onError(String message);

  void onFinish();

  void onDetailsReceived(UserModel model);

  void onBrandReceived(CarDetailsEntryModel model);

  void onBrandsDBReceived(List<CarDetailsEntryModel> models);

  void onBrandsEdited(String message, boolean result);
}
