package ir.tdaapp.carpro.carpro.Models.Repository.Server;

import java.io.IOException;

import io.reactivex.Single;
import ir.tdaapp.li_volley.Enum.ResaultCode;
import ir.tdaapp.li_volley.Volleys.GetStringVolley;

public class UserEnabledStateRepository extends BaseRepository {

  GetStringVolley volley;

  public Single<String> checkState(int id) {
    return Single.create(emitter -> {
      new Thread(() -> {

        volley = new GetStringVolley(API_URL + "api/UserCarPro/CheckEnableUserCarPro?id=" + id, resault -> {

          if (resault.getResault() == ResaultCode.Success){
            emitter.onSuccess(resault.getMessage());
          }else {
              emitter.onError(new IOException(resault.getResault().toString()));
          }

        });

      }).start();
    });
  }
}
