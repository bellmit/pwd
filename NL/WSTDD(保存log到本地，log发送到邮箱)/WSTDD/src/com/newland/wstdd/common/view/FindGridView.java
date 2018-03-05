package com.newland.wstdd.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

 

public class FindGridView extends GridView {

    public FindGridView(Context context) {

        super(context);

    }

 

    public FindGridView(Context context, AttributeSet attrs) {

        super(context, attrs);

    }

 

    public FindGridView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

    }

 

    @Override

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int heightSpec;

 

        if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {

            // The <span id="4_nwp" style="width: auto; height: auto; float: none;"><a id="4_nwl" style="text-decoration: none;" href="http://cpro.baidu.com/cpro/ui/uijs.php?adclass=0&app_id=0&c=news&cf=1001&ch=0&di=128&fv=0&is_app=0&jk=253534860e1030ee&k=great&k0=great&kdi0=0&luki=2&n=10&p=baidu&q=31010181_cpr&rb=0&rs=1&seller_id=1&sid=ee30100e86343525&ssp2=1&stid=0&t=tpclicked3_hc&td=2318395&tu=u2318395&u=http%3A%2F%2Fblog%2Echengyunfeng%2Ecom%2F%3Fp%3D444&urlid=0" target="_blank" mpid="4"><span style="width: auto; height: auto; color: rgb(0, 0, 255); font-size: 14px; float: none;">great</span></a></span> Android "hackatlon", the love, the magic.

            // The two leftmost bits in the height <span id="5_nwp" style="width: auto; height: auto; float: none;"><a id="5_nwl" style="text-decoration: none;" href="http://cpro.baidu.com/cpro/ui/uijs.php?adclass=0&app_id=0&c=news&cf=1001&ch=0&di=128&fv=0&is_app=0&jk=253534860e1030ee&k=measure&k0=measure&kdi0=0&luki=4&n=10&p=baidu&q=31010181_cpr&rb=0&rs=1&seller_id=1&sid=ee30100e86343525&ssp2=1&stid=0&t=tpclicked3_hc&td=2318395&tu=u2318395&u=http%3A%2F%2Fblog%2Echengyunfeng%2Ecom%2F%3Fp%3D444&urlid=0" target="_blank" mpid="5"><span style="width: auto; height: auto; color: rgb(0, 0, 255); font-size: 14px; float: none;">measure</span></a></span> spec have

            // a special meaning, hence we can't use them to describe height.

            heightSpec = MeasureSpec.makeMeasureSpec(

                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

        }

        else {

            // Any other height should be <span id="6_nwp" style="width: auto; height: auto; float: none;"><a id="6_nwl" style="text-decoration: none;" href="http://cpro.baidu.com/cpro/ui/uijs.php?adclass=0&app_id=0&c=news&cf=1001&ch=0&di=128&fv=0&is_app=0&jk=253534860e1030ee&k=res&k0=res&kdi0=0&luki=3&n=10&p=baidu&q=31010181_cpr&rb=0&rs=1&seller_id=1&sid=ee30100e86343525&ssp2=1&stid=0&t=tpclicked3_hc&td=2318395&tu=u2318395&u=http%3A%2F%2Fblog%2Echengyunfeng%2Ecom%2F%3Fp%3D444&urlid=0" target="_blank" mpid="6"><span style="width: auto; height: auto; color: rgb(0, 0, 255); font-size: 14px; float: none;">res</span></a></span>pected as is.

            heightSpec = heightMeasureSpec;

        }

 

        super.onMeasure(widthMeasureSpec, heightSpec);

    }

}

