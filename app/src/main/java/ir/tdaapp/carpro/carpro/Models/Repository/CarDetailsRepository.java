package ir.tdaapp.carpro.carpro.Models.Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailPhotoModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarModel;
import ir.tdaapp.li_volley.Enum.ResaultCode;
import ir.tdaapp.li_volley.Volleys.GetJsonArrayVolley;
import ir.tdaapp.li_volley.Volleys.GetJsonObjectVolley;
import ir.tdaapp.li_volley.Volleys.PutJsonObjectVolley;

public class CarDetailsRepository extends BaseRepository {

  GetJsonObjectVolley volley;
  PutJsonObjectVolley confirmVolley, rejectVolley,editVolley;

  public Single<CarDetailModel> getItems(int id) {
    return Single.create(emitter -> new Thread(() -> {

      volley = new GetJsonObjectVolley(API_URL + "api/CarPro/GetDetailCar/" + id, resault -> {

        JSONObject object = resault.getObject();
        CarDetailModel model = new CarDetailModel();

        if (resault.getResault() == ResaultCode.Success) {
          try {
            JSONArray array = object.getJSONArray("Images");
            ArrayList<CarDetailPhotoModel> photoModels = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
              CarDetailPhotoModel photoModel = new CarDetailPhotoModel();
              photoModel.setImageName(array.getString(i));

              photoModels.add(photoModel);
            }

            model.setId(object.getInt("Id"));
            model.setFunction(object.getInt("Function"));
            model.setPrice(object.getDouble("Price"));
            model.setBrandId(object.getInt("BrandId"));
            model.setEngineStatusId(object.getInt("EngineStatusId"));
            model.setChassisStatusId(object.getInt("ChassisConditionId"));
            model.setCarBodyStatusId(object.getInt("BodyConditionId"));
            model.setInsuranceTimeId(object.getInt("InsuranceDeadlineId"));
            model.setGearBoxId(object.getInt("GearboxId"));
            model.setDocumentId(object.getInt("DocumentId"));
            model.setCategoryId(object.getInt("CategoryId"));
            model.setHowToSellId(object.getInt("HowToSellIdId"));
            model.setProductionYearId(object.getInt("YearOfConstructionId"));
            model.setExchange(object.getBoolean("Exchange"));
            model.setTitle(object.getString("Title"));
            model.setAddress(object.getString("Address"));
            model.setPhone(object.getString("Phone"));
            model.setColorId(object.getInt("ColorId"));
            model.setDateInsert(object.getString("DateInsert"));
            model.setDescription(object.getString("Description"));


          } catch (JSONException e) {
            e.printStackTrace();
            emitter.onError(e);
          }
          emitter.onSuccess(model);
        } else {
          emitter.onError(new IOException(resault.getResault().toString()));
        }

      });

    }).start());
  }

  public Single<ApiDefaultResponse> confirmCar(JSONObject object) {
    return Single.create(emitter -> new Thread(() -> {

      confirmVolley = new PutJsonObjectVolley(API_URL + "api/CarPro/ConfirmCar", object, resault -> {

        if (resault.getResault() == ResaultCode.Success) {
          ApiDefaultResponse response = new ApiDefaultResponse();
          try {
            JSONArray messages = resault.getObject().getJSONArray("Messages");
            ArrayList<String> messageList = new ArrayList<>();
            for (int i = 0; i < messages.length(); i++) {
              messageList.add(messages.getString(i));
            }

            response.setMessages(messageList);
            response.setCode(resault.getObject().getInt("Code"));
            response.setResult(resault.getObject().getBoolean("Result"));

            emitter.onSuccess(response);
          } catch (JSONException e) {
            e.printStackTrace();
          }
        } else {
          emitter.onError(new IOException(resault.getResault().toString()));
        }

      });

    }).start());
  }

  public Single<ApiDefaultResponse> rejectCar(JSONObject object) {
    return Single.create(emitter -> new Thread(() -> {

      rejectVolley = new PutJsonObjectVolley(API_URL + "api/CarPro/RejectCar", object, resault -> {

        if (resault.getResault() == ResaultCode.Success) {
          ApiDefaultResponse response = new ApiDefaultResponse();
          try {
            JSONArray messages = resault.getObject().getJSONArray("Messages");
            ArrayList<String> messageList = new ArrayList<>();
            for (int i = 0; i < messages.length(); i++) {
              messageList.add(messages.getString(i));
            }

            response.setMessages(messageList);
            response.setCode(resault.getObject().getInt("Code"));
            response.setResult(resault.getObject().getBoolean("Result"));

            emitter.onSuccess(response);
          } catch (JSONException e) {
            e.printStackTrace();
          }
        } else {
          emitter.onError(new IOException(resault.getResault().toString()));
        }

      });

    }).start());
  }

  public Single<ApiDefaultResponse> editCar(JSONObject object) {
    return Single.create(emitter -> new Thread(() ->
      editVolley = new PutJsonObjectVolley(API_URL + "api/CarPro/EditCar", object, resault -> {

      if (resault.getResault() == ResaultCode.Success) {
        ApiDefaultResponse response = new ApiDefaultResponse();
        try {
          ArrayList<String> messageList = new ArrayList<>();
          JSONArray messages = resault.getObject().getJSONArray("Messages");

          for (int i = 0; i < messages.length(); i++) {
            messageList.add(messages.getString(i));
          }

          response.setMessages(messageList);
          response.setCode(resault.getObject().getInt("Code"));
          response.setResult(resault.getObject().getBoolean("Result"));

          emitter.onSuccess(response);
        } catch (JSONException e) {
          e.printStackTrace();
        }
      } else {
        emitter.onError(new IOException(resault.getResault().toString()));
      }

    })).start());
  }
}
