package com.example.smartparking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.smartparking.Adaptere.DotariAdapter;
import com.example.smartparking.Adaptere.ListaAutovehiculeAdapter;
import com.example.smartparking.Adaptere.ListaPlatiAdapter;
import com.example.smartparking.Adaptere.ParcariRecenteAdapter;
import com.example.smartparking.Adaptere.PreturiAdapter;
import com.example.smartparking.Adaptere.ProgramAdapter;
import com.example.smartparking.Adaptere.RecenziiAdapter;
import com.example.smartparking.Obiecte.Autovehicule;
import com.example.smartparking.Obiecte.Dotari;
import com.example.smartparking.Obiecte.Plati;
import com.example.smartparking.Obiecte.Preturi;
import com.example.smartparking.Obiecte.Program;
import com.example.smartparking.Obiecte.Recenzie;
import com.example.smartparking.Obiecte.Utilizator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class BdComunica {


    private DatabaseReference reference;
    private FirebaseAuth autentifica;
    private FirebaseUser user;
    private Utilizator utilizator=new Utilizator();
    private int index=0;
    String pretul;

    public BdComunica()
    { };

    public void inregistrare()
    {
        autentifica=FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference().child("Utilizatori");
    }
    public void parcare()
    {

        reference= FirebaseDatabase.getInstance().getReference().child("Parcari");
    }
    public void preturi(Context context, int id_pret_parcare, ListView listapreturi)
    {
            reference= FirebaseDatabase.getInstance().getReference().child("Preturi").child("id").child(String.valueOf(id_pret_parcare));
            getReferinta().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Preturi preturi = new Preturi(Integer.parseInt(dataSnapshot.getKey()));

                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    preturi.setTimp(ds.getKey());
                    preturi.setPret(Integer.parseInt(String.valueOf(ds.getValue())));
                }
                PreturiAdapter adapter=new PreturiAdapter(context,preturi);
                listapreturi.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void plati(String ziua,String ora,String nrinmatriculare,String nume_parcare,int id_parcare,String oraplata)
    {
        index=0;
        Plati plati=new Plati(ziua,pretul,nrinmatriculare,nume_parcare);
        plati.setOraplata(oraplata);
        plati.setOra(ora);
        if(useruid()!=null)
        {
            reference=FirebaseDatabase.getInstance().getReference().child("Utilizatori").child(useruid()).child("Plati").child(reference.push().getKey());
        }
        else
        {
            reference=FirebaseDatabase.getInstance().getReference().child("Plata_anonim").child(reference.push().getKey());
        }
        reference.setValue(plati);
        if(useruid()!=null) {
            reference.orderByChild("Plati");

            reference = FirebaseDatabase.getInstance().getReference().child("Utilizatori").child(useruid()).child("Parcari_recente");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.getChildrenCount() > 6) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            DatabaseReference referinta = FirebaseDatabase.getInstance().getReference().child("Utilizatori").child(useruid()).child("Parcari_recente").child(ds.getKey());

                            referinta.setValue(null);
                            break;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
            reference = FirebaseDatabase.getInstance().getReference().child("Utilizatori").child(useruid()).child("Parcari_recente").child(reference.push().getKey());
            plati.setOra(ora);
            reference.setValue(plati);
        }

index=0;
        reference=FirebaseDatabase.getInstance().getReference().child("Parcari").child(String.valueOf(id_parcare)).child("ocupate");
     reference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

             if(index==0) {
                 int aux = dataSnapshot.getValue(int.class);
                 aux++;
                 reference.setValue(aux);
                 index++;
             }
         }
         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) { }
     });
    }

    public void preturi(int id_pret_parcare, Spinner oraplata, Context context, String numeparcare, TextView totalplata)
    {
        reference= FirebaseDatabase.getInstance().getReference().child("Preturi").child("id").child(String.valueOf(id_pret_parcare));
        getReferinta().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Preturi preturi = new Preturi(Integer.parseInt(dataSnapshot.getKey()));

                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    preturi.setTimp(ds.getKey());
                    preturi.setPret(Integer.parseInt(String.valueOf(ds.getValue())));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, preturi.getTimp());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                oraplata.setAdapter(adapter);
                oraplata.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        totalplata.setText(numeparcare+" - "+preturi.getTimp().get(position)+" = "+preturi.getPret().get(position)+" RON +TVA");
                        pretul=preturi.getPret().get(position).toString();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
    public  void program(Context context, int id_program, ListView listaprogram)
    {
        reference= FirebaseDatabase.getInstance().getReference().child("Program").child("id").child(String.valueOf(id_program));
        getReferinta().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Program program = new Program(Integer.parseInt(dataSnapshot.getKey()));
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    program.setZi(ds.getKey());
                    program.setOre(String.valueOf(ds.getValue()));
                }
                ProgramAdapter adapter=new ProgramAdapter(context,program);
                listaprogram.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    public  void dotari(Context context, int id_dotari, ListView listadotari)
    {
        reference= FirebaseDatabase.getInstance().getReference().child("Dotari").child("id").child(String.valueOf(id_dotari));
        getReferinta().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Dotari dotari=new Dotari(Integer.parseInt(dataSnapshot.getKey()));

              for (DataSnapshot ds: dataSnapshot.getChildren()) {
                  dotari.setDotare(String.valueOf(ds.getValue()));
              }
                DotariAdapter adapter=new DotariAdapter(context,dotari);
                listadotari.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public  void stergeutilizator()
    {
        reference=FirebaseDatabase.getInstance().getReference().child("Utilizatori").child(useruid());
        reference.removeValue();
        autentifica.getCurrentUser().delete();
        signout();
    }

    public void stergenr(String numarul)
    {
        reference= FirebaseDatabase.getInstance().getReference().child("Utilizatori").child(useruid()).child("Autovehicule").child(String.valueOf(numarul));
        reference.removeValue();
    }

    public  void adauganrnou(String numarul)
    {
        reference= FirebaseDatabase.getInstance().getReference().child("Utilizatori").child(useruid()).child("Autovehicule").child(String.valueOf(numarul));
        reference.setValue(1);
        index++;
    }
    public  void listaplati( LinearLayoutManager mlayoutmanager,RecyclerView listaplati)
    {
        index=0;
        reference=FirebaseDatabase.getInstance().getReference().child("Utilizatori").child(useruid()).child("Plati");

        ArrayList<Plati> plati=new ArrayList<Plati>();

        getReferinta().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                plati.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    Plati aux=new Plati((String)ds.child("oraplata").getValue(),(String)ds.child("nume_parcare").getValue(),(String)ds.child("pret").getValue(),(String)ds.child("nrinmatriculare").getValue(),(String)ds.child("data").getValue());
                    aux.setOra((String)ds.child("ora").getValue());
                    aux.setOraplata((String)ds.child("oraplata").getValue());
                        plati.add(aux);
                }
                ListaPlatiAdapter adapter=new ListaPlatiAdapter(plati);
                listaplati.setLayoutManager(mlayoutmanager);
                listaplati.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
    public  void listaparcarirecente( LinearLayoutManager mlayoutmanager,RecyclerView listarecente)
    {
        index=0;
        reference=FirebaseDatabase.getInstance().getReference().child("Utilizatori").child(useruid()).child("Parcari_recente");

        ArrayList<Plati> plati=new ArrayList<Plati>();
        getReferinta().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                plati.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    Plati aux=new Plati((String)ds.child("oraplata").getValue(),(String)ds.child("nume_parcare").getValue(),(String)ds.child("pret").getValue(),(String)ds.child("nrinmatriculare").getValue(),(String)ds.child("data").getValue());
                    aux.setOra((String)ds.child("ora").getValue());
                    aux.setOraplata((String)ds.child("oraplata").getValue());
                    plati.add(aux);
                }
                ParcariRecenteAdapter adapter=new ParcariRecenteAdapter(plati);
                listarecente.setLayoutManager(mlayoutmanager);
                listarecente.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void autovehicule(String uid, LinearLayoutManager mlayoutmanager,RecyclerView listaauto)
    {
        reference= FirebaseDatabase.getInstance().getReference().child("Utilizatori").child(uid).child("Autovehicule");
        getReferinta().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Autovehicule autovehicule=new Autovehicule();

                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    autovehicule.setNrinmatriculare(String.valueOf(ds.getKey()));
                }
                ListaAutovehiculeAdapter adapter=new ListaAutovehiculeAdapter(autovehicule);
                listaauto.setLayoutManager(mlayoutmanager);
                listaauto.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
    public void autovehicule(String uid, Spinner masina,Context context)
    {
        reference= FirebaseDatabase.getInstance().getReference().child("Utilizatori").child(uid).child("Autovehicule");
        Autovehicule autovehicule=new Autovehicule();

        getReferinta().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    autovehicule.setNrinmatriculare(String.valueOf(ds.getKey()));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, autovehicule.getNrinmatriculare());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                masina.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    public void recenzii(String id, Context context, RecyclerView listarecenzie)
    {
        reference= FirebaseDatabase.getInstance().getReference().child("Recenzii").child("id").child(id);
        getReferinta().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                index=0;
                Recenzie[] recenzie=new Recenzie[(int) dataSnapshot.getChildrenCount()];
             for(DataSnapshot ds:dataSnapshot.getChildren())
             {
                 recenzie[index] = ds.getValue(Recenzie.class);
                 index++;
             }
                listarecenzie.setHasFixedSize(true);
                RecyclerView.LayoutManager mlayoutmanager = new LinearLayoutManager(context);
                RecenziiAdapter adapter=new RecenziiAdapter(context,recenzie);
                listarecenzie.setLayoutManager(mlayoutmanager);
                listarecenzie.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    public void cautauser(String uid)
    {
        reference= FirebaseDatabase.getInstance().getReference().child("Utilizatori").child(uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 utilizator.setNume((String)dataSnapshot.child("nume").getValue());
                 utilizator.setUsername((String)dataSnapshot.child("username").getValue());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
    public void signout()
    {
        autentifica.signOut();
    }
    public void seteazauser(TextView nume,TextView prenume,TextView email,TextView totalplati,TextView totalmaxini)
    {
    reference= FirebaseDatabase.getInstance().getReference().child("Utilizatori").child(useruid());
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            nume.setText((String)dataSnapshot.child("nume").getValue());
            prenume.setText((String)dataSnapshot.child("prenume").getValue());
            email.setText((String)dataSnapshot.child("email").getValue());

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    });

    reference= FirebaseDatabase.getInstance().getReference().child("Utilizatori").child(useruid()).child("Plati");
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

         for (DataSnapshot ds:dataSnapshot.getChildren())
         {
             index++;
         }

         totalplati.setText(index+"");
         index=0;

        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    });
    reference= FirebaseDatabase.getInstance().getReference().child("Utilizatori").child(useruid()).child("Autovehicule");
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for (DataSnapshot ds:dataSnapshot.getChildren())
            {
                index++;
            }
            totalmaxini.setText(index+"");
            index=0;
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    });
}
    public void medierecenzi(String id) {
        reference = FirebaseDatabase.getInstance().getReference().child("Recenzii").child("id").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int numar=0;
                float suma=0,medie=0;
                for(DataSnapshot ds:dataSnapshot.getChildren())
               {
                   Recenzie recenzie=ds.getValue(Recenzie.class);
                   suma=recenzie.getNota()+suma;
                   numar++;
               }
                medie=suma/numar;
                float ozecimala= (float) (Math.round(medie*10)/10.0);
                reference= FirebaseDatabase.getInstance().getReference().child("Parcari").child(id).child("nota");
                reference.setValue(ozecimala);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public Utilizator getUtilizator()
    {
        return utilizator;
    }
    public String useruid()
    {
        autentifica=FirebaseAuth.getInstance();
        user=autentifica.getCurrentUser();

        if(user!=null)
        {
            return user.getUid();
        }
        else
            return null;

    }
    public DatabaseReference getReferinta()
    {
        return reference;
    }
    public FirebaseAuth getAutentifica()
    {
        return autentifica;
    }
}
