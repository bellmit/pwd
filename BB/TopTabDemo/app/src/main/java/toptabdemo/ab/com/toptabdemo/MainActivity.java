package toptabdemo.ab.com.toptabdemo;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import toptabdemo.ab.com.toptabdemo.bottomtab.BottomTabActivity;
import toptabdemo.ab.com.toptabdemo.bottomtab.BottomTabCustomActivity;
import toptabdemo.ab.com.toptabdemo.normalindicator.TopTabActivity;
import toptabdemo.ab.com.toptabdemo.underlinepageindicator.ULActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        findViewById(R.id.toptabactivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TopTabActivity.class));
            }
        });

        findViewById(R.id.ulactivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ULActivity.class));
            }
        });

        findViewById(R.id.bottomtabactivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BottomTabActivity.class));
            }
        });

        findViewById(R.id.BottomTabCustomActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BottomTabCustomActivity.class));
            }
        });
    }

}
