package com.example.myhisabadmin.Adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhisabadmin.Model.AdminUserList.Datum;
import com.example.myhisabadmin.R;

import java.util.List;

public class UserListAdaptar extends RecyclerView.Adapter<UserListAdaptar.ViewHolders> {

    List<Datum> userlist;
    Context context;

    private int currentPage = 1;
    private int lastPage = 1;
    private boolean loading = false;

    public UserListAdaptar(List<Datum> userlist, Context context) {
        this.userlist = userlist;
        this.context = context;
    }

    public void addData(List<Datum> newData) {
        newData.addAll(newData);
        notifyDataSetChanged();
    }

    public boolean hasMoreData() {
        return currentPage < lastPage;
    }
    public void setLoading(boolean isLoading) {
        loading = isLoading;
    }
    public boolean isLoading() {
        return loading;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int page) {
        currentPage = page;
    }

    public void setLastPage(int page) {
        lastPage = page;
    }



    @NonNull
    @Override
    public UserListAdaptar.ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userlist_model, parent, false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdaptar.ViewHolders holder, int position) {
        holder.User_Name.setText(userlist.get(position).getName());
        holder.User_Phone.setText(userlist.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView User_Name, User_Phone;

        public ViewHolders(@NonNull View itemView) {
            super(itemView);

            User_Name = itemView.findViewById(R.id.user_name);
            User_Phone = itemView.findViewById(R.id.user_phn);
        }
    }
}
