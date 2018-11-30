package yinq.situation.dataModel;

/**
 * Created by YinQ on 2018/11/29.
 */

public class MealSituationDetModel extends SituationDetModel {

    private int code;
    private int speed;
    private int amount;
    private int feed;
    private float score;

    public MealSituationDetModel(){

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getFeed() {
        return feed;
    }

    public void setFeed(int feed) {
        this.feed = feed;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
