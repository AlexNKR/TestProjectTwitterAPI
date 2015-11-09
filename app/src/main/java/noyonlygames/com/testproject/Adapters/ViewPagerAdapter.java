package noyonlygames.com.testproject.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import noyonlygames.com.testproject.Fragments.TabHome.ItemSingleTweetFragment;

/**
 * Created by Алексей on 27.09.2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    int count;
    List<Tweet> tweetList;

    public ViewPagerAdapter(FragmentManager fm, List<Tweet> tweetList) {
        super(fm);
        this.tweetList = tweetList;
        count = tweetList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return ItemSingleTweetFragment.newInstance(tweetList.get(position).getId());
    }

    @Override
    public int getCount() {
        return count;
    }
}
