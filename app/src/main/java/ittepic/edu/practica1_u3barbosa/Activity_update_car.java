package ittepic.edu.practica1_u3barbosa;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class Activity_update_car extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName;
    private EditText editTextBrand;
    private EditText editTextDesc;
    private EditText editTextPrice;


    private FirebaseFirestore db;

    private Carro product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_car);
        product = (Carro) getIntent().getSerializableExtra("Carros");
        db = FirebaseFirestore.getInstance();

        editTextName = findViewById(R.id.edittext_name);
        editTextBrand = findViewById(R.id.edittext_brand);
        editTextDesc = findViewById(R.id.edittext_desc);
        editTextPrice = findViewById(R.id.edittext_price);


        editTextName.setText(product.getPlaca());
        editTextBrand.setText(product.getColor());
        editTextDesc.setText(product.getMarca());
        editTextPrice.setText(product.getTipo());

        findViewById(R.id.button_update).setOnClickListener(this);
        findViewById(R.id.button_delete).setOnClickListener(this);
    }
    private boolean hasValidationErrors(String placa, String color, String marca, String tipo) {
        if (placa.isEmpty()) {
            editTextName.setError("Requerido");
            editTextName.requestFocus();
            return true;
        }

        if (color.isEmpty()) {
            editTextBrand.setError("Requerido");
            editTextBrand.requestFocus();
            return true;
        }

        if (marca.isEmpty()) {
            editTextDesc.setError("Requerido");
            editTextDesc.requestFocus();
            return true;
        }
        if (tipo.isEmpty()) {
            editTextPrice.setError("Requerido");
            editTextPrice.requestFocus();
            return true;
        }




        return false;
    }
    private void updateProduct() {
        String placa = editTextName.getText().toString().trim();
        String color = editTextBrand.getText().toString().trim();
        String marca = editTextDesc.getText().toString().trim();
        String tipo = editTextPrice.getText().toString().trim();


        if (!hasValidationErrors(placa, color, marca, tipo)) {

            Carro p = new Carro(
                    placa, color, marca,tipo

            );


            db.collection("Carros").document(product.getId())
                    .update(
                            "placa", p.getPlaca(),
                            "color", p.getColor(),
                            "marca", p.getMarca(),
                            "tipo", p.getTipo())

                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Activity_update_car.this, "Carro actualizado", Toast.LENGTH_LONG).show();
                            Intent intent= new Intent(Activity_update_car.this,MainActivity.class);
                            startActivity(intent);
                        }
                    });
        }
    }
    private void deleteProduct() {
        db.collection("Carros").document(product.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Activity_update_car.this, "Carro eliminado", Toast.LENGTH_LONG).show();
                            Intent i= new Intent(Activity_update_car.this,MainActivity.class);
                            startActivity(i);
                            finish();

                           // startActivity(new Intent(Activity_update_car.this, Activity_carros.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_update:
                updateProduct();
                break;
            case R.id.button_delete:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Estas seguro?");
                builder.setMessage("Deletion is permanent...");

                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProduct();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();

                break;
        }
    }
}
