package com.example.assignment1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    // UI Elements
    private TextInputEditText editTextPrincipal;
    private TextInputEditText editTextInterest;
    private TextInputEditText editTextTerm;
    private TextView textViewOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Set up views
        editTextPrincipal = findViewById(R.id.editTextPrincipal);
        editTextInterest = findViewById(R.id.editTextInterest);
        editTextTerm = findViewById(R.id.editTextTerm);
        textViewOutput = findViewById(R.id.textViewOutput);
        Button buttonCalculate = findViewById(R.id.buttonCalculate);

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set button click listener
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateMortgage();
            }
        });
    }

    private void calculateMortgage() {
        // Get user inputs
        String principalInput = editTextPrincipal.getText().toString();
        String interestInput = editTextInterest.getText().toString();
        String termInput = editTextTerm.getText().toString();

        // Validate inputs
        if (TextUtils.isEmpty(principalInput) || TextUtils.isEmpty(interestInput) || TextUtils.isEmpty(termInput)) {
            Toast.makeText(this, "Empty fields not allowed!", Toast.LENGTH_SHORT).show();
            return;
        }

        double principal = Double.parseDouble(principalInput);
        double interest = Double.parseDouble(interestInput) / 100 / 12; // Convert annual rate to monthly
        int term = Integer.parseInt(termInput);

        if (principal <= 0 || interest < 0 || term <= 0) {
            Toast.makeText(this, "Values must be greater than 0!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Calculate monthly payment
        double monthlyPayment = (principal * interest * Math.pow(1 + interest, term)) / (Math.pow(1 + interest, term) - 1);

        // Display the result
        textViewOutput.setText("Monthly Payment: $" + String.format("%.2f", monthlyPayment));
    }
}
