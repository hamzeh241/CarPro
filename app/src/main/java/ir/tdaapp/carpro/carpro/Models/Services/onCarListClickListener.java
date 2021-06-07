package ir.tdaapp.carpro.carpro.Models.Services;

import ir.tdaapp.carpro.carpro.Models.ViewModels.CarListModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarModel;

public interface onCarListClickListener {
  void onClick(CarModel model, int position);
}

