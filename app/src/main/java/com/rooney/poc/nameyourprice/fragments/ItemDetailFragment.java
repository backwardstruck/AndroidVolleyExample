package com.rooney.poc.nameyourprice.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.rooney.poc.nameyourprice.R;
import com.rooney.poc.nameyourprice.activities.MainActivity;
import com.rooney.poc.nameyourprice.activities.NamePriceActivity;
import com.rooney.poc.nameyourprice.models.ItemContent;

public class ItemDetailFragment extends Fragment {

    private int mIndex = -1;

    public static ItemDetailFragment newInstance(int index) {
        ItemDetailFragment fragment = new ItemDetailFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);

        mIndex = getArguments().getInt("index", -1);

        //we have no data to display
        if(mIndex < 0){
            Toast.makeText(getActivity(), R.string.noDataFound, Toast.LENGTH_LONG).show();
            getActivity().onBackPressed();
            return view;
        }

        final ImageView deal_detail_image_view = (ImageView) view.findViewById(R.id.deal_detail_image_view);
        TextView deal_price = (TextView) view.findViewById(R.id.deal_price);
        TextView deal_title = (TextView) view.findViewById(R.id.deal_title);
        TextView deal_description = (TextView) view.findViewById(R.id.deal_description);
        final ImageView fullview = (ImageView) view.findViewById(R.id.fullview);



        //set values
        try {
            deal_detail_image_view.setImageBitmap(ItemContent.ITEMS.get(mIndex).imageBitmap);
            //handle null prices
            if(ItemContent.ITEMS.get(mIndex).salePrice == null){
                //name you
                deal_price.setText(getActivity().getString(R.string.name_your_price));

                deal_price.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //show name your price dialog
                        nameYourPrice();
                    }
                });



            } else {
                deal_price.setText(ItemContent.ITEMS.get(mIndex).salePrice);
            }
            deal_title.setText(ItemContent.ITEMS.get(mIndex).title);
            deal_description.setText(ItemContent.ITEMS.get(mIndex).description);
        } catch (NullPointerException e) {
            return view;
        }

        //show full image
        deal_detail_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get current image
                Bitmap bitmap = ((BitmapDrawable)deal_detail_image_view.getDrawable()).getBitmap();
                if(bitmap != null){
                    fullview.setImageBitmap(bitmap);
                    fullview.setVisibility(View.VISIBLE);
                }
            }
        });

        //hide full image
        fullview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullview.setVisibility(View.GONE);
            }
        });


        //retrieve full-size image
        getFullImage(deal_detail_image_view);

        return view;

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /*
    * Retrieve the larger sized image
    * */
    private void getFullImage(final ImageView imageView){

        String url = ItemContent.ITEMS.get(mIndex).image;


        //if we are on http://lorempixel.com/
        if(url.indexOf("lorempixel.com") > 0){
            //change the image size, etc
            url = url.substring(0,url.lastIndexOf("/"));
            //placeholder image features
            url = url.concat("/700/900/technics/On-Sale-Now-On-Sale-Now-On-Sale-Now/");
        }


        // get image for view
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                        imageView.refreshDrawableState();
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        return;
                    }
                });


        // Access the RequestQueue through your singleton class.
        ((MainActivity)getActivity()).getImage(request);



    }

    /*
    * Allow user to enter their own price
    * */
    private void nameYourPrice(){
        Intent intent = new Intent(getActivity(), NamePriceActivity.class);
        startActivityForResult(intent, NamePriceActivity.NAME_PRICE_REQUEST);

    }

    /*
    * Retrieve result from user
    * */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == NamePriceActivity.NAME_PRICE_REQUEST ) {
            if(data == null){
                //no data returned
                return;
            }
            //get user price
            String userPrice = data.getExtras().getString(NamePriceActivity.PRICE);

            //if not null, display
            if(userPrice != null){
                try {
                    TextView deal_price = (TextView) getActivity().findViewById(R.id.deal_price);
                    double priceDouble = Double.parseDouble(userPrice);
                    deal_price.setText(String.format( "$%.2f", priceDouble));
                } catch (NumberFormatException e) {
                    return;
                }
            }
        }
    }


}