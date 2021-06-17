package ir.tdaapp.carpro.carpro.Models.ViewModels;

import java.util.ArrayList;

public class UserModel {

  int id;
  String name,cellPhone,email,imageUrl;
  boolean enabled;

  ArrayList<String> images;

  public UserModel() {
  }

  public UserModel(int id, String name, String cellPhone, String email, String imageUrl, boolean enabled, ArrayList<String> images) {
    this.id = id;
    this.name = name;
    this.cellPhone = cellPhone;
    this.email = email;
    this.imageUrl = imageUrl;
    this.enabled = enabled;
    this.images = new ArrayList<>();
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCellPhone() {
    return cellPhone;
  }

  public void setCellPhone(String cellPhone) {
    this.cellPhone = cellPhone;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
