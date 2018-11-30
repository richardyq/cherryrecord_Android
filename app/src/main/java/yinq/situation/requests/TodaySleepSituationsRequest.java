package yinq.situation.requests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import yinq.Request.HttpRequest;
import yinq.Request.HttpRequestObservice;
import yinq.Request.HttpRequestUrl;
import yinq.situation.dataModel.SleepSituationModel;

/**
 * Created by YinQ on 2018/11/30.
 */

public class TodaySleepSituationsRequest extends HttpRequest {

    public TodaySleepSituationsRequest(String json, HttpRequestObservice observice){
        super(json, observice);
    }

    @Override
    protected String getHttpUrl() {
        return HttpRequestUrl.getHttpUrl("situationService", "todaySleepSituation");
    }

    @Override
    protected Object getRespResult(Object respResult) {
        Gson gson = new Gson();
        String json = gson.toJson(respResult);

        List<SleepSituationModel> models = gson.fromJson(json, new TypeToken<List<SleepSituationModel>>(){}.getType());


        return models;
    }
}
