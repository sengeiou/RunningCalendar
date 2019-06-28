package com.everglow.paobu.huanlepao.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.everglow.paobu.huanlepao.R;
import com.everglow.paobu.huanlepao.step.UpdateUiCallBack;
import com.everglow.paobu.huanlepao.step.service.StepService;
import com.everglow.paobu.huanlepao.step.utils.SharedPreferencesUtils;
import com.everglow.paobu.huanlepao.view.StepArcView;

/**
 * 记步主页
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_data;
    private StepArcView cc;
    private SharedPreferencesUtils sp;

    private void assignViews() {
        cc = (StepArcView) findViewById(R.id.cc);
        findViewById(R.id.suiji).setOnClickListener(this);
        findViewById(R.id.mohuan).setOnClickListener(this);
        findViewById(R.id.rl_huilv).setOnClickListener(this);
        findViewById(R.id.kuaishan).setOnClickListener(this);
        findViewById(R.id.rl_gesui).setOnClickListener(this);
        findViewById(R.id.rl_daxie).setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        assignViews();
        initData();
        addListener();
    }


    private void addListener() {

    }

    private void initData() {
        sp = new SharedPreferencesUtils(this);
        //获取用户设置的计划锻炼步数，没有设置过的话默认7000
        String planWalk_QTY = (String) sp.getParam("planWalk_QTY", "7000");
        //设置当前步数为0
        cc.setCurrentCount(Integer.parseInt(planWalk_QTY), 0);
        setupService();
    }


    private boolean isBind = false;

    /**
     * 开启计步服务
     */
    private void setupService() {
        Intent intent = new Intent(this, StepService.class);
        isBind = bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    /**
     * 用于查询应用服务（application Service）的状态的一种interface，
     * 更详细的信息可以参考Service 和 context.bindService()中的描述，
     * 和许多来自系统的回调方式一样，ServiceConnection的方法都是进程的主线程中调用的。
     */
    ServiceConnection conn = new ServiceConnection() {
        /**
         * 在建立起于Service的连接时会调用该方法，目前Android是通过IBind机制实现与服务的连接。
         * @param name 实际所连接到的Service组件名称
         * @param service 服务的通信信道的IBind，可以通过Service访问对应服务
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            StepService stepService = ((StepService.StepBinder) service).getService();
            //设置初始化数据
            String planWalk_QTY = (String) sp.getParam("planWalk_QTY", "7000");
            cc.setCurrentCount(Integer.parseInt(planWalk_QTY), stepService.getStepCount());

            //设置步数监听回调
            stepService.registerCallback(new UpdateUiCallBack() {
                @Override
                public void updateUi(int stepCount) {
                    String planWalk_QTY = (String) sp.getParam("planWalk_QTY", "7000");
                    cc.setCurrentCount(Integer.parseInt(planWalk_QTY), stepCount);
                }
            });
        }

        /**
         * 当与Service之间的连接丢失的时候会调用该方法，
         * 这种情况经常发生在Service所在的进程崩溃或者被Kill的时候调用，
         * 此方法不会移除与Service的连接，当服务重新启动的时候仍然会调用 onServiceConnected()。
         * @param name 丢失连接的组件名称
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.suiji) {
            startActivity(new Intent(this, SetPlanActivity.class));
        } else if (i == R.id.mohuan) {
            startActivity(new Intent(this, HistoryActivity.class));
        } else if (i == R.id.rl_huilv) {
            ARouter.getInstance().build("/jibu/home").navigation();
        } else if (i == R.id.kuaishan) {
            ARouter.getInstance().build("/jibu/sport").navigation();
        } else if (i == R.id.rl_gesui) {
            ARouter.getInstance().build("/web/ActivityWeb1").navigation();
        }
        else if (i == R.id.rl_daxie) {
            ARouter.getInstance().build("/web/ActivityWeb2").navigation();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBind) {
            this.unbindService(conn);
        }
    }
}
