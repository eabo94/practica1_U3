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

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName;
    private EditText editTextBrand;
    private EditText editTextDesc;
    private EditText editTextPrice;


    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        db = FirebaseFirestore.getInstance();

        editTextName = findViewById(R.id.edittext_na);
        editTextBrand = findViewById(R.id.edittext_dom);
        editTextDesc = findViewById(R.id.edittext_ed);
        editTextPrice = findViewById(R.id.edittext_nac);

        findViewById(R.id.button_save).setOnClickListener(this);
        findViewById(R.id.textview_view_products).setOnClickListener(this);
    }
    private boolean valida(String nombre, String domicilio, String edad, String nacionalidad) {
        if (nombre.isEmpty()) {
            editTextName.setError("Requerido");
            editTextName.requestFocus();
            return true;
        }

        if (domicilio.isEmpty()) {
            editTextBrand.setError("Requerido");
            editTextBrand.requestFocus();
            return true;
        }

        if (edad.isEmpty()) {
            editTextDesc.setError("Requerido");
            editTextDesc.requestFocus();
            return true;
        }

        if (nacionalidad.isEmpty()) {
            editTextPrice.setError("Requerido");
            editTextPrice.requestFocus();
            return true;
        }

        return false;
    }
    private void saveProduct(){
        String nombre = editTextName.getText().toString().trim();
        String domicilio= editTextBrand.getText().toString().trim();
        String edad = editTextDesc.getText().toString().trim();
        String nacionalidad = editTextPrice.getText().toString().trim();

        if (!valida(nombre, domicilio, edad, nacionalidad)) {

            CollectionReference dbProducts = db.collection("Propietario");

            propietario product = new propietario(
                    nombre, domicilio, edad, nacionalidad
            );

            dbProducts.add(product)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(Main3Activity.this, "Propietario agregado", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Main3Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        }
        limpiar();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_save:
                saveProduct();
                break;
            case R.id.textview_view_products:
                startActivity(new Intent(this, Main4Activity.class));
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
