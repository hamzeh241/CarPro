package ir.tdaapp.carpro.carpro.Models.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.carpro.carpro.Models.Repository.BaseRepository;
import ir.tdaapp.carpro.carpro.Models.Services.onCarListClickListener;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarListModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarModel;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.databinding.RecyclerCarListBinding;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder> {

  Context context;
  ArrayList<CarModel> models;
  onCarListClickListener clickListener;

  RecyclerCarListBinding binding;

  public CarListAdapter(Context context) {
    this.context = context;
    models = new ArrayList<>();
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    binding = RecyclerCarListBinding.inflate(LayoutInflater.from(context), parent, false);

    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    CarModel model = models.get(position);
    holder.binding.txtCarListName.setText(model.getTitle());
    holder.binding.txtCarListPrice.setText(model.getPrice());
    holder.binding.txtCarListMileage.setText(model.getFunction());
    holder.binding.txtCarListColor.setText(model.getColor());
    holder.binding.txtCarListProductionYear.setText(new StringBuilder().append(model.getMiladiDate()).append(" - ").append(model.getShamsiDate()).toString());
    Glide.with(context)
      .load(BaseRepository.API_IMAGE_CAR + model.getImageName())
      .placeholder(R.drawable.ic_baseline_sync_24)
      .error(R.drawable.ic_baseline_running_with_errors_24)
      .into(holder.binding.imgCarList);

    holder.binding.carListRoot.setOnClickListener(v -> {
      clickListener.onClick(model, position);
    });
  }

  public void add(CarModel model) {
    models.add(model);
    notifyItemInserted(models.size());
  }

  public void clear() {
    models.clear();
    notifyDataSetChanged();
  }


  public void setClickListener(onCarListClickListener clickListener) {
    this.clickListener = clickListener;
  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    RecyclerCarListBinding binding;

    public ViewHolder(@NonNull RecyclerCarListBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }
}
