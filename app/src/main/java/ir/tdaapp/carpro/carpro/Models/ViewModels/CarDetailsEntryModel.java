package ir.tdaapp.carpro.carpro.Models.ViewModels;

import java.util.Objects;

import ir.tdaapp.li_utility.Codes.Replace;

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
    return Replace.Number_en_To_fa(title);
  }

  public void setTitle(String title) {
    this.title = Replace.Number_fn_To_en(title);
  }

  @Override
  public String toString() {
    if (title == null) {
      return Replace.Number_en_To_fa(titleShamsi) + " - " + Replace.Number_en_To_fa(titleMiladi);
    } else {
      return Replace.Number_en_To_fa(title);
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
    return Replace.Number_en_To_fa(titleShamsi);
  }

  public void setTitleShamsi(String titleShamsi) {
    this.titleShamsi = Replace.Number_fn_To_en(titleShamsi);
  }

  public String getTitleMiladi() {
    return Replace.Number_en_To_fa(titleMiladi);
  }

  public void setTitleMiladi(String titleMiladi) {
    this.titleMiladi = Replace.Number_fn_To_en(titleMiladi);
  }
}
