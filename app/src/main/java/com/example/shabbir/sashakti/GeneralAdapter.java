package com.example.shabbir.sashakti;

/**
 * Created by shabbir on 11/16/2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shabbir on 4/8/17.
 */



public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.MyViewHolder>{
    private List<General> movieList;




    public void updateList(List<General> list){
        movieList = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public ImageView imageView;

        public MyViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.textGeneral);
            imageView=(ImageView)view.findViewById(R.id.imageGeneral);


        }
    }

    public GeneralAdapter(List<General> movies){
        movieList = movies;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.general_list_row,parent,false);

        final MyViewHolder myViewHolder = new MyViewHolder(view);
        return  myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        General movie = movieList.get(position);
        holder.title.setText(movie.getName());
        Picasso.with(holder.title.getContext())
                .load(movie.getImageResource())
                .into(holder.imageView);
        //holder.imageView.setImageResource(movie.getImageResource());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


}
