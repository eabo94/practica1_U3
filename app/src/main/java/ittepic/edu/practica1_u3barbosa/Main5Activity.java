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

public class Main5Activity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName;
    private EditText editTextBrand;
    private EditText editTextDesc;
    private EditText editTextPrice;


    private FirebaseFirestore db;

    private propietario pro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        pro = (propietario) getIntent().getSerializableExtra("Propietario");
        db = FirebaseFirestore.getInstance();

        editTextName = findViewById(R.id.edittext_na);
        editTextBrand = findViewById(R.id.edittext_domi);
        editTextDesc = findViewById(R.id.edittext_ed);
        editTextPrice = findViewById(R.id.edittext_nacio);


        editTextName.setText(pro.getNombre());
        editTextBrand.setText(pro.getDomicilio());
        editTextDesc.setText(pro.getEdad());
        editTextPrice.setText(pro.getNacionalidad());

        findViewById(R.id.button_update).setOnClickListener(this);
        findViewById(R.id.button_delete).setOnClickListener(this);
    }

    private boolean hasValidationErrors(String nombre, String domicilio, String edad, String nacionalidad) {
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
    private void updateProduct() {
        String nombre = editTextName.getText().toString().trim();
        String domicilio = editTextBrand.getText().toString().trim();
        String edad = editTextDesc.getText().toString().trim();
        String nacionalidad = editTextPrice.getText().toString().trim();


        if (!hasValidationErrors(nombre,domicilio,edad,nacionalidad)) {

            propietario p = new propietario(
                   nombre,domicilio,edad,nacionalidad
            );


            db.collection("Propietario").document(pro.getId())
                    .update(
                            "nombre", p.getNombre(),
                            "domicilio", p.getDomicilio(),
                            "edad", p.getEdad(),
                            "nacionalidad", p.getNacionalidad())

                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Main5Activity.this, "Propietario actualizado", Toast.LENGTH_LONG).show();

                            Intent intent= new Intent(Main5Activity.this,Main3Activity.class);
                            startActivity(intent);
                        }
                    });
        }
        limpiar();
    }
    private void deleteProduct() {
        db.collection("Propietario").document(pro.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Main5Activity.this, "Propietario eliminado", Toast.LENGTH_LONG).show();
                            finish();
                            Intent i= new Intent(Main5Activity.this,Main3Activity.class);
                            startActivity(i);
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
                builder.setMessage("Eliminar...");

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
    public  void limpiar(){
        editTextName.setText("");
        editTextBrand.setText("");
        editTextDesc.setText("");
        editTextPrice.setText("");
    }
}
