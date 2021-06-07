package ir.tdaapp.carpro.carpro.Models.ViewModels;

public class CarModel {

  int id, function;
  String title, price, imageName, shamsiDate, miladiDate, color;

  public CarModel(int id, int function, String title, String price, String imageName, String shamsiDate, String miladiDate) {
    this.id = id;
    this.function = function;
    this.title = title;
    this.price = price;
    this.imageName = imageName;
    this.shamsiDate = shamsiDate;
    this.miladiDate = miladiDate;
  }

  public CarModel() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getFunction() {
    return function;
  }

  public void setFunction(int function) {
    this.function = function;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getImageName() {
    return imageName;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  public String getShamsiDate() {
    return shamsiDate;
  }

  public void setShamsiDate(String shamsiDate) {
    this.shamsiDate = shamsiDate;
  }

  public String getMiladiDate() {
    return miladiDate;
  }

  public void setMiladiDate(String miladiDate) {
    this.miladiDate = miladiDate;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }
}
