package com.timo.certification.ui.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timo.certification.R;
import com.timo.certification.data.database.UserEntry;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListAdapterViewHolder> {

    private Context mContext;
    private UserListAdapterOnItemClickHandler mClickHandler;

    private List<UserEntry> mUserEntryList;

    public UserListAdapter(Context context, UserListAdapterOnItemClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public UserListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_user,parent,false);
        return new UserListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapterViewHolder holder, int position) {
        UserEntry currentUser = mUserEntryList.get(position);

        holder.phoneText.setText(currentUser.getPhone());
        holder.usernameText.setText(currentUser.getUsername());
    }

    @Override
    public int getItemCount() {
        if (mUserEntryList == null) return 0;
        else return mUserEntryList.size();
    }

    void swapForecast(List<UserEntry> entries) {
        mUserEntryList = entries;
        notifyDataSetChanged();
    }

    public interface UserListAdapterOnItemClickHandler {
        void onItemClick(int id);
    }

    class UserListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView usernameText;
        TextView phoneText;


        public UserListAdapterViewHolder(View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.username_text);
            phoneText = itemView.findViewById(R.id.phone_text);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            int id = mUserEntryList.get(adapterPosition).getId();
            mClickHandler.onItemClick(id);
        }
    }
}
