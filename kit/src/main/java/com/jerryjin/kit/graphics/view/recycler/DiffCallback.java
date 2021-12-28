package com.jerryjin.kit.graphics.view.recycler;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

/**
 * Author: Jerry
 * Generated at: 2019-07-04 22:31
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
public class DiffCallback<T extends DiffCallback.IDiffer<T>> extends DiffUtil.Callback {

    private List<T> oldList;
    private List<T> newList;

    public DiffCallback(List<T> oldList, List<T> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        T oldItem = oldList.get(oldItemPosition);
        T newItem = newList.get(newItemPosition);
        return oldItem.areItemsTheSame(newItem);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        T oldItem = oldList.get(oldItemPosition);
        T newItem = newList.get(newItemPosition);
        return oldItem.areContentsTheSame(newItem);
    }

    public interface IDiffer<T> {
        boolean areItemsTheSame(T other);
        boolean areContentsTheSame(T other);
    }

}
