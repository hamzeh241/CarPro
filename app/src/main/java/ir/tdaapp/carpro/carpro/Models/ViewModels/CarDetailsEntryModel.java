package ir.tdaapp.carpro.carpro.Models.ViewModels;

public class CarDetailsEntryModel {

  int id;
  String title, titleShamsi, titleMiladi;

  public CarDetailsEntryModel(int id, String title) {
    this.id = id;
    this.title = title;
  }

  public CarDetailsEntryModel() {
  }

  public CarDetailsEntryModel(int id, String titleShamsi, String titleMiladi) {
    this.id = id;
    this.titleShamsi = titleShamsi;
    this.titleMiladi = titleMiladi;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    if (title == null) {
      return titleShamsi + " - " + titleMiladi;
    } else {
      return title;
    }
  }

  public String getTitleShamsi() {
    return titleShamsi;
  }

  public void setTitleShamsi(String titleShamsi) {
    this.titleShamsi = titleShamsi;
  }

  public String getTitleMiladi() {
    return titleMiladi;
  }

  public void setTitleMiladi(String titleMiladi) {
    this.titleMiladi = titleMiladi;
  }
}
