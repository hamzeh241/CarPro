package ir.tdaapp.carpro.carpro.Models.Repository.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarModel;
import ir.tdaapp.li_volley.Enum.ResaultCode;
import ir.tdaapp.li_volley.Volleys.GetJsonArrayVolley;

public class WaitingToAcceptRepository extends BaseRepository {

  GetJsonArrayVolley volley;

  public Single<List<CarModel>> getItems(int userId, int page) {
    return Single.create(emitter -> new Thread(() -> {

      volley = new GetJsonArrayVolley(API_URL + "api/CarPro/GetAwaitingApprovalCars?userId=" + userId + "&page=" + page, resault -> {

        if (resault.getResault() == ResaultCode.Success) {
          JSONArray array = resault.getJsonArray();
          List<CarModel> models = new ArrayList<>();

          try {

            for (int i = 0; i < array.length(); i++) {
              JSONObject object = array.getJSONObject(i);
              CarModel model = new CarModel();

              model.setId(object.getInt("Id"));
              model.setTitle(object.getString("Title"));
              model.setPrice(object.getString("Price"));
              model.setImageName(object.getString("Image"));
              model.setShamsiDate(object.getString("ShamsiDate"));
              model.setMiladiDate(object.getString("MiladiDate"));
              model.setFunction(object.getInt("Function"));

              models.add(model);
            }

          } catch (JSONException e) {
            e.printStackTrace();
            emitter.onError(e);
          }
          emitter.onSuccess(models);
        } else {
          emitter.onError(new IOException(resault.getResault().toString()));
        }

      });

    }).start());
  }
}
