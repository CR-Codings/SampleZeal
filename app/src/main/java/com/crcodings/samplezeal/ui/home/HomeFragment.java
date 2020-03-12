package com.crcodings.samplezeal.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.crcodings.samplezeal.NewCaseActivity;
import com.crcodings.samplezeal.R;
import com.crcodings.samplezeal.ReportsActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RelativeLayout rlNewCase = root.findViewById(R.id.rlNewCase);
        RelativeLayout rlExistingCase = root.findViewById(R.id.rlExistingCase);

        rlNewCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewCaseActivity.class);
                startActivity(intent);
            }
        });

        rlExistingCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReportsActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}
