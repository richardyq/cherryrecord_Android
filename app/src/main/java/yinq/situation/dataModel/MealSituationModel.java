package yinq.situation.dataModel;

/**
 * Created by YinQ on 2018/11/29.
 */

public class MealSituationModel extends SituationModel {

    private MealSituationDetModel det;

    public MealSituationModel(){
        super();
    }

    public MealSituationDetModel getDet() {
        return det;
    }

    public void setDet(MealSituationDetModel det) {
        this.det = det;
    }
}



