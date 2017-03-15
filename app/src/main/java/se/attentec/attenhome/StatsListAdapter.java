package se.attentec.attenhome;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import java.util.List;

public class StatsListAdapter extends RecyclerView.Adapter<StatsListAdapter.ViewHolder> {
    private List<LineData> data;

    public StatsListAdapter() {
    }

    public void setStatsData(List<LineData> statsData) {
        this.data = statsData;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHeader;
        public LineChart expandingContent;
        public RelativeLayout clickyContainer;
        public ImageView arrow;
        private int mOriginalHeight = 0;
        private boolean mIsViewExpanded = false;

        public ViewHolder(final View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.stats_header);
            clickyContainer = (RelativeLayout) v.findViewById(R.id.clicky_container);
            expandingContent = (LineChart) v.findViewById(R.id.chart1);
            if (!mIsViewExpanded) {
                expandingContent.setVisibility(View.GONE);
                expandingContent.setEnabled(false);
            }
            arrow = (ImageView) v.findViewById(R.id.collapse_arrow);
            arrow.setImageResource(R.drawable.ic_expand_more_black_24dp);
            clickyContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (mOriginalHeight == 0) {
                        mOriginalHeight = v.getHeight();
                    }
                    ValueAnimator valueAnimator;
                    if (!mIsViewExpanded) {
                        mIsViewExpanded = true;
                        arrow.setImageResource(R.drawable.ic_expand_less_black_24dp);
                        expandingContent.setVisibility(View.VISIBLE);
                        expandingContent.setEnabled(true);
                        valueAnimator = ValueAnimator.ofInt(mOriginalHeight,(int)(mOriginalHeight + mOriginalHeight * 5.4));
                    } else {
                        mIsViewExpanded = false;
                        valueAnimator = ValueAnimator.ofInt((int)(mOriginalHeight + mOriginalHeight * 5.4), mOriginalHeight);
                        arrow.setImageResource(R.drawable.ic_expand_more_black_24dp);
                        expandingContent.setVisibility(View.GONE);
                        expandingContent.setEnabled(false);
                    }
                    valueAnimator.setDuration(250);
                    valueAnimator.setInterpolator(new LinearInterpolator());
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            v.getLayoutParams().height = (Integer) animation.getAnimatedValue();
                            v.requestLayout();
                        }
                    });
                    valueAnimator.start();
                }
            });
            if (!mIsViewExpanded) {
                expandingContent.setVisibility(View.GONE);
                expandingContent.setEnabled(false);
            }

        }
    }

    @Override
    public StatsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_stats, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        LineData lineData = data.get(position);
        Entry entry = lineData.getDataSetByIndex(0).getEntryForIndex(0);
        float value = (float) Math.round(10*entry.getVal())/10;
        final String valueData = String.valueOf(value);
        String s;
        if(position == 0){
            s = "Power consumption: "+valueData+" W";
            holder.txtHeader.setText(s);
        }
        else{
            s = "Temperature: "+valueData+" C"+(char) 0x00B0;
            holder.txtHeader.setText(s);
        }
        holder.expandingContent.getLegend().setEnabled(false);
        holder.expandingContent.getAxisRight().setEnabled(false);
        holder.expandingContent.setDescription("");
        holder.expandingContent.getAxisLeft().setAxisMinValue(0);
        holder.expandingContent.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        holder.expandingContent.setData(data.get(position));
        holder.txtHeader.setTypeface(Typeface.createFromAsset(holder.txtHeader.getContext().getAssets(),
                "fonts/lato.ttf"));
    }

    @Override
    public int getItemCount() {
        if(data != null) return data.size();
        return 0;
    }
}
