package ir.tdaapp.carpro.carpro.Models.Repository.Database;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import ir.tdaapp.carpro.carpro.Models.Adapters.DBAdapter;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailsEntryModel;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;

public class Tbl_CarDetails {

  Context context;
  DBAdapter db;

  public Tbl_CarDetails(Context context) {
    this.context = context;
    db = ((MainActivity) context).getDbAdapter();
  }

  public List<CarDetailsEntryModel> getBrands() {
    List<CarDetailsEntryModel> results = new ArrayList<>();

    Cursor q = db.ExecuteQ(" select * from TblBrand where ParentId IS NULL");

    for (int i = 0; i < q.getCount(); i++) {

      try {
        results.add(new CarDetailsEntryModel(q.getInt(q.getColumnIndex("Id")),
          q.getString(q.getColumnIndex("Title"))));
      } catch (Exception e) {
        e.printStackTrace();
      }
      q.moveToNext();
    }
    q.close();
    return results;
  }

  public int getBrandByModel(int modelId) {
    int selectedBrandId = 0;
    Cursor q = db.ExecuteQ(" select * from TblBrand where Id=" + modelId);

    for (int i = 0; i < q.getCount(); i++) {

      try {
        selectedBrandId = q.getInt(q.getColumnIndex("ParentId"));
      } catch (Exception e) {
        e.printStackTrace();
      }
      q.moveToNext();
    }
    q.close();
    return selectedBrandId;
  }

  public List<CarDetailsEntryModel> getModels(int parentId) {
    List<CarDetailsEntryModel> results = new ArrayList<>();

    Cursor q = db.ExecuteQ(" select * from TblBrand where ParentId=" + parentId);

    for (int i = 0; i < q.getCount(); i++) {

      try {
        results.add(new CarDetailsEntryModel(q.getInt(q.getColumnIndex("Id")),
          q.getString(q.getColumnIndex("Title"))));
      } catch (Exception e) {
        e.printStackTrace();
      }
      q.moveToNext();
    }
    q.close();
    return results;
  }

  public List<CarDetailsEntryModel> getColors() {
    List<CarDetailsEntryModel> results = new ArrayList<>();

    Cursor q = db.ExecuteQ(" select * from TblColorCar");

    for (int i = 0; i < q.getCount(); i++) {

      try {
        results.add(new CarDetailsEntryModel(q.getInt(q.getColumnIndex("Id")),
          q.getString(q.getColumnIndex("Title"))));
      } catch (Exception e) {
        e.printStackTrace();
      }
      q.moveToNext();
    }
    q.close();
    return results;
  }

  public List<CarDetailsEntryModel> getEngineStatus() {
    List<CarDetailsEntryModel> results = new ArrayList<>();

    Cursor q = db.ExecuteQ(" Select * from TblEngineStatus");

    for (int i = 0; i < q.getCount(); i++) {

      try {
        results.add(new CarDetailsEntryModel(q.getInt(q.getColumnIndex("Id")),
          q.getString(q.getColumnIndex("Title"))));
      } catch (Exception e) {
        e.printStackTrace();
      }
      q.moveToNext();
    }
    q.close();
    return results;
  }

  public List<CarDetailsEntryModel> getChassisStatus() {
    List<CarDetailsEntryModel> results = new ArrayList<>();

    Cursor q = db.ExecuteQ(" Select * from TblChassisCondition");

    for (int i = 0; i < q.getCount(); i++) {

      try {
        results.add(new CarDetailsEntryModel(q.getInt(q.getColumnIndex("Id")),
          q.getString(q.getColumnIndex("Title"))));
      } catch (Exception e) {
        e.printStackTrace();
      }
      q.moveToNext();
    }
    q.close();
    return results;
  }

  public List<CarDetailsEntryModel> getBodyConditions() {
    List<CarDetailsEntryModel> results = new ArrayList<>();

    Cursor q = db.ExecuteQ(" Select * from TblBodyCondition");

    for (int i = 0; i < q.getCount(); i++) {

      try {
        results.add(new CarDetailsEntryModel(q.getInt(q.getColumnIndex("Id")),
          q.getString(q.getColumnIndex("Title"))));
      } catch (Exception e) {
        e.printStackTrace();
      }
      q.moveToNext();
    }
    q.close();
    return results;
  }

  public List<CarDetailsEntryModel> getInsuranceDeadlines() {
    List<CarDetailsEntryModel> results = new ArrayList<>();

    Cursor q = db.ExecuteQ(" Select * from TblInsuranceDeadline");

    for (int i = 0; i < q.getCount(); i++) {

      try {
        results.add(new CarDetailsEntryModel(q.getInt(q.getColumnIndex("Id")),
          q.getString(q.getColumnIndex("Title"))));
      } catch (Exception e) {
        e.printStackTrace();
      }
      q.moveToNext();
    }
    q.close();
    return results;
  }

  public List<CarDetailsEntryModel> getGearboxes() {
    List<CarDetailsEntryModel> results = new ArrayList<>();

    Cursor q = db.ExecuteQ(" Select * from TblGearbox");

    for (int i = 0; i < q.getCount(); i++) {

      try {
        results.add(new CarDetailsEntryModel(q.getInt(q.getColumnIndex("Id")),
          q.getString(q.getColumnIndex("Title"))));
      } catch (Exception e) {
        e.printStackTrace();
      }
      q.moveToNext();
    }
    q.close();
    return results;
  }

  public List<CarDetailsEntryModel> getDocuments() {
    List<CarDetailsEntryModel> results = new ArrayList<>();

    Cursor q = db.ExecuteQ(" Select * from TblDocument");

    for (int i = 0; i < q.getCount(); i++) {

      try {
        results.add(new CarDetailsEntryModel(q.getInt(q.getColumnIndex("Id")),
          q.getString(q.getColumnIndex("Title"))));
      } catch (Exception e) {
        e.printStackTrace();
      }
      q.moveToNext();
    }
    q.close();
    return results;
  }

  public List<CarDetailsEntryModel> getSellType() {
    List<CarDetailsEntryModel> results = new ArrayList<>();

    Cursor q = db.ExecuteQ(" Select * from TblHowToSell");

    for (int i = 0; i < q.getCount(); i++) {

      try {
        results.add(new CarDetailsEntryModel(q.getInt(q.getColumnIndex("Id")),
          q.getString(q.getColumnIndex("Title"))));
      } catch (Exception e) {
        e.printStackTrace();
      }
      q.moveToNext();
    }
    q.close();
    return results;
  }

  public List<CarDetailsEntryModel> getConstructionYears() {
    List<CarDetailsEntryModel> results = new ArrayList<>();

    Cursor q = db.ExecuteQ(" Select * from TblYearOfConstruction");

    for (int i = 0; i < q.getCount(); i++) {

      try {
        results.add(new CarDetailsEntryModel(q.getInt(q.getColumnIndex("Id")),
          q.getString(q.getColumnIndex("TitleShamsi")), q.getString(q.getColumnIndex("TitleMiladi"))));
      } catch (Exception e) {
        e.printStackTrace();
      }
      q.moveToNext();
    }
    q.close();
    return results;
  }
}
