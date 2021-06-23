package ir.tdaapp.carpro.carpro.Views.Fragments;

import java.util.logging.Handler;

import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;
import ir.tdaapp.carpro.carpro.Models.Utilities.Error;
import ir.tdaapp.carpro.carpro.Views.Dialogs.ConfirmationDialog;
import ir.tdaapp.carpro.carpro.Views.Dialogs.ErrorDialog;

public class BaseFragment extends Fragment {

  void showErrorDialog(String title, String subtitle, ErrorDialog.onRetryClick clickListener) {
    ErrorDialog dialog = new ErrorDialog(title, subtitle, clickListener);
    dialog.show(getActivity().getSupportFragmentManager(), ErrorDialog.TAG);
  }

  void showErrorDialog(String title, String subtitle, @DrawableRes int imageRes, ErrorDialog.onRetryClick clickListener) {
    ErrorDialog dialog = new ErrorDialog(title, subtitle, imageRes, clickListener);
    dialog.show(getActivity().getSupportFragmentManager(), ErrorDialog.TAG);
  }

  void showConfirmationDialog(String title, String subtitle, ConfirmationDialog.onConfirmationClick clickListener) {
    ConfirmationDialog dialog = new ConfirmationDialog(title, subtitle, clickListener);
    dialog.show(getActivity().getSupportFragmentManager(), ConfirmationDialog.TAG);
  }

  void showConfirmationDialog(String title, String subtitle, @DrawableRes int imageRes, ConfirmationDialog.onConfirmationClick clickListener) {
    ConfirmationDialog dialog = new ConfirmationDialog(title, subtitle, imageRes, clickListener);
    dialog.show(getActivity().getSupportFragmentManager(), ConfirmationDialog.TAG);
  }
}
