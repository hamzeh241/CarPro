package ir.tdaapp.carpro.carpro.Models.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.carpro.carpro.Models.Services.onUserModelClickListener;
import ir.tdaapp.carpro.carpro.Models.ViewModels.AddItemPhotoModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.UserModel;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.databinding.RecyclerUserCarproBinding;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.ViewHolder> {

  Context context;
  ArrayList<UserModel> models;
  ArrayList<AddItemPhotoModel> photoModels;
  RecyclerUserCarproBinding binding;

  onUserModelClickListener clickListener;

  public MembersAdapter(Context context) {
    this.context = context;
    models = new ArrayList<>();
    photoModels = new ArrayList<>();
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    binding = RecyclerUserCarproBinding.inflate(LayoutInflater.from(context), parent, false);

    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    UserModel model = models.get(position);

    holder.binding.txtName.setText(model.getName());
    holder.binding.txtPhone.setText(model.getCellPhone());
    holder.binding.btnChangeStatus.setCardBackgroundColor(model.isEnabled() ?
      context.getResources().getColor(R.color.colorBlue) : context.getResources().getColor(R.color.colorRed));
    holder.binding.txtStatus.setText(model.isEnabled() ?
      context.getResources().getString(R.string.active) : context.getResources().getString(R.string.deactive));
    holder.binding.btnChangeStatus.setOnClickListener(v -> clickListener.onChangeState(model));
    holder.binding.getRoot().setOnClickListener(v -> clickListener.onClick(model));
  }

  public void add(UserModel model) {
    models.add(model);
    notifyItemInserted(models.size());
  }

  public void add(AddItemPhotoModel model) {
    photoModels.add(model);
    notifyItemInserted(models.size());
  }

  public void clear() {
    models.clear();
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  public void setClickListener(onUserModelClickListener clickListener) {
    this.clickListener = clickListener;
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    RecyclerUserCarproBinding binding;

    public ViewHolder(@NonNull RecyclerUserCarproBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }
}
