package ir.tdaapp.carpro.carpro.Models.ViewModels;

public class CarDetailPhotoModel {

  String imageName;

  public CarDetailPhotoModel(String imageName) {
    this.imageName = imageName;
  }

  public CarDetailPhotoModel() {
  }

  public String getImageName() {
    return imageName;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }
}
