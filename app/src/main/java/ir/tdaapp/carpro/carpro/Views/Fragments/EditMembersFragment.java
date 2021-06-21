package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;
import ir.tdaapp.carpro.carpro.Models.Adapters.MemberBrandsAdapter;
import ir.tdaapp.carpro.carpro.Models.Services.MemberDetailsService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailsEntryModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.UserModel;
import ir.tdaapp.carpro.carpro.Presenters.MemberDetailsPresenter;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;
import ir.tdaapp.carpro.carpro.databinding.FragmentEditMemberBinding;

public class EditMembersFragment extends BaseFragment implements MemberDetailsService, View.OnClickListener {

  public static final String TAG = "EditMembersFragment";

  FragmentEditMemberBinding binding;
  MemberDetailsPresenter presenter;

  MemberBrandsAdapter adapter;
  GridLayoutManager gridManager;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = FragmentEditMemberBinding.inflate(inflater, container, false);

    implement();

    return binding.getRoot();
  }

  private void implement() {
    presenter = new MemberDetailsPresenter(getContext(), this);
    adapter = new MemberBrandsAdapter(getContext());
    gridManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);

    presenter.start(getArguments().getInt("ID"));

    binding.btnAdd.setOnClickListener(this);
    binding.btnSave.setOnClickListener(this);
  }

  @Override
  public void onLoading(boolean state) {
    binding.mkLoader.setVisibility(state ? View.VISIBLE : View.GONE);
  }

  @Override
  public void onPresenterStart() {
    binding.brandList.setLayoutManager(gridManager);
    binding.brandList.setAdapter(adapter);
  }

  @Override
  public void onError(String message) {

  }

  @Override
  public void onFinish() {

  }

  @Override
  public void onDetailsReceived(UserModel model) {
    binding.txtFullName.setText(model.getName());
    binding.txtPhone.setText(model.getCellPhone());
    binding.txtDateInsert.setText(model.getDateInsert());
    binding.txtLastSeen.setText(model.getLastOnline());
    binding.txtState.setText(model.isEnabled() ? "فعال" : "غیرفعال");
    binding.txtState.setBackgroundResource(model.isEnabled() ? R.drawable.enabled_background : R.drawable.disabled_background);
  }

  @Override
  public void onBrandReceived(CarDetailsEntryModel model) {
    adapter.add(model);
  }

  @Override
  public void onBrandsDBReceived(List<CarDetailsEntryModel> models) {
    ArrayAdapter<CarDetailsEntryModel> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, models);
    new AlertDialog.Builder(getContext())
      .setAdapter(arrayAdapter, (DialogInterface.OnClickListener) (dialog, which) -> {
        CarDetailsEntryModel selectedItem = (CarDetailsEntryModel) arrayAdapter.getItem(which);
        if (!adapter.getModels().contains(selectedItem))
          adapter.add(selectedItem);
      }).show();
  }

  @Override
  public void onBrandsEdited(String message, boolean result) {
    if (result)
      Toasty.success(getContext(), message).show();
    else Toasty.error(getContext(), message).show();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnAdd:
        presenter.getBrandsFromDatabase();
        break;

      case R.id.btnSave:
        presenter.editBrands(getArguments().getInt("ID"), adapter.getModels());
        break;
    }
  }
}
