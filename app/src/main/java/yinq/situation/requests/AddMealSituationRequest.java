package yinq.situation.requests;

import com.google.gson.Gson;

import yinq.Request.HttpRequest;
import yinq.Request.HttpRequestObservice;
import yinq.Request.HttpRequestUrl;
import yinq.situation.dataModel.MealSituationModel;

/**
 * Created by YinQ on 2018/11/29.
 */

public class AddMealSituationRequest extends HttpRequest {

    public AddMealSituationRequest(String json, HttpRequestObservice observice){
        super(json, observice);
    }

    @Override
    protected String getHttpUrl() {
        return HttpRequestUrl.getHttpUrl("situationService", "addMealSituation");
    }

    @Override
    protected Object getRespResult(Object respResult) {
        Gson gson = new Gson();
        String json = gson.toJson(respResult);

        MealSituationModel model = (MealSituationModel) new MealSituationModel().fromJson(json);
        return model;
    }
}
