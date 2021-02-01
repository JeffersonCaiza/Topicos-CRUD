package com.example.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.crud.model.persona;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private List<persona> listperson=new ArrayList<persona>();
    ArrayAdapter<persona> personaArrayAdapter;

    EditText nom, ape, correop, passwordp;
    ListView listpersonas;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    persona personaSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nom=findViewById(R.id.editTextTextPersonName);
        ape=findViewById(R.id.editTextTextPersonName2);
        correop=findViewById(R.id.editTextTextPersonName3);
        passwordp=findViewById(R.id.editTextTextPersonName4);

        listpersonas=findViewById(R.id.lista);
        inicializarfirebase();
        listardatos();

        listpersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                personaSelected = (persona) parent.getItemAtPosition(position);
                nom.setText(personaSelected.getNombre());
                ape.setText(personaSelected.getApellido());
                correop.setText(personaSelected.getCorreos());
                passwordp.setText(personaSelected.getPassword());
            }
        });
    }

    private void listardatos(){
        databaseReference.child("persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listperson.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    persona p = objSnaptshot.getValue(persona.class);
                    listperson.add(p);

                    personaArrayAdapter = new ArrayAdapter<persona>(MainActivity.this, android.R.layout.simple_list_item_1, listperson);
                    listpersonas.setAdapter(personaArrayAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inicializarfirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        
        String nombre=nom.getText().toString();
        String apellido=ape.getText().toString();
        String correo=correop.getText().toString();
        String password=passwordp.getText().toString();

        
        
        switch (item.getItemId()){
            case R.id.icon_add:{
                if (nombre.equals("")||correop.equals("")||passwordp.equals("")||ape.equals("")){
                    validation();
                }
                else {
                    persona p=new persona();
                    p.setId(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setApellido(apellido);
                    p.setCorreos(correo);
                    p.setPassword(password);

                    databaseReference.child("persona").child(p.getId()).setValue(p);


                    Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show();
                    limpiar();

                }
                break;
            }

            case R.id.icon_save:{
                persona p = new persona();
                p.setId(personaSelected.getId());
                p.setNombre(nom.getText().toString().trim());
                p.setApellido(ape.getText().toString().trim());
                p.setCorreos(correop.getText().toString().trim());
                p.setPassword(passwordp.getText().toString().trim());
                databaseReference.child("persona").child(p.getId()).setValue(p);
                Toast.makeText(this,"Actualizado", Toast.LENGTH_LONG).show();
                limpiar();
                break;
            }

            case R.id.icon_delete:{
                persona p = new persona();
                p.setId(personaSelected.getId());
                databaseReference.child("persona").child(p.getId()).removeValue();
                Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
                limpiar();
                break;
            }
            default:break;
        }
        return true;
    }

    private void validation() {
        String nombre=nom.getText().toString();
        String apellido=ape.getText().toString();
        String contra=passwordp.getText().toString();
        String corre=correop.getText().toString();

        if(nombre.equals("")){
            nom.setError("Required");
        }
        else if(apellido.equals("")){
            ape.setError("Required");
        }

        else if(contra.equals("")){
            passwordp.setError("Required");
        }

        else if(corre.equals("")){
            correop.setError("Required");
        }


    }

    private void limpiar(){
        nom.setText("");
        correop.setText("");
        passwordp.setText("");
        ape.setText("");
    }
}