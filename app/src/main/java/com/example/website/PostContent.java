package com.example.website;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;

import android.view.Menu;
import android.view.MenuItem;

import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.website.Adapters.Adapter_PostContent;
import com.example.website.model.Posts;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.util.ArrayList;
import java.util.List;



public class PostContent extends AppCompatActivity {
    static int flag=1;
    Menu menu;
    Toolbar toolbar;
    CoordinatorLayout coordinatorLayout;
    ImageView img;
    TextView headingContent,authorContent,dateContent;
    RecyclerView recyclerView_content;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    Posts p;
    List<String> stringContent = new ArrayList<>();
    String imageUrl=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_content);
        toolbar = findViewById(R.id.toolBar_content);
        setSupportActionBar(toolbar);
        coordinatorLayout = findViewById(R.id.coordinator);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolBar);
        appBarLayout = findViewById(R.id.appBarLayout);
        img = findViewById(R.id.image_home_content);
        headingContent = findViewById(R.id.heading_home_content);
        authorContent = findViewById(R.id.author_home_content);
        dateContent = findViewById(R.id.date_home_content);
        recyclerView_content = findViewById(R.id.recycler_home_content);
        recyclerView_content.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        try {
            p = (Posts) getIntent().getSerializableExtra("CONTENT");
            dataSetter();
            recycleDataList();

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        collapsingToolBar();

    }


    public void dataSetter(){

       try {
            imageUrl = p.getThumbnailImage().getFull().getUrl();
        }catch (Exception e) {
        }
       getPalette();
       headingContent.setText(Jsoup.parse(p.getTitle()).text());
        dateContent.setText(p.getDate().substring(0,10)+"  |  "+p.getDate().substring(11,19));
        authorContent.setText(p.getAuthor().getName());


    }
    public void recycleDataList(){
        String s = p.getContent();
        int w=0,x=0,y=0 ,z=0,flag=0,h1=0,h2=0,h3=0,h4=0,h5=0;
        Document doc = Jsoup.parse(s);
        Document document = Jsoup.parse(s.replaceAll("(?i)<br[^>]*>", "br2n"));


        String content = doc.toString();
        String[] words = content.split("\\s+");
        Elements images = doc.select("img");
        Elements para = document.select("p");
        Elements heading1 = doc.select("h1");
        Elements heading2 = doc.select("h2");
        Elements heading3 = doc.select("h3");
        Elements heading4 = doc.select("h4");
        Elements heading5 = doc.select("h5");
        Elements list = doc.select("li");
        Elements code = document.select("pre");

        for(int i=0;i<words.length;i++){

            if(words[i].contains("<img")) {
                if (x < images.size()) {
                    String a1 = images.get(x).attr("src");
                    stringContent.add("img"+a1);
                    x++;
                }
            }else if(y==0) {
                if (words[i].contains("<p")) {
                    if (y < para.size()) {
                        String a2 = para.get(y).text().replaceAll("br2n","\n");
                        stringContent.add(a2);
                        y++;
                    }
                }
            }else if(words[i].contains("</p>") && flag==0){
                flag=1;
            }
            else
            if(words[i].contains("</p>") && flag==1){
                if(y<para.size()){
                    String a2 = para.get(y).text().replaceAll("br2n","\n");
                    stringContent.add(a2);
                    y++;
                }
            }
            else if(words[i].contains("</li>")){
                if(z<list.size()){
                    String a3 = list.get(z).text();
                    stringContent.add(a3);
                    z++;
                }
            }
            else if(words[i].contains("</pre>")){
                if(w<code.size()){
                    String a4=code.get(w).text().replaceAll("br2n","\n");
                    stringContent.add("cod"+a4);
                    w++;
                }
            }
            else if(words[i].contains("<h1>")){
                if(h1<heading1.size()){
                    String a5=heading1.get(h1).text();
                    stringContent.add("one"+a5);
                    h1++;
                }
            }
            else if(words[i].contains("<h2>")){
                if(h2<heading2.size()){
                    String a5=heading2.get(h2).text();
                    stringContent.add("two"+a5);
                    h2++;
                }
            }
            else if(words[i].contains("<h3>")){
                if(h3<heading3.size()){
                    String a5=heading3.get(h3).text();
                    stringContent.add("thr"+a5);
                    h3++;
                }
            }
            else if(words[i].contains("<h4>")){
                if(h4<heading4.size()){
                    String a5=heading4.get(h4).text();
                    stringContent.add("fou"+a5);
                    h4++;
                }
            }
            else if(words[i].contains("<h5>")){
                if(h5<heading5.size()){
                    String a5=heading5.get(h5).text();
                    stringContent.add("fiv"+a5);
                    h5++;
                }
            }

        }
        recyclerView_content.setAdapter(new Adapter_PostContent(stringContent,this));
    }

    public void collapsingToolBar(){

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("FreshlyBuilt");
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }

    public void getPalette(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int deviceHeight = displayMetrics.heightPixels;
        img.getLayoutParams().height = deviceHeight/3;
        Glide.with(this)
                .asBitmap()
                .placeholder(android.R.drawable.gallery_thumb)
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        img.setImageBitmap(resource);
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                int mutedColor = palette.getMutedColor(getResources().getColor(R.color.coloreyeBlue));
                                collapsingToolbarLayout.setContentScrimColor(mutedColor);
                                getWindow().setStatusBarColor(palette.getDarkMutedColor(getResources().getColor(R.color.colorPrimaryDark)));
                            }
                        });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.posttoolbar, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.like:
                //Toast.makeText(this, "like button", Toast.LENGTH_SHORT).show();
                if (flag == 1) {
                    menu.findItem(R.id.like).setIcon(R.drawable.ic_favorite_24px);
                    flag--;
                }else
                if (flag == 0) {
                    menu.findItem(R.id.like).setIcon(R.drawable.ic_favorite_border_24px);
                    flag++;
                }
                break;
            case R.id.comment:
                Toast.makeText(this, "Comment", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share:
                shareContent();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void shareContent() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String link = p.getUrl();
        intent.putExtra(Intent.EXTRA_TEXT, link);
        startActivity(Intent.createChooser(intent, "Share using"));
    }




    }



