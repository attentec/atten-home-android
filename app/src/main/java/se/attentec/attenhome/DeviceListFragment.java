package se.attentec.attenhome;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import se.attentec.attenhome.rest.DeviceGetByRoomTask;

public class DeviceListFragment extends Fragment {
    private static String ARG_ROOM_ID = "roomId";
    private String mRoomId;
    private RecyclerView mRecyclerView;

    public DeviceListFragment() {
    }

    public static DeviceListFragment newInstance(String roomId) {
        DeviceListFragment fragment = new DeviceListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ROOM_ID, roomId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRoomId = getArguments().getString(ARG_ROOM_ID);
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        new DeviceGetByRoomTask(mRoomId, mRecyclerView).execute();
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
