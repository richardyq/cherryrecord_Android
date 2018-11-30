package com.yinq.cherrydialyrecord;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import yinq.history.fragment.HistoryStartFragment;
import yinq.person.fragments.PersonStartFragment;
import yinq.situation.fragments.SituationStartFragment;
import yinq.ui.activity.ActivityUtil;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    SituationStartFragment situationStartFragment;
    HistoryStartFragment historyStartFragment;
    PersonStartFragment personStartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        initNavigationBar();

        initTabbar();

        createFragments();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityUtil.shareActivityUtil().setTopActivity(this);


    }

    protected void initNavigationBar(){
        TextView titleTextView = findViewById(R.id.navigation_title);
        titleTextView.setText("今日情况");

        ImageButton button = findViewById(R.id.navigation_back_button);
        button.setVisibility(View.INVISIBLE);
    }

    private void setNavigationTitle(String title){
        TextView titleTextView = findViewById(R.id.navigation_title);
        System.out.println("setNavigationTitle title: " + title);
        titleTextView.setText(title);

    }

    protected void initTabbar(){
        RadioGroup tabGroup = findViewById(R.id.main_start_tabbar);

        tabGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String navigationTitle = "";
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                hideAllFragments(transaction);
                switch (i){
                    case R.id.main_tab_rd_home:{
                        navigationTitle = "今日情况";
                        transaction.show(situationStartFragment);
                        break;
                    }
                    case R.id.main_tab_rd_history:{
                        navigationTitle = "历史记录";
                        transaction.show(historyStartFragment);
                        break;
                    }
                    case R.id.main_tab_rd_person: {
                        navigationTitle = "个人空间";
                        transaction.show(personStartFragment);
                        break;
                    }
                }
                transaction.commit();
                setNavigationTitle(navigationTitle);
            }

        });
    }

    protected void createFragments(){
        fragmentManager = getFragmentManager();
        situationStartFragment = new SituationStartFragment();
        historyStartFragment = new HistoryStartFragment();
        personStartFragment = new PersonStartFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_start_content_fragment, situationStartFragment);
        transaction.add(R.id.main_start_content_fragment, historyStartFragment);
        transaction.add(R.id.main_start_content_fragment, personStartFragment);

        hideAllFragments(transaction);
        transaction.show(situationStartFragment);
        transaction.commit();
    }

    private void hideAllFragments(FragmentTransaction transaction){
        transaction.hide(situationStartFragment);
        transaction.hide(historyStartFragment);
        transaction.hide(personStartFragment);
    }
}
