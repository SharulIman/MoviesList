package sg.edu.rp.c346.id22018526.movieslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText titleinp;
    EditText genreinp;
    EditText yearinp;
    Spinner starSpinner; // Use Spinner for ratings selection
    Button insertit;
    Button showit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleinp = findViewById(R.id.inputTitle);
        genreinp = findViewById(R.id.inputSinger);
        yearinp = findViewById(R.id.inputYear);
        starSpinner = findViewById(R.id.starSpinner); // Initialize Spinner for ratings selection
        insertit = findViewById(R.id.insertBtn);
        showit = findViewById(R.id.showBtn);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ratings_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        starSpinner.setAdapter(adapter);

        insertit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleinp.getText().toString();
                String singer = genreinp.getText().toString();
                String inputyear = yearinp.getText().toString();
                int year = Integer.parseInt(inputyear);
                String rating = starSpinner.getSelectedItem().toString(); // Get selected rating as String

                DBHelper dbh = new DBHelper(MainActivity.this);
                dbh.insertMovie(title, singer, year, rating);
                Toast.makeText(MainActivity.this, "Movie successfully inserted.", Toast.LENGTH_SHORT).show();
            }
        });

        showit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rating = starSpinner.getSelectedItem().toString(); // Get selected rating as String
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("ratings", rating);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            // Refresh the movie list when returning from ListActivity
            DBHelper dbh = new DBHelper(MainActivity.this);
            dbh.getAllMovies(); // Assuming you want to show all movies after returning from ListActivity
        }
    }
}
