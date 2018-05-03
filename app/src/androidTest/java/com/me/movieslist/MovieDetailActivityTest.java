package com.me.movieslist;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;


import com.me.movieslist.config.UrlConfig;
import com.me.movieslist.network.ServiceGenerator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by xvbp3947 on 23/04/18.
 */

@RunWith(AndroidJUnit4.class)
public class MovieDetailActivityTest extends InstrumentationTestCase {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class, true, false);

    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        server = new MockWebServer();
        server.start();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        UrlConfig.BASE_URL = server.url("/").toString();
    }

    @Test
    public void testMovieIsShown() throws Exception {
        String fileName = "movie_200_ok_response.json";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.movie_title_tv)).check(matches(withText("Fight Club")));
        onView(withId(R.id.movie_overview_tv)).check(matches(withText("A ticking-time-bomb insomniac and a slippery " +
                "soap salesman channel primal male aggression into a shocking new form of therapy. Their concept " +
                "catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in " +
                "the way and ignites an out-of-control spiral toward oblivion.")));
        onView(withId(R.id.movie_avg_views_tv)).check(matches(withText("8.3")));

    }


}
