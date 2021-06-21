package ir.tdaapp.carpro.carpro.Models.ViewModels;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CarDetailsEntryModel model = (CarDetailsEntryModel) o;
    return id == model.id &&
      title.equals(model.title) &&
      Objects.equals(titleShamsi, model.titleShamsi) &&
      Objects.equals(titleMiladi, model.titleMiladi);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, titleShamsi, titleMiladi);
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
