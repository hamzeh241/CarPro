package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;
import ir.tdaapp.carpro.carpro.Models.Adapters.CarDetailsPhotosAdapter;
import ir.tdaapp.carpro.carpro.Models.Services.CarDetailsService;
import ir.tdaapp.carpro.carpro.Models.Services.onCarPhotosListener;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarChipsListModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailPhotoModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailsEntryModel;
import ir.tdaapp.carpro.carpro.Presenters.CarDetailsPresenter;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;
import ir.tdaapp.carpro.carpro.Views.Dialogs.ImageViewerDialog;
import ir.tdaapp.carpro.carpro.databinding.FragmentCarDetailsBinding;
import ir.tdaapp.li_utility.Codes.Validation;

public class CarDetailsFragment extends BaseFragment implements CarDetailsService, View.OnClickListener {

  public static final String TAG = "CarDetailsFragment";

  FragmentCarDetailsBinding binding;
  CarDetailsPresenter presenter;

  CarDetailModel item;
  CarDetailsPhotosAdapter adapter;

  Handler handler;
  ArrayList<CarDetailPhotoModel> photos;

  int modelId;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = FragmentCarDetailsBinding.inflate(inflater, container, false);

    implement();
    setListeners();

    return binding.getRoot();
  }

  private void implement() {
    presenter = new CarDetailsPresenter(getContext(), this);
    handler = new Handler();

    presenter.start(getArguments().getInt("ID"));
  }

  private void setListeners() {
    binding.includeContentAddCarPhotos.btnAddCarInsertPhoto.setOnClickListener(this);
    binding.btnAccept.setOnClickListener(this);
    binding.btnEdit.setOnClickListener(this);
    binding.btnReject.setOnClickListener(this);
  }

  private void selectedItemById(Spinner spinner, int brandId) {
    ArrayAdapter<CarDetailsEntryModel> adapter = (ArrayAdapter<CarDetailsEntryModel>) spinner.getAdapter();
    for (int i = 0; i < adapter.getCount(); i++) {
      if (((CarDetailsEntryModel) spinner.getItemAtPosition(i)).getId() == brandId) {
        spinner.setSelection(i);
        return;
      }
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnAddCarInsertPhoto:
        presenter.requestStoragePermission(getActivity());
        break;
      case R.id.btnReject:
        if (checkValidation())
          presenter.rejectCar(2086, item.getId());
        break;
      case R.id.btnAccept:
        if (checkValidation())
          presenter.confirmCar(2086, item.getId());
        break;
      case R.id.btnEdit:
        if (checkValidation()) {
          CarDetailModel model = new CarDetailModel();
          model.setId(item.getId());
          model.setTitle(binding.includeContentAddCar.edtTitle.getText().toString());
          model.setPrice(Double.parseDouble(binding.includeContentAddCar.edtPrice.getText().toString()));
          model.setFunction(Integer.parseInt(binding.includeContentAddCar.edtMileage.getText().toString()));
          model.setExchange(binding.includeContentAddCarMoreInfo.checkboxExchange.isChecked());
          model.setPhone(binding.includeContentAddCarContactInfo.edtPhone.getText().toString());
          model.setAddress(binding.includeContentAddCarContactInfo.edtAddress.getText().toString());
          model.setDescription(binding.includeContentAddCarDescription.edtDescription.getText().toString());
          model.setProductionYearId(((CarDetailsEntryModel) binding.
            includeContentAddCar.spinnerFromProductionYear.getSelectedItem()).getId());
          model.setCategoryId(((CarChipsListModel) binding.
            includeContentAddCar.spinnerCategory.getSelectedItem()).getId());
          model.setColorId(((CarDetailsEntryModel) binding.
            includeContentAddCarMoreInfo.spinnerColor.getSelectedItem()).getId());
          model.setBrandId(((CarDetailsEntryModel) binding.
            includeContentAddCarMoreInfo.spinnerModel.getSelectedItem()).getId());
          model.setEngineStatusId(((CarDetailsEntryModel) binding.
            includeContentAddCarMoreInfo.spinnerEngineStatus.getSelectedItem()).getId());
          model.setHowToSellId(((CarDetailsEntryModel) binding.
            includeContentAddCarMoreInfo.spinnerSellType.getSelectedItem()).getId());
          model.setDocumentId(((CarDetailsEntryModel) binding.
            includeContentAddCarMoreInfo.spinnerDocuments.getSelectedItem()).getId());
          model.setGearBoxId(((CarDetailsEntryModel) binding.
            includeContentAddCarMoreInfo.spinnerGearbox.getSelectedItem()).getId());
          model.setInsuranceTimeId(((CarDetailsEntryModel) binding.
            includeContentAddCarMoreInfo.spinnerInsurance.getSelectedItem()).getId());
          model.setCarBodyStatusId(((CarDetailsEntryModel) binding.
            includeContentAddCarMoreInfo.spinnerBodyStatus.getSelectedItem()).getId());
          model.setChassisStatusId(((CarDetailsEntryModel) binding.
            includeContentAddCarMoreInfo.spinnerChassisStatus.getSelectedItem()).getId());
          model.setPhotos(photos);

          presenter.editCar(model);
        }
        break;
    }
  }

  @Override
  public void onPresenterStart() {

  }

  @Override
  public void onItemReceived(CarDetailModel item) {
    this.item = item;
    modelId = item.getBrandId();
    binding.includeContentAddCar.edtTitle.setText(item.getTitle());
    binding.includeContentAddCar.edtMileage.setText(new StringBuilder().append("").append(item.getFunction()).toString());
    binding.includeContentAddCar.edtPrice.setText(new StringBuilder().append("").append(item.getPrice()).toString());
    binding.includeContentAddCarDescription.edtDescription.setText(item.getDescription());
    binding.includeContentAddCarContactInfo.edtAddress.setText(item.getAddress());
    binding.includeContentAddCarContactInfo.edtPhone.setText(item.getPhone());
    binding.includeContentAddCarMoreInfo.checkboxExchange.setChecked(item.isExchange());

    photos = item.getPhotos();
    adapter = new CarDetailsPhotosAdapter(getContext(), photos);
    adapter.setListener(new onCarPhotosListener() {
      @Override
      public void remove(CarDetailPhotoModel model, int position) {
        adapter.remove(position);
      }

      @Override
      public void click(CarDetailPhotoModel model, int position) {
        ImageViewerDialog dialog = new ImageViewerDialog(model.getImageName());
        dialog.show(getActivity().getSupportFragmentManager(), ImageViewerDialog.TAG);
      }
    });
    setRecyclerView();
  }

  private void setRecyclerView() {
    LinearLayoutManager manager=new LinearLayoutManager(getContext());
    manager.setOrientation(RecyclerView.HORIZONTAL);
    binding.includeContentAddCarPhotos.addCarPhotoList.setLayoutManager(manager);
    binding.includeContentAddCarPhotos.addCarPhotoList.setAdapter(adapter);
  }

  @Override
  public void onConfirmCarSuccessful(boolean result, String message) {
    if (result) {
      Toasty.success(getContext(), message).show();
      getActivity().onBackPressed();
    } else Toasty.error(getContext(), message).show();
  }

  @Override
  public void onRejectCarSuccessful(boolean result, String message) {
    if (result) {
      Toasty.success(getContext(), message).show();
      getActivity().onBackPressed();
    } else Toasty.error(getContext(), message).show();
  }

  @Override
  public void onEditCarSuccessful(boolean result, String message) {
    if (result) Toasty.success(getContext(), message).show();
    else Toasty.error(getContext(), message).show();
  }

  @Override
  public void onError(String message) {

  }

  @Override
  public void onLoading(boolean state) {
//    TransitionManager.beginDelayedTransition(binding.getRoot());
    binding.addCarLoading.setVisibility(state ? View.VISIBLE : View.GONE);
  }

  @Override
  public void onCategoriesReceived(List<CarChipsListModel> model) {
    ArrayAdapter<CarChipsListModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    binding.includeContentAddCar.spinnerCategory.setAdapter(adapter);
    binding.includeContentAddCar.spinnerCategory.setSelection(item.getCategoryId() - 1);
  }

  @Override
  public void onProductionYearsReceived(List<CarDetailsEntryModel> model) {
    ArrayAdapter<CarDetailsEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    binding.includeContentAddCar.spinnerFromProductionYear.setAdapter(adapter);
    binding.includeContentAddCar.spinnerFromProductionYear.setSelection(item.getProductionYearId() - 1);
  }

  @Override
  public void onBrandsReceived(List<CarDetailsEntryModel> model, int selected) {
    ArrayAdapter<CarDetailsEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    binding.includeContentAddCarMoreInfo.spinnerBrand.setAdapter(adapter);
    selectedItemById(binding.includeContentAddCarMoreInfo
      .spinnerBrand, selected);
    binding.includeContentAddCarMoreInfo.spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        presenter.getModels(((CarDetailsEntryModel) binding.includeContentAddCarMoreInfo
          .spinnerBrand.getSelectedItem()).getId());
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
  }

  @Override
  public void onModelsReceived(List<CarDetailsEntryModel> model) {
    ArrayAdapter<CarDetailsEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    binding.includeContentAddCarMoreInfo
      .spinnerModel
      .setAdapter(adapter);
    selectedItemById(binding.includeContentAddCarMoreInfo
      .spinnerModel, item.getBrandId());
  }

  @Override
  public void onEngineStatusReceived(List<CarDetailsEntryModel> model) {
    ArrayAdapter<CarDetailsEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    binding.includeContentAddCarMoreInfo
      .spinnerEngineStatus
      .setAdapter(adapter);
    binding.includeContentAddCarMoreInfo.spinnerEngineStatus.setSelection(item.getEngineStatusId() - 1);
  }

  @Override
  public void onChassisStatusReceived(List<CarDetailsEntryModel> model) {
    ArrayAdapter<CarDetailsEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    binding.includeContentAddCarMoreInfo
      .spinnerChassisStatus
      .setAdapter(adapter);
    binding.includeContentAddCarMoreInfo.spinnerChassisStatus.setSelection(item.getChassisStatusId() - 1);
  }

  @Override
  public void onBodyStatusReceived(List<CarDetailsEntryModel> model) {
    ArrayAdapter<CarDetailsEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    binding.includeContentAddCarMoreInfo
      .spinnerBodyStatus
      .setAdapter(adapter);
    binding.includeContentAddCarMoreInfo.spinnerBodyStatus.setSelection(item.getCarBodyStatusId() - 1);
  }

  @Override
  public void onInsuranceDeadlineReceived(List<CarDetailsEntryModel> model) {
    ArrayAdapter<CarDetailsEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    binding.includeContentAddCarMoreInfo
      .spinnerInsurance
      .setAdapter(adapter);
    binding.includeContentAddCarMoreInfo.spinnerInsurance.setSelection(item.getInsuranceTimeId() - 1);
  }

  @Override
  public void onGearBoxReceived(List<CarDetailsEntryModel> model) {
    ArrayAdapter<CarDetailsEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    binding.includeContentAddCarMoreInfo
      .spinnerGearbox
      .setAdapter(adapter);
    binding.includeContentAddCarMoreInfo.spinnerGearbox.setSelection(item.getGearBoxId() - 1);
  }

  @Override
  public void onDocumentReceived(List<CarDetailsEntryModel> model) {
    ArrayAdapter<CarDetailsEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    binding.includeContentAddCarMoreInfo
      .spinnerDocuments
      .setAdapter(adapter);
    binding.includeContentAddCarMoreInfo.spinnerDocuments.setSelection(item.getDocumentId() - 1);
  }

  @Override
  public void onSellTypeReceived(List<CarDetailsEntryModel> model) {
    ArrayAdapter<CarDetailsEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    binding.includeContentAddCarMoreInfo
      .spinnerSellType
      .setAdapter(adapter);
    binding.includeContentAddCarMoreInfo.spinnerSellType.setSelection(item.getHowToSellId() - 1);
  }

  @Override
  public void onColorsReceived(List<CarDetailsEntryModel> model) {
    ArrayAdapter<CarDetailsEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    binding.includeContentAddCarMoreInfo
      .spinnerColor
      .setAdapter(adapter);
    binding.includeContentAddCarMoreInfo.spinnerColor.setSelection(item.getColorId() - 1);
  }

  @Override
  public void onImagesUploaded(ArrayList<String> arrayList, List<Uri> uris) {
    photos = adapter.getModels();
    for (String s : arrayList) {
//      photos.add(new CarDetailPhotoModel(s));
      adapter.add(new CarDetailPhotoModel(s));
    }
  }

  @Override
  public void onImageUploading(boolean loading) {
    handler.post(() -> {
      binding.includeContentAddCarPhotos.mkLoader.setVisibility(loading ? View.VISIBLE : View.GONE);
      binding.includeContentAddCarPhotos.addCarPhotoList.setVisibility(loading ? View.GONE : View.VISIBLE);
    });
  }

  @Override
  public void onStoragePermissionGranted() {
    presenter.openImageSelector(getActivity());
  }

  @Override
  public void onStoragePermissionDenied() {
    Toasty.error(getContext(), R.string.enable_permission).show();
  }

  private boolean checkValidation() {
    boolean a = Validation.Required(binding.includeContentAddCar.edtTitle, "عنوان باید پر شود");
    boolean b = Validation.Required(binding.includeContentAddCar.edtMileage, "کارکرد باید پر شود");
    boolean c = Validation.Required(binding.includeContentAddCarDescription.edtDescription, "توضیحات باید پر شود");
    boolean d = Validation.Required(binding.includeContentAddCarContactInfo.edtPhone, "شماره تلفن باید پر شود");
    boolean e = Validation.Required(binding.includeContentAddCar.edtPrice, "قیمت باید پر شود");
    boolean f = Validation.Required(binding.includeContentAddCarContactInfo.edtAddress, "آدرس باید پر شود");
    boolean g = checkImagesUploaded();

    return a && b && c && d && e && f && g;
  }

  private boolean checkImagesUploaded() {
    if (photos.size() > 0) {
      return true;
    } else {
      Toasty.error(getContext(), getContext().getString(R.string.noPhotoSelected)).show();
      return false;
    }
  }

  @Override
  public void onDestroy() {
    try {
      List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
      for (Fragment fragment : fragments) {
        if (fragment instanceof PublishedCarsFragment)
          ((PublishedCarsFragment) fragment).startPresenter();
        else if (fragment instanceof WaitingToPublishFragment)
          ((WaitingToPublishFragment) fragment).startPresenter();
        else if (fragment instanceof ArchivedFragment)
          ((ArchivedFragment) fragment).startPresenter();
      }
    } catch (Exception e) {

    }
    super.onDestroy();
  }
}
