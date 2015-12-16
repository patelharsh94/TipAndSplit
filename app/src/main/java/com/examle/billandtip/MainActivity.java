package com.examle.billandtip;
/*
* This is the ad free version of the tip and bill calculator app,
* Project Name: TipAndBill
* Author: Harsh Patel
* System Tested on: LG Nexus 5
* */

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

    public static EditText total;               // The total cost box.
    public static TextView tipTotal;            // To show the final total after the tip.
    public static TextView tipVal;              // To show the tip value to the user.
    public static TextView numPeople;           // To show the number of people to split by.
    public static TextView perPersonBill;       // To show the per person bill.
    public static SeekBar  tipSeek;             // The SeekBar for tipping.
    public static SeekBar  splitSeek;           // The SeekBar for splitting the tip.
    public static double finalTotal;            // The final total.
    public static int numberOfPeople;           // The number of people we want to split by.
    public static int finalTip;                 // The final tip.
    /*
    *  This method initializes all the variables, and gets the reference to them.
    *
    */
    public void initialize()
    {
        finalTotal = 0.0;                       // Initializing the variables.
        numberOfPeople = 1;
        finalTip = 0;
                                                // initializing the tip value.
        tipVal = (TextView)findViewById(R.id.tipVal);
                                                // the output after the tip is added..
        tipTotal = (TextView)findViewById(R.id.tipTotal);
                                                // the number of people we want to split the bill between.
        numPeople = (TextView)findViewById(R.id.numPeople);
                                                // This is the per person bill.
        perPersonBill = (TextView)findViewById(R.id.perPersonBill);
                                                // total = input
        total = (EditText)findViewById(R.id.total);
                                                // the seek bar for tip
        tipSeek = (SeekBar)findViewById(R.id.tipSeek);
                                                // Seek bar for number of people to split between.
        splitSeek = (SeekBar)findViewById(R.id.splitSeek);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();                           // Initialize all the variables and the GUI.
                                                // Adding listeners to all the inputs.
        total.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                tipTotal.setText("$ "+total.getText());

                if(total.getText().toString().matches(""))          // If nothing entered, then
                {                                                   // final total = 0
                    finalTotal = 0.0;
                }
                else                                                // else read the entered value.
                {
                    finalTotal = Double.parseDouble(total.getText().toString());
                }

                perPersonBill.setText("$ "+total.getText());        // Set the new total.
                CalculateTotalAfterTip(finalTotal,finalTip);        // Calculate the tip as text changes.
                CalculatePerPersonTotal();                          // Calculate the per person total.

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        tipSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {

            int progress = 0;                           // The progress of the tip seek bar.
            String Total;                               // The total amount of money entered by the user.
            double currTotal = 0.0;                     // The current total after accounting the tip.

            @Override
            public void onProgressChanged(SeekBar seekBar, int Progress, boolean fromUser)
            {
                progress = Progress;                    // Initializing the values.
                finalTip = progress;
                Total = total.getText().toString();

                                                        // make sure that we have a total amount..
                if(Total.matches(""))
                {
                    total.setError("Total can't be empty");
                }
                                                        // Calculate the new total!
                else
                {
                    // get the total value and calculating the tip.
                    currTotal = Double.parseDouble(total.getText().toString());
                    finalTotal = currTotal;
                    CalculateTotalAfterTip(currTotal,progress);
                    CalculatePerPersonTotal();

                }
                tipVal.setText(progress+" %");          // updating the text.
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                tipVal.setText(progress+" %");          // Initial tip%
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                tipVal.setText(progress+" %");          // Final tip%.
            }
        });
                                                        // Adding change listener for split SeekBar.
        splitSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int Progress, boolean fromUser)
            {
                progress = Progress+1;                  // Increment progress by 1's

                if(progress == 1)                       // If the progress was 1 : 1 person
                {
                    perPersonBill.setText(tipTotal.getText());
                    numberOfPeople = progress;
                    numPeople.setText(progress + " person");
                }
                else                                    // Else more than one person : people.
                {
                    numberOfPeople = progress;
                    numPeople.setText(progress + " people");
                    CalculatePerPersonTotal();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
    }
    // This method calculates the total tip, after the tip is entered.
    public void CalculateTotalAfterTip(double currTotal, int tip)
    // PRE: currTotal and tip must be initialized.
    // POST: Calculates the total after the tip has been entered.
    {
           double currTip;                // The current tip.

           currTip = (double)tip;
                                          // Calculate the total after the tip.
           finalTotal = currTotal * (currTip/100) + currTotal;

           tipTotal.setText("$ " + String.format("%.2f", finalTotal));
           CalculatePerPersonTotal();     // Calculate the per person total after the new amount
                                          // has been calculated.
    }
    // This method calculates the per person total.
    public void CalculatePerPersonTotal()
    {
        double perPerson;                 // This is the perPerson bill.

        perPerson = finalTotal / numberOfPeople;    // Calculate the bill for each person.
                                                    // And update the text.
        perPersonBill.setText("$ " + String.format("%.2f",perPerson));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    /*
    * This method allows us to manipulate the over flow menu,
    * It will give the user to select a theme for the app.
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }
}
