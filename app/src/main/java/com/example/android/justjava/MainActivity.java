package com.example.android.justjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayQantity(quantity);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            Toast.makeText(MainActivity.this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }else {
            quantity++;
            displayQantity(quantity);
        }
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1){
            Toast.makeText(MainActivity.this, "You cannot have less than 1 coffees", Toast.LENGTH_SHORT).show();
            return;
        }else {
            quantity--;
            displayQantity(quantity);
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameEditText = findViewById(R.id.nameEditText);
        String name = nameEditText.getText().toString();

        CheckBox whippedCreamCheckBox = findViewById(R.id.whippedCreamCheckBox);
        boolean whippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = findViewById(R.id.chocolateCheckBox);
        boolean chocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(whippedCream, chocolate);

        String message = createOrderSummary(name, price, whippedCream, chocolate);
        displayMessage(message);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, "eng.ahmed.86313@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.orderSummaryTextView);
        orderSummaryTextView.setText(message);
    }

    /**
     * Calculate the price of the order
     *
     * @param whippedCream is whether or not the user wants whipped cream topping
     * @param chocolate is whether or not the user wants chocolate topping
     * @return total price
     */
    private int calculatePrice(boolean whippedCream, boolean chocolate) {
        int price = 5;
        if (whippedCream) {
            price += 1;
        }
        if(chocolate) {
            price += 2;
        }
        return quantity * price;
    }

    /**
     * Create summary of the order
     *
     * @param price of the order
     * @param whippedCream is whether or not the user wants whipped cream topping
     * @param chocolate is whether or not the user wants chocolate topping
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean whippedCream, boolean chocolate){
        String priceMessage = getString(R.string.name) + " " + name;
        priceMessage += "\n" + getString(R.string.whippedCreamString) + " " + whippedCream;
        priceMessage += "\n" + getString(R.string.chocolateString) + " " + chocolate;
        priceMessage += "\n" + getString(R.string.quantityString) + " " + quantity;
        priceMessage += "\n" + getString(R.string.price) + " " + price;
        priceMessage += "\n" + getString(R.string.thankYou) ;
        return priceMessage;
    }


}
