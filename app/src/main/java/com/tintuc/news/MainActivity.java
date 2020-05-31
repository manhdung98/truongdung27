package com.manhdung.news;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.tintuc.adapter.MenuAdapter;
import com.tintuc.adapter.PostAdapter;
import com.tintuc.api.NewsApi;
import com.tintuc.entity.MenuEntity;
import com.tintuc.entity.PostEntity;
import com.tintuc.interfac.AdapterListener;
import com.tintuc.interfac.HttpCallback;
import com.tintuc.until.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    private ImageView imageMenu;
    private RecyclerView rvMenu;
    private RecyclerView rvPost;
    private MenuAdapter menuAdapter;
    private Context context = this;
    private List<MenuEntity> menuEntities = new ArrayList<>();
    private List<PostEntity> postEntities = new ArrayList<>();
    private PostAdapter postAdapter;
    private int categoryId;
    private TextView tvTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MenuEntity menuThoiSu = new MenuEntity();
        menuThoiSu.setId(1);
        menuThoiSu.setTitle("Thời sự");
        menuThoiSu.setSelected(true);

        MenuEntity menuTheThao = new MenuEntity();
        menuTheThao.setId(2);
        menuTheThao.setTitle("Thể thao");

        MenuEntity menuKinhTe = new MenuEntity();
        menuKinhTe.setId(3);
        menuKinhTe.setTitle("Kinh tế");

        MenuEntity menuChinhTri = new MenuEntity();
        menuChinhTri.setId(4);
        menuChinhTri.setTitle("Chính trị");

        menuEntities.add(menuThoiSu);
        menuEntities.add(menuTheThao);
        menuEntities.add(menuKinhTe);
        menuEntities.add(menuChinhTri);

        imageMenu = findViewById(R.id.img_menu);
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(menuThoiSu.getTitle());


        final SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen._12sdp);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen._60sdp);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.layout_menu);

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle();
            }
        });
        menuAdapter = new MenuAdapter(menuEntities, new AdapterListener() {
            @Override
            public void onItemClickListener(Object o, int pos, RecyclerView.ViewHolder holder) {

                for (int i = 0; i < menuEntities.size(); ++i) {
                    MenuEntity entity = menuEntities.get(i);
                    entity.setSelected(false);
                }

                MenuEntity menuEntity = (MenuEntity) o;
                categoryId = menuEntity.getId();
                Log.e("categoryId", categoryId + "");
                tvTitle.setText(menuEntity.getTitle());
                menuEntity.setSelected(true);
                menuAdapter.notifyDataSetChanged();
                menu.toggle();

                postEntities.clear();
                postAdapter.notifyDataSetChanged();
                layDanhsachBaiviet();
            }
        });

        rvMenu = menu.findViewById(R.id.rv_menu);
        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        rvMenu.setItemAnimator(new DefaultItemAnimator());
        rvMenu.setAdapter(menuAdapter);

        rvPost = findViewById(R.id.rv_post);
        rvPost.setLayoutManager(new LinearLayoutManager(this));
        rvPost.setItemAnimator(new DefaultItemAnimator());

        postAdapter = new PostAdapter(postEntities, new AdapterListener() {
            @Override
            public void onItemClickListener(Object o, int pos, RecyclerView.ViewHolder holder) {
                if (pos < postEntities.size()){
                    //click len bai post
                   // Toast.makeText(context, "click len bai post", Toast.LENGTH_SHORT).show();
                    PostEntity postEntity = (PostEntity) o;
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("post", postEntity);
                    startActivity(intent);
                }
                else {
                    //click len nut xem them
                    //Toast.makeText(context, "click len nut xem them", Toast.LENGTH_SHORT).show();
                    layDanhsachBaiviet();
                }
            }
        });

        rvPost.setAdapter(postAdapter);
        layDanhsachBaiviet();
    }

    private void layDanhsachBaiviet() {
        NewsApi.getListPost(context, categoryId, 3, postEntities.size(), new HttpCallback() {
            @Override
            public void onSuccess(final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray jsonArray = new JSONArray(s);
                            for (int i = 0; i < jsonArray.length(); ++i) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                PostEntity postEntity = new PostEntity(jsonObject);
                                postEntities.add(postEntity);
                                LogUtil.d("postEntity", postEntity.toString());
                            }
                            postAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }
        });
    }
}
//http://192.168.1.5/Tintuc/webservice/get_list_post.php?category_id2&limit10&offset0 // đấu = đâu?