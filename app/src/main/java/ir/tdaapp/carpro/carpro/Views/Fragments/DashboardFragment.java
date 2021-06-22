package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import ir.tdaapp.carpro.carpro.databinding.FragmentDashboardBinding;

public class DashboardFragment extends BaseFragment {
  public static final String TAG = "DashboardFragment";

  FragmentDashboardBinding binding;

  String[] titles = {"تایید شده","در انتظار تایید" ,"آرشیو خودرو ها"};
  int index;

  public DashboardFragment(int index) {
    this.index = index;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = FragmentDashboardBinding.inflate(inflater, container, false);

    implement();
    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(index));
    binding.imgBack.setOnClickListener(v -> {
      getActivity().onBackPressed();
    });

    return binding.getRoot();
  }

  private void implement(){
    binding.viewpager.setAdapter(new ViewPagerFragmentAdapter(getActivity()));

    // attaching tab mediator
    new TabLayoutMediator(binding.tabLayout, binding.viewpager,
      (tab, position) -> tab.setText(titles[position])).attach();
  }

  private class ViewPagerFragmentAdapter extends FragmentStateAdapter {

    public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
      super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
      switch (position) {
        case 0:
          return new PublishedCarsFragment();
        case 1:
          return new WaitingToPublishFragment();
        case 2:
          return new ArchivedFragment();
      }
      return new WaitingToPublishFragment();
    }

    @Override
    public int getItemCount() {
      return titles.length;
    }
  }
}
