package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import ir.tdaapp.carpro.carpro.Models.Adapters.VieaPagerAdapterAcceptedCars;
import ir.tdaapp.carpro.carpro.Models.Adapters.VieaPagerAdapterWaitingAceept;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.databinding.FragmentWaitingAcceptBinding;

public class WaitingToAcceptFragment extends Fragment {
    public static final String TAG = "WaitingToAcceptFragment";

    VieaPagerAdapterWaitingAceept adapter;


    FragmentWaitingAcceptBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWaitingAcceptBinding.inflate(inflater, container, false);


        implement();
        setTabLayout();
        return binding.getRoot();
    }



    private void implement() {


    }

    private void setTabLayout() {

        FragmentManager manager = getActivity().getSupportFragmentManager();
        adapter = new VieaPagerAdapterWaitingAceept(manager, getLifecycle());
        binding.vieewPagerWaitingtoAccept.setAdapter(adapter);
        binding.tablayoutWaiting.addTab(binding.tablayoutWaiting.newTab().setText("منتشر شده"));
        binding.tablayoutWaiting.addTab(binding.tablayoutWaiting.newTab().setText("انتظار تایید"));
        binding.tablayoutWaiting.addTab(binding.tablayoutWaiting.newTab().setText("آرشیو خودرو"));


        binding.tablayoutWaiting.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.vieewPagerWaitingtoAccept.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        binding.vieewPagerWaitingtoAccept.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {


                binding.tablayoutWaiting.selectTab(binding.tablayoutWaiting.getTabAt(1));


            }
        });


    }
}
