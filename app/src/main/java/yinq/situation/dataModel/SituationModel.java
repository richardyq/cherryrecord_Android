package yinq.situation.dataModel;

import yinq.datamodel.JsonObject;

/**
 * Created by YinQ on 2018/11/29.
 */

public class SituationModel extends JsonObject {
    private SituationRecordModel record;
    //private SituationDetModel det;

    public SituationModel(){

    }

    public SituationRecordModel getRecord() {
        return record;
    }

    public void setRecord(SituationRecordModel record) {
        this.record = record;
    }


}
