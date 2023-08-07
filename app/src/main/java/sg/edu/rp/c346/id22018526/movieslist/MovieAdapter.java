package sg.edu.rp.c346.id22018526.movieslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movies> {

    public MovieAdapter(Context context, ArrayList<Movies> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_movie_item, parent, false);
        }

        Movies currentMovie = getItem(position);

        TextView titleTextView = listItemView.findViewById(R.id.titleTextView);
        TextView singerTextView = listItemView.findViewById(R.id.singerTextView);
        TextView yearTextView = listItemView.findViewById(R.id.yearTextView);
        ImageView ratingimage = listItemView.findViewById(R.id.imageViewRating);

        titleTextView.setText(currentMovie.getTitle());
        singerTextView.setText("Singer: " + currentMovie.getGenre());
        yearTextView.setText("Year: " + currentMovie.getYear());
        if ("G".equals(currentMovie.getRating())) {
            ratingimage.setImageResource(R.drawable.rating_g);
        } else if ("PG".equals(currentMovie.getRating())) {
            ratingimage.setImageResource(R.drawable.rating_pg);
        } else if ("PG13".equals(currentMovie.getRating())) {
            ratingimage.setImageResource(R.drawable.rating_pg13);
        } else if ("NC16".equals(currentMovie.getRating())) {
            ratingimage.setImageResource(R.drawable.rating_nc16);
        } else if ("M18".equals(currentMovie.getRating())) {
            ratingimage.setImageResource(R.drawable.rating_m18);
        } else if ("R21".equals(currentMovie.getRating())) {
            ratingimage.setImageResource(R.drawable.rating_r21);
        }



        return listItemView;
    }
}
