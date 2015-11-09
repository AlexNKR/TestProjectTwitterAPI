package noyonlygames.com.testproject;/**
 * Created by Алексей on 23.09.2015.
 */

import android.app.Application;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

public class MyApplication extends Application {

    private static final String TWITTER_KEY = "GXVQpgsq6k8Yza8SnXyKO3QFL";
    private static final String TWITTER_SECRET = "3vMG9fT8b2gmeDjFnIwE7pOL7vvCPrbH9zvZZoDNlaGRVPjzOf";

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
    }
}
