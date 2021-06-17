package ir.tdaapp.carpro.carpro.Views.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import ir.tdaapp.carpro.carpro.Models.Repository.Server.BaseRepository;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.databinding.DialogImageViewerBinding;

public class ImageViewerDialog extends DialogFragment {

  public static final String TAG = "ImageViewerDialog";

  DialogImageViewerBinding binding;

  String imageUrl;

  public ImageViewerDialog(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = DialogImageViewerBinding.inflate(inflater, container, false);
    binding.imgClose.setOnClickListener(v -> dismiss());
    String image = BaseRepository.API_IMAGE_CAR + imageUrl;

    Glide.with(getContext())
      .load(image)
      .placeholder(R.drawable.ic_baseline_sync_24)
      .placeholder(R.drawable.ic_baseline_running_with_errors_24)
      .into(binding.image);

    return binding.getRoot();
  }

  @Override
  public void onStart() {
    super.onStart();
    Dialog dialog = getDialog();
    if (dialog != null) {
      int width = ViewGroup.LayoutParams.MATCH_PARENT;
      int height = ViewGroup.LayoutParams.MATCH_PARENT;
      dialog.getWindow().setLayout(width, height);
      dialog.getWindow().setWindowAnimations(R.style.Theme_MyApplication_Slide);
    }
  }
}
