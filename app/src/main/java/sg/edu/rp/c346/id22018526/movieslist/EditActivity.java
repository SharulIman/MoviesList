package sg.edu.rp.c346.id22018526.movieslist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    EditText titleinp;
    EditText genreinp;
    EditText yearinp;
    Spinner starSpinner; // Use Spinner for ratings selection
    Button update;
    Button delete;
    Button cancel;

    DBHelper dbHelper;
    Movies movieToUpdate;
    int positionToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        dbHelper = new DBHelper(this);

        titleinp = findViewById(R.id.inputTitle);
        genreinp = findViewById(R.id.inputSinger);
        yearinp = findViewById(R.id.inputYear);
        starSpinner = findViewById(R.id.starSpinner); // Initialize Spinner for ratings selection
        update = findViewById(R.id.updateBtn);
        delete = findViewById(R.id.deleteBtn);
        cancel = findViewById(R.id.cancelBtn);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ratings_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        starSpinner.setAdapter(adapter);

        // Receive the data from the previous activity (ListActivity)
        Intent i = getIntent();
        movieToUpdate = (Movies) i.getSerializableExtra("data");
        int id = movieToUpdate.get_id();
        String title = movieToUpdate.getTitle();
        String genre = movieToUpdate.getGenre();
        int year = movieToUpdate.getYear();
        String rating = movieToUpdate.getRating();
        positionToUpdate = i.getIntExtra("position", -1);

        // Populate the editable fields with data
        titleinp.setText(title);
        genreinp.setText(genre);
        yearinp.setText(String.valueOf(year));

        // Set selected rating in the Spinner
        int ratingPosition = adapter.getPosition(rating);
        if (ratingPosition >= 0) {
            starSpinner.setSelection(ratingPosition);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);
                myBuilder.setTitle("Update this Movie");
                myBuilder.setMessage("Do you want to update this movie?.");
                myBuilder.setCancelable(false);

                //Configure the 'Positive' button
                myBuilder.setPositiveButton("Affirmative", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateMovieData();
                    }
                });

                myBuilder.setNeutralButton("Cancel", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);
                myBuilder.setTitle("Delete this Movie");
                myBuilder.setMessage("Do you want to delete this movie?");
                myBuilder.setCancelable(false);

                //Configure the 'Positive' button
                myBuilder.setPositiveButton("Affirmative", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteMovie(movieToUpdate.get_id());
                        finish();
                    }
                });

                myBuilder.setNeutralButton("Cancel", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateMovieData() {
        movieToUpdate.setTitle(titleinp.getText().toString());
        movieToUpdate.setGenre(genreinp.getText().toString());
        movieToUpdate.setYear(Integer.parseInt(yearinp.getText().toString()));
        String rating = starSpinner.getSelectedItem().toString(); // Get selected rating as String
        movieToUpdate.setRating(rating);

        dbHelper.updateMovies(movieToUpdate);
        setResultAndFinish();
    }

    private void setResultAndFinish() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("position", positionToUpdate);
        resultIntent.putExtra("updatedMovie", movieToUpdate);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}

