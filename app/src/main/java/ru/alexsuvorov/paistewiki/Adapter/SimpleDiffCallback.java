package ru.alexsuvorov.paistewiki.Adapter;

import android.support.v7.util.DiffUtil;

import java.util.List;

import ru.alexsuvorov.paistewiki.ItemModel.NewsModel;

public class SimpleDiffCallback extends DiffUtil.Callback {

    private final List<NewsModel> oldList;
    private final List<NewsModel> newList;

    SimpleDiffCallback(List<NewsModel> oldList, List<NewsModel> newList) {
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
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return areItemsTheSame(oldItemPosition, newItemPosition);
    }
}