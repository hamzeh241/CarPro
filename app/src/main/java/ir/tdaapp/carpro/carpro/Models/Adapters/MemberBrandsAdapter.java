package ir.tdaapp.carpro.carpro.Models.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.carpro.carpro.Models.Services.onBrandClick;
import ir.tdaapp.carpro.carpro.Models.Services.onCarListClickListener;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailsEntryModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarModel;
import ir.tdaapp.carpro.carpro.databinding.RowMemberBrandsBinding;

public class MemberBrandsAdapter extends RecyclerView.Adapter<MemberBrandsAdapter.ViewHolder> {

  RowMemberBrandsBinding binding;

  Context context;
  ArrayList<CarDetailsEntryModel> models;
  onBrandClick clickListener;

  public MemberBrandsAdapter(Context context) {
    this.context = context;
    models = new ArrayList<>();
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    binding = RowMemberBrandsBinding.inflate(LayoutInflater.from(context), parent, false);

    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.binding.txtTitle.setText(models.get(position).getTitle());
    holder.binding.btnRemove.setOnClickListener(v -> remove(position));
  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  public void add(CarDetailsEntryModel model) {
    models.add(model);
    notifyItemInserted(models.size());
  }

  public void clear() {
    models.clear();
    notifyDataSetChanged();
  }

  public ArrayList<CarDetailsEntryModel> getModels(){
    return models;
  }

  public void remove(int position) {
    models.remove(position);
    notifyItemRemoved(position);
    notifyItemRangeChanged(position, models.size());
  }

  public void setClickListener(onBrandClick clickListener) {
    this.clickListener = clickListener;
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    RowMemberBrandsBinding binding;

    public ViewHolder(@NonNull RowMemberBrandsBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }
}
