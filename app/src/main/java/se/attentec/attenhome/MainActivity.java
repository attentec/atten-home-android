package se.attentec.attenhome;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance=this;
        String[] tabTitles = {"Rooms", "Statistics"};
        List<Fragment> fragments = createFragments();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if(viewPager != null) viewPager.setAdapter(new SampleFragmentPageAdapter(getSupportFragmentManager(),
                MainActivity.this,tabTitles, fragments));
        SpannableString s = new SpannableString("Test Home");
        s.setSpan(new TypefaceSpan(this, "merriweather_italic.ttf", R.color.AttentecRed, 60), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ActionBar bar = getSupportActionBar();
        bar.setCustomView(R.layout.toolbar);
        bar.setDisplayShowCustomEnabled(true);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText(s);
        bar.setElevation(0);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        if(tabLayout != null) tabLayout.setupWithViewPager(viewPager);
    }

    private List<Fragment> createFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(RoomListFragment.newInstance("56e7e5e8c798cfaf310641b7"));
        fragments.add(StatsListFragment.newInstance("56e7e5e8c798cfaf310641b7", "House"));
        return fragments;
    }
}
