package ir.tdaapp.carpro.carpro.Models.Services;

import ir.tdaapp.carpro.carpro.Models.ViewModels.UserModel;

public interface onUserModelClickListener {

  void onChangeState(UserModel model);

  void onClick(UserModel model);
}
