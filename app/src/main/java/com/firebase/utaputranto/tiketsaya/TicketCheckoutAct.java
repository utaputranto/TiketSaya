package com.firebase.utaputranto.tiketsaya;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TicketCheckoutAct extends AppCompatActivity {

    Button btn_buy_ticket, btn_mines, btn_plus;
    TextView textJumlahTicket, textMyBalance, textTotalHarga;
    Integer valueJumlahTicket = 1;
    Integer myBalance = 200;
    Integer valueTotalHarga = 0;
    Integer valueHargaTiket = 75;
    ImageView notice_uang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        btn_mines = findViewById(R.id.btn_mines);
        btn_plus = findViewById(R.id.btn_plus);
        textJumlahTicket = findViewById(R.id.textJumlahTicket);
        textMyBalance = findViewById(R.id.textMyBalance);
        textTotalHarga = findViewById(R.id.textTotalHarga);
        notice_uang = findViewById(R.id.notice_uang);

        //setting value baru untuk beberapa komponen
        textJumlahTicket.setText(valueJumlahTicket.toString());
        textMyBalance.setText("US$ " + myBalance + "");
        valueTotalHarga= valueHargaTiket*valueJumlahTicket;
        textTotalHarga.setText("US$ " + valueTotalHarga +"");


        //hide btn mines
        btn_mines.animate().alpha(0).setDuration(300).start();
        btn_mines.setEnabled(false);
        notice_uang.setVisibility(View.GONE);

        btn_mines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueJumlahTicket -=1;
                textJumlahTicket.setText(valueJumlahTicket.toString());
                if(valueJumlahTicket < 2){
                    btn_mines.animate().alpha(0).setDuration(300).start();
                    btn_mines.setEnabled(false);
                }
                valueTotalHarga= valueHargaTiket*valueJumlahTicket;
                textTotalHarga.setText("US$ " + valueTotalHarga +"");
                    if(valueTotalHarga < myBalance){
                        btn_buy_ticket.animate().translationY(0)
                                .alpha(1).setDuration(350).start();
                        btn_buy_ticket.setEnabled(true);
                        textMyBalance.setTextColor(Color.parseColor("#203DD1"));
                        notice_uang.setVisibility(View.GONE);

                }
            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueJumlahTicket +=1;
                textJumlahTicket.setText(valueJumlahTicket.toString());
                if(valueJumlahTicket > 1){
                    btn_mines.animate().alpha(1).setDuration(300).start();
                    btn_mines.setEnabled(true);
                }
                valueTotalHarga= valueHargaTiket*valueJumlahTicket;
                textTotalHarga.setText("US$ " + valueTotalHarga +"");
                if(valueTotalHarga > myBalance){
                    btn_buy_ticket.animate().translationY(250)
                            .alpha(0).setDuration(350).start();
                    btn_buy_ticket.setEnabled(false);
                    textMyBalance.setTextColor(Color.parseColor("#D1206B"));
                    notice_uang.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosuccessbuyticket = new Intent(TicketCheckoutAct.this, SuccessBuyTicketAct.class);
                startActivity(gotosuccessbuyticket);
                finish();
            }
        });
    }
}
