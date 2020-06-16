package com.example.website.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.website.Adapters.Adapter_ques;
import com.example.website.Api.RetrofitClient;
import com.example.website.Ask;
import com.example.website.R;
import com.example.website.model.Avatar;
import com.example.website.model.QuestionResponse;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Q_A extends Fragment {
    LinearLayoutManager linearLayoutManager;
    Boolean isScrolling=false;
    int currentItems,totalItems,scrolledOutItems;
    RecyclerView recyclerView;
    ShimmerFrameLayout shimmerFrameLayout;
    ProgressBar progressBar;
    int page=1;
    Adapter_ques adapter_ques;
    FloatingActionButton floatingActionButton;
    List<QuestionResponse> q;
  //  List<AnswerResponse> a;
    List<String> avatar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.activity_fragment_ques,container,false);
        progressBar = v.findViewById(R.id.progress_ques);
        shimmerFrameLayout = v.findViewById(R.id.shimmer);
        recyclerView = v.findViewById(R.id.recyclerview_ques);
        q=new ArrayList<>();
      //  a = new ArrayList<>();
        adapter_ques = new Adapter_ques(getContext(),q);
        floatingActionButton = v.findViewById(R.id.floating_ques);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Ask.class));
            }
        });
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        quesFetch(page);
        nextQues();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }

    public void quesFetch(final int page){
        Call<List<QuestionResponse>> call = RetrofitClient.getInstance().getApi().ques(page);
        call.enqueue(new Callback<List<QuestionResponse>>() {
            @Override
            public void onResponse(Call<List<QuestionResponse>> call, Response<List<QuestionResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        q.addAll(response.body());
                        adapter_ques.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);

                        if (page == 1) {
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setAdapter(adapter_ques);
                        }

                    } catch (Exception e) {
                        Toast.makeText(getContext(), "question prob", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "No more Ques", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<QuestionResponse>> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage()+"\n / Check Your Connectivity", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void nextQues(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrolledOutItems = linearLayoutManager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems+scrolledOutItems==totalItems) && dy>10){
                    isScrolling = false;
                    progressBar.setVisibility(View.VISIBLE);
                    page+=1;
                    quesFetch(page);
                    Log.d("SCROLLE",String.valueOf(dy));
                }
            }
        });
    }


    /*

    public void ansFetch(){
        Call<List<AnswerResponse>> call2 = RetrofitClient.getInstance().getApi().ans();
        call2.enqueue(new Callback<List<AnswerResponse>>() {
            @Override
            public void onResponse(Call<List<AnswerResponse>> call, Response<List<AnswerResponse>> response) {
               try {

                   if (response.isSuccessful() && response.body() != null) {

                       Log.d("QUES3",q.toString());
                       Log.d("QUES7",a.toString());



                   }
               }
               catch (Exception e){
                   Toast.makeText(getContext(), "answer problem", Toast.LENGTH_SHORT).show();
                   Log.d("QUES2",e.getMessage());
               }
            }

            @Override
            public void onFailure(Call<List<AnswerResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                Log.d("QUES1",t.getMessage());

            }
        });
    }

     */

  /*  public void fetchAvatar(int id){
        final Call<Avatar> avatarCall = RetrofitClient.getInstance().getApi().avatar(id,"full");
        avatarCall.enqueue(new Callback<Avatar>() {
            @Override
            public void onResponse(Call<Avatar> call, Response<Avatar> response) {
                if(response.body()!=null){
                    try{
                        String s=response.body().getAvatar();
                    }
                    catch (Exception e){

                    }
                }
            }

            @Override
            public void onFailure(Call<Avatar> call, Throwable t) {

            }
        });
    }

   */
}

