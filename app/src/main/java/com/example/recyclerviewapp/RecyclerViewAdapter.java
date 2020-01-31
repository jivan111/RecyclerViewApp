package com.example.recyclerviewapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {
   List<String> movieList;
   List<String>movieListAll;
   RecyclerViewClick recyclerViewClick;

    public RecyclerViewAdapter(List<String> movieList,RecyclerViewClick recyclerViewClick) {
        this.movieList = movieList;
        this.movieListAll=new ArrayList<>(movieList);
        this.recyclerViewClick=recyclerViewClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.row_element,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        Log.d("view",String.valueOf(view)+" parent"+String.valueOf(parent.getContext())+" viewtype"+String.valueOf(viewType));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.textView.setText(String.valueOf(movieList.get(position)));
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter =new Filter() {
        @Override
        //perform filtering in backgroud thread
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.d(TAG, "performFiltering: "+constraint);
            List<String> filterList=new ArrayList<>();
            if(!constraint.toString().isEmpty()){
                for(String mov:movieListAll){
                    if(mov.toString().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filterList.add(mov);
                    }

                }



            }else{
                filterList.addAll(movieListAll);

            }


            FilterResults filterResults=new FilterResults();
            filterResults.values=filterList;
            return filterResults;


        }
//filtered results is sent to this method
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            movieList.clear();
            movieList.addAll((Collection<? extends String>) results.values);
            notifyDataSetChanged();

        }
    };

    class ViewHolder extends RecyclerView.ViewHolder  {

        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView2);
            textView=itemView.findViewById(R.id.textView2);
//            itemView.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    recyclerViewClick.onClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    recyclerViewClick.onItemLongClick(getAdapterPosition());

                    return false;
                }
            });

//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    movieList.remove(getAdapterPosition());
//                    notifyItemRemoved(getAdapterPosition());
//                    return false;
//                }
//            });

        }


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
