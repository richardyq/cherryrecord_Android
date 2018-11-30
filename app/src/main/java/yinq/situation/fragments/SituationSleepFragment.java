package yinq.situation.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yinq.cherrydialyrecord.R;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import yinq.Request.HttpRequestObservice;
import yinq.situation.ListAdapter.SleepSituationListAdapter;
import yinq.situation.dataModel.SleepSituationModel;
import yinq.situation.util.SleepSituationUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class SituationSleepFragment extends Fragment {

    ListView listView;
    ArrayList<SleepSituationModel> sleepSituationModels;
    public SituationSleepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_situation_sleep, container, false);
        initListView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        startLoadTodaySituations();
    }

    protected void initListView(View view){
        listView = view.findViewById(R.id.sleep_situation_listview);

        listView.setAdapter(new SleepSituationListAdapter(getContext()));


    }

    protected void startLoadTodaySituations(){
        SleepSituationUtil util = new SleepSituationUtil();

        HttpRequestObservice observice = new HttpRequestObservice() {
            @Override
            public void onRequestSuccess(Object result) {
                List<SleepSituationModel> models = (List<SleepSituationModel>) result;
                sleepSituationModels = new ArrayList<SleepSituationModel>(models);

                Gson gson = new Gson();
                String modelsJson = gson.toJson(sleepSituationModels);
                System.out.println("today sleep situaitons ");
                System.out.println(modelsJson);
            }

            @Override
            public void onRequestFail(int errCode, String errMsg) {
                Toast.makeText(getContext(), errMsg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestComplete(int errCode) throws InvocationTargetException, NoSuchMethodException, java.lang.InstantiationException, IllegalAccessException {
                if (errCode == 0){
                    SleepSituationListAdapter adapter = (SleepSituationListAdapter) listView.getAdapter();
                    adapter.setModels(sleepSituationModels);
                }

            }
        };

        util.startLoadTodaySleepSituations(observice);
    }

}
