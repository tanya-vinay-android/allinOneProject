package com.example.website.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.website.R;

import java.util.List;

public class Adapter_PostContent extends RecyclerView.Adapter {
    List<String> result;
    Context context;

    public Adapter_PostContent(List<String> result, Context context) {
        this.result = result;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (result.get(position).startsWith("img")) {
            return 0;
        } else if(result.get(position).startsWith("cod")){
            return 1;
        }else
            return 2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if(viewType==0){
            view = inflater.inflate(R.layout.content_recycle_card_image,parent,false);
            return new ViewHolderOne(view);
        }else if(viewType==1){
            view = inflater.inflate(R.layout.content_code_view,parent,false);
            return new ViewHolderThree(view);
        }
        view = inflater.inflate(R.layout.content_recycle_card_text,parent,false);
        return new ViewHolderTwo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String content = result.get(position);
        if(content.startsWith("img")){
            ViewHolderOne viewHolderOne = (ViewHolderOne) holder;
            Glide.with(context).load(content.substring(3)).into(viewHolderOne.imageRecycleContent);
        }
        else if(content.startsWith("one")){
            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
            viewHolderTwo.textRecycleContent.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            viewHolderTwo.textRecycleContent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolderTwo.textRecycleContent.setText(content.substring(3));
        }
        else if(content.startsWith("two")){
            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
            viewHolderTwo.textRecycleContent.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
            viewHolderTwo.textRecycleContent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolderTwo.textRecycleContent.setText(content.substring(3));
        }
        else if(content.startsWith("thr")){
            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
            viewHolderTwo.textRecycleContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 18.72);
            viewHolderTwo.textRecycleContent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolderTwo.textRecycleContent.setText(content.substring(3));
        }
        else if(content.startsWith("fou")){

            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
            viewHolderTwo.textRecycleContent.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
            viewHolderTwo.textRecycleContent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            viewHolderTwo.textRecycleContent.setText(content.substring(3));
        }
        else if(content.startsWith("fiv")){

            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
            viewHolderTwo.textRecycleContent.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            viewHolderTwo.textRecycleContent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            viewHolderTwo.textRecycleContent.setText(content.substring(3));
        }else if(content.startsWith("cod")){
            ViewHolderThree viewHolderThree = (ViewHolderThree) holder;
            viewHolderThree.code.setText(content.substring(3));
        }
        else {
            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
            viewHolderTwo.textRecycleContent.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            viewHolderTwo.textRecycleContent.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            viewHolderTwo.textRecycleContent.setText(content+"\n");
        }

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolderOne extends RecyclerView.ViewHolder{
        ImageView imageRecycleContent;
        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            imageRecycleContent = itemView.findViewById(R.id.image_recycle_content);

        }
    }

    public class ViewHolderTwo extends RecyclerView.ViewHolder{

        TextView textRecycleContent;
        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);

            textRecycleContent = itemView.findViewById(R.id.textview_content_recycle);

        }
    }
    public class ViewHolderThree extends RecyclerView.ViewHolder{

        TextView code;
        ScrollView scrollView;


        public ViewHolderThree(@NonNull View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.textView_code);
        }
    }


}
