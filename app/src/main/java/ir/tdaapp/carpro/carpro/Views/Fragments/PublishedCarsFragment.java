package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.carpro.carpro.Models.Adapters.CarListAdapter;
import ir.tdaapp.carpro.carpro.Models.Services.AcceptedCarsService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarModel;
import ir.tdaapp.carpro.carpro.Presenters.AcceptedCarsPresenter;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;
import ir.tdaapp.carpro.carpro.databinding.FragmentPublishedCarsViewPagerBinding;

public class PublishedCarsFragment extends BaseFragment implements AcceptedCarsService {

  public static final String TAG = "PublishedCarsFragment";

  private int previousTotal = 0;
  private int page = 0;
  private boolean isLoading = true;
  private final int visibleThreshold = 5;
  int firstVisibleItem, visibleItemCount, totalItemCount;

  FragmentPublishedCarsViewPagerBinding binding;
  AcceptedCarsPresenter presenter;

  CarListAdapter adapter;
  LinearLayoutManager manager;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = FragmentPublishedCarsViewPagerBinding.inflate(inflater, container, false);

    implement();

    return binding.getRoot();
  }

  private void implement() {
    presenter = new AcceptedCarsPresenter(getContext(), this);
    adapter = new CarListAdapter(getContext());
    manager = new LinearLayoutManager(getContext());

    adapter.setClickListener((model, position) -> {
      CarDetailsFragment fragment = new CarDetailsFragment();
      Bundle bundle = new Bundle();
      bundle.putInt("ID", model.getId());
      bundle.putString("STATE", PublishedCarsFragment.TAG);
      fragment.setArguments(bundle);

      ((MainActivity) getActivity()).onAddFragment(fragment, R.anim.fadein, R.anim.fadeout, true, CarDetailsFragment.TAG);
    });

    startPresenter();
  }

  public void startPresenter() {
    presenter.start(((MainActivity) getActivity()).getTbl_user().getUserId(), 0);
    adapter.clear();
  }

  private void setPagination() {
    binding.publishedCarsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = binding.publishedCarsList.getChildCount();
        totalItemCount = manager.getItemCount();
        firstVisibleItem = manager.findFirstVisibleItemPosition();

        if (isLoading) {
          if (totalItemCount > previousTotal) {
            isLoading = false;
            previousTotal = totalItemCount;
          }
        }
        if (adapter.getItemCount() > 40) {
          if (!isLoading && (totalItemCount - visibleItemCount)
            <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached
            page++;
            presenter.getItemsByPage(((MainActivity) getActivity()).getTbl_user().getUserId(), page);

            // Do something
            isLoading = true;
          }
        }
      }
    });
  }

  @Override
  public void onPresenterStart() {
    binding.publishedCarsList.setLayoutManager(manager);
    binding.publishedCarsList.setAdapter(adapter);
    setPagination();
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



