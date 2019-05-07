package com.firebase.utaputranto.tiketsaya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessBuyTicketAct extends AppCompatActivity {

    Animation app_splash, btt, ttb;
    Button btn_view_ticket, btn_my_dashboard;
    ImageView icon_success_ticket;
    TextView app_title, app_subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);

        btn_view_ticket     = findViewById(R.id.btn_view_ticket);
        btn_my_dashboard    = findViewById(R.id.btn_my_dashboard);
        icon_success_ticket = findViewById(R.id.icon_success_ticket);
        app_title           = findViewById(R.id.app_title);
        app_subtitle        = findViewById(R.id.app_subtitle);

        //load anim
        app_splash  = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt         = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb         = AnimationUtils.loadAnimation(this, R.anim.ttb);

        //run anim
        icon_success_ticket.startAnimation(app_splash);

        app_title.startAnimation(ttb);
        app_subtitle.startAnimation(ttb);

        btn_my_dashboard.startAnimation(btt);
        btn_view_ticket.startAnimation(btt);



    }
}
