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
import ir.tdaapp.carpro.carpro.Models.Services.RejectedCarsService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarModel;
import ir.tdaapp.carpro.carpro.Presenters.RejectedCarsPresenter;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;
import ir.tdaapp.carpro.carpro.databinding.FragmentArchivedCarsViewPagerBinding;
import ir.tdaapp.li_volley.Enum.ResaultCode;

public class ArchivedFragment extends BaseFragment implements RejectedCarsService {

  public static final String TAG = "ArchivedFragment";

  private int previousTotal = 0;
  private int page = 0;
  private boolean isLoading = true;
  private final int visibleThreshold = 5;
  int firstVisibleItem, visibleItemCount, totalItemCount;

  FragmentArchivedCarsViewPagerBinding binding;
  RejectedCarsPresenter presenter;

  CarListAdapter adapter;
  LinearLayoutManager manager;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = FragmentArchivedCarsViewPagerBinding.inflate(inflater, container, false);

    implement();

    return binding.getRoot();
  }

  private void implement() {
    presenter = new RejectedCarsPresenter(getContext(), this);
    adapter = new CarListAdapter(getContext());
    manager = new LinearLayoutManager(getContext());

    adapter.setClickListener((model, position) -> {
      CarDetailsFragment fragment = new CarDetailsFragment();
      Bundle bundle = new Bundle();
      bundle.putInt("ID", model.getId());
      bundle.putString("STATE", ArchivedFragment.TAG);
      fragment.setArguments(bundle);

      ((MainActivity) getActivity()).onAddFragment(fragment, R.anim.fadein, R.anim.fadeout, true, CarDetailsFragment.TAG);
    });
    startPresenter();
  }

  public void startPresenter() {
    presenter.start(0);
    adapter.clear();
  }

  private void setPagination() {
    binding.archivedList.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = binding.archivedList.getChildCount();
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
            presenter.getItemsByPage(page);

            // Do something
            isLoading = true;
          }
        }
      }
    });
  }


  @Override
  public void onPresenterStart() {
    binding.archivedList.setLayoutManager(manager);
    binding.archivedList.setAdapter(adapter);
    setPagination();
  }

  @Override
  public void onItemReceived(CarModel item) {
    adapter.add(item);
  }

  @Override
  public void onError(ResaultCode code) {
    String error = "";
    String title = "";

    switch (code) {
      case TimeoutError:
        error = getString(R.string.timeout_error);
        title = getString(R.string.timeout_error_title);
        break;
      case NetworkError:
        error = getString(R.string.network_error);
        title = getString(R.string.network_error_title);
        break;
      case ServerError:
        error = getString(R.string.server_error);
        title = getString(R.string.server_error_title);
        break;
      case ParseError:
      case Error:
        title = getString(R.string.unknown_error_title);
        error = getString(R.string.unknown_error);
        break;
    }

    showErrorDialog(title, error, () ->
      presenter.start(0));
  }

  @Override
  public void onLoading(boolean state) {
    binding.mkLoader.setVisibility(state ? View.VISIBLE : View.GONE);
  }

  @Override
  public void onFinish() {

  }
}
