package se.attentec.attenhome;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

public class SampleFragmentPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragments;
    private String tabTitles[];
    private Context context;

    public SampleFragmentPageAdapter(FragmentManager fm, Context context,
                                     String[] tabTitles, List<Fragment> fragments) {
        super(fm);
        this.context = context;
        this.tabTitles = tabTitles;
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
