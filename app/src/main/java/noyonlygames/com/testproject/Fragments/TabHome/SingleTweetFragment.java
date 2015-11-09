package noyonlygames.com.testproject.Fragments.TabHome;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.software.shell.fab.ActionButton;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import java.util.List;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import noyonlygames.com.testproject.Adapters.ViewPagerAdapter;
import noyonlygames.com.testproject.Fragments.BaseFragment;
import noyonlygames.com.testproject.Models.TabConstants;
import noyonlygames.com.testproject.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingleTweetFragment extends BaseFragment implements View.OnClickListener {

    VerticalViewPager pager;
    ViewPagerAdapter pagerAdapter;
    ActionButton up, down;

    @Override
    protected String initializeTitle() {
        return TabConstants.TAB_HOME_TITLE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_single_tweet, container, false);

        up = (ActionButton)view.findViewById(R.id.single_tweet_button_up);
        up.removeShadow();
        up.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_white_36dp));
        up.setButtonColor(R.color.single_tweet_button);
        up.setOnClickListener(this);

        down = (ActionButton)view.findViewById(R.id.single_tweet_button_down);
        down.removeShadow();
        down.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_white_36dp));
        down.setButtonColor(R.color.single_tweet_button);
        down.setOnClickListener(this);

        pager = (VerticalViewPager) view.findViewById(R.id.vertical_view_pager);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadTweets();
    }

    public void loadTweets() {

        final StatusesService service = Twitter.getInstance().getApiClient().getStatusesService();

        service.userTimeline(null, null, null, null, null, null, null, null, null, new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        pagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), result.data);
                        pager.setAdapter(pagerAdapter);
                    }

                    @Override
                    public void failure(TwitterException error) {

                    }
                }
        );
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.single_tweet_button_up:
                pager.setCurrentItem(pager.getCurrentItem()-1);
                break;

            case R.id.single_tweet_button_down:
                pager.setCurrentItem(pager.getCurrentItem()+1);
                break;
        }
    }
}
