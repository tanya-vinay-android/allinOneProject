package com.example.website;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.website.Api.RetrofitClient;
import com.example.website.Fragments.Fragment_Q_A;
import com.example.website.Fragments.Fragment_Write;
import com.example.website.Fragments.Fragment_about_us;
import com.example.website.Fragments.Fragment_faq;
import com.example.website.Fragments.Fragment_groups;
import com.example.website.Fragments.Fragment_home;
import com.example.website.Storage.SharedPrefManager;
import com.example.website.model.TagsList;
import com.example.website.model.TagsListResponse;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    List<String> tags;
    ActionBarDrawerToggle toggle;
    BottomNavigationView bottomNavigationView;
    private DrawerLayout drawerLayout;
    ImageButton search;
    LinearLayout searchHomeLayout;
    AutoCompleteTextView autoCompleteTextView;
    BottomSheetBehavior bottomSheetBehavior;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        search = findViewById(R.id.search_button);
        searchHomeLayout = findViewById(R.id.search_home_layout);
        bottomSheetBehavior = BottomSheetBehavior.from(searchHomeLayout);
        drawerLayout = findViewById(R.id.drawer_layout);
        autoCompleteTextView = findViewById(R.id.auto_complete_tv);
        View v = MainActivity.this.getCurrentFocus();
        if(v!=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tags);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        bottomSheetState();

        fetchTags();
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(nav);

        bottomNavigationView = findViewById(R.id.Bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.bottom_home);


    }


    private BottomNavigationView.OnNavigationItemSelectedListener bottom_nav =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.bottom_home:
                            selectedFragment = new Fragment_home();
                            search.setVisibility(View.VISIBLE);
                            break;
                        case R.id.bottom_groups:
                            selectedFragment = new Fragment_groups();
                            search.setVisibility(View.GONE);
                            break;
                        case R.id.bottom_ques:
                            selectedFragment = new Fragment_Q_A();
                            search.setVisibility(View.VISIBLE);
                            break;
                        case R.id.bottom_write:
                            selectedFragment = new Fragment_Write();
                            search.setVisibility(View.GONE);

                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).addToBackStack("Home").commit();
                    return true;
                }
            };
    private NavigationView.OnNavigationItemSelectedListener nav = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.about_us:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_about_us()).addToBackStack("Home").commit();
                    break;
                case R.id.contact_us:
                    contactUs();
                    break;
                case R.id.join_us:
                    Alertdialog();
                    break;
                case R.id.faq:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_faq()).addToBackStack("Home").commit();
                    break;
                case R.id.donate:
                    Toast.makeText(MainActivity.this, "donate", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.share:
                    share();
                    break;
                case R.id.logout:
                    logout();
                    break;
            }
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        }
    };

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            autoCompleteTextView.setText("");
        } else if (bottomNavigationView.getSelectedItemId() == R.id.bottom_home) {
            super.onBackPressed();
            finish();
        } else if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStack("Home", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        }
    }


    public void Alertdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Apply For :");
        String[] items = {"1. Mentor at FreshlyBuilt", "2. Internship at FreshlyBuilt"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent intent1 = new Intent();
                        intent1.setAction(Intent.ACTION_VIEW);
                        intent1.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent1.setData(Uri.parse("https://freshlybuilt.com/how-to-become-mentor-at-freshlybuilt/"));
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent();
                        intent2.setAction(Intent.ACTION_VIEW);
                        intent2.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent2.setData(Uri.parse("https://freshlybuilt.com/internship-at-freshlybuilt/"));
                        startActivity(intent2);
                        break;

                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void share() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String link = "https://freshlybuilt.com/";
        intent.putExtra(Intent.EXTRA_TEXT, link);
        startActivity(Intent.createChooser(intent, "Share using"));
    }

    public void logout() {
        SharedPrefManager.getInstance(this).clear();
        // mGoogleSignInClient.signOut();
        // mGoogleSignInClient.revokeAccess();

        startActivity(new Intent(this, Login.class));
    }


    public void contactUs() {
        final Item[] items = {new Item("   Voice Call", R.drawable.ic_phone_call), new Item("   Email", R.drawable.ic_gmail), new Item("   Whats app", R.drawable.ic_whatsapp), new Item("   Instagram", R.drawable.ic_instagram)};

        ListAdapter adapter = new ArrayAdapter<Item>(this, android.R.layout.select_dialog_item, android.R.id.text1, items) {
            public View getView(int position, View convertView, ViewGroup parent) {
                //Use super class to create the View
                View v = super.getView(position, convertView, parent);
                TextView tv = v.findViewById(android.R.id.text1);

                //Put the image on the TextView
                tv.setCompoundDrawablesRelativeWithIntrinsicBounds(items[position].icon, 0, 0, 0);
                return v;
            }
        };
        new AlertDialog.Builder(this)
                .setTitle("Make a choice :")
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                String Phone = "+9191491 89644";
                                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Phone));
                                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                                            Manifest.permission.CALL_PHONE
                                    }, 1000);
                                }
                                startActivity(i);
                                break;
                            case 1:
                                String Email = "info@freshlybuilt.com";
                                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + Email));
                                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
                                break;
                            case 2:
                                String number = "91491 89644";
                                Uri uri = Uri.parse("smsto:" + number);
                                Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                                sendIntent.setPackage("com.whatsapp");
                                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(Intent.createChooser(sendIntent, ""));
                                } else {
                                    Toast.makeText(MainActivity.this, "Whatsapp not installed! please install whatsapp", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 3:
                                Uri ur = Uri.parse("https://instagram.com/freshlybuiltofficial?igshid=1x2c8s4w905b6");
                                Intent insta = new Intent(Intent.ACTION_VIEW, ur);

                                insta.setPackage("com.instagram.android");

                                try {
                                    startActivity(insta);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(MainActivity.this, "Instagram not installed! please install instagram", Toast.LENGTH_SHORT).show();
                                }
                                break;


                        }
                    }
                }).show();
    }


    public void fetchTags() {
        Log.d("FETCH1", "In func");

        Call<TagsListResponse> callTags = RetrofitClient.getInstance().getApi().tags();
        callTags.enqueue(new Callback<TagsListResponse>() {
            @Override
            public void onResponse(Call<TagsListResponse> call, Response<TagsListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        try {
                            tags = new ArrayList();
                            for (TagsList tagsList : response.body().getTags()) {
                                tags.add(tagsList.getSlug());
                            }
                            adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, tags);

                            autoCompleteTextView.setAdapter(adapter);
                            autoCompleteSearch();

                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TagsListResponse> call, Throwable t) {
                Log.d("FECTH3", t.getMessage());

            }
        });
    }

    public void autoCompleteSearch() {
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = adapter.getItem(position);
                Bundle b = new Bundle();
                b.putString("tags",s);
                Fragment_home fragment_home = new Fragment_home();
                fragment_home.setArguments(b);
                View v = MainActivity.this.getCurrentFocus();
                if(v!=null){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                }
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment_home).commit();

            }
        });


    }

    public void bottomSheetState(){
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if(i==BottomSheetBehavior.STATE_COLLAPSED){
                    autoCompleteTextView.setText("");
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }
}


