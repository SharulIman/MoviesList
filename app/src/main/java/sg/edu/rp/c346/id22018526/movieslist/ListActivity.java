package sg.edu.rp.c346.id22018526.movieslist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    TextView starview;
    ListView displaylist;

    ArrayList<Movies> al;
    MovieAdapter sa;

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        starview = findViewById(R.id.tvlist);
        displaylist = findViewById(R.id.showList);
        back = findViewById(R.id.backbtn);
        al = new ArrayList<>();
        sa = new MovieAdapter(this, al);
        displaylist.setAdapter(sa);

        displaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movies data = al.get(position);
                Intent i = new Intent(ListActivity.this, EditActivity.class);
                i.putExtra("data", data);
                i.putExtra("position", position);
                startActivityForResult(i, 1);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Receive the data from the previous activity (MainActivity)
        Intent intent = getIntent();
        String ratings = intent.getStringExtra("ratings");
        String startext = "Movies that have a rating of " + ratings;
        DBHelper dbh = new DBHelper(this);
        if (ratings != null) {
            starview.setText(startext);
            ArrayList<Movies> moviesByRatings = dbh.getAllMovies(ratings);
            al.addAll(moviesByRatings);
        } else {
            starview.setText("All Movies");
            ArrayList<Movies> allMovies = dbh.getAllMovies();
            al.addAll(allMovies);
        }
        sa.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(this);
        ArrayList<Movies> allMovies = dbh.getAllMovies();
        al.clear();
        al.addAll(allMovies);
        sa.notifyDataSetChanged();
    }
}

