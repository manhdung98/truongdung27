package com.tintuc.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.manhdung.news.R;
import com.tintuc.entity.PostEntity;
import com.tintuc.interfac.AdapterListener;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter {
    static final int TYPE_ITEM_NORMAL = 0;
    static final int TYPE_ITEM_LOAD_MORE = 1;
    private AdapterListener listener;
    private List<PostEntity> postEntities;
    public PostAdapter(List<PostEntity> postEntities, AdapterListener listener ){
        this.postEntities = postEntities;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM_NORMAL){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, null);
            PostViewholder postViewholder = new PostViewholder(v);
            return postViewholder;
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, null);
        LoadMoreViewHolder loadMoreViewHolder = new LoadMoreViewHolder(v);
        return loadMoreViewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int pos) {
        if (holder instanceof PostViewholder){
            final PostEntity postEntity = postEntities.get(pos);
            final PostViewholder postViewholder = (PostViewholder) holder;

            postViewholder.tvTitle.setText(postEntity.getTitle());
            postViewholder.tvDesc.setText(postEntity.getDesc());

            Glide.with(postViewholder.imgThumb.getContext()).load(postEntity.getThumb()).into(postViewholder.imgThumb);
            postViewholder.rlPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemClickListener(null, pos, postViewholder);
                    }
                }
            });
        }else {
            final LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder) holder;
            loadMoreViewHolder.btnLoadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemClickListener(null, pos, loadMoreViewHolder);
                    }
                }
            });
        }

    }

    private class PostViewholder extends RecyclerView.ViewHolder{
        RelativeLayout rlPost;
        ImageView imgThumb;
        TextView tvTitle;
        TextView tvDesc;
        public PostViewholder(View itemView) {
            super(itemView);
            imgThumb = itemView.findViewById(R.id.img_thumb);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            rlPost = itemView.findViewById(R.id.rl_post);
        }
    }

    private class LoadMoreViewHolder extends RecyclerView.ViewHolder{
        Button btnLoadMore;
        public LoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
            btnLoadMore = itemView.findViewById(R.id.btn_load_more);
        }
    }

    @Override
    public int getItemViewType(int position){
        if (position < postEntities.size()){
            return TYPE_ITEM_NORMAL;
        }
        return TYPE_ITEM_LOAD_MORE;
    }

    @Override
    public int getItemCount() {
        return postEntities.size() + 1;
    }
}
