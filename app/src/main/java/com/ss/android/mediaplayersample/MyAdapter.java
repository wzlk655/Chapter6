package com.ss.android.mediaplayersample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.zip.Inflater;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private static final String TAG="MyAdapter";
    private int mNumberItems;
    private static int viewHolderCount;
    private final ListItemClickListener mOnClickListener;

    public MyAdapter(ListItemClickListener listener) {
        mNumberItems = 5;
        viewHolderCount=0;
        mOnClickListener=listener;
        Log.d(TAG,"Created");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.video_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,viewGroup,shouldAttachToParentImmediately);
        MyViewHolder viewHolder=new MyViewHolder(view);

        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: " + viewHolderCount);
        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Log.d(TAG, "onBindViewHolder: #" + position);
        myViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final StandardGSYVideoPlayer mVideoPlayer;
        private final Button mButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mVideoPlayer = itemView.findViewById(R.id.video_player);
            mButton = itemView.findViewById(R.id.btn_details);
            itemView.setOnClickListener(v -> {
                mOnClickListener.onListItemClick(getAdapterPosition());
            });
            mButton.setOnClickListener(v -> {
                mOnClickListener.onListButtonClick(getAdapterPosition());
            });
        }

        @Override
        public void onClick(View v) {
            int  clickedPosition = getAdapterPosition();
            if(mOnClickListener!=null){
                mOnClickListener.onListItemClick(clickedPosition);
            }
        }

        public void bind(int position){
            String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
            mVideoPlayer.setUp(source1,true,"测试视频");
            ImageView imageView = new ImageView(mVideoPlayer.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(R.mipmap.xxx1);
            mVideoPlayer.setThumbImageView(imageView);
            //mVideoPlayer.setImageResource(R.mipmap.xxx1);
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
        void onListButtonClick(int clickedItemIndex);
    }
}
