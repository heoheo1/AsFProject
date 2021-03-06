package com.hj.AsFProject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import AsFProject.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<Item> itemArrayList;
    Context context;

    public MyAdapter(Context context,ArrayList<Item> itemArrayList) {
        this.itemArrayList = itemArrayList;
        this.context =context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(itemArrayList.get(position).getTitle());
        holder.contents.setText(itemArrayList.get(position).getContents());
        if(itemArrayList.get(position).getUri()!=null) {
            holder.imageView.setImageURI(Uri.parse(itemArrayList.get(position).getUri()));
        }
        int i =position;
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(context, PictureActivtiy.class);
                    intent.putExtra("uri", itemArrayList.get(i).getUri());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView contents;
        ImageView imageView;
        ImageView img_comment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.txt_card_title);
            this.contents = itemView.findViewById(R.id.txt_card_contents);
            imageView = itemView.findViewById(R.id.card_image);
            img_comment =itemView.findViewById(R.id.img_comment);

            img_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context,CommentActivity.class);
                    int i =getAdapterPosition();
                    intent.putExtra("id", i);
                    Log.d("ddddd", i+"");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        }
    }
}
