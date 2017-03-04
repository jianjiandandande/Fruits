package comyhyhyhte.example.gaowenxing.materialtest;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefresh;

    private DrawerLayout mDrawerLayout;
    private Fruit[] fruits={new Fruit(" ",R.drawable.ic_pingguo),
            new Fruit("西瓜",R.drawable.ic_xigua),
            new Fruit("草莓",R.drawable.ic_caomei),
            new Fruit("橙子",R.drawable.ic_chengzi),
            new Fruit("菠萝",R.drawable.ic_boluo)};

    private FruitAdapter adapter;
    private List<Fruit> mList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefresh=(SwipeRefreshLayout)this.findViewById(R.id.swipe_Refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                reFresh();
            }
        });
        Toolbar toolbar=(Toolbar)this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout=(DrawerLayout)this.findViewById(R.id.drawer_layout);
        NavigationView navView=(NavigationView)this.findViewById(R.id.nav_view);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);//显示导航菜单按钮
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);//设置导航菜单按钮图标
        }

        initFruits();
        RecyclerView recycleView=(RecyclerView)this.findViewById(R.id.rv_list);
        GridLayoutManager manager=new GridLayoutManager(this,2);
        recycleView.setLayoutManager(manager);
        adapter=new FruitAdapter(mList);
        recycleView.setAdapter(adapter);
        navView.setCheckedItem(R.id.nav_call);//默认项为Call
        //菜单的点击事件
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        FloatingActionButton fab=(FloatingActionButton)this.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"删除数据",Snackbar.LENGTH_SHORT)
                        .setAction("取消",new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "数据已恢复", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this, "你点击了Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "你点击了Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "你点击了Setting", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
    private void initFruits(){
        mList.clear();
        for(int i=0;i<50;i++){
            Random random=new Random();
            int index=random.nextInt(fruits.length);
            mList.add(fruits[index]);
        }
    }
    /**
     * 下拉刷新
     */
    private void reFresh(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                //将线程切换为主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        adapter.notifyDataSetChanged();//通知数据发生了变化
                        swipeRefresh.setRefreshing(false);//隐藏刷新进度条
                    }
                });
            }
        }).start();
    }
}
