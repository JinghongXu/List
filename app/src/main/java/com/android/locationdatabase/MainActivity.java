package com.android.locationdatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private FavoriteLocationDatabase db = new FavoriteLocationDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db.getWritableDatabase();
        db.delete();
        db.getFavoriteLocations().clear();

        TextView textView = (TextView)findViewById(R.id.location);
        textView.setText("Favourite Location:"+ "\n");


        Button b1 = (Button)findViewById(R.id.initialize);
        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                db.getWritableDatabase();
                db.getFavoriteLocations().clear();
                db.loadDataBase();

                TextView textView = (TextView)findViewById(R.id.textView);
                String str = "Database is loaded.";
                textView.setText(str);

                TextView textView1 = (TextView)findViewById(R.id.location);
                textView1.setText("Favourite Location:"+ "\n");

                for(int i = 0; i < db.getFavoriteLocations().size(); i++){

                    FavoriteLocation l = db.getFavoriteLocations().get(i);
                    String string = "Name is "+l.getName()+" Latitude is "+l.getLatitude()+" Longitude is "+l.getLongitude()+" RingTone is "+l.getRingTone()+"\n";
                    textView1.setText(textView1.getText()+ string);
                }
            }

        });

        Button b2 = (Button)findViewById(R.id.addLocation);
        b2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                TextView textView1 = (TextView)findViewById(R.id.name);
                TextView textView2 = (TextView)findViewById(R.id.latitude);
                TextView textView3 = (TextView)findViewById(R.id.longitude);

                String name = textView1.getText().toString();
                Double lat = Double.parseDouble(textView2.getText().toString());
                Double lng = Double.parseDouble(textView3.getText().toString());

                db.getWritableDatabase();
                db.addLocation(name, lat, lng);

                TextView textView = (TextView)findViewById(R.id.textView2);
                String str = "Location is added.";
                textView.setText(str);
            }

        });


        Button b3 = (Button)findViewById(R.id.editLocation);
        b3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                TextView textView1 = (TextView)findViewById(R.id.name);
                TextView textView2 = (TextView)findViewById(R.id.latitude);
                TextView textView3 = (TextView)findViewById(R.id.longitude);
                TextView textView4 = (TextView)findViewById(R.id.ringtone);

                String name = textView1.getText().toString();
                Double lat = Double.parseDouble(textView2.getText().toString());
                Double lng = Double.parseDouble(textView3.getText().toString());
                String ringtone = textView4.getText().toString();

                db.getWritableDatabase();
                db.editLocation(name, lat, lng, ringtone);

                TextView textView = (TextView)findViewById(R.id.textView3);
                String str = "Location is updated.";
                textView.setText(str);
            }

        });


    }
}
