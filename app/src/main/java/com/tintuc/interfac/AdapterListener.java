package com.tintuc.interfac;

import android.support.v7.widget.RecyclerView;

public interface AdapterListener {
    public void onItemClickListener(Object o, int pos, RecyclerView.ViewHolder holder);
}
