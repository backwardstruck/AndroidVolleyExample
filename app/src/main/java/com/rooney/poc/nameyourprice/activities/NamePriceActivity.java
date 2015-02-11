package com.rooney.poc.nameyourprice.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.rooney.poc.nameyourprice.R;

public class NamePriceActivity extends Activity {

    private RequestQueue queue;
    public static final int NAME_PRICE_REQUEST = 1001;
    public static final String PRICE = "user price";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_price);

        //set listener and input filter
        final EditText priceText = (EditText) findViewById(R.id.price);


        priceText.setFilters(new InputFilter[] {
                new DigitsKeyListener(Boolean.FALSE, Boolean.TRUE) {
                    int beforeDecimal = 8, afterDecimal = 2;

                    @Override
                    public CharSequence filter(CharSequence source, int start, int end,
                                               Spanned dest, int dstart, int dend) {
                        String temp = priceText.getText() + source.toString();

                        //do not allow zero or just a decimal
                        if (temp.equals(".")) {
                            return "0.";
                        } else if (temp.equals("0")) {
                            return "";
                        }
                        else if (temp.toString().indexOf(".") == -1) {
                            if (temp.length() > beforeDecimal) {
                                return "";
                            }
                        } else {
                            temp = temp.substring(temp.indexOf(".") + 1);
                            if (temp.length() > afterDecimal) {
                                return "";
                            }
                        }

                        return super.filter(source, start, end, dest, dstart, dend);
                    }
                }
        });

        FrameLayout submit = (FrameLayout) findViewById(R.id.submit_price);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptToSubmit();
            }
        });




        TextView.OnEditorActionListener exampleListener = new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event) {
                attemptToSubmit();
                return true;
            }
        };

        //set IME listener
        priceText.setOnEditorActionListener(exampleListener);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Don't create the menu
        return false;
    }



   @Override
   public void onResume(){
       super.onResume();



   }

    /*
    * Validate user entered price
    * */
    private boolean validPrice(String price){
        try {
            if(price != null){
                if(Double.parseDouble(price) > 1){
                    return true;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    /*
    * Show prompt
    * */
    private void showPrompt(int message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /*
    * Show prompt
    * */
    private void closeDialog(String value){
        Intent intent = new Intent();
        intent.putExtra(PRICE, value);
        setResult(NAME_PRICE_REQUEST, intent);
        finish();
    }


    /*
     * Show prompt
     * */
    private void attemptToSubmit() {
        final EditText priceText = (EditText) findViewById(R.id.price);

        //validate entry
        String priceString = priceText.getText().toString();
        if (validPrice(priceString)) {
            closeDialog(priceString);
        } else {
            showPrompt(R.string.enter_new_price);
        }
    }


}
