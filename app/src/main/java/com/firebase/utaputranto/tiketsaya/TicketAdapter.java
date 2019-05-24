package com.firebase.utaputranto.tiketsaya;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    Context context;
    ArrayList<MyTicket> myTickets;

    public TicketAdapter(Context context, ArrayList<MyTicket> myTickets) {
        this.context = context;
        this.myTickets = myTickets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater
                .from(context).inflate(R.layout
                .item_myticket,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.xnama_wisata.setText(myTickets.get(i).getNama_wisata());
        viewHolder.xlokasi.setText(myTickets.get(i).getLokasi());
        viewHolder.xjumlah_tiket.setText(myTickets.get(i).getJumlah_tiket()+" Ticket");

        final String getNamaWisata = myTickets.get(i).getNama_wisata();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomyticketdetails = new Intent(context,MyTicketDetailAct.class);
                gotomyticketdetails.putExtra("nama_wisata",getNamaWisata);
                context.startActivity(gotomyticketdetails);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myTickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView xnama_wisata, xlokasi, xjumlah_tiket;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            xnama_wisata    = itemView.findViewById(R.id.xnama_wisata);
            xlokasi    = itemView.findViewById(R.id.xlokasi);
            xjumlah_tiket    = itemView.findViewById(R.id.xjumlah_tiket);

        }
    }
}
