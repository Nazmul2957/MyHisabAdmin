package com.example.myhisabadmin.Adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhisabadmin.R;

import java.util.ArrayList;

public class EarnListAdaptar extends RecyclerView.Adapter<EarnListAdaptar.ViewHolders> {
    ArrayList<EarnModel> earnlist;
    Context context;

    public EarnListAdaptar(ArrayList<EarnModel> earnlist, Context context) {
        this.earnlist = earnlist;
        this.context = context;
    }

    @NonNull
    @Override
    public EarnListAdaptar.ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.earnlist_model, parent, false);

        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EarnListAdaptar.ViewHolders holder, int position) {
        holder.earnhead.setText(earnlist.get(position).getHead());

    }

    @Override
    public int getItemCount() {
        return earnlist.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {

        TextView earnhead;

        public ViewHolders(@NonNull View itemView) {
            super(itemView);

            earnhead = itemView.findViewById(R.id.earn_head);

        }
    }
}
