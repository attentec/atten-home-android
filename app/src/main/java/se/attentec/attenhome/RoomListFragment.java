package se.attentec.attenhome;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import se.attentec.attenhome.rest.RoomGetTask;

public class RoomListFragment extends Fragment{
    private static final String ARG_HOUSE_ID = "houseId";
    private RecyclerView mRecyclerView;
    private String mHouseId;

    @Override
    public void onStart() {
        super.onStart();
        new RoomGetTask(mHouseId, mRecyclerView).execute();
    }

    public static RoomListFragment newInstance(String houseId) {
        RoomListFragment fragment = new RoomListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_HOUSE_ID, houseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHouseId = getArguments().getString(ARG_HOUSE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new ListDivider(getActivity()));
        return view;
    }
}
