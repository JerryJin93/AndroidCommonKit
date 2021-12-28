package com.jerryjin.kit.graphics.view.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jerryjin.kit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Jerry
 * Generated at: 2019-07-04 22:01
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version:
 * Description:
 */
@SuppressWarnings({"WeakerAccess", "unchecked"})
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<T>>
        implements View.OnClickListener, View.OnLongClickListener, AdapterCallback<T> {


    private List<T> mDataList;
    private AdapterListener<T> mListener;

    public RecyclerAdapter() {
        this(null);
    }

    public RecyclerAdapter(AdapterListener<T> listener) {
        this(new ArrayList<T>(), listener);
    }

    public RecyclerAdapter(List<T> mDataList, AdapterListener<T> mListener) {
        this.mDataList = mDataList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType, parent, false);
        ViewHolder<T> holder = onCreateViewHolder(root, viewType);
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);
        root.setTag(R.id.tag_recycler_view_holder, holder);
        holder.mCallback = this;
        return holder;
    }

    protected abstract ViewHolder<T> onCreateViewHolder(View root, int viewType);

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    @LayoutRes
    protected abstract int getItemViewType(int position, T data);

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<T> holder, int position) {
        T data = mDataList.get(position);
        holder.onBind(data);
    }

    @Override
    public void update(ViewHolder<T> holder, T data) {
        int position = holder.getAdapterPosition();
        if (position >= 0) {
            mDataList.set(position, data);
            notifyItemChanged(position);
        }
    }

    public void add(T data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        ViewHolder<T> holder = (ViewHolder<T>) v.getTag(R.id.tag_recycler_view_holder);
        if (mListener != null) {
            int position = holder.getAdapterPosition();
            mListener.onItemClick(holder, mDataList.get(position));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ViewHolder<T> holder = (ViewHolder<T>) v.getTag(R.id.tag_recycler_view_holder);
        if (mListener != null) {
            int position = holder.getAdapterPosition();
            mListener.onItemLongClick(holder, mDataList.get(position));
            return true; // why
        }
        return false;
    }

    public interface AdapterListener<T> {
        void onItemClick(ViewHolder<T> holder, T data);

        void onItemLongClick(ViewHolder<T> holder, T data);
    }

    public static abstract class ViewHolder<T> extends RecyclerView.ViewHolder {

        protected T mData;
        private AdapterCallback<T> mCallback;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(T data) {
            this.mData = data;
            onBind(data);
        }

        public void updateData(T data) {
            if (this.mCallback != null) {
                this.mCallback.update(this, data);
            }
        }

        protected abstract void onBind(T data);
    }

    public abstract static class AdapterListenerImpl<T> implements AdapterListener<T> {
        @Override
        public void onItemClick(ViewHolder<T> holder, T data) {

        }

        @Override
        public void onItemLongClick(ViewHolder<T> holder, T data) {

        }
    }
}
