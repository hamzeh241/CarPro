package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ir.tdaapp.carpro.carpro.databinding.FragmentCarDetailsBinding;

public class CarDetailsFragment extends BaseFragment implements View.OnClickListener{

  FragmentCarDetailsBinding binding;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = FragmentCarDetailsBinding.inflate(inflater, container, false);

    implement();

    return binding.getRoot();
  }

  private void implement(){

  }


  @Override
  public void onClick(View v) {

  }
}
