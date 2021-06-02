package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ir.tdaapp.carpro.carpro.databinding.FragmentConfigurationMessageBinding;

public class UserConfigurationMessageFragment extends BaseFragment {

    FragmentConfigurationMessageBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      binding = FragmentConfigurationMessageBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }
}
