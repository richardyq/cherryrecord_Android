package yinq.situation.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.yinq.cherrydialyrecord.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SituationStartFragment extends Fragment {

    FragmentManager fragmentManager;

    SituationMealFragment mealFragment;
    SituationSleepFragment sleepFragment;
    SituationInterestFragment interestFragment;


    public SituationStartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_situation_start, container, false);
        initSwitchTab(view);
        createFragments();
        return view;


    }

    protected void initSwitchTab(View view){
        RadioGroup radioGroup = view.findViewById(R.id.situation_switch_tab_radio_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                hideAllFragments(transaction);
                switch (i){
                    case R.id.situation_switch_tab_radio_meal:{

                        transaction.show(mealFragment);
                        break;
                    }
                    case R.id.situation_switch_tab_radio_sleep:{

                        transaction.show(sleepFragment);
                        break;
                    }
                    case R.id.situation_switch_tab_radio_interest: {

                        transaction.show(interestFragment);
                        break;
                    }
                }
                transaction.commit();
            }
        });
    }

    protected void createFragments(){
        fragmentManager = getFragmentManager();
        mealFragment = new SituationMealFragment();
        sleepFragment = new SituationSleepFragment();
        interestFragment = new SituationInterestFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.situation_content_fragmetn, mealFragment);
        transaction.add(R.id.situation_content_fragmetn, sleepFragment);
        transaction.add(R.id.situation_content_fragmetn, interestFragment);

        hideAllFragments(transaction);
        transaction.show(mealFragment);
        transaction.commit();
    }

    private void hideAllFragments(FragmentTransaction transaction){
        transaction.hide(mealFragment);
        transaction.hide(sleepFragment);
        transaction.hide(interestFragment);
    }
}
