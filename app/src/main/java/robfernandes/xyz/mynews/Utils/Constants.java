package robfernandes.xyz.mynews.Utils;

import android.support.v4.app.Fragment;

import robfernandes.xyz.mynews.Controller.ArtsFragment;
import robfernandes.xyz.mynews.Controller.BusinessFragment;
import robfernandes.xyz.mynews.Controller.MostPopularFragment;
import robfernandes.xyz.mynews.Controller.SportsFragment;
import robfernandes.xyz.mynews.Controller.TopStoriesFragment;
import robfernandes.xyz.mynews.Controller.TravelFragment;

/**
 * Created by Roberto Fernandes on 05/12/2018.
 */
public final class Constants {
    public static final String[] PAGE_TITLE = {"TOP STORIES", "MOST POPULAR", "SPORTS", "BUSINESS", "ARTS", "TRAVEL"};
    public static final Fragment[] FRAGMENTS = {new TopStoriesFragment(), new MostPopularFragment(), new SportsFragment(), new BusinessFragment(), new ArtsFragment(), new TravelFragment()};
    public static final int TOP_STORIES_INDEX = 0;
    public static final int MOST_POPULAR_INDEX = 1;
    public static final int SPORTS_INDEX = 2;
    public static final int BUSINESS_INDEX = 3;
    public static final int ARTS_INDEX = 4;
    public static final int TRAVEL_INDEX = 5;
}
