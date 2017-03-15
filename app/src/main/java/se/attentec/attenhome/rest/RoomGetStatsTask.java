package se.attentec.attenhome.rest;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.github.mikephil.charting.data.LineData;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import se.attentec.attenhome.StatsListAdapter;
import se.attentec.attenhome.models.House;
import se.attentec.attenhome.models.Room;

/**
 * Created by Attentec on 22/03/16.
 */
public class RoomGetStatsTask extends StatsBaseTask<Room> {
    private String mRoomId;
    private RecyclerView mList;

    public RoomGetStatsTask(String roomId, RecyclerView list){
        mRoomId = roomId;
        mList = list;
    }

    @Override
    protected Room doInBackground(Void... params) {
        try {
            final String url = "http://188.166.33.205:3000/api/rooms/" + mRoomId;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Room room = restTemplate.getForObject(url, Room.class);
            return room;
        } catch (Exception e) {
            Log.e("RoomListFragment", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Room room) {
        List<List<Double>> list = new ArrayList<>();
        list.add(room.getPowerData());
        list.add(room.getTemperature());
        if(mList.getAdapter() == null){
            mList.setAdapter(new StatsListAdapter());
        }
        List<LineData> data = createData(list);
        StatsListAdapter adapter = (StatsListAdapter) mList.getAdapter();
        adapter.setStatsData(data);    }
}
