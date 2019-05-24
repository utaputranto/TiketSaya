package com.firebase.utaputranto.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class TicketCheckoutAct extends AppCompatActivity {

    Button btn_buy_ticket, btn_mines, btn_plus;
    TextView textJumlahTicket, textMyBalance, textTotalHarga, nama_wisata, lokasi, ketentuan;
    Integer valueJumlahTicket = 1;
    Integer myBalance = 0;
    Integer valueTotalHarga = 0;
    Integer valueHargaTiket = 0;
    ImageView notice_uang;
    LinearLayout btn_back;

    DatabaseReference reference, reference2, reference3, reference4;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    Integer sisa_balance = 0;

    String date_wisata = "";
    String time_wisata = "";


    //generarte nomor secara random
    //karna kita ingin
    Integer nomor_transaksi = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        getUsernameLocal();

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        btn_mines = findViewById(R.id.btn_mines);
        btn_plus = findViewById(R.id.btn_plus);
        textJumlahTicket = findViewById(R.id.textJumlahTicket);
        textMyBalance = findViewById(R.id.textMyBalance);
        textTotalHarga = findViewById(R.id.textTotalHarga);
        notice_uang = findViewById(R.id.notice_uang);
        btn_back = findViewById(R.id.btn_back);

        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);

        //setting value baru untuk beberapa komponen
        textJumlahTicket.setText(valueJumlahTicket.toString());


        //hide btn mines
        btn_mines.animate().alpha(0).setDuration(300).start();
        btn_mines.setEnabled(false);
        notice_uang.setVisibility(View.GONE);

        //mengambil data dari user yang sedang login
        reference2 = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myBalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                textMyBalance.setText("US $ " + myBalance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //mengambil data dari firebase
        reference = FirebaseDatabase.getInstance().getReference()
                .child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());

                date_wisata = dataSnapshot.child("date_wisata").getValue().toString();
                time_wisata = dataSnapshot.child("time_wisata").getValue().toString();

                valueHargaTiket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());
                valueTotalHarga = valueHargaTiket * valueJumlahTicket;
                textTotalHarga.setText("US$ " + valueTotalHarga + "");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btn_mines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueJumlahTicket -= 1;
                textJumlahTicket.setText(valueJumlahTicket.toString());
                if (valueJumlahTicket < 2) {
                    btn_mines.animate().alpha(0).setDuration(300).start();
                    btn_mines.setEnabled(false);
                }
                valueTotalHarga = valueHargaTiket * valueJumlahTicket;
                textTotalHarga.setText("US$ " + valueTotalHarga + "");
                if (valueTotalHarga < myBalance) {
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
                valueJumlahTicket += 1;
                textJumlahTicket.setText(valueJumlahTicket.toString());
                if (valueJumlahTicket > 1) {
                    btn_mines.animate().alpha(1).setDuration(300).start();
                    btn_mines.setEnabled(true);
                }
                valueTotalHarga = valueHargaTiket * valueJumlahTicket;
                textTotalHarga.setText("US$ " + valueTotalHarga + "");
                if (valueTotalHarga > myBalance) {
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
                //menyimpan data user kepada firebase dan membuar tabel baru "My Tickets"
                reference3 = FirebaseDatabase.getInstance().getReference()
                        .child("MyTickets").child(username_key_new)
                        .child(nama_wisata.getText().toString() + nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket").setValue(nama_wisata.getText().toString() + nomor_transaksi);
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("jumlah_tiket").setValue(valueJumlahTicket.toString());
                        reference3.getRef().child("date_wisata").setValue(date_wisata);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);

                        Intent gotosuccessbuyticket = new Intent(TicketCheckoutAct.this, SuccessBuyTicketAct.class);
                        startActivity(gotosuccessbuyticket);
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //mengupdate data balance kepada users (yang saat ini login)
                //mengambil data user dari firebase
                reference4 = FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_balance = myBalance - valueTotalHarga;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
