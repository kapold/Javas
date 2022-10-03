package com.example.lw2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cost_tv2 = findViewById(R.id.cost_TV2);
        cost_tv4 = findViewById(R.id.cost_TV4);
        cost_et2 = findViewById(R.id.cost_ET2);
        average_et = findViewById(R.id.average_ET);
        firstChecked = false;
        secondChecked = true;
        distanceTraveled = findViewById(R.id.total_distance);
        fuelCost = findViewById(R.id.cost_ET);
        averageFuelConsumption = findViewById(R.id.average_ET);
        usedUp = findViewById(R.id.cost_ET2);

        averageConsumption_TV = findViewById(R.id.averageConsumption_TV);
        totalFuel_TV = findViewById(R.id.totalFuel_TV);
    }
    TextView cost_tv2;
    TextView cost_tv4;
    EditText cost_et2;
    EditText average_et;
    boolean firstChecked;
    boolean secondChecked;
    TextView distanceTraveled;
    TextView fuelCost;
    EditText averageFuelConsumption;
    EditText usedUp;
    TextView averageConsumption_TV;
    TextView totalFuel_TV;

    public void onRadioButtonClicked(View view) {
        // если переключатель отмечен
        boolean checked = ((RadioButton) view).isChecked();
        // Получаем нажатый переключатель
        switch(view.getId()) {
            case R.id.totalConsumption_RB:
                if (checked){
                    cost_tv4.setVisibility(View.VISIBLE);
                    average_et.setVisibility(View.VISIBLE);
                    cost_tv2.setVisibility(View.INVISIBLE);
                    cost_et2.setVisibility(View.INVISIBLE);
                    firstChecked = true;
                    secondChecked = false;
                    averageConsumption_TV.setVisibility(View.INVISIBLE);
                    totalFuel_TV.setVisibility(View.VISIBLE);
                    PrintResult(0, 0, 0);
                }
                break;
            case R.id.averageConsumption_RB:
                if (checked){
                    cost_tv4.setVisibility(View.INVISIBLE);
                    average_et.setVisibility(View.INVISIBLE);
                    cost_tv2.setVisibility(View.VISIBLE);
                    cost_et2.setVisibility(View.VISIBLE);
                    firstChecked = false;
                    secondChecked = true;
                    averageConsumption_TV.setVisibility(View.VISIBLE);
                    totalFuel_TV.setVisibility(View.INVISIBLE);
                    PrintResult(0, 0, 0);
                }
                break;
        }
    }

    public void PrintResult(float r1, float r2, float r3)
    {
        TextView tv1 = findViewById(R.id.totalFuel_TV);
        TextView tv2 = findViewById(R.id.costFuel_TV);
        TextView tv3 = findViewById(R.id.averageConsumption_TV);
        tv2.setText(String.format("Стоимость (руб): %s", r2));
        if (firstChecked)
            tv1.setText(String.format("Израсходовано топлива (л): %s", r1));
        if (secondChecked)
            tv3.setText(String.format("Средний расход (л/100км): %s", r3));
    }

    public void CalculateFuel_Btn(View view)
    {
        float r1 = 0, r2 = 0, r3 = 0; // r1 - израсходавано, r2 - стоимость топлива, r3 - средний расход
        try {
            if (firstChecked)
            {
                r1 = Float.parseFloat(averageFuelConsumption.getText().toString()) * Float.parseFloat(distanceTraveled.getText().toString()) / 100;
                r2 = Float.parseFloat(distanceTraveled.getText().toString()) * Float.parseFloat(fuelCost.getText().toString());

                PrintResult(r1, r2, r3);
                return;
            }
            else if (secondChecked)
            {
                r2 = Float.parseFloat(usedUp.getText().toString()) * Float.parseFloat(fuelCost.getText().toString());
                r3 = Float.parseFloat(usedUp.getText().toString()) / Float.parseFloat(distanceTraveled.getText().toString()) * 100;

                PrintResult(r1, r2, r3);
                return;
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}