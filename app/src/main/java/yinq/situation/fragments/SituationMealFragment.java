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
import yinq.situation.ListAdapter.MealSituationListAdapter;
import yinq.situation.dataModel.MealSituationModel;
import yinq.situation.util.MealSituationUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class SituationMealFragment extends Fragment {


    ListView listView;
    ArrayList<MealSituationModel> mealSituationModels ;
    public SituationMealFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_situation_meal, container, false);
        initListView(view);
        return view;
    }

    protected void initListView(View view){
        listView = view.findViewById(R.id.meal_situation_list_view);

        listView.setAdapter(new MealSituationListAdapter(getContext()));
        startLoadTodaySituations();
    }

    private void startLoadTodaySituations(){
        MealSituationUtil util = new MealSituationUtil();

        HttpRequestObservice observice = new HttpRequestObservice() {
            @Override
            public void onRequestSuccess(Object result) {
                List<MealSituationModel> models = (List<MealSituationModel>) result;
                mealSituationModels = new ArrayList<MealSituationModel>(models);

                Gson gson = new Gson();
                String modelsJson = gson.toJson(mealSituationModels);
                System.out.println("today meal situaitons ");
                System.out.println(modelsJson);
            }

            @Override
            public void onRequestFail(int errCode, String errMsg) {
                Toast.makeText(getContext(), errMsg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestComplete(int errCode) throws InvocationTargetException, NoSuchMethodException, java.lang.InstantiationException, IllegalAccessException {
                if (errCode == 0){
                    MealSituationListAdapter adapter = (MealSituationListAdapter) listView.getAdapter();
                    adapter.setModels(mealSituationModels);
                }
            }
        };

        util.startLoadTodaySituations(observice);
    }

}
