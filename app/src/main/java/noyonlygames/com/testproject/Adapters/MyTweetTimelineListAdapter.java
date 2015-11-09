package noyonlygames.com.testproject.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.internal.util.AspectRatioImageView;

import noyonlygames.com.testproject.Activities.MainActivity;
import noyonlygames.com.testproject.Fragments.TabHome.ShowImageFragment;

/**
 * Created by Алексей on 21.09.2015.
 */
public class MyTweetTimelineListAdapter extends TweetTimelineListAdapter {

    public MyTweetTimelineListAdapter(Context context, Timeline<Tweet> timeline) {
        super(context, timeline);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        if(view instanceof ViewGroup){
            disableViewAndSubViews((ViewGroup) view, getItem(position));
        }

        view.setEnabled(true);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        MainActivity mainActivity = (MainActivity)context;
        mainActivity.pushInCurrentTab(ShowImageFragment.newInstance(url));
    }
}
