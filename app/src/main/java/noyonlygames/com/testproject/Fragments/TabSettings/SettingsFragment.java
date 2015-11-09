package noyonlygames.com.testproject.Fragments.TabSettings;


import android.os.Bundle;
import android.app.Fragment;

import noyonlygames.com.testproject.Fragments.BaseFragment;
import noyonlygames.com.testproject.Models.TabConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends BaseFragment {

    @Override
    protected String initializeTitle() {
        return TabConstants.TAB_SETTINGS_TITLE;
    }
}