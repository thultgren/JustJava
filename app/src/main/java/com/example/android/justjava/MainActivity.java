package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int quantity = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Comment
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String OrderSummary = createOrderSummary();

        EditText NameEditText = (EditText) findViewById(R.id.name_edit_text);
        String Name = NameEditText.getText().toString();

        displayMessage(OrderSummary);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","thultgren4@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order for " + Name);
        emailIntent.putExtra(Intent.EXTRA_TEXT, OrderSummary);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }

    public String createOrderSummary() {

        EditText NameEditText = (EditText) findViewById(R.id.name_edit_text);
        String Name = NameEditText.getText().toString();

        CheckBox WhippedCreamCheck = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        String WhippedYesNo = "No";

        CheckBox ChocolateCheck = (CheckBox) findViewById(R.id.chocolate_check_box);
        String ChocolateYesNo = "No";

        if (WhippedCreamCheck.isChecked() == true) {
            WhippedYesNo = "Yes";
        }

        if (ChocolateCheck.isChecked() == true) {
            ChocolateYesNo = "Yes";
        }

        return "Name: " + Name +
                "\nAdd Whipped Cream? " + WhippedYesNo +
                "\nAdd Chocolate? " + ChocolateYesNo +
                "\nQuantity: " + quantity +
                "\nTotal: $" + calculatePrice(quantity) +
                "\nThank You!";
    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity++;
        }
        else {
            Toast.makeText(getApplicationContext(), "You cannot order more than 100 cups of coffee.", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
        displayPrice(quantity);
    }

    public void decrement(View view) {
        if (quantity > 1) {
            quantity--;
        }
        else {
            Toast.makeText(getApplicationContext(), "You cannot order less than 1 cup of coffee.", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
        displayPrice(quantity);
    }



    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public void checkboxPrice(View view) {
        displayPrice(quantity);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(calculatePrice(number)));
    }

    private void displayPriceOrdered(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText("Order Submitted. Total: " + NumberFormat.getCurrencyInstance().format(calculatePrice(number)));
    }

    private int calculatePrice(int quantity) {
        int price = 5;

        CheckBox WhippedCreamCheck = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        String WhippedYesNo = "No";

        CheckBox ChocolateCheck = (CheckBox) findViewById(R.id.chocolate_check_box);
        String ChocolateYesNo = "No";

        if (WhippedCreamCheck.isChecked() == true) {
            price += 1;
        }

        if (ChocolateCheck.isChecked() == true) {
            price += 2;
        }

        price = price * quantity;

        return price;
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}