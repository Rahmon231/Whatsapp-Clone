package com.lemzeeyyy.whatsappclone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lemzeeyyy.whatsappclone.MessageActivity;
import com.lemzeeyyy.whatsappclone.R;
import com.lemzeeyyy.whatsappclone.model.Users;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context context;
    private List<Users> myUsers;
    private Boolean isChat;

    public UserAdapter(Context context, List<Users> myUsers, Boolean isChat) {
        this.context = context;
        this.myUsers = myUsers;
        this.isChat = isChat;
    }


    public UserAdapter() {
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.user_item,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
      Users user = myUsers.get(position);
      holder.username.setText(user.getUsername());
        if (user.getImageURL().equals("default")){
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        }else{
            // Adding Glide Library
            Glide.with(context)
                    .load(user.getImageURL())
                    .into(holder.imageView);
        }
       //  Status check
        if (isChat){

            if(user.getStatus().equals("online")){
                holder.imageViewOn.setVisibility(View.VISIBLE);
                holder.imageViewOff.setVisibility(View.GONE);
            }else{

                holder.imageViewOn.setVisibility(View.GONE);
                holder.imageViewOff.setVisibility(View.VISIBLE);
            }
        }else{

            holder.imageViewOn.setVisibility(View.GONE);
            holder.imageViewOff.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, MessageActivity.class);
            i.putExtra("userid", user.getId());
            context.startActivity(i);

        });

    }

    @Override
    public int getItemCount() {
        return myUsers.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private ImageView imageView;
        private ImageView imageViewOn;
        private ImageView imageViewOff;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.textView30);
            imageView = itemView.findViewById(R.id.imageView);
            imageViewOn = itemView.findViewById(R.id.statusimageON);
            imageViewOff = itemView.findViewById(R.id.statusimageOFF);
        }
    }
}
