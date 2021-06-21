package ir.tdaapp.carpro.carpro.Models.Repository.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailsEntryModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.UserModel;
import ir.tdaapp.li_volley.Enum.ResaultCode;
import ir.tdaapp.li_volley.Volleys.GetJsonArrayVolley;
import ir.tdaapp.li_volley.Volleys.GetJsonObjectVolley;
import ir.tdaapp.li_volley.Volleys.PostJsonObjectVolley;
import ir.tdaapp.li_volley.Volleys.PutJsonObjectVolley;

public class MemberDetailsRepository extends BaseRepository {

  GetJsonObjectVolley getUserDetails;
  GetJsonArrayVolley getUserBrands;
  PutJsonObjectVolley editBrands;

  public Single<UserModel> getUserDetails(int id) {
    return Single.create(emitter -> new Thread(() ->
      getUserDetails = new GetJsonObjectVolley(API_URL + "api/UserCarPro/GetDetailUser?id=" + id, resault -> {

        if (resault.getResault() == ResaultCode.Success) {
          UserModel model = new UserModel();
          JSONObject object = resault.getObject();
          try {

            model.setId(object.getInt("Id"));
            model.setName(object.getString("FullName"));
            model.setCellPhone(object.getString("CellPhone"));
            model.setImageUrl(object.getString("Image"));
            model.setDateInsert(object.getString("DateInsert"));
            model.setLastOnline(object.getString("LastOnline"));
            model.setEnabled(object.getBoolean("Status"));

            emitter.onSuccess(model);

          } catch (JSONException e) {
            e.printStackTrace();
          }
        } else {
          emitter.onError(new IOException(resault.getResault().toString()));
        }

      })).start());
  }

  public Single<List<CarDetailsEntryModel>> getUserBrands(int id) {
    return Single.create(emitter -> new Thread(() ->
      getUserBrands = new GetJsonArrayVolley(API_URL + "api/UserCarPro/GetUserBrands/" + id, resault -> {

        if (resault.getResault() == ResaultCode.Success) {
          List<CarDetailsEntryModel> models = new ArrayList<>();
          JSONArray array = resault.getJsonArray();
          try {
            for (int i = 0; i < array.length(); i++) {
              JSONObject object = array.getJSONObject(i);

              CarDetailsEntryModel model = new CarDetailsEntryModel();
              model.setId(object.getInt("Id"));
              model.setTitle(object.getString("Title"));

              models.add(model);
            }

            emitter.onSuccess(models);

          } catch (JSONException e) {
            e.printStackTrace();
          }
        } else {
          emitter.onError(new IOException(resault.getResault().toString()));
        }

      })).start());
  }

  public Single<ApiDefaultResponse> editBrands(JSONObject object) {
    return Single.create(emitter -> new Thread(() ->
      editBrands = new PutJsonObjectVolley(API_URL + "api/UserCarPro/EditBrandsUser", object, resault -> {

        if (resault.getResault() == ResaultCode.Success) {
          JSONObject responseObject = resault.getObject();

          try {
            ApiDefaultResponse response = new ApiDefaultResponse();
            JSONArray array = responseObject.getJSONArray("Messages");
            ArrayList<String> messages = new ArrayList<>();
            response.setCode(responseObject.getInt("Code"));
            response.setResult(responseObject.getBoolean("Result"));

            for (int i = 0; i < array.length(); i++) {
              messages.add(array.getString(i));
            }

            response.setMessages(messages);

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