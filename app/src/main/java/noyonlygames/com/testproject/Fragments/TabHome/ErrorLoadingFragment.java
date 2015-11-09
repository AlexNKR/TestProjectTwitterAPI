package noyonlygames.com.testproject.Fragments.TabHome;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import noyonlygames.com.testproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ErrorLoadingFragment extends Fragment {


    public ErrorLoadingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_error_loading, container, false);
        Button refresh = (Button) view.findViewById(R.id.loading_error_refresh_button);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getActivity().getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getActivity().finish();
                startActivity(intent);
            }
        });

        return view;
    }


}
