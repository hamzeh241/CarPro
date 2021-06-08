package ir.tdaapp.carpro.carpro.Models.Repository.Server;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Models.ViewModels.UserModel;
import ir.tdaapp.li_volley.Enum.ResaultCode;
import ir.tdaapp.li_volley.Volleys.GetJsonArrayVolley;
import ir.tdaapp.li_volley.Volleys.PutJsonObjectVolley;

public class MemberRepository extends BaseRepository {

  GetJsonArrayVolley getUsers;
  PutJsonObjectVolley changeStatus;

  public Single<List<UserModel>> getUsers() {
    return Single.create(emitter -> new Thread(() ->
      getUsers = new GetJsonArrayVolley(API_URL + "api/UserCarPro/GetUsers", resault -> {

        if (resault.getResault() == ResaultCode.Success) {
          List<UserModel> models = new ArrayList<>();
          JSONArray array = resault.getJsonArray();
          try {

            for (int i = 0; i < array.length(); i++) {
              JSONObject object = array.getJSONObject(i);
              UserModel model = new UserModel();
              model.setId(object.getInt("Id"));
              model.setName(object.getString("FullName"));
              model.setCellPhone(object.getString("CellPhone"));
              model.setEnabled(object.getBoolean("Status"));

              models.add(model);
            }

            emitter.onSuccess(models);

          } catch (JSONException e) {
            e.printStackTrace();
            Log.i("TAG", "getUsers: json exception");
          }
        } else {
          Log.i("TAG", "getUsers: io exception");
          emitter.onError(new IOException(resault.getResault().toString()));
        }

      })).start());
  }

  public Single<ApiDefaultResponse> changeUserState(JSONObject object) {
    return Single.create(emitter -> new Thread(() ->
      changeStatus = new PutJsonObjectVolley(API_URL + "api/UserCarPro/ChangeStatusUserPro", object, resault -> {

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
