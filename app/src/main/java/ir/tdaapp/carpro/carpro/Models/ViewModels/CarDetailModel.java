package ir.tdaapp.carpro.carpro.Models.ViewModels;

import java.util.ArrayList;

public class CarDetailModel {

  int id, function, brandId, engineStatusId, chassisStatusId,
    carBodyStatusId, insuranceTimeId, gearBoxId, documentId, categoryId,
    howToSellId, productionYearId;
  double price;
  String title, phone, address, dateInsert,description;
  boolean exchange;
  ArrayList<CarDetailPhotoModel> photos;


  public CarDetailModel() {
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

  public int getBrandId() {
    return brandId;
  }

  public void setBrandId(int brandId) {
    this.brandId = brandId;
  }

  public int getEngineStatusId() {
    return engineStatusId;
  }

  public void setEngineStatusId(int engineStatusId) {
    this.engineStatusId = engineStatusId;
  }

  public int getChassisStatusId() {
    return chassisStatusId;
  }

  public void setChassisStatusId(int chassisStatusId) {
    this.chassisStatusId = chassisStatusId;
  }

  public int getCarBodyStatusId() {
    return carBodyStatusId;
  }

  public void setCarBodyStatusId(int carBodyStatusId) {
    this.carBodyStatusId = carBodyStatusId;
  }

  public int getInsuranceTimeId() {
    return insuranceTimeId;
  }

  public void setInsuranceTimeId(int insuranceTimeId) {
    this.insuranceTimeId = insuranceTimeId;
  }

  public int getGearBoxId() {
    return gearBoxId;
  }

  public void setGearBoxId(int gearBoxId) {
    this.gearBoxId = gearBoxId;
  }

  public int getDocumentId() {
    return documentId;
  }

  public void setDocumentId(int documentId) {
    this.documentId = documentId;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public int getHowToSellId() {
    return howToSellId;
  }

  public void setHowToSellId(int howToSellId) {
    this.howToSellId = howToSellId;
  }

  public int getProductionYearId() {
    return productionYearId;
  }

  public void setProductionYearId(int productionYearId) {
    this.productionYearId = productionYearId;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getDateInsert() {
    return dateInsert;
  }

  public void setDateInsert(String dateInsert) {
    this.dateInsert = dateInsert;
  }

  public boolean isExchange() {
    return exchange;
  }

  public void setExchange(boolean exchange) {
    this.exchange = exchange;
  }

  public ArrayList<CarDetailPhotoModel> getPhotos() {
    return photos;
  }

  public void setPhotos(ArrayList<CarDetailPhotoModel> photos) {
    this.photos = photos;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}