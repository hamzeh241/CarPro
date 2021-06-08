package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import ir.tdaapp.carpro.carpro.Models.Adapters.MembersAdapter;
import ir.tdaapp.carpro.carpro.Models.Services.MemberService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.UserModel;
import ir.tdaapp.carpro.carpro.Presenters.MemberPresenter;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.databinding.FragmentMembersManagmentBinding;

public class MemberFragment extends BaseFragment implements MemberService {

  public static final String TAG = "MemberFragment";
  MemberPresenter presenter;

  FragmentMembersManagmentBinding binding;

  MembersAdapter adapter;
  LinearLayoutManager manager;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = FragmentMembersManagmentBinding.inflate(inflater, container, false);

    implement();

    return binding.getRoot();
  }

  public void implement() {
    presenter = new MemberPresenter(getContext(), this);
    adapter = new MembersAdapter(getContext());
    manager = new LinearLayoutManager(getContext());

    presenter.start();

    adapter.setClickListener(model -> presenter.changeUserProStatus(1, 2091, !model.isEnabled()));
  }

  @Override
  public void onLoading(boolean state) {
    binding.memberList.setVisibility(state ? View.GONE : View.VISIBLE);
    TransitionManager.beginDelayedTransition(binding.getRoot(), new Slide(Gravity.TOP));
    binding.progressBar.setVisibility(state ? View.VISIBLE : View.GONE);
  }

  @Override
  public void onError(String message) {
  }

  @Override
  public void onFinish() {

  }

  @Override
  public void onDataReceived(UserModel model) {
    adapter.add(model);
  }

  @Override
  public void onStatusChangeSuccessful(boolean state) {
    if (state) {
      Toast.makeText(getContext(), getContext().getResources().getString(R.string.status_changed), Toast.LENGTH_LONG).show();
      presenter.start();
    } else
      Toast.makeText(getContext(), getContext().getResources().getString(R.string.status_not_changed), Toast.LENGTH_LONG).show();
  }

  @Override
  public void onPresenterStart() {
    adapter.clear();
    binding.memberList.setLayoutManager(manager);
    binding.memberList.setAdapter(adapter);
  }
}
