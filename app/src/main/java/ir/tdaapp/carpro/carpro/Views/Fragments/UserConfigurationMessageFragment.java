package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;
import ir.tdaapp.carpro.carpro.databinding.FragmentConfigurationMessageBinding;

public class UserConfigurationMessageFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = "UserConfigurationMessageFragment";

    FragmentConfigurationMessageBinding binding;
    int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentConfigurationMessageBinding.inflate(inflater, container, false);

        implement();

        return binding.getRoot();
    }

    private void implement() {

        binding.btnExit.setOnClickListener(this);
        binding.btnExitAccount.setOnClickListener(this);

        userId = ((MainActivity)getActivity()).getTbl_user().getUserId();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_exit:
                getActivity().finish();
            break;

            case R.id.btn_exit_account:

                ((MainActivity)getActivity()).getTbl_user().removeUser();

                ((MainActivity)getActivity()).onAddFragment(new LoginFragment(),0,0,false,LoginFragment.TAG);

                break;

        }


    }
}
