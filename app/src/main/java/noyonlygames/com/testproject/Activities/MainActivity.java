package noyonlygames.com.testproject.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.AccountService;

import noyonlygames.com.testproject.Fragments.TabHome.SingleTweetFragment;
import noyonlygames.com.testproject.Fragments.BaseFragment;
import noyonlygames.com.testproject.Fragments.TabHome.ErrorLoadingFragment;
import noyonlygames.com.testproject.Fragments.TabHome.SomeTweetFragment;
import noyonlygames.com.testproject.Fragments.TabMap.MapFragment;
import noyonlygames.com.testproject.Fragments.TabProfile.ProfileFragment;
import noyonlygames.com.testproject.Fragments.TabSettings.SettingsFragment;
import noyonlygames.com.testproject.Models.TabConstants;
import noyonlygames.com.testproject.Models.TabContent;
import noyonlygames.com.testproject.R;

public class MainActivity extends BaseTabActivity {

    private User user;
    private ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    final int STATE_SOME_TWEET = 1, STATE_SINGLE_TWEET = 2;
    int primaryState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_36dp);

        initNavigationDrawer();

        AccountService acService = Twitter.getApiClient().getAccountService();
        acService.verifyCredentials(null, null, new Callback<User>() {
            @Override
            public void success(Result<User> result) {
                user = result.data;
                addTabs();
            }

            @Override
            public void failure(TwitterException e) {
                getSupportFragmentManager().beginTransaction().replace(android.R.id.tabcontent, new ErrorLoadingFragment()).commit();
            }
        });
    }

    @Override
    protected TabHost initializeTabHost() {
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                updateActionBar(tabId);
            }
        });
        return tabHost;
    }

    private void addTabs(){
        addTab(TabConstants.TAB_HOME, new SomeTweetFragment()
                , R.id.main_tab_home, R.drawable.ic_home_white_36dp);

        addTab(TabConstants.TAB_PROFILE, new ProfileFragment()
                , R.id.main_tab_profile, R.drawable.ic_person_white_36dp);

        addTab(TabConstants.TAB_MAP, new MapFragment()
                , R.id.main_tab_map, R.drawable.ic_place_white_36dp);

        addTab(TabConstants.TAB_SETTINGS, new SettingsFragment()
                , R.id.main_tab_settings, R.drawable.ic_settings_white_36dp);

        primaryState = STATE_SOME_TWEET;
    }

    public void pushInCurrentTab(Fragment fragment){
        String tabTag = getCurrentTab();
        pushFragment(tabTag, fragment);
        updateActionBar(tabTag);
    }

    public void replaceCurrentFragment(Fragment fragment){
        replaceFragment(getCurrentTab(), fragment);
        updateActionBar(getCurrentTab());
    }

    @Override
    public void popFragment() {
        super.popFragment();
        updateActionBar(getCurrentTab());
    }

    protected void updateActionBar(String tabTag){
        TabContent tabContent = getTabContent(tabTag);

        actionBar.setDisplayHomeAsUpEnabled(tabContent.size() > 1);
        actionBar.setTitle(((BaseFragment) tabContent.getLastFragment()).getTitle());
     }

    private void initNavigationDrawer() {
        drawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.main_navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.logout:
                        Twitter.getSessionManager().clearActiveSession();
                        finish();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        break;

                    case R.id.help:
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://get.fabric.io/"));
                        startActivity(browserIntent);
                        break;

                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.main_action_side_bar:
                if (!drawerLayout.isDrawerOpen(Gravity.RIGHT))
                    drawerLayout.openDrawer(Gravity.RIGHT);
                else
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                break;

            case R.id.main_action_switch_view:
                if (getCurrentTab() == TabConstants.TAB_HOME){

                    if (primaryState == STATE_SOME_TWEET) {
                        replaceCurrentFragment(new SingleTweetFragment());
                        primaryState = STATE_SINGLE_TWEET;
                    }
                    else if (primaryState == STATE_SINGLE_TWEET) {
                        replaceCurrentFragment(new SomeTweetFragment());
                        primaryState = STATE_SOME_TWEET;
                    }
                }
                break;

            default: break;
        }
        return true;
    }

    public User getUser(){
        return user;
    }
}
