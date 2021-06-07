package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.databinding.FragmentHomeBinding;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
  public static final String TAG = "HomeFragment";

  FragmentHomeBinding binding;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = FragmentHomeBinding.inflate(inflater, container, false);

    implement();

    return binding.getRoot();
  }


  public void implement() {
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
}
