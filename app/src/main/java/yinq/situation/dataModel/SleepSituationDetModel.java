package yinq.situation.dataModel;

/**
 * Created by YinQ on 2018/11/30.
 */

public class SleepSituationDetModel extends SituationDetModel {

    private int code;
    private int status;
    private int score;

    public SleepSituationDetModel(){

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
