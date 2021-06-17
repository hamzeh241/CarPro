package ir.tdaapp.carpro.carpro.Models.Services;

import ir.tdaapp.carpro.carpro.Models.Adapters.CarDetailsPhotosAdapter;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailPhotoModel;

public interface onCarPhotosListener {

  void remove(CarDetailPhotoModel model, int position);

  void click(CarDetailPhotoModel model, int position);
}
