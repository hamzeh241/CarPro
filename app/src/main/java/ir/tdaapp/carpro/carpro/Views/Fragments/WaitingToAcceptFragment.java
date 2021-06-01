package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Adapters.VieaPagerAdapterAcceptedCars;
import ir.tdaapp.carpro.carpro.Views.Adapters.VieaPagerAdapterWaitingAceept;

public class WaitingToAcceptFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "WaitingToAcceptFragment";


    TabLayout tabLayout;
    ViewPager2 viewPager;
    VieaPagerAdapterWaitingAceept adapter;
    ImageButton back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiting_accept, container, false);

        findView(view);

        implement();

        return view;
    }

    private void findView(View view) {
        tabLayout = view.findViewById(R.id.tablayoutWaiting);
        viewPager = view.findViewById(R.id.vieewPagerWaitingtoAccept);
        back = view.findViewById(R.id.img_back_waiting);
    }

    private void implement() {

        back.setOnClickListener(this);

        setTabLayout();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.img_back_waiting:
                getActivity().onBackPressed();
                break;
        }



    }

    //set fragments on tablayout
    private void setTabLayout() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        adapter = new VieaPagerAdapterWaitingAceept(manager, getLifecycle());

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
                tabLayout.selectTab(tabLayout.getTabAt(1));
            }
        });

    }

}
