package yinq.situation.dataModel;

import yinq.datamodel.JsonObject;

/**
 * Created by YinQ on 2018/11/29.
 */

public class SituationDetModel extends JsonObject {

    private String id;

    public SituationDetModel(){

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
