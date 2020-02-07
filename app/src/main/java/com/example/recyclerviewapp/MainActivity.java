package com.example.recyclerviewapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewClick {
RecyclerView recyclerView;
//ActionMode actionMode;
List<String> moviesList;
Toolbar toolbar;
SwipeRefreshLayout swipeRefreshLayout;
RecyclerViewAdapter recyclerViewAdapter;
MainActivity m;
Snackbar snackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout=findViewById(R.id.swiperefreshlayout);
       toolbar=findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

        moviesList=new ArrayList<>();
        moviesList.add("hoye");
        moviesList.add("na na re...");
        moviesList.add("bas kar bewde");
        moviesList.add("kay chutiye");
        moviesList.add("mazaak tha boss");
        moviesList.add("tere papa kon");
        moviesList.add("na bhai ");
        moviesList.add("man singh tomar");
        moviesList.add("bho shing");
        moviesList.add("pung pung ");
        moviesList.add("na na karte hogya");
        moviesList.add("bhaiching ka sher");
        moviesList.add("koi na koi to hai");
        moviesList.add("pumkar");
        moviesList.add("na na karte");
        moviesList.add("kumkum bhagya");
        moviesList.add("dishhyom");
        moviesList.add("bhai ka bhai");
        moviesList.add("teri baat ka raat");
        moviesList.add("kay baat");
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter=new RecyclerViewAdapter(moviesList,this);
        recyclerView.setAdapter(recyclerViewAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                moviesList.add("bhai added");

                recyclerViewAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
     ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
     itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onClick(int position,View view) {
        Toast.makeText(this,moviesList.get(position),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemLongClick(int position,View view) {

            startSupportActionMode(callback);
            view.setSelected(true);




//      recyclerViewAdapter.notifyItemRemoved(position);
//        moviesList.remove(position);


    }
    ActionMode.Callback callback=new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.actioncontextual,menu);
            mode.setTitle("ActionMode");
            mode.setSubtitle("delshare");


            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch(item.getItemId()){
                case R.id.delete:
                    Toast.makeText(MainActivity.this,"delete icon pressed",Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
                case R.id.share:
                    Toast.makeText(MainActivity.this,"share icon pressed",Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
                default:
                    return false;

            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
//             actionMode=null;
        }
    };

    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int from=viewHolder.getAdapterPosition();
            int to=target.getAdapterPosition();
            Collections.swap(moviesList,from,to);
            recyclerView.getAdapter().notifyItemMoved(from,to);



            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final String deletedMovie;
           switch(direction){

               case ItemTouchHelper.LEFT:
                   final int position=viewHolder.getAdapterPosition();
                   deletedMovie=moviesList.get(position);
                   moviesList.remove(position);
                   recyclerViewAdapter.notifyItemRemoved(position);
                   snackbar.make(recyclerView,deletedMovie, Snackbar.LENGTH_LONG)
                           .setAction("Undo", new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   moviesList.add(position,deletedMovie);
                                   recyclerViewAdapter.notifyItemInserted(position);
                               }
                           }).show();

                   break;
               case ItemTouchHelper.RIGHT:
                   break;
           }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_file,menu);
       MenuItem item= menu.findItem(R.id.search);
        final SearchView searchView= (SearchView) item.getActionView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            searchView.setBackgroundColor(getColor(R.color.black));

        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
