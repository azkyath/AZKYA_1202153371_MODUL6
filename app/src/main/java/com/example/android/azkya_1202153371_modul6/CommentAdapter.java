package com.example.android.azkya_1202153371_modul6;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Azkya Telisha Harton on 4/1/2018.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    Context context;
    List<Comment> commentList;
//    RoundedBitmapDrawable rounded;

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mUsername;
        TextView mComment;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img_avatar);
            mUsername=itemView.findViewById(R.id.tv_username);
            mComment=itemView.findViewById(R.id.tv_comment);
        }
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_comment,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Comment comment= commentList.get(position);

        holder.mUsername.setText(comment.getUsername());
        holder.mComment.setText(comment.getComment());
//        setupImageRounded();
        holder.imageView.setImageResource(R.drawable.ic_person_black_24dp);

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
