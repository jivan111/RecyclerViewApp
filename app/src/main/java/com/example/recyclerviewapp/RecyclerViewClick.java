package com.example.recyclerviewapp;

import android.view.View;

public interface RecyclerViewClick {
    void  onClick(int position, View view);
    void onItemLongClick(int position,View view);

}
