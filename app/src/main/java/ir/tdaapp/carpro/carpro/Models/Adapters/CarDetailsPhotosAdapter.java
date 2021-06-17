package ir.tdaapp.carpro.carpro.Models.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.carpro.carpro.Models.Repository.Server.BaseRepository;
import ir.tdaapp.carpro.carpro.Models.Services.onCarPhotosListener;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailPhotoModel;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.databinding.RowCarDetailsPhotoBinding;

public class CarDetailsPhotosAdapter extends RecyclerView.Adapter<CarDetailsPhotosAdapter.ViewHolder> {

  Context context;
  RowCarDetailsPhotoBinding binding;
  ArrayList<CarDetailPhotoModel> models;

  onCarPhotosListener listener;

  public CarDetailsPhotosAdapter(Context context, ArrayList<CarDetailPhotoModel> models) {
    this.context = context;
    this.models = models;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    binding = RowCarDetailsPhotoBinding.inflate(LayoutInflater.from(context), parent, false);

    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    CarDetailPhotoModel item = models.get(position);
    String imageUrl = BaseRepository.API_IMAGE_CAR + item.getImageName().replace("\"", "");
    Glide.with(context)
      .load(imageUrl)
      .placeholder(R.drawable.ic_baseline_sync_24)
      .error(R.drawable.ic_baseline_running_with_errors_24)
      .into(holder.binding.image);

    holder.binding.image.setOnClickListener(v -> listener.click(models.get(position), position));
    holder.binding.imgRemove.setOnClickListener(v -> listener.remove(models.get(position), position));
  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  public void add(CarDetailPhotoModel model) {
    models.add(model);
    notifyItemInserted(models.size());
    notifyDataSetChanged();
  }

  public void remove(int position) {
    models.remove(position);
    notifyItemRemoved(position);
    notifyItemRangeChanged(position, models.size());
  }

  public void setListener(onCarPhotosListener listener) {
    this.listener = listener;
  }

  public ArrayList<CarDetailPhotoModel> getModels() {
    return models;
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    RowCarDetailsPhotoBinding binding;

    public ViewHolder(@NonNull RowCarDetailsPhotoBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }
}
