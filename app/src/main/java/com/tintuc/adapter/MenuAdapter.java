package com.tintuc.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.manhdung.news.R;
import com.tintuc.entity.MenuEntity;
import com.tintuc.interfac.AdapterListener;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter {

    private AdapterListener listener;
    private List<MenuEntity> menuEntities;

    public MenuAdapter(List<MenuEntity> menuEntities, AdapterListener listener){
        this.listener = listener;
        this.menuEntities = menuEntities;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item_menu, null);
        return new MenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,final int position) {
        final MenuViewHolder menuViewHolder = (MenuViewHolder) holder;
        final MenuEntity menuEntity = menuEntities.get(position);

        String title = menuEntity.getTitle();
        menuViewHolder.tvMenu.setText(title);
        if (menuEntity.isSelected()){
            menuViewHolder.rlItemMenu.setBackgroundResource(R.color.actionbar);
        }else {
            menuViewHolder.rlItemMenu.setBackgroundResource(R.color.white);
        }

        menuViewHolder.rlItemMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onItemClickListener(menuEntity, position, menuViewHolder);
                }
            }
        });
    }

    private class MenuViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rlItemMenu;
        TextView tvMenu;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            rlItemMenu = itemView.findViewById(R.id.rl_item_menu);
            tvMenu = itemView.findViewById(R.id.tv_menu);
        }

    }
    @Override
    public int getItemCount() {
        return menuEntities.size();
    }
}
