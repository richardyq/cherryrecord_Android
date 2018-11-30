package yinq.situation.dataModel;

/**
 * Created by YinQ on 2018/11/30.
 */

public class SleepSituationModel extends SituationModel {
    private SleepSituationDetModel det;

    public SleepSituationModel(){

    }

    public SleepSituationDetModel getDet() {
        return det;
    }

    public void setDet(SleepSituationDetModel det) {
        this.det = det;
    }
}
