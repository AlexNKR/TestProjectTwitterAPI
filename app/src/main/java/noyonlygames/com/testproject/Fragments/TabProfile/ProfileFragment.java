package noyonlygames.com.testproject.Fragments.TabProfile;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.User;

import noyonlygames.com.testproject.Fragments.BaseFragment;
import noyonlygames.com.testproject.Models.TabConstants;
import noyonlygames.com.testproject.R;


public class ProfileFragment extends BaseFragment {

    ImageView profileImage, verified, bannerImage;

    TextView tweets, following, followers, name, email;

    @Override
    protected String initializeTitle() {
        return TabConstants.TAB_PROFILE_TITLE;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tweets = (TextView) view.findViewById(R.id.profile_tweets_count);
        followers = (TextView) view.findViewById(R.id.profile_followers_count);
        following = (TextView) view.findViewById(R.id.profile_following_count);
        name = (TextView) view.findViewById(R.id.profile_name);
        email = (TextView) view.findViewById(R.id.profile_email);

        profileImage = (ImageView) view.findViewById(R.id.profile_image);
        verified = (ImageView) view.findViewById(R.id.profile_verified_image);
        bannerImage = (ImageView) view.findViewById(R.id.profile_banner_image);

        loadProfile();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void loadProfile() {

        User profile = mActivity.getUser();

        tweets.setText(String.valueOf(profile.statusesCount));

        name.setText(profile.name);
        email.setText("@" + profile.screenName);

        followers.setText(String.valueOf(profile.followersCount));
        following.setText(String.valueOf(profile.friendsCount));

        if (profile.profileBannerUrl != null) {
            Picasso.with(getActivity())
                    .load(profile.profileBannerUrl)
                    .fit().centerCrop()
                    .into(bannerImage);
        } else bannerImage.setBackgroundColor(getResources().getColor(R.color.primary));

        Picasso.with(getActivity())
                .load(profile.profileImageUrl.replace("_normal", "_400x400"))
                .placeholder(R.drawable.ic_account_grey600_48dp)
                .into(profileImage);

        if (profile.verified)
            verified.setVisibility(View.VISIBLE);
        else verified.setVisibility(View.INVISIBLE);
    }
}
