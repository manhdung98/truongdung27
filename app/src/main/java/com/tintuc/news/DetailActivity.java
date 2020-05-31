package com.manhdung.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tintuc.api.NewsApi;
import com.tintuc.entity.PostEntity;
import com.tintuc.interfac.HttpCallback;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;

public class DetailActivity extends AppCompatActivity {
    private ImageView imgBack;
    private WebView webView;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        final PostEntity postEntity = (PostEntity) bundle.getSerializable("post");
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView = findViewById(R.id.wv_content);
        tvTitle = findViewById(R.id.tv_title2);
        tvTitle.setText(postEntity.getTitle());

        NewsApi.getPostDetail(this, postEntity.getId(), new HttpCallback() {
            @Override
            public void onSuccess(final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            PostEntity postEntity1 = new PostEntity(jsonObject);
                            String data = "<html><head><title></title><head></head><body>"+postEntity1.getContent()+"</body></html>";
                            webView.loadData(data, "text/html; charset=utf-8", "utf-8");
                        }catch (Exception e){
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DetailActivity.this, "ket noi mang co van de!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
