package yinq.situation.ListAdapter;

import android.content.Context;
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
import yinq.situation.dataModel.SituationRecordModel;
import yinq.situation.dataModel.SleepSituationDetModel;
import yinq.situation.dataModel.SleepSituationModel;
import yinq.situation.util.SleepSituationUtil;

/**
 * Created by YinQ on 2018/11/30.
 */

public class SleepSituationListAdapter extends BaseAdapter {

    Context context;
    int selectedIndex;
    private ArrayList<SleepSituationModel> models;

    public SleepSituationListAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return getModels().size();
    }

    @Override
    public SleepSituationModel getItem(int i) {
        return getModels().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemview = null;
        if (view == null){
            itemview = LayoutInflater.from(context).inflate(R.layout.listcell_sleep_situation_edit, viewGroup, false);
        }
        else {
            itemview = view;
        }

        TextView titleTextView = itemview.findViewById(R.id.sleep_edit_situation_title_textview);
        RadioGroup radioGroup = itemview.findViewById(R.id.sleep_edit_situation_speed_radiogroup);
        Button button = itemview.findViewById(R.id.sleep_edit_situation_commit_button);
        TextView scoreTextView = itemview.findViewById(R.id.sleep_edit_situation_score_textview);
        TextView updateTextView = itemview.findViewById(R.id.sleep_edit_situation_update_textview);

        SleepSituationDetModel detModel = (SleepSituationDetModel) getItem(i).getDet();
        titleTextView.setText(sleepTitle(detModel));


        SleepSituationModel model = models.get(i);
        if (model.getRecord() == null){
            button.setVisibility(View.VISIBLE);
            scoreTextView.setVisibility(View.INVISIBLE);
            updateTextView.setVisibility(View.INVISIBLE);

            enableRadioGroup(radioGroup);
        }
        else {
            button.setVisibility(View.INVISIBLE);
            scoreTextView.setVisibility(View.VISIBLE);
            updateTextView.setVisibility(View.VISIBLE);

            disableRadioGroup(radioGroup);
            scoreTextView.setText("得分: " +  detModel.getScore());
            SituationRecordModel recordModel = model.getRecord();
            updateTextView.setText(recordModel.getUser().getUserName() + recordModel.getUpdateTime());

            radioGroup.check(radioCheckedIndex(detModel));
        }

        final Integer tag = new Integer(i);
        button.setTag(tag);
        radioGroup.setTag(tag);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer tag = (Integer) view.getTag();
                commitButtonClicked(tag);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rg, int i) {
                Integer tag = (Integer) rg.getTag();
                radioGroupCheckChanged(i, tag.intValue());
            }
        });
        return itemview;
    }

    public ArrayList<SleepSituationModel> getModels() {
        if (models == null){
            models = new ArrayList<SleepSituationModel>();
            for (int i = 0; i < 2; i++) {
                SleepSituationModel model = new SleepSituationModel();
                SleepSituationDetModel detModel = new  SleepSituationDetModel();
                detModel.setCode(i);
                model.setDet(detModel);

                models.add(model);
            }
        }
        return models;

    }

    public void setModels(ArrayList<SleepSituationModel> models) {
        ArrayList<SleepSituationModel> situationModels = getModels();
        for (SleepSituationModel model: models) {
            situationModels.set(model.getDet().getCode(), model);
        }
        notifyDataSetChanged();
    }

    protected String sleepTitle(SleepSituationDetModel detModel){
        String mealTitle = "";
        switch (detModel.getCode()){
            case 0:{
                mealTitle = "睡午觉";
                break;
            }
            case 1:{
                mealTitle = "晚上";
                break;
            }

        }

        return mealTitle;
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

    protected void commitButtonClicked(Integer tag){
        int index = tag.intValue();
        System.out.println("commitButtonClicked code = " + index);
        SleepSituationModel situationModel = getItem(index);
        SleepSituationDetModel detModel = situationModel.getDet();

        selectedIndex = index;

        HttpRequestObservice observice = new HttpRequestObservice() {
            @Override
            public void onRequestSuccess(Object result) {
                SleepSituationModel situationModel = (SleepSituationModel) result;
                models.set(selectedIndex, situationModel);
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

        SleepSituationUtil util = new SleepSituationUtil();
        util.startAddSleepSituation(context, detModel.getCode(), detModel.getStatus(), observice);
    }

    protected void radioGroupCheckChanged(int idx, int code){
        SleepSituationDetModel detModel = (SleepSituationDetModel) getItem(code).getDet();
        switch (idx){
            case R.id.sleep_edit_situation_rb_late:{
                detModel.setStatus(0);
                break;
            }
            case R.id.sleep_edit_situation_rb_normal:{
                detModel.setStatus(1);
                break;
            }
            case R.id.sleep_edit_situation_rb_early:{
                detModel.setStatus(2);
                break;
            }
        }
    }

    protected int radioCheckedIndex(SleepSituationDetModel detModel){
        int index = R.id.sleep_edit_situation_rb_late;
        switch (detModel.getStatus()){
            case 0:{
                index = R.id.sleep_edit_situation_rb_late;
                break;
            }
            case 1:{
                index = R.id.sleep_edit_situation_rb_normal;
                break;
            }
            case 2:{
                index = R.id.sleep_edit_situation_rb_early;
                break;
            }
        }
        return index;
    }
}
