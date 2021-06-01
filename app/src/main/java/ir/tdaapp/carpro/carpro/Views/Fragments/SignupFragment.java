package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ir.tdaapp.carpro.carpro.databinding.FragmentSignupBinding;

public class SignupFragment extends BaseFragment {

  public static final String TAG = "SignupFragment";

  FragmentSignupBinding binding;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = FragmentSignupBinding.inflate(inflater, container, false);

    return binding.getRoot();
  }
}
