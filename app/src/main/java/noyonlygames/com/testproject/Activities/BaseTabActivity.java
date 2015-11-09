package noyonlygames.com.testproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import java.util.HashMap;

import noyonlygames.com.testproject.Models.TabContent;

public abstract class BaseTabActivity extends AppCompatActivity {

    protected TabHost mTabHost;

    private HashMap<String, TabContent> mStacks;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStacks = new HashMap<String, TabContent>();
    }

    protected abstract TabHost initializeTabHost();

    protected void addTab(String tabTag, Fragment fragment, final int containerId, int drawableId) {

        mStacks.put(tabTag, new TabContent(containerId));
        pushFragment(tabTag, fragment);

        TabHost.TabSpec spec = mTabHost.newTabSpec(tabTag);
        spec.setContent(containerId);
        spec.setIndicator("", getResources().getDrawable(drawableId));
        mTabHost.addTab(spec);
    }

    public String getCurrentTab() {
        return mTabHost.getCurrentTabTag();
    }

    public void pushFragment(String tagTab, Fragment fragment) {

        TabContent tabContent = getTabContent(tagTab);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(tabContent.getContainerId(), fragment)
                .commit();

        tabContent.push(fragment);
    }

    public void popFragment() {

        TabContent tabContent = getTabContent(getCurrentTab());

        tabContent.pop();

        if (tabContent.size() >= 1)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(tabContent.getContainerId(), tabContent.getLastFragment())
                    .commit();
    }

    public void replaceFragment(String tabTag, Fragment fragment){

        TabContent tabContent = getTabContent(tabTag);

        tabContent.pop();
        tabContent.push(fragment);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(tabContent.getContainerId(), fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {

        if (getTabContent(getCurrentTab()).size() == 1) {
            super.onBackPressed();
        } else {
            popFragment();
        }
    }

    public TabContent getTabContent(String tabTag){
        return mStacks.get(tabTag);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TabContent tabContent = getTabContent(getCurrentTab());

        if (tabContent.size() == 0) {
            return;
        }
        tabContent.getLastFragment().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mTabHost = initializeTabHost();
        mTabHost.setup();
    }
}
