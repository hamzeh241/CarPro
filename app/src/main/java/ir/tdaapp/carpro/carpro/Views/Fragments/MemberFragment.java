package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Models.Adapters.ViewPagerAdapterMember;

public class MemberFragment extends BaseFragment {

    public static final String TAG = "MemberFragment";

    TabLayout tabLayout;
    ViewPager2 viewPager;
    ViewPagerAdapterMember adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_members_managment,container,false);

        findViews(view);

        implement();


        return view;
    }

    public void findViews(View view){

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        FragmentManager manager = getActivity().getSupportFragmentManager();
        adapter = new ViewPagerAdapterMember(manager,getLifecycle());

    }
    public void implement(){
            viewPager.setAdapter(adapter);
            tabLayout.addTab(tabLayout.newTab().setText("ثبت"));
            tabLayout.addTab(tabLayout.newTab().setText("ویرایش اعضا"));

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
                   tabLayout.selectTab(tabLayout.getTabAt(position));
                }
            });
    }
}
