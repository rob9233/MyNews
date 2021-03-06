package robfernandes.xyz.mynews.ui.fragments.topStories;


import android.support.v4.app.Fragment;

import robfernandes.xyz.mynews.R;

import static robfernandes.xyz.mynews.utils.Constants.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtsFragment extends BaseFragment {

    @Override
    protected int getSwipeRefreshLayoutID() {
        return R.id.fragment_arts_swipe_refresh_layout;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_arts;
    }

    @Override
    protected int getRecyclerViewID() {
        return R.id.fragment_arts_recycler_view;
    }

    @Override
    protected String getSection() {
        return APIConstants.ARTS_SECTION;
    }
}
