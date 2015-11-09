package noyonlygames.com.testproject.Fragments.TabHome;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;
import com.twitter.sdk.android.tweetui.internal.util.AspectRatioImageView;

import noyonlygames.com.testproject.Activities.MainActivity;
import noyonlygames.com.testproject.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemSingleTweetFragment extends android.support.v4.app.Fragment {

    static final String ARG_TWEET_ID = "arg_tweet_id";
    private long tweetId;

    public static ItemSingleTweetFragment newInstance(long tweetId) {
        ItemSingleTweetFragment tweetFragment = new ItemSingleTweetFragment();
        Bundle arguments = new Bundle();
        arguments.putLong(ARG_TWEET_ID, tweetId);
        tweetFragment.setArguments(arguments);
        return tweetFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.tweetId = getArguments().getLong(ARG_TWEET_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_tweet, container, false);

        final FrameLayout frameLayout = (FrameLayout)view.findViewById(R.id.item_layout);

        TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                TweetView tweetView = new TweetView(getActivity(), result.data);
                disableViewAndSubViews((ViewGroup)tweetView, result.data);

                tweetView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                tweetView.setEnabled(true);
                frameLayout.addView(tweetView);
            }

            @Override
            public void failure(TwitterException e) {

            }
        });

        return view;
    }

    private void disableViewAndSubViews(ViewGroup layout, final Tweet tweet) {
        layout.setEnabled(false);
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);

            if (child instanceof ViewGroup) {
                disableViewAndSubViews((ViewGroup) child, tweet);
            } else {
                if (child.getClass().getName().equalsIgnoreCase(AspectRatioImageView.class.getName())) {

                    child.setEnabled(true);
                    child.setClickable(true);
                    child.setFocusable(true);
                    child.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loadImageToShow(tweet.entities.media.get(0).mediaUrl + ":large");
                        }
                    });
                }
                else {
                    child.setEnabled(false);
                    child.setClickable(false);
                    child.setLongClickable(false);
                }
            }
        }
    }

    void loadImageToShow(String url){

        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.pushInCurrentTab(ShowImageFragment.newInstance(url));
    }


}
