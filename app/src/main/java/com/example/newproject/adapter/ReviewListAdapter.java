package com.example.newproject.adapter;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newproject.R;
import com.example.newproject.model.Review;

import java.util.List;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {

    Context context;
    List<Review> reviewList;

    public ReviewListAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reviews_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(reviewList.get(position));

        holder.tvreview_item_name.setText(reviewList.get(position).getR_name());
        holder.tvreview_item_message.setText(reviewList.get(position).getR_message());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.tvreview_item_message.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }


    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvreview_item_name;
        TextView tvreview_item_message;

        public ViewHolder(View itemView) {
            super(itemView);
            tvreview_item_name=itemView.findViewById(R.id.tvreview_item_name);
            tvreview_item_message=itemView.findViewById(R.id.tvreview_item_message);
        }
    }

}
