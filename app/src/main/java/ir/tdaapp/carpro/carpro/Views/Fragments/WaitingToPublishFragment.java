package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import ir.tdaapp.carpro.carpro.Models.Adapters.CarListAdapter;
import ir.tdaapp.carpro.carpro.Models.Services.WaitingToAcceptService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarModel;
import ir.tdaapp.carpro.carpro.Presenters.WaitingToAcceptPresenter;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;
import ir.tdaapp.carpro.carpro.databinding.FragmentWaitingToAcceptCarsPagerBinding;

public class WaitingToPublishFragment extends BaseFragment implements WaitingToAcceptService {

  public static final String TAG = "WaitingToPublishFragmen";

  FragmentWaitingToAcceptCarsPagerBinding binding;
  WaitingToAcceptPresenter presenter;

  CarListAdapter adapter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = FragmentWaitingToAcceptCarsPagerBinding.inflate(inflater, container, false);

    implement();

    return binding.getRoot();
  }

  private void implement() {
    presenter = new WaitingToAcceptPresenter(getContext(), this);
    adapter = new CarListAdapter(getContext());

    adapter.setClickListener((model, position) -> {
      CarDetailsFragment fragment = new CarDetailsFragment();
      Bundle bundle = new Bundle();
      bundle.putInt("ID", model.getId());
      bundle.putString("STATE", WaitingToPublishFragment.TAG);
      fragment.setArguments(bundle);

      ((MainActivity) getActivity()).onAddFragment(fragment, R.anim.fadein, R.anim.fadeout, true, CarDetailsFragment.TAG);
    });

    startPresenter();
  }

  public void startPresenter() {
    presenter.start(((MainActivity)getActivity()).getTbl_user().getUserId(), 0);
    adapter.clear();
  }

  @Override
  public void onPresenterStart() {
    binding.waitingToAcceptList.setLayoutManager(new LinearLayoutManager(getContext()));
    binding.waitingToAcceptList.setAdapter(adapter);
  }

  @Override
  public void onItemReceived(CarModel item) {
    adapter.add(item);
  }

  @Override
  public void onError(String message) {

  }

  @Override
  public void onLoading(boolean state) {
    binding.mkLoader.setVisibility(state ? View.VISIBLE : View.GONE);
  }

  @Override
  public void onFinish() {

  }
}


