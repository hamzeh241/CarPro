package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

import es.dmoral.toasty.Toasty;
import ir.tdaapp.carpro.carpro.Models.Repository.Server.UserEnabledStateRepository;
import ir.tdaapp.carpro.carpro.Models.Services.HomeFragmentService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Presenters.HomeFragmentPresenter;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.databinding.FragmentHomeBinding;

public class HomeFragment extends BaseFragment implements View.OnClickListener, HomeFragmentService {
  public static final String TAG = "HomeFragment";

  FragmentHomeBinding binding;
  HomeFragmentPresenter presenter;
  int userId;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = FragmentHomeBinding.inflate(inflater, container, false);
    implement();

    return binding.getRoot();
  }


  public void implement() {
    presenter = new HomeFragmentPresenter(getContext(),this);

    userId = ((MainActivity)getActivity()).getTbl_user().getUserId();
    Log.i(TAG, "implement: "+ userId);
    presenter.start();


    binding.membersLayout.setOnClickListener(this);
    binding.waitingLayout.setOnClickListener(this);
    binding.acceptedCarsLayout.setOnClickListener(this);
    binding.archivedLayout.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {

    switch (v.getId()) {

      case R.id.members_layout:
        ((MainActivity) getActivity()).onAddFragment(new MemberFragment(), R.anim.fadein, R.anim.fadeout, true, MemberFragment.TAG);
        break;

      case R.id.accepted_cars_layout:
        ((MainActivity) getActivity()).onAddFragment(new DashboardFragment(0), R.anim.fadein, R.anim.fadeout,
          true, DashboardFragment.TAG);
        break;

      case R.id.waiting_layout:
        ((MainActivity) getActivity()).onAddFragment(new DashboardFragment(1), R.anim.fadein, R.anim.fadeout,
          true, DashboardFragment.TAG);
        break;

      case R.id.archived_layout:
        ((MainActivity) getActivity()).onAddFragment(new DashboardFragment(2), R.anim.fadein, R.anim.fadeout,
          true, DashboardFragment.TAG);
        break;
    }

  }

  @Override
  public void onPresenterStart() {
      presenter.getUserStatus(userId);
  }

  @Override
  public void onResponseRecive(ApiDefaultResponse status) {
    if (!status.isResult()) {
      binding.loadingHome.setVisibility(View.VISIBLE);
      ((MainActivity) getActivity()).onAddFragment(new UserConfigurationMessageFragment(), 0, 0, false, UserConfigurationMessageFragment.TAG);
    }else{
      presenter.checkIsAdmin(userId);
      binding.loadingHome.setVisibility(View.GONE);
      binding.homeFragmentLayout.setVisibility(View.VISIBLE);
    }


  }

  @Override
  public void onResponseReciveAdmin(ApiDefaultResponse status) {

    if (status.isResult()){
      binding.membersLayout.setVisibility(View.VISIBLE);
    }else {
      binding.membersLayout.setVisibility(View.INVISIBLE);
    }


  }

  @Override
  public void onLoading(boolean load) {

  }

  @Override
  public void onError(String message) {

  }

  @Override
  public void onFinish() {

    FragmentManager manager = getActivity().getSupportFragmentManager();
    List<Fragment> fragmentList = manager.getFragments();

    if (fragmentList instanceof SignupFragment){

      manager.popBackStack();
    }

  }
}
