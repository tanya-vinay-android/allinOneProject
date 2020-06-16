package com.example.website.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.website.PostContent;
import com.example.website.R;
import com.example.website.model.Posts;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.Serializable;
import java.util.List;

public class Adapter_home extends RecyclerView.Adapter<Adapter_home.views> {
    private List<Posts> post;
    private Context context;
    private AdapterOnClickEvents adapterOnClickEvents;

    public Adapter_home(Context context,List<Posts> post,AdapterOnClickEvents adapterOnClickEvents) {
        this.adapterOnClickEvents = adapterOnClickEvents;
        this.context = context;
        this.post = post;
    }

    @NonNull
    @Override
    public views onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_home_card,parent,false);
        return new views(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final views holder, int position) {

        final Posts p = post.get(position);
        String image=null;
        try {
             image = p.getThumbnailImage().getFull().getUrl();
        }
        catch (Exception e) {
        }
//        if(image==null) {
//                Glide.with(context).load(android.R.drawable.gallery_thumb).into(holder.image);
//            }else {
//                Glide.with(context).load(image).into(holder.image);
//            }
        Glide.with(context).load(image).placeholder(android.R.drawable.gallery_thumb).into(holder.image);
        Document title = Jsoup.parse(p.getTitle());
        holder.heading.setText(title.text());
        holder.date.setText(p.getDate().substring(0,10)+"  |  "+p.getDate().substring(11,19));
        holder.author.setText(p.getAuthor().getName());
        String excerpt = p.getExcerpt();
        String s = android.text.Html.fromHtml(excerpt).toString();
        holder.text.setText(s);
        holder.heading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterOnClickEvents.onClickContent(p,holder.image);
            }
        });
        holder.see_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterOnClickEvents.onClickContent(p,holder.image);
            }
        });


    }

    @Override
    public int getItemCount() {
        return post.size();

    }

    public class views extends RecyclerView.ViewHolder {
        TextView text,date,heading,author,see_more;
        ImageView image;
        public views(@NonNull View itemView) {
            super(itemView);
            see_more = itemView.findViewById(R.id.see_more);
            author = itemView.findViewById(R.id.author_home);
            text = itemView.findViewById(R.id.post_home);
            date = itemView.findViewById(R.id.date_home);
            heading = itemView.findViewById(R.id.heading_home);
            image = itemView.findViewById(R.id.image_home);
        }
    }
    public interface AdapterOnClickEvents{
        void onClickContent(Posts object,ImageView imageView);
    }



   /* public void openCustomTabs(final String url){

    }*/
}
