package ir.tdaapp.carpro.carpro.Views.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ir.tdaapp.carpro.carpro.Views.Fragments.ArchivedFragment;
import ir.tdaapp.carpro.carpro.Views.Fragments.PublishedCarsFragment;
import ir.tdaapp.carpro.carpro.Views.Fragments.WaitingToPublishFragment;

public class VieaPagerAdapterArchived extends FragmentStateAdapter {

    public VieaPagerAdapterArchived(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
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

        return new ArchivedFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
