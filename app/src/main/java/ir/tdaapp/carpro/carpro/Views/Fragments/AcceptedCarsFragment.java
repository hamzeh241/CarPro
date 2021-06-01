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

import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Adapters.VieaPagerAdapterAcceptedCars;

public class AcceptedCarsFragment extends Fragment {

    public static final String TAG = "AcceptedCarsFragment";

    TabLayout tabLayout;
    ViewPager2 viewPager;
    VieaPagerAdapterAcceptedCars adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accepted_cars, container, false);

        findViews(view);
        implement();

        return view;
    }

    private void findViews(View view){

        tabLayout = view.findViewById(R.id.tabLayoutAcceptedCars);
        viewPager = view.findViewById(R.id.viewPagerAcceptedCars);
    }

    private void implement(){

        FragmentManager manager = getActivity().getSupportFragmentManager();
        adapter = new VieaPagerAdapterAcceptedCars(manager,getLifecycle());

        viewPager.setAdapter(adapter);
        tabLayout.addTab(tabLayout.newTab().setText("منتشر شده"));
        tabLayout.addTab(tabLayout.newTab().setText("انتظار تایید"));
        tabLayout.addTab(tabLayout.newTab().setText("آرشیو خودرو"));






        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {


                tabLayout.selectTab(tabLayout.getTabAt(0));



            }
        });


    }

}
