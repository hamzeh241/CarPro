package ir.tdaapp.carpro.carpro.Views.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ir.tdaapp.carpro.carpro.Views.Fragments.EditMembersFragment;
import ir.tdaapp.carpro.carpro.Views.Fragments.RegistrationMemberFragment;

public class ViewPagerAdapterMember extends FragmentStateAdapter {

    public ViewPagerAdapterMember(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){

            case 0:
                return new RegistrationMemberFragment();
            case 1:
                return new EditMembersFragment();

        }

        return new EditMembersFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
