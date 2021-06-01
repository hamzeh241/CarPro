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

public class HomeFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "HomeFragment";

    LinearLayout memberlayout,acceptedCarslayout,waitingLayout,archivedlayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        findViews(view);

        implement();

        return view;
    }

    public void findViews(View view){

        memberlayout = view.findViewById(R.id.members_layout);
        acceptedCarslayout = view.findViewById(R.id.accepted_cars_layout);
        waitingLayout = view.findViewById(R.id.waiting_layout);
        archivedlayout = view.findViewById(R.id.archived_layout);
    }

    public void implement(){
        memberlayout.setOnClickListener(this);
        waitingLayout.setOnClickListener(this);
        acceptedCarslayout.setOnClickListener(this);
        archivedlayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.members_layout:
                ((MainActivity)getActivity()).onAddFragment(new MemberFragment(),R.anim.fadein,R.anim.fadeout,true,MemberFragment.TAG);
            break;

            case R.id.accepted_cars_layout:
                ((MainActivity)getActivity()).onAddFragment(new AcceptedCarsFragment(),R.anim.fadein,R.anim.fadeout,true,AcceptedCarsFragment.TAG);
            break;

            case R.id.waiting_layout:
                ((MainActivity)getActivity()).onAddFragment(new WaitingToAcceptFragment(),R.anim.fadein,R.anim.fadeout,true,WaitingToAcceptFragment.TAG);
            break;

            case R.id.archived_layout:
                ((MainActivity)getActivity()).onAddFragment(new CarArchivedFragment(),R.anim.fadein,R.anim.fadeout,true,CarArchivedFragment.TAG);
            break;
        }

    }
}
