package com.rooney.dealbrowserpoc.nameyourprice.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rooney.dealbrowserpoc.nameyourprice.R;
import com.rooney.dealbrowserpoc.nameyourprice.models.DealItem;

import java.util.List;
import java.util.Locale;

public class DealListItemAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<DealItem> dealItems;
    private Context context;


    public static DealListItemAdapter newInstance(Context context, List<DealItem> items) {
        return new DealListItemAdapter(context, items);
    }

    public DealListItemAdapter(Context ctx, List<DealItem> items) {
        super();
        context = ctx;
        if(context != null){
            inflater = LayoutInflater.from(context);
        }
        dealItems = items;
    }

    @Override
    public int getCount() {
        return dealItems.size();
    }

    @Override
    public Object getItem(int position){
        try {
            return dealItems.get(position);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if(convertView == null) {
            view = inflater.inflate(R.layout.deal_list_item, parent, false);
            holder = new ViewHolder();
            holder.productImage = (ImageView)view.findViewById(R.id.deal_list_item_image_view);
            holder.title = (TextView)view.findViewById(R.id.deal_list_item_title);
            holder.price = (TextView)view.findViewById(R.id.deal_list_item_price);
            holder.aisle = (TextView)view.findViewById(R.id.deal_list_item_aisle);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

        //set values from data
        DealItem dealItem = dealItems.get(position);
        holder.productImage.setImageBitmap(dealItem.imageBitmap);
        holder.title.setText(dealItem.title);
        String price = null;
        if(dealItem.salePrice != null){
            price = dealItem.salePrice;
        } else {
            price = dealItem.price;
        }
        if(price != null){
            holder.price.setText(price);
        }
        if(dealItem.aisle != null){
            holder.aisle.setText(dealItem.aisle.toUpperCase(Locale.US));
        }

        return view;
    }

    private class ViewHolder {
        public ImageView productImage;
        public TextView title, price, aisle;
    }
}
