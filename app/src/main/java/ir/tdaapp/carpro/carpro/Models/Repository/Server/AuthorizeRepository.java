package ir.tdaapp.carpro.carpro.Models.Repository.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Single;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.li_volley.Enum.ResaultCode;
import ir.tdaapp.li_volley.Volleys.PostJsonObjectVolley;

public class AuthorizeRepository extends BaseRepository {

  PostJsonObjectVolley volley;

  public Single<ApiDefaultResponse> authorize(JSONObject authorizeObject) {
    return Single.create(emitter -> {
      new Thread(() -> {

        volley = new PostJsonObjectVolley(API_URL + "api/UserCarPro/AuthorizeUserCarPro", authorizeObject, resault -> {
          if (resault.getResault() == ResaultCode.Success) {
            JSONObject object = resault.getObject();

            ApiDefaultResponse response = new ApiDefaultResponse();
            try {

              response.setCode(object.getInt("Code"));
              response.setResult(object.getBoolean("Result"));
              JSONArray messages = object.getJSONArray("Messages");
              ArrayList<String> messageArray = new ArrayList<>();

              for (int i = 0; i < messages.length(); i++) {
                messageArray.add(messages.getString(i));
              }

              response.setMessages(messageArray);

            } catch (JSONException e) {
              e.printStackTrace();
              emitter.onError(e);
            }
            emitter.onSuccess(response);
          } else {
            emitter.onError(new IOException(resault.getResault().toString()));
          }
        });

      }).start();
    });
  }
}
