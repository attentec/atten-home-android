package se.attentec.attenhome;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Timer;
import java.util.TimerTask;
import se.attentec.attenhome.rest.DeviceGetStatsTask;
import se.attentec.attenhome.rest.HouseGetStatsTask;
import se.attentec.attenhome.rest.RoomGetStatsTask;

public class StatsListFragment extends Fragment {
    static String ARG_ID = "id", ARG_TYPE="type";
    private String mId, mType;
    private RecyclerView mRecyclerView;
    private Timer timer;

    public StatsListFragment() {
    }

    public static StatsListFragment newInstance(String id, String type) {
        StatsListFragment fragment = new StatsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mId = getArguments().getString(ARG_ID);
            mType = getArguments().getString(ARG_TYPE);
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        timer = new Timer();
        TimerTask task;
        switch (mType){
            case "House":
                task = new TimerTask() {
                    @Override
                    public void run() {
                        new HouseGetStatsTask(mId, mRecyclerView).execute();
                    }
                };
                break;
            case "Room":
                task = new TimerTask() {
                    @Override
                    public void run() {
                        new RoomGetStatsTask(mId, mRecyclerView).execute();
                    }
                };
                break;
            case "Device":
                task = new TimerTask() {
                    @Override
                    public void run() {
                        new DeviceGetStatsTask(mId, mRecyclerView).execute();
                    }
                };
                break;
            default:
                task = new TimerTask() {
                    @Override
                    public void run() {

                    }
                };
        }
        timer.schedule(task, 0, 2000);
    }

    @Override
    public void onPause(){
        super.onPause();
        timer.cancel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_devicelist, container, false);
        if (view instanceof RecyclerView) {
            mRecyclerView = (RecyclerView) view;
            mRecyclerView.addItemDecoration(new ListDivider(getActivity()));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

