package se.attentec.attenhome;

import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import se.attentec.attenhome.models.Device;
import se.attentec.attenhome.models.Lamp;
import se.attentec.attenhome.models.Radiator;

public class DeviceActivity extends AppCompatActivity {
    public static DeviceActivity instance;
    private Device mDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        mDevice = (Device) getIntent().getExtras().get("device");
        instance=this;
        String[] tabTitles = {"Settings", "Statistics"};
        List<Fragment> fragments = createFragments();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if(viewPager != null) viewPager.setAdapter(new SampleFragmentPageAdapter(getSupportFragmentManager(),
                DeviceActivity.this,tabTitles, fragments));
        SpannableString s = new SpannableString(mDevice.getName());
        s.setSpan(new TypefaceSpan(this, "merriweather_italic.ttf", R.color.AttentecRed, 60), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ActionBar bar = getSupportActionBar();
        bar.setCustomView(R.layout.toolbar);
        bar.setDisplayShowCustomEnabled(true);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText(s);
        bar.setElevation(0);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);
    }

    private List<Fragment> createFragments(){
        List<Fragment> fragments = new ArrayList<>();
        if(mDevice instanceof Lamp){
            fragments.add(LampFragment.newInstance((Lamp) mDevice));
        }else if (mDevice instanceof Radiator){
            fragments.add(RadiatorFragment.newInstance((Radiator) mDevice));
        }
        fragments.add(StatsListFragment.newInstance(mDevice.get_id(), "Device"));
        return fragments;
    }
}