package yinq.situation.ListAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.YuvImage;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yinq.cherrydialyrecord.R;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import yinq.Request.HttpRequestObservice;
import yinq.situation.dataModel.MealSituationDetModel;
import yinq.situation.dataModel.MealSituationModel;

import yinq.situation.dataModel.SituationRecordModel;
import yinq.situation.util.MealSituationUtil;



/**
 * Created by YinQ on 2018/11/29.
 */

public class MealSituationListAdapter extends BaseAdapter {

    Context context;
    private int selectedIndex = NO_SELECTION;
    private ArrayList<MealSituationModel> models;

    public MealSituationListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return getModels().size();
    }

    @Override
    public MealSituationModel getItem(int i) {
        return getModels().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View itemView = null;
        if (view == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.listcell_meal_situation_edit, viewGroup, false);
        }
        else {
            itemView = view;
        }
        TextView titleTextView = itemView.findViewById(R.id.meal_edit_situation_title_textview);

        MealSituationDetModel detModel = (MealSituationDetModel) getItem(i).getDet();


        Button button = itemView.findViewById(R.id.meal_edit_situation_commit_button);
        RadioGroup speedRadioGroup = itemView.findViewById(R.id.meal_edit_situation_speed_radiogroup);
        RadioGroup amountRadioGroup = itemView.findViewById(R.id.meal_edit_situation_amount_radiogroup);
        RadioGroup feedRadioGroup = itemView.findViewById(R.id.meal_edit_situation_feed_radiogroup);
        TextView scoreTextView = itemView.findViewById(R.id.meal_edit_situation_score_textview);
        TextView updateTextView = itemView.findViewById(R.id.meal_edit_situation_update_textview);

        MealSituationModel model = models.get(i);
        if (model.getRecord() == null){
            button.setVisibility(View.VISIBLE);
            scoreTextView.setVisibility(View.INVISIBLE);
            updateTextView.setVisibility(View.INVISIBLE);

            enableRadioGroup(speedRadioGroup);
            enableRadioGroup(amountRadioGroup);
            enableRadioGroup(feedRadioGroup);
        }
        else {
            button.setVisibility(View.INVISIBLE);
            scoreTextView.setVisibility(View.VISIBLE);
            updateTextView.setVisibility(View.VISIBLE);

            disableRadioGroup(speedRadioGroup);
            disableRadioGroup(amountRadioGroup);
            disableRadioGroup(feedRadioGroup);

            scoreTextView.setText("得分: " + String.format("%.2f", detModel.getScore()));
            SituationRecordModel recordModel = model.getRecord();
            updateTextView.setText(recordModel.getUser().getUserName() + recordModel.getUpdateTime());
        }

        final Integer tag = new Integer(i);
        button.setTag(tag);

        speedRadioGroup.setTag(tag);
        speedRadioGroup.check(speedRadioCheckedIndex(detModel));
        amountRadioGroup.setTag(tag);
        amountRadioGroup.check(amountRadioCheckedIndex(detModel));
        feedRadioGroup.setTag(tag);
        feedRadioGroup.check(feedRadioCheckedIndex(detModel));



        titleTextView.setText(mealTitle(detModel));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer tag = (Integer) view.getTag();
                commitButtonClicked(tag);
            }
        });


        speedRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Integer tag = (Integer) radioGroup.getTag();
                speedRadioGroupChangeHandler(i, tag.intValue());
            }
        });

        amountRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Integer tag = (Integer) radioGroup.getTag();
                amountRadioGroupChangeHandler(i, tag.intValue());
            }
        });

        feedRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Integer tag = (Integer) radioGroup.getTag();
                feedRadioGroupChangeHandler(i, tag.intValue());
            }
        });

        return itemView;
    }

    public void disableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++)
        {
            testRadioGroup.getChildAt(i).setClickable(false);
        }
    }

    public void enableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++)
        {
            testRadioGroup.getChildAt(i).setClickable(true);
        }
    }


    protected void commitButtonClicked(int i){
        System.out.println("commitButtonClicked code = " + i);
        MealSituationModel situationModel = getItem(i);
        MealSituationDetModel detModel = situationModel.getDet();

        selectedIndex = i;
        HttpRequestObservice observice = new HttpRequestObservice() {
            @Override
            public void onRequestSuccess(Object result) {
                MealSituationModel mealSituationModel = (MealSituationModel) result;
                models.set(selectedIndex, mealSituationModel);
            }

            @Override
            public void onRequestFail(int errCode, String errMsg) {
                Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestComplete(int errCode) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
                if (errCode == 0){
                    notifyDataSetChanged();
                }
            }
        };
        MealSituationUtil util = new MealSituationUtil();
        util.startAddMealSituation(context, i, detModel.getSpeed(), detModel.getAmount(), detModel.getFeed(), observice);
    }

    public ArrayList<MealSituationModel> getModels() {
        if (models == null){
            models = new ArrayList<MealSituationModel>();
            for (int i = 0; i < 3; i++) {
                MealSituationModel model = new MealSituationModel();
                MealSituationDetModel detModel = new  MealSituationDetModel();
                detModel.setCode(i);
                model.setDet(detModel);
                models.add(model);
            }
        }
        return models;
    }

    public void setModels(ArrayList<MealSituationModel> models) {
        ArrayList<MealSituationModel> situationModels = getModels();
        for (MealSituationModel model: models) {
            situationModels.set(model.getDet().getCode(), model);
        }
        notifyDataSetChanged();
    }

    protected String mealTitle(MealSituationDetModel detModel){
        String mealTitle = "";
        switch (detModel.getCode()){
            case 0:{
                mealTitle = "早饭";
                break;
            }
            case 1:{
                mealTitle = "午饭";
                break;
            }
            case 2:{
                mealTitle = "晚饭";
                break;
            }
        }

        return mealTitle;
    }

    protected void speedRadioGroupChangeHandler(int idx, int code){
        MealSituationDetModel detModel = (MealSituationDetModel) getItem(code).getDet();
        switch (idx){
            case R.id.meal_edit_situation_speed_rb_slow:{
                detModel.setSpeed(0);
                break;
            }
            case R.id.meal_edit_situation_speed_rb_normal:{
                detModel.setSpeed(1);
                break;
            }
            case R.id.meal_edit_situation_speed_rb_fast:{
                detModel.setSpeed(2);
                break;
            }
        }

    }

    protected void amountRadioGroupChangeHandler(int idx, int code){
        MealSituationDetModel detModel = (MealSituationDetModel) getItem(code).getDet();
        switch (idx){
            case R.id.meal_edit_situation_amount_rb_few:{
                detModel.setAmount(0);
                break;
            }
            case R.id.meal_edit_situation_amount_rb_normal:{
                detModel.setAmount(1);
                break;
            }
            case R.id.meal_edit_situation_amount_rb_many:{
                detModel.setAmount(2);
                break;
            }
        }

    }

    protected void feedRadioGroupChangeHandler(int idx, int code){
        MealSituationDetModel detModel = (MealSituationDetModel) getItem(code).getDet();
        switch (idx){
            case R.id.meal_edit_situation_feed_rb_low:{
                detModel.setFeed(0);
                break;
            }
            case R.id.meal_edit_situation_feed_rb_normal:{
                detModel.setFeed(1);
                break;
            }
            case R.id.meal_edit_situation_feed_rb_high:{
                detModel.setFeed(2);
                break;
            }
        }

    }

    protected int speedRadioCheckedIndex(MealSituationDetModel detModel){
        int index = R.id.meal_edit_situation_speed_rb_slow;
        switch (detModel.getSpeed()){
            case 0:{
                index = R.id.meal_edit_situation_speed_rb_slow;
                break;
            }
            case 1:{
                index = R.id.meal_edit_situation_speed_rb_normal;
                break;
            }
            case 2:{
                index = R.id.meal_edit_situation_speed_rb_fast;
                break;
            }
        }
        return index;
    }

    protected int amountRadioCheckedIndex(MealSituationDetModel detModel){
        int index = R.id.meal_edit_situation_amount_rb_few;
        switch (detModel.getAmount()){
            case 0:{
                index = R.id.meal_edit_situation_amount_rb_few;
                break;
            }
            case 1:{
                index = R.id.meal_edit_situation_amount_rb_normal;
                break;
            }
            case 2:{
                index = R.id.meal_edit_situation_amount_rb_many;
                break;
            }
        }
        return index;
    }

    protected int feedRadioCheckedIndex(MealSituationDetModel detModel){
        int index = R.id.meal_edit_situation_feed_rb_low;
        switch (detModel.getFeed()){
            case 0:{
                index = R.id.meal_edit_situation_feed_rb_low;
                break;
            }
            case 1:{
                index = R.id.meal_edit_situation_feed_rb_normal;
                break;
            }
            case 2:{
                index = R.id.meal_edit_situation_feed_rb_high;
                break;
            }
        }
        return index;
    }
}
