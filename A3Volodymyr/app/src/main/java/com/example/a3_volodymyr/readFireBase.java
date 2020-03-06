package com.example.a3_volodymyr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class readFireBase extends AppCompatActivity {

    ListView listDATOS;
    DatabaseReference data;
    List<DatosParaFirebase> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_fire_base);

        data= FirebaseDatabase.getInstance().getReference();
        listDATOS= findViewById(R.id.listables);

        list = new ArrayList<>();
        /*// Display a indeterminate progress bar on title bar
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        this.setContentView(R.layout.main);

        this.listView = (ListView) findViewById(R.id.listView);
        this.webView = (WebView) findViewById(R.id.webView);

        List<Item> items = new ArrayList<Item>();
        items.add(new Item(R.drawable.following, "Following",
                "http://www.imdb.com/title/tt0154506/"));
        items.add(new Item(R.drawable.memento, "Memento",
                "http://www.imdb.com/title/tt0209144/"));
        items.add(new Item(R.drawable.batman_begins, "Batman Begins",
                "http://www.imdb.com/title/tt0372784/"));
        items.add(new Item(R.drawable.the_prestige, "The Prestige",
                "http://www.imdb.com/title/tt0482571/"));
        items.add(new Item(R.drawable.the_dark_knight, "The Dark Knight",
                "http://www.imdb.com/title/tt0468569/"));
        items.add(new Item(R.drawable.inception, "Inception",
                "http://www.imdb.com/title/tt1375666/"));
        items.add(new Item(R.drawable.the_dark_knight_rises,
                "The Dark Knight Rises", "http://www.imdb.com/title/tt1345836/"));

        // Sets the data behind this ListView
        this.listView.setAdapter(new ItemAdapter(this, items));

        // Register a callback to be invoked when an item in this AdapterView
        // has been clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {
                // Sets the visibility of the indeterminate progress bar in the
                // title
                setProgressBarIndeterminateVisibility(true);
                // Show progress dialog
                progressDialog = ProgressDialog.show(MainActivity.this,
                        "ProgressDialog", "Loading!");

                // Tells JavaScript to open windows automatically.
                webView.getSettings().setJavaScriptEnabled(true);
                // Sets our custom WebViewClient.
                webView.setWebViewClient(new myWebClient());
                // Loads the given URL
                Item item = (Item) listView.getAdapter().getItem(position);
                webView.loadUrl(item.getUrl());
            }
        });
        */
    }

    @Override
    protected void onStart() {
        super.onStart();

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot datSnap: dataSnapshot.getChildren()){

                    DatosParaFirebase fire = datSnap.getValue(DatosParaFirebase.class);

                    list.add(fire);
                }

                listDatos listaAdapter = new listDatos(readFireBase.this,list);
                listDATOS.setAdapter(listaAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
