package com.example.hp.in_a_click.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.in_a_click.R;
import com.example.hp.in_a_click.activities.HomeItemSettings;
import com.example.hp.in_a_click.model.HomeItem;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolders> implements View.OnClickListener, Filterable {
    protected Context context;
    private List<HomeItem> homeItemList, homeItemListFiltered = null;
    private RecyclerView recyclerView = null;

    public RecyclerViewAdapter(Context context, List<HomeItem> homeItemList, RecyclerView recyclerView) {
        this.homeItemList = homeItemList;
        this.homeItemListFiltered = homeItemList;
        this.context = context;
        this.recyclerView = recyclerView;

    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolders viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_home_item, parent, false);
        layoutView.setOnClickListener(this);
        viewHolder = new RecyclerViewHolders(layoutView, homeItemList);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {

        HomeItem homeItem = this.homeItemList.get(position);


        holder.tvDate.setText(homeItem.getInsertDate());
        holder.tvAddress.setText(homeItem.getLocationName());
        holder.tvHomeCatName.setText(homeItem.getCatName());


    }

    @Override
    public int getItemCount() {
        //return this.homeItemList.size();
        return this.homeItemListFiltered.size();
    }


    @Override
    public void onClick(View v) {

        int itemPosition = recyclerView.indexOfChild(v);
        HomeItem homeItem = this.homeItemList.get(itemPosition);

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                Log.e("QueryString", charString);
                if (charString.isEmpty()) {
                    homeItemListFiltered = homeItemList;
                } else {
                    List<HomeItem> filteredList = new ArrayList<>();
                    for (HomeItem row : homeItemList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match

                        if (row.getCatName().equalsIgnoreCase(charString)) {
                            filteredList.add(row);
                            Log.e("OkAhmed201300", "ok");
                        }
                    }

                    homeItemListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = homeItemListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                homeItemListFiltered = (ArrayList<HomeItem>) filterResults.values;
                Log.e("homeItemListFiltered", homeItemListFiltered.size()+"");
                notifyDataSetChanged();
            }
        };
    }

    class RecyclerViewHolders extends RecyclerView.ViewHolder {

        TextView tvHomeCatName, tvDate, tvAddress;
        ImageView imageViewOptions;
        private List<HomeItem> homeItemList;


        public RecyclerViewHolders(final View itemView, final List<HomeItem> homeItemList) {
            super(itemView);
            this.homeItemList = homeItemList;
            //init views
            tvHomeCatName = itemView.findViewById(R.id.tvHomeCatName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDate = itemView.findViewById(R.id.tvInsertDate);
            imageViewOptions = itemView.findViewById(R.id.ivOptions);
//            imageViewOptions.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showPopUpMenu(v, homeItemList.get(getAdapterPosition()));
//                }
//            });


        }
    }


}