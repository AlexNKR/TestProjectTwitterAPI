package noyonlygames.com.testproject.Fragments.TabHome;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.UserTimeline;

import noyonlygames.com.testproject.Adapters.MyTweetTimelineListAdapter;
import noyonlygames.com.testproject.Fragments.BaseFragment;
import noyonlygames.com.testproject.Models.TabConstants;
import noyonlygames.com.testproject.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SomeTweetFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    MyTweetTimelineListAdapter adapter;
    SwipeRefreshLayout refreshLayout;
    ListView listView;

    @Override
    protected String initializeTitle() {
        return TabConstants.TAB_HOME_TITLE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadTweets();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.home_refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        listView = (ListView)view.findViewById(android.R.id.list);
        listView.setAdapter(adapter);
        return view;
    }

    public void loadTweets() {

        String screenName = mActivity.getUser().screenName;

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(screenName)
                .build();

        adapter = new MyTweetTimelineListAdapter(getActivity(), userTimeline);

    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        adapter.refresh(new Callback<TimelineResult<Tweet>>() {
            @Override
            public void success(Result<TimelineResult<Tweet>> result) {
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getActivity(), R.string.toast_home_load_error, Toast.LENGTH_LONG);
            }
        });
    }
}
