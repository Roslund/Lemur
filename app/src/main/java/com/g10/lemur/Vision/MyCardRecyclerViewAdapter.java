package com.g10.lemur.Vision;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.g10.lemur.R;
import com.g10.lemur.Vision.CardFragment.OnListFragmentInteractionListener;
import com.g10.lemur.Vision.content.VisionContent.VisionItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link VisionItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCardRecyclerViewAdapter extends RecyclerView.Adapter<MyCardRecyclerViewAdapter.ViewHolder> {

    public List<VisionItem> mValues;
    public OnListFragmentInteractionListener mListener;

    public MyCardRecyclerViewAdapter(List<VisionItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == 1)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.vision_labels_card, parent, false);
        }
        else if (viewType == 2)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.vision_labels_card, parent, false);
        }
        else if (viewType == 3)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.vision_colors_card, parent, false);
        }
        else
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.vision_labels_card, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (mValues.get(position).type == "Labels") {
            return 1;
        }
        else if (mValues.get(position).type == "Safe")
        {
            return 2;
        }
        else if (mValues.get(position).type == "Colors")
        {
            return 3;
        }
        else
        {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.cardTitle.setText(mValues.get(position).title);
        if (mValues.get(position).type != "Colors")
        {
            holder.cardContent.setText(mValues.get(position).content);
        }
        else
        {
            String colorString = mValues.get(position).content;
            String[] colorAndPercent = colorString.split(";");
            String color;
            String percent;
            int sum = 0;
            ImageView im;

            for (String a:
                    colorAndPercent)
            {
                im = new ImageView(holder.colorsLL.getContext());
                percent = a.split("#")[0];
                color = a.split("#")[1];
                sum += Integer.valueOf(percent);

                im.setBackgroundColor(Color.parseColor("#"+color+""));
                holder.colorsLL.addView(im, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, Float.valueOf(percent)));
            }
            holder.colorsLL.setWeightSum(sum);

        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView cardTitle;
        public LinearLayout colorsLL;
        public TextView cardContent;
        public VisionItem mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            cardTitle = (TextView) view.findViewById(R.id.id);
            colorsLL = (LinearLayout) view.findViewById(R.id.colorsCard);
            cardContent = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + cardContent.getText() + "'";
        }
    }
}
