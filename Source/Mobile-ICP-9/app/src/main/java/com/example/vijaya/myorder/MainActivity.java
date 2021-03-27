package com.example.vijaya.myorder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_TAG = "MainActivity";
    final int PIZZA_PRICE = 10;
    final int CHEESE_PRICE = 1;
    final int CHICKEN_PRICE = 2;
    final int PEPPERONI_PRICE = 2;
    final int BEEF_PRICE = 2;

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button order = (Button) findViewById(R.id.btnOrder);
        Button summary = (Button) findViewById(R.id.btnSummary);

        // Order Button
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send order summary to user by email
                submitOrder(null);
            }
        });

        // Summary Button
        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show user their order summary
                submitSummary(null);
            }
        });
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {

        // get user input
        EditText userNameView = (EditText) findViewById(R.id.userName);
        String userName = userNameView.getText().toString();

        // get user email
        EditText userMailView = (EditText) findViewById(R.id.userMail);
        String userMail = userMailView.getText().toString();

        // check if cheese is selected
        CheckBox cheese = (CheckBox) findViewById(R.id.cheese_checked);
        boolean hasCheese = cheese.isChecked();

        // check if pepperoni is selected
        CheckBox pepperoni = (CheckBox) findViewById(R.id.pepperoni_checked);
        boolean hasPepperoni = pepperoni.isChecked();

        // check if beef is selected
        CheckBox beef = (CheckBox) findViewById(R.id.beef_checked);
        boolean hasBeef = beef.isChecked();

        // check if chicken is selected
        CheckBox chicken = (CheckBox) findViewById(R.id.chicken_checked);
        boolean hasChicken = chicken.isChecked();

        // calculate and store the total price
        float totalPrice = calculatePrice(hasCheese, hasPepperoni, hasBeef, hasChicken);

        // create and store the order summary
        String orderSummaryMessage = createOrderSummary(userName, hasCheese, hasPepperoni, hasBeef, hasChicken, totalPrice);

        sendEmail(userMail, orderSummaryMessage);

    }

    /**
     * This method is called when the summary button is clicked.
     */

    public void submitSummary(View view) {

        // get user input
        EditText userNameView = (EditText) findViewById(R.id.userName);
        String userName = userNameView.getText().toString();

        // check if cheese is selected
        CheckBox cheese = (CheckBox) findViewById(R.id.cheese_checked);
        boolean hasCheese = cheese.isChecked();

        // check if pepperoni is selected
        CheckBox pepperoni = (CheckBox) findViewById(R.id.pepperoni_checked);
        boolean hasPepperoni = pepperoni.isChecked();

        // check if beef is selected
        CheckBox beef = (CheckBox) findViewById(R.id.beef_checked);
        boolean hasBeef = beef.isChecked();

        // check if chicken is selected
        CheckBox chicken = (CheckBox) findViewById(R.id.chicken_checked);
        boolean hasChicken = chicken.isChecked();

        // calculate and store the total price
        float totalPrice = calculatePrice(hasCheese, hasPepperoni, hasBeef, hasChicken);

        // create and store the order summary
        String orderSummaryMessage = createOrderSummary(userName, hasCheese, hasPepperoni, hasBeef, hasChicken, totalPrice);

        // Start a new activity and display order information
        Intent summary = new Intent(MainActivity.this, Summary.class);
        summary.putExtra(Intent.EXTRA_TEXT, orderSummaryMessage);
        startActivity(summary);

    }

    public void sendEmail(String mail, String output) {

        // Write the relevant code for triggering email
        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:"));
        email.putExtra(Intent.EXTRA_EMAIL, new String[] {mail});
        email.putExtra(Intent.EXTRA_SUBJECT, "Your Order Summary.");
        email.putExtra(Intent.EXTRA_TEXT, output);
        startActivity(email);

    }

    private String boolToString(boolean bool) {
        return bool ? (getString(R.string.yes)) : (getString(R.string.no));
    }

    private String createOrderSummary(String userName, boolean hasCheese, boolean hasPepperoni,
                                      boolean hasBeef, boolean hasChicken, float price) {
        String orderSummaryMessage = getString(R.string.order_summary_name, userName) + "\n" +
                getString(R.string.order_summary_cheese, boolToString(hasCheese)) + "\n" +
                getString(R.string.order_summary_pepperoni, boolToString(hasPepperoni)) + "\n" +
                getString(R.string.order_summary_beef, boolToString(hasBeef)) + "\n" +
                getString(R.string.order_summary_chicken, boolToString(hasChicken)) + "\n" +
                getString(R.string.order_summary_quantity, quantity) + "\n" +
                getString(R.string.order_summary_total_price, price) + "\n" +
                getString(R.string.thank_you);
        return orderSummaryMessage;
    }

    /**
     * Method to calculate the total price
     *
     * @return total Price
     */
    private float calculatePrice(boolean hasCheese, boolean hasPepperoni, boolean hasBeef, boolean hasChicken) {
        int basePrice = PIZZA_PRICE;
        if (hasCheese) {
            basePrice += CHEESE_PRICE;
        }
        if (hasPepperoni) {
            basePrice += PEPPERONI_PRICE;
        }
        if (hasBeef) {
            basePrice += BEEF_PRICE;
        }
        if (hasChicken) {
            basePrice += CHICKEN_PRICE;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    @SuppressLint("SetTextI18n")
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method increments the quantity of pizza by one
     *
     * @param view on passes the view that we are working with to the method
     */

    public void increment(View view) {
        if (quantity < 20) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select less than twenty pizzas");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_much_pizza);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
        }
    }

    /**
     * This method decrements the quantity of pizza by one
     *
     * @param view passes on the view that we are working with to the method
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select at least one pizza");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_little_pizza);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
        }
    }

}