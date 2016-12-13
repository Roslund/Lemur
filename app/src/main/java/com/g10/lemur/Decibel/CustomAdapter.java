package com.g10.lemur.Decibel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.g10.lemur.R;

/**
 * Created by William on 2016-12-11.
 */

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private String[] mDataset;
    private String[] mDescription;
    private int[] mDataSetTypes;

    public static final int DATA = 0;
    public static final int GRAPH = 1;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View v) {
            super(v);

        }
    }

    public class DataViewHolder extends ViewHolder{
        TextView data;
        TextView description;
        ImageView tempImage;

        public DataViewHolder(View v){
            super(v);
            this.data = (TextView) v.findViewById(R.id.dataText);
            this.description = (TextView) v.findViewById(R.id.descriptiveText);
            this.tempImage = (ImageView) v.findViewById(R.id.thumbnail);
        }
    }

    public class GraphViewHolder extends ViewHolder {
        ImageView temp;

        public GraphViewHolder (View v) {
            super(v);
            this.temp = (ImageView) v.findViewById(R.id.graphPic);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CustomAdapter(String[] dataset, String[] description, int[] dataSetTypes) {

        mDataset = dataset;
        mDescription = description;
        mDataSetTypes = dataSetTypes;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create new view/views
        if(viewType == DATA){

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.data_card, parent, false);
            return new DataViewHolder(v);

        }
        else {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.graph_card, parent, false);

            return new GraphViewHolder(v);

        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(holder.getItemViewType() == DATA){

            DataViewHolder dataHolder = (DataViewHolder) holder;

            dataHolder.data.setText(mDataset[position]);
            dataHolder.description.setText(mDescription[position]);
            dataHolder.tempImage.setImageResource(R.drawable.sound);


        } else if (holder.getItemViewType() == GRAPH){

            GraphViewHolder graphHolder = (GraphViewHolder) holder;

            graphHolder.temp.setImageResource(R.drawable.graph);

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSetTypes[position];
    }
}