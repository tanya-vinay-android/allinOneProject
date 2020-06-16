package com.example.website.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.website.Adapters.Adapter_home;
import com.example.website.Api.RetrofitClient;
import com.example.website.PostContent;
import com.example.website.R;
import com.example.website.model.PostResponse;
import com.example.website.model.Posts;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_home extends Fragment implements Adapter_home.AdapterOnClickEvents {
    ShimmerFrameLayout shimmerFrameLayoutHome;
     int page=1,totalpage;
    ProgressBar progressBar1,progressBar2,progressBar3;
    LinearLayoutManager manager;
    Adapter_home adapter_home;
    Boolean isScrolling = false;
    int currentItems,totalItems,scrolledOutItems;
    private RecyclerView recyclerView;
    private List<Posts> postsList = new ArrayList<>();
    private ObjectAnimator progressAnimator;
    String tag;
    SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.activity_home_fragment,container,false);
        shimmerFrameLayoutHome = v.findViewById(R.id.shimmerHome);
        progressBar1 = v.findViewById(R.id.home_progress);
        recyclerView =  v.findViewById(R.id.recycler_home);
        swipeRefreshLayout = v.findViewById(R.id.refresh_homeFragmet);
//------------progressbar1  for header to show loading of fragment_home-------------
        adapter_home = new Adapter_home(getContext(),postsList,this);
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);

        Bundle b1 = getArguments();
        if(b1!=null) {
            tag = b1.getString("tags");
            homePost(tag);
        }else
        if(b1==null) {
            homePosts(page);    //to fetch posts(post count - 10)
            nextPost();     //more posts after scrolling to end
        }


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                shimmerFrameLayoutHome.startShimmer();
                shimmerFrameLayoutHome.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                postsList.clear();
                homePosts(page);
                nextPost();
                swipeRefreshLayout.setRefreshing(false);


            }
        });

        return  v;

    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayoutHome.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayoutHome.stopShimmer();
    }

    public void homePosts(final int page){
        Call<PostResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .post(page);
        call.enqueue(new Callback<PostResponse>() {
                         @Override
                         public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                             try {
                                 if (response.isSuccessful() && response.body().getPosts() != null) {
                                     postsList.addAll(response.body().getPosts());
                                     adapter_home.notifyDataSetChanged();
                                     progressBar1.setVisibility(View.GONE);

                                     if (page == 1) {
                                         shimmerFrameLayoutHome.stopShimmer();
                                         shimmerFrameLayoutHome.setVisibility(View.GONE);
                                         recyclerView.setVisibility(View.VISIBLE);
                                        totalpage = response.body().getPages();
                                         recyclerView.setAdapter(adapter_home);
                                     }
                                 }



                             } catch (Exception e) {
                                 Toast.makeText(getActivity(), "catch   "+page, Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onFailure(Call<PostResponse> call, Throwable t) {
                             Toast.makeText(getActivity(),t.getMessage()+"\n OR Check Your Connectivity", Toast.LENGTH_SHORT).show();

                         }
                     }
        );


    }

    public void nextPost(){
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
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrolledOutItems = manager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems+scrolledOutItems==totalItems) && dy>10){
                    isScrolling = false;
                    page+=1;
                    if(page>totalpage){
                        Toast.makeText(getContext(), "No more posts", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    progressBar1.setVisibility(View.VISIBLE);
                    homePosts(page);



                }
            }
        });
    }


    public void homePost(final String tag){
        Call<PostResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .tagPost(tag);
        call.enqueue(new Callback<PostResponse>() {
                         @Override
                         public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                             try {
                                 if (response.isSuccessful() && response.body().getPosts() != null) {
                                     if (!postsList.isEmpty()) {
                                         postsList.clear();
                                     }
                                     postsList.addAll(response.body().getPosts());
                                     adapter_home.notifyDataSetChanged();
                                     progressBar1.setVisibility(View.GONE);
                                     recyclerView.setAdapter(adapter_home);
                                     Log.d("RESS","HY");


                                 }
                             } catch (Exception e) {
                                 Toast.makeText(getActivity(), "catch   "+page, Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onFailure(Call<PostResponse> call, Throwable t) {
                             Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT).show();

                         }
                     }
        );


    }


    @Override
    public void onClickContent(Posts object, ImageView imageView) {
        try {
            Intent intent = new Intent(getActivity(), PostContent.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),imageView,"postImage_transition");
            intent.putExtra("CONTENT", object);
            startActivity(intent,options.toBundle());
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}



