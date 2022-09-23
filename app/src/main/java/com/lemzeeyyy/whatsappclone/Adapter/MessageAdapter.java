package com.lemzeeyyy.whatsappclone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lemzeeyyy.whatsappclone.R;
import com.lemzeeyyy.whatsappclone.model.Chats;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private Context context;
    private List<Chats> mChats;
    private String imageURL;
    // Firebase
    FirebaseUser fUser;

    public static final  int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT  = 1;

    public MessageAdapter(Context context, List<Chats> mChats, String imageURL) {
        this.context = context;
        this.mChats = mChats;
        this.imageURL = imageURL;
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == MSG_TYPE_LEFT){
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        Chats chat = mChats.get(position);
        holder.show_message.setText(chat.getMessage());
        if(imageURL.equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(context).load(imageURL).into(holder.profile_image);
        }
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        public TextView show_message;
        public ImageView profile_image;
        public TextView txt_seen;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.txt_seen_status);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChats.get(position).getSenderId().equals(fUser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else{
            return MSG_TYPE_LEFT;
        }
    }
}
