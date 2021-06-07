package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ir.tdaapp.carpro.carpro.Models.Services.RejectedCarsService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarModel;
import ir.tdaapp.carpro.carpro.Presenters.RejectedCarsPresenter;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;
import ir.tdaapp.carpro.carpro.databinding.FragmentArchivedCarsViewPagerBinding;

public class ArchivedFragment extends Fragment implements RejectedCarsService {

  RejectedCarsPresenter presenter;

  FragmentArchivedCarsViewPagerBinding binding;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = FragmentArchivedCarsViewPagerBinding.inflate(inflater, container, false);

    implement();

    return binding.getRoot();
  }

  private void implement() {
    presenter = new RejectedCarsPresenter(getContext(), this);
  }

  @Override
  public void onPresenterStart() {

  }

  @Override
  public void onItemReceived(CarModel item) {

  }

  @Override
  public void onError(String message) {

  }

  @Override
  public void onLoading(boolean state) {

  }

  @Override
  public void onFinish() {

  }
}
