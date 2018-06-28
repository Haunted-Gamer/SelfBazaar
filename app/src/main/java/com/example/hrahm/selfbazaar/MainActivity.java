package com.example.hrahm.selfbazaar;

import android.content.SharedPreferences;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences userSharedPreferences;
    private DrawerLayout drawer;
    TextView nameText;
    TextView emailText;
    ImageView userImage;
    NavigationView navigationView;
    View hView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userSharedPreferences = getSharedPreferences(login.PREFS_NAME,0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        hView = navigationView.inflateHeaderView(R.layout.nav_header);
        nameText = (TextView) hView.findViewById(R.id.nameText);
        emailText = (TextView) hView.findViewById(R.id.emailText);
        userImage= (ImageView) hView.findViewById(R.id.UserImage);
        String SPEmail=userSharedPreferences.getString("Email", "Guest@guest.com");
        String SPName=userSharedPreferences.getString("Name", "Guest");
        String Paths = userSharedPreferences.getString("Ipath", "GuestImage");
        String imagePath = apiClient.BASE_URL.concat(Paths);

        nameText.setText(SPName);
        emailText.setText(SPEmail);
        if(Paths.equals("GuestImage"))
        {

        }
        else {
            Picasso.get()
                    .load(imagePath)
                    .resize(100, 100)
                    .centerCrop()
                    .into(userImage);



        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.nav_account:
getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MyAccountFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView =  (SearchView) item.getActionView();
        final Search_Fragment sf = new Search_Fragment();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
Search_Fragment.crack=query;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Search_Fragment()).commit();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Search_Fragment.crack=newText;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Search_Fragment()).commit();
                return false;
            }
        });

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Search_Fragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
