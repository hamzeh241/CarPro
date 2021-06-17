package ir.tdaapp.carpro.carpro.Models.Repository.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Single;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailPhotoModel;
import ir.tdaapp.li_volley.Enum.ResaultCode;
import ir.tdaapp.li_volley.Volleys.GetJsonObjectVolley;
import ir.tdaapp.li_volley.Volleys.GetStringVolley;

public class UserEnabledStateRepository extends BaseRepository {

    GetJsonObjectVolley volley;

    public Single<ApiDefaultResponse> checkStatus(int id) {
        return Single.create(emitter -> new Thread(() -> {

            volley = new GetJsonObjectVolley(API_URL + "api/UserCarPro/CheckEnableUserCarPro?id=" + id, resault -> {



                if (resault.getResault() == ResaultCode.Success) {
                    JSONObject object = resault.getObject();
                    ApiDefaultResponse model = new ApiDefaultResponse();

                    try {
                     model.setResult(object.getBoolean("Status"));
                     model.setCode(object.getInt("Code"));
                     model.setTitel(object.getString("Titel"));

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

    public Single<ApiDefaultResponse> checkAdmin(int id) {
        return Single.create(emitter -> new Thread(() -> {

            volley = new GetJsonObjectVolley(API_URL + "api/UserCarPro/CheckAdminUser/" + id, resault -> {



                if (resault.getResault() == ResaultCode.Success) {
                    JSONObject object = resault.getObject();
                    ApiDefaultResponse model = new ApiDefaultResponse();

                    try {
                     model.setResult(object.getBoolean("Status"));
                     model.setCode(object.getInt("Code"));
                     model.setTitel(object.getString("Titel"));

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
}
