package noyonlygames.com.testproject.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;

import noyonlygames.com.testproject.Activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    protected MainActivity mActivity;
    protected String title;

    public BaseFragment(){
        title = initializeTitle();
    }

    protected abstract String initializeTitle();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity =	(MainActivity) this.getActivity();
    }

    public String getTitle(){
        return title;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){

    }
}
