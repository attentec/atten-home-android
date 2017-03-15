package se.attentec.attenhome.rest;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import se.attentec.attenhome.RoomListAdapter;
import se.attentec.attenhome.models.Room;

public class RoomGetTask extends AsyncTask<Void, Void, Room[]> {
    private String mHouseId;
    private RecyclerView mListView;

    public RoomGetTask(String houseId, RecyclerView list){
        mHouseId = houseId;
        mListView = list;
    }

    @Override
    protected Room[] doInBackground(Void... params) {
        try {
            final String url = "http://188.166.33.205:3000/api/rooms?houseId=" + mHouseId;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return restTemplate.getForObject(url, Room[].class);
        } catch (Exception e) {
            Log.e("RoomGetTask", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Room[] rooms) {
        mListView.setAdapter(new RoomListAdapter(rooms));
    }
}
