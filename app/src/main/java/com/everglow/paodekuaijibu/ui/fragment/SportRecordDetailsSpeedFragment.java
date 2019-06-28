package com.everglow.paodekuaijibu.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.everglow.paodekuaijibu.R;
import com.everglow.paodekuaijibu.commmon.bean.PathRecord;
import com.everglow.paodekuaijibu.ui.BaseFragment;
import com.everglow.paodekuaijibu.ui.activity.SportRecordDetailsActivity;

import java.text.DecimalFormat;

import butterknife.BindView;

/** 
 * 描述: 运动记录详情-配速
 * 作者: james
 * 日期: 2019/2/27 15:25 
 * 类名: SportRecordDetailsSpeedFragment
*/
public class SportRecordDetailsSpeedFragment extends BaseFragment {

    @BindView(R.id.tvDistribution)
    TextView tvDistribution;

    private PathRecord pathRecord = null;

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sportrecorddetailsspeed;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            pathRecord = bundle.getParcelable(SportRecordDetailsActivity.SPORT_DATA);
        }

        if (null != pathRecord)
            tvDistribution.setText(decimalFormat.format(pathRecord.getDistribution()));
    }

    @Override
    public void initListener() {

    }

}
