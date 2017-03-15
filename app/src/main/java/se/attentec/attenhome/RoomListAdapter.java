package se.attentec.attenhome;

import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import se.attentec.attenhome.models.Room;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.ViewHolder> {
    private Room[] mDataset;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHeader;
        public TextView powerData;
        public TextView temperature;
        private RelativeLayout clicky;

        public ViewHolder(View v) {
            super(v);
            clicky = (RelativeLayout) v.findViewById(R.id.clicky_room_container);
            txtHeader = (TextView) v.findViewById(R.id.name);
            powerData = (TextView) v.findViewById(R.id.power_data);
            temperature = (TextView) v.findViewById(R.id.temp_data);
        }
    }

    public RoomListAdapter(Room[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public RoomListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String roomName = mDataset[position].getName();
        int powerInt = (int) Math.round(mDataset[position].getPowerData().get(mDataset[position].getPowerData().size() - 1));
        final String powerData = String.valueOf(powerInt);
        int tempInt = (int) Math.round(mDataset[position].getTemperature().get(mDataset[position].getTemperature().size()-1));
        final String temperature = String.valueOf(tempInt);
        holder.txtHeader.setText(roomName);
        holder.txtHeader.setTypeface(Typeface.createFromAsset(holder.txtHeader.getContext().getAssets(), "fonts/lato.ttf"));
        holder.clicky.setOnClickListener(new RoomClickListener(mDataset[position]));
        String s = powerData+" W";
        holder.powerData.setText(s);
        holder.powerData.setTypeface(Typeface.createFromAsset(holder.txtHeader.getContext().getAssets(), "fonts/lato.ttf"));
        s = temperature+" C"+(char) 0x00B0;
        holder.temperature.setText(s);
        holder.temperature.setTypeface(Typeface.createFromAsset(holder.txtHeader.getContext().getAssets(), "fonts/lato.ttf"));

    }

    @Override
    public int getItemCount() {
        if(mDataset != null) return mDataset.length;
        return 0;
    }


    private class RoomClickListener implements OnClickListener{
        private Room mRoom;

        public RoomClickListener(Room room){
            mRoom = room;
        }

        @Override
        public void onClick(View v) {
            Log.d("Pressed room", mRoom.getName());
            Intent intent=new Intent(v.getContext(),RoomActivity.class);
            intent.putExtra("room", mRoom);
            v.getContext().startActivity(intent);
        }
    }
}
