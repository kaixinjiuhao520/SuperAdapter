package com.jessewu.superadapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jessewu.library.SuperAdapter;
import com.jessewu.library.builder.HeaderBuilder;
import com.jessewu.library.paging.LoadDataListener;
import com.jessewu.library.paging.LoadDataStatus;
import com.jessewu.library.view.ViewHolder;
import com.jessewu.library.widget.SimpleFooterBuilder;
import com.jessewu.superadapter.data.DataModel;
import com.jessewu.superadapter.data.TestEntity;

public class MoreDataActivity extends AppCompatActivity {




    SuperAdapter<TestEntity> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);



        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter = new SuperAdapter<TestEntity>(R.layout.view_list_item_1){

            @Override
            public void bindView(ViewHolder itemView, TestEntity data,int position) {
                itemView.<TextView>getView(R.id.text_1).setText(data.getTitle());
            }
        };
        recyclerView.setAdapter(adapter);

        HeaderBuilder builder = new HeaderBuilder() {
            @Override
            public int getHeaderLayoutId() {
                return R.layout.view_header_single;
            }

            @Override
            public void bindHeaderView(ViewHolder viewHolder) {
                viewHolder.<ImageView>getView(R.id.header_img).setImageResource(R.mipmap.ic_launcher);
                viewHolder.<TextView>getView(R.id.header_tv).setText("这是一个头部");
            }
        };

        adapter.addHeader(builder);



        adapter.addFooter(new SimpleFooterBuilder("这是个底部","正在加载数据中","加载数据失败","已经到底啦"));

        adapter.setEmptyDataView(R.layout.activity_empty_view);

        findViewById(R.id.btn).setVisibility(View.VISIBLE);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
            }
        });

    }

    private void setData(){
        adapter.clearData();
        adapter.setPaginationData(-1, new LoadDataListener() {
            @Override
            public void onLoadingData(final int loadPage, final LoadDataStatus loadDataStatus) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadDataStatus.onSuccess(DataModel.getMoreData(loadPage));
                    }
                },2000);
            }
        });
    }

}
