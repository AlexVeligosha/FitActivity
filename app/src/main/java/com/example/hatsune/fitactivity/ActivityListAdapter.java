package com.example.hatsune.fitactivity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hatsune.fitactivity.dto.ActivityDTO;

import java.util.LinkedList;
import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Created by hatsune on 17.12.16.
 */

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ActivitiViewHolder>{
    List<ActivityDTO> data;

    public ActivityListAdapter(List<ActivityDTO> data) {
        this.data = data;
    }

    @Override
    public ActivitiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        return new ActivitiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivitiViewHolder holder, int position) {
        ActivityDTO item = data.get(position);
        holder.duration.setText(item.getDuration() + " min");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ActivitiViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;

        TextView duration;
        public ActivitiViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            duration = (TextView) itemView.findViewById(R.id.duration);
        }

    }

    public void setData(List<ActivityDTO> data) {
        this.data = data;
    }
}
