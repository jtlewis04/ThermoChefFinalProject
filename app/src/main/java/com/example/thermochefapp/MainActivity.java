package com.example.thermochefapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.thermochefapp.databinding.ActivityMainBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;

    private LineChart lineChart;

    private NavController navController;

        public List<Entry> exentries = new ArrayList<Entry>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        ArrayList<String> examples = new ArrayList<String>();
        examples.add("Water");
        examples.add("Beef");
        examples.add("Chicken");


        Spinner spinner=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinadapter=ArrayAdapter.createFromResource(this,R.array.examples, android.R.layout.simple_spinner_item);
        spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(spinadapter);

        // Set the OnItemSelectedListener on the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected option's name
                String selectedOption = parent.getItemAtPosition(position).toString();
                for(int i=0;i<=100;i++) {
                    if ("Water".equals(selectedOption)) {
                        exentries.add(new Entry((float)i,(float)Math.sqrt(i)*10));
                    } else if ("Chicken".equals(selectedOption)) {
                        exentries.add(new Entry((float)i,(float)0));
                    } else if ("Pork".equals(selectedOption)) {
                        exentries.add(new Entry((float)i,(float)0));
                    } else if ("Beef".equals(selectedOption)) {
                        exentries.add(new Entry((float)i,(float)0));
                    }
                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });





//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);



//        final ProgressBar progressBar = findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.GONE);



//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });







        // IMAGE SLIDESHOW -----------------------------------------------------
        String url1 = "https://images.pexels.com/photos/236887/pexels-photo-236887.jpeg?auto=compress&cs=tinysrgb&w=1200";
        String url2 = "https://images.pexels.com/photos/2233729/pexels-photo-2233729.jpeg?auto=compress&cs=tinysrgb&w=1200";
        String url3 = "https://images.pexels.com/photos/1327344/pexels-photo-1327344.jpeg?auto=compress&cs=tinysrgb&w=1200";
        String url4 = "https://images.pexels.com/photos/2313686/pexels-photo-2313686.jpeg?auto=compress&cs=tinysrgb&w=1200";
        String url5 = "https://images.pexels.com/photos/2673353/pexels-photo-2673353.jpeg?auto=compress&cs=tinysrgb&w=1200";
        String url6 = "https://images.pexels.com/photos/3997609/pexels-photo-3997609.jpeg?auto=compress&cs=tinysrgb&w=1200";

        // we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        // initializing the slider view.
        SliderView sliderView = findViewById(R.id.slider);

        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));
        sliderDataArrayList.add(new SliderData(url4));
        sliderDataArrayList.add(new SliderData(url5));
        sliderDataArrayList.add(new SliderData(url6));

        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);

        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(6);

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
        sliderView.startAutoCycle();
    }



//    public void func() {
//
//        Fragment currentFragment = getSupportFragmentManager().getPrimaryNavigationFragment();
//        if (currentFragment instanceof FirstFragment) {
//            // Code to re-run when returning from FirstFragment
//            ArrayList<String> examples = new ArrayList<String>();
//            examples.add("Water");
//            examples.add("Beef");
//            examples.add("Chicken");
//            examples.add("Pork");
//
//
//            Spinner spinner=findViewById(R.id.spinner);
//            ArrayAdapter<CharSequence> spinadapter=ArrayAdapter.createFromResource(this,R.array.examples, android.R.layout.simple_spinner_item);
//            spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//
//            spinner.setAdapter(spinadapter);
//
//            // Set the OnItemSelectedListener on the spinner
//            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    // Get the selected option's name
//                    String selectedOption = parent.getItemAtPosition(position).toString();
//                    exentries.clear();
//                    for(int i=0;i<=100;i++) {
//                        if ("Water".equals(selectedOption)) {
//                            exentries.add(new Entry((float)i,(float)(25*(Math.exp(-0.6*i)))));
//                        } else if ("Chicken".equals(selectedOption)) {
//                            exentries.add(new Entry((float)i,(float)(25*(Math.exp(-0.5*i)))));
//                        } else if ("Pork".equals(selectedOption)) {
//                            exentries.add(new Entry((float)i,(float)(30*(Math.exp(-0.5*i)))));
//                        } else if ("Beef".equals(selectedOption)) {
//                            exentries.add(new Entry((float)i,(float)(30*(Math.exp(-0.5*i)))));
//                        }
//                    }
//
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//                    // Do nothing
//                }
//            });
//
//
//            // IMAGE SLIDESHOW -----------------------------------------------------
//            String url1 = "https://images.pexels.com/photos/236887/pexels-photo-236887.jpeg?auto=compress&cs=tinysrgb&w=1200";
//            String url2 = "https://images.pexels.com/photos/2233729/pexels-photo-2233729.jpeg?auto=compress&cs=tinysrgb&w=1200";
//            String url3 = "https://images.pexels.com/photos/1327344/pexels-photo-1327344.jpeg?auto=compress&cs=tinysrgb&w=1200";
//            String url4 = "https://images.pexels.com/photos/2313686/pexels-photo-2313686.jpeg?auto=compress&cs=tinysrgb&w=1200";
//            String url5 = "https://images.pexels.com/photos/2673353/pexels-photo-2673353.jpeg?auto=compress&cs=tinysrgb&w=1200";
//            String url6 = "https://images.pexels.com/photos/3997609/pexels-photo-3997609.jpeg?auto=compress&cs=tinysrgb&w=1200";
//
//            // we are creating array list for storing our image urls.
//            ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
//
//            // initializing the slider view.
//            SliderView sliderView = findViewById(R.id.slider);
//
//            // adding the urls inside array list
//            sliderDataArrayList.add(new SliderData(url1));
//            sliderDataArrayList.add(new SliderData(url2));
//            sliderDataArrayList.add(new SliderData(url3));
//            sliderDataArrayList.add(new SliderData(url4));
//            sliderDataArrayList.add(new SliderData(url5));
//            sliderDataArrayList.add(new SliderData(url6));
//
//            // passing this array list inside our adapter class.
//            SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);
//
//            // below method is used to set auto cycle direction in left to
//            // right direction you can change according to requirement.
//            sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
//
//            // below method is used to
//            // setadapter to sliderview.
//            sliderView.setSliderAdapter(adapter);
//
//            // below method is use to set
//            // scroll time in seconds.
//            sliderView.setScrollTimeInSec(6);
//
//            // to set it scrollable automatically
//            // we use below method.
//            sliderView.setAutoCycle(true);
//
//            // to start autocycle below method is used.
//            sliderView.startAutoCycle();
//        }
//
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }


}





