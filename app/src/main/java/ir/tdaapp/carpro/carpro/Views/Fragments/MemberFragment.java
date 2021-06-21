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

import es.dmoral.toasty.Toasty;
import ir.tdaapp.carpro.carpro.Models.Adapters.MembersAdapter;
import ir.tdaapp.carpro.carpro.Models.Services.MemberService;
import ir.tdaapp.carpro.carpro.Models.Services.onUserModelClickListener;
import ir.tdaapp.carpro.carpro.Models.ViewModels.UserModel;
import ir.tdaapp.carpro.carpro.Presenters.MemberPresenter;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;
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

    adapter.setClickListener(new onUserModelClickListener() {
      @Override
      public void onChangeState(UserModel model) {
        presenter.changeUserProStatus(((MainActivity) getActivity()).getTbl_user()
          .getUserId(), model.getId(), !model.isEnabled());
      }

      @Override
      public void onClick(UserModel model) {
        EditMembersFragment fragment = new EditMembersFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ID", model.getId());
        fragment.setArguments(bundle);

        ((MainActivity) getActivity()).onAddFragment(fragment, R.anim.fadein,
          R.anim.fadeout, true, EditMembersFragment.TAG);
      }
    });
  }

  @Override
  public void onLoading(boolean state) {
    TransitionManager.beginDelayedTransition(binding.getRoot(), new Slide(Gravity.TOP));
    binding.progressBar.setVisibility(state ? View.VISIBLE : View.GONE);
    binding.memberList.setVisibility(state ? View.GONE : View.VISIBLE);
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
  public void onStatusChangeSuccessful(String message, boolean state) {
    if (state) {
      Toasty.success(getContext(), message, Toast.LENGTH_LONG).show();
      presenter.start();
    } else
      Toasty.error(getContext(), message, Toast.LENGTH_LONG).show();
  }

  @Override
  public void onPresenterStart() {
    adapter.clear();
    binding.memberList.setLayoutManager(manager);
    binding.memberList.setAdapter(adapter);
  }
}
