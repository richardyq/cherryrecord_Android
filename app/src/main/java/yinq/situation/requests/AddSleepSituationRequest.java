package yinq.situation.requests;

import com.google.gson.Gson;

import yinq.Request.HttpRequest;
import yinq.Request.HttpRequestObservice;
import yinq.Request.HttpRequestUrl;
import yinq.situation.dataModel.SleepSituationModel;

/**
 * Created by YinQ on 2018/11/30.
 */

public class AddSleepSituationRequest extends HttpRequest {
    public AddSleepSituationRequest(String json, HttpRequestObservice observice){
        super(json, observice);
    }

    @Override
    protected String getHttpUrl() {
        return HttpRequestUrl.getHttpUrl("situationService", "addSleepSituation");
    }

    @Override
    protected Object getRespResult(Object respResult) {
        Gson gson = new Gson();
        String json = gson.toJson(respResult);

        SleepSituationModel model = (SleepSituationModel) new SleepSituationModel().fromJson(json);
        return model;
    }
}
