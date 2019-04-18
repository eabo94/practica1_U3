package ittepic.edu.practica1_u3barbosa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName;
    private EditText editTextBrand;
    private EditText editTextDesc;
    private EditText editTextPrice;


    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        editTextName = findViewById(R.id.edittext_name);
        editTextBrand = findViewById(R.id.edittext_brand);
        editTextDesc = findViewById(R.id.edittext_desc);
        editTextPrice = findViewById(R.id.edittext_price);

        findViewById(R.id.button_save).setOnClickListener(this);
        findViewById(R.id.textview_view_products).setOnClickListener(this);
    }
    private boolean valida(String placa, String color, String marca, String tipo) {
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
    private void saveProduct(){
        String placa = editTextName.getText().toString().trim();
        String color= editTextBrand.getText().toString().trim();
        String marca = editTextDesc.getText().toString().trim();
        String tipo = editTextPrice.getText().toString().trim();

        if (!valida(placa, color, marca, tipo)) {

            CollectionReference dbProducts = db.collection("Carros");

            Carro product = new Carro(
                    placa,
                    color,
                    marca,
                    tipo

            );

            dbProducts.add(product)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            limpiar();
                            Toast.makeText(MainActivity.this, "Carro agregado", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,"Error" +e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_save:
                saveProduct();
                break;
            case R.id.textview_view_products:
                startActivity(new Intent(this, Activity_carros.class));
                break;
        }
    }
    public  void limpiar(){
        editTextName.setText("");
        editTextBrand.setText("");
        editTextDesc.setText("");
        editTextPrice.setText("");
    }
}
