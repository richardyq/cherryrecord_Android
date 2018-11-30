package yinq.situation.requests;

import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import yinq.Request.HttpRequest;
import yinq.Request.HttpRequestObservice;
import yinq.Request.HttpRequestUrl;
import yinq.situation.dataModel.MealSituationModel;

/**
 * Created by YinQ on 2018/11/29.
 */

public class TodayMealSituationsRequest extends HttpRequest {

    public TodayMealSituationsRequest(String json, HttpRequestObservice observice){
        super(json, observice);
    }

    @Override
    protected String getHttpUrl() {
        return HttpRequestUrl.getHttpUrl("situationService", "todayMealSituation");
    }

    @Override
    protected Object getRespResult(Object respResult) {
        Gson gson = new Gson();
        String json = gson.toJson(respResult);

        List<MealSituationModel> models = gson.fromJson(json, new TypeToken<List<MealSituationModel>>(){}.getType());


        return models;
    }
}
