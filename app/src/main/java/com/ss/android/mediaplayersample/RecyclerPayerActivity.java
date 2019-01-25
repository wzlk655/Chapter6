package com.ss.android.mediaplayersample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class RecyclerPayerActivity extends AppCompatActivity implements MyAdapter.ListItemClickListener{

    RecyclerView videoList;
    MyAdapter mMyadapter;
    StandardGSYVideoPlayer c_Player = null;

    String TAG = "RecyclerActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        videoList = findViewById(R.id.list_item_recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        videoList.setLayoutManager(layoutManager);
        videoList.setHasFixedSize(true);

        mMyadapter = new MyAdapter(this);
        videoList.setAdapter(mMyadapter);
        videoList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private int lastCompletelyVisibleItemPosition;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager=recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (visibleItemCount > 0 && lastCompletelyVisibleItemPosition >= totalItemCount - 1) {
                        Toast.makeText(RecyclerPayerActivity.this, "已滑动到底部!,触发loadMore", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                }
                Log.d(TAG, "onScrolled: lastVisiblePosition=" + lastCompletelyVisibleItemPosition);
                int firstCompletelyVisibleItemPosition = ((LinearLayoutManager)layoutManager).findFirstCompletelyVisibleItemPosition();
                View view = layoutManager.findViewByPosition(firstCompletelyVisibleItemPosition);
                if(view!=null)
                {
                    StandardGSYVideoPlayer player = view.findViewById(R.id.video_player);
                    if(!player.isInPlayingState()) {
                        player.startPlayLogic();
                        c_Player = player;
                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(c_Player!=null)
            c_Player.onVideoPause();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }

    @Override
    public void onListButtonClick(int clickedItemIndex) {
        startActivity(new Intent(RecyclerPayerActivity.this,DetailPlayerActivity.class));
    }
}
