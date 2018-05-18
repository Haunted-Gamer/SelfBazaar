package com.example.hrahm.selfbazaar;

import android.content.SharedPreferences;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {
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
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();

    }
}
