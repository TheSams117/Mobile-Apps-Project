package com.example.entregaaplicacionesmoviles.list.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.entregaaplicacionesmoviles.R;
import com.example.entregaaplicacionesmoviles.activities.RecommendActivity;
import com.example.entregaaplicacionesmoviles.list.Viewmodel.RecommendViewModel;
import com.example.entregaaplicacionesmoviles.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class RecommedAdapter extends RecyclerView.Adapter<RecommendViewModel> {
    private ArrayList<User> users;
    private OnRecommendClickListener listener;

    public RecommedAdapter(){
        users = new ArrayList<>();
    }

    @Override
    public RecommendViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recommendrow,parent,false);
        RecommendViewModel recommendViewModel = new RecommendViewModel(view);

        return recommendViewModel;
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendViewModel holder, int position) {
        User user = users.get(position);
        holder.getNameRow().setText(user.getName());
        holder.getActionRow().setOnClickListener(
                v->{
                    listener.onRecommendClick(user);
                }
        );

        FirebaseStorage.getInstance().getReference().child("profiles").child("photo").child(user.getId()).getDownloadUrl().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Glide.with(holder.getImageRow()).load(task.getResult().toString()).into(holder.getImageRow());
            }

        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void addUserToRecommend(User user){
        users.add(user);
        notifyDataSetChanged();
    }

    public void clear(){
        users.clear();
        notifyDataSetChanged();
    }

    public void setListener(OnRecommendClickListener onRecommendClickListener){
        this.listener = onRecommendClickListener;
    }

    public interface OnRecommendClickListener{
        void onRecommendClick(User user);
    }

    public void removeRecommend(User user){
        users.remove(user);
        notifyDataSetChanged();
    }
}
