package ir.tdaapp.carpro.carpro.Views.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.databinding.DialogConfirmationBinding;
import ir.tdaapp.carpro.carpro.databinding.DialogErrorBinding;

public class ConfirmationDialog extends DialogFragment {

  public interface onConfirmationClick {
    void onPressedYes();

    void onPressedNo();
  }

  public static final String TAG = "ConfirmationDialog";

  DialogConfirmationBinding binding;

  String title, subtitle;
  int image;
  onConfirmationClick clickListener;

  public ConfirmationDialog(String title, String subtitle, int image, onConfirmationClick clickListener) {
    this.title = title;
    this.subtitle = subtitle;
    this.image = image;
    this.clickListener = clickListener;
  }

  public ConfirmationDialog(String title, String subtitle, onConfirmationClick clickListener) {
    this.title = title;
    this.subtitle = subtitle;
    this.clickListener = clickListener;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = DialogConfirmationBinding.inflate(inflater, container, false);

    setCancelable(false);
    implement();

    return binding.getRoot();
  }

  @Override
  public int getTheme() {
    return R.style.RoundedCornersDialog;
  }

  private void implement() {
    if (!title.isEmpty())
      binding.txtErrorTitle.setText(title);

    if (!subtitle.isEmpty())
      binding.txtSubtitle.setText(subtitle);

    if (image != 0)
      Glide.with(getContext())
        .load(image)
        .into(binding.imageView3);

    binding.btnYes.setOnClickListener(v -> {
      clickListener.onPressedYes();
      dismiss();
    });
    binding.btnNo.setOnClickListener(v -> {
      clickListener.onPressedNo();
      dismiss();
    });


    binding.imgDismiss.setOnClickListener(v -> dismiss());
  }
}
