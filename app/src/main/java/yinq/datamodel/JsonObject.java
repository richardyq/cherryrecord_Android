package yinq.datamodel;

import com.google.gson.Gson;

import org.json.JSONObject;


/**
 * Created by YinQ on 2018/11/28.
 */

public class JsonObject {

    public JsonObject(){

    }

    public Gson createGson(){
        Gson gson = new Gson();
        return gson;
    }

    public String toJson(){
        Gson gson = createGson();
        String json = gson.toJson(this);
        return json;
    }

    public JsonObject fromJson(String json){
        Gson gson = createGson();
        JsonObject retModel = gson.fromJson(json, this.getClass());

        return retModel;
    }
}
