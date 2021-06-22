package ir.tdaapp.carpro.carpro.Views.Dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.databinding.DialogErrorBinding;

public class ErrorDialog extends DialogFragment {

  public interface onRetryClick {
    void onRetry();
  }

  public static final String TAG = "ErrorDialog";

  DialogErrorBinding binding;

  String title, subtitle;
  int image;
  onRetryClick clickListener;

  public ErrorDialog(String title, String subtitle, int image, onRetryClick clickListener) {
    this.title = title;
    this.subtitle = subtitle;
    this.image = image;
    this.clickListener = clickListener;
  }

  public ErrorDialog(String title, String subtitle, onRetryClick clickListener) {
    this.title = title;
    this.subtitle = subtitle;
    this.clickListener = clickListener;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = DialogErrorBinding.inflate(inflater, container, false);

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

    binding.btnRetry.setOnClickListener(v -> {
      clickListener.onRetry();
      dismiss();
    });

    binding.imgDismiss.setOnClickListener(v -> dismiss());
  }
}
