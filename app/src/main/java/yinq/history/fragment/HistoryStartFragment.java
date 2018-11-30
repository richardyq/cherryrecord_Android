package yinq.history.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yinq.cherrydialyrecord.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryStartFragment extends Fragment {


    public HistoryStartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_start, container, false);
    }

}
