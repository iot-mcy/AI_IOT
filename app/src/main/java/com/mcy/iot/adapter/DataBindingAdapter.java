package com.mcy.iot.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcy.iot.BR;

import java.util.Collection;
import java.util.List;

/**
 * Created by mcy on 2017/4/25.
 */

public class DataBindingAdapter<T> extends RecyclerView.Adapter<DataBindingAdapter.BindingViewHolder> {

    private int layoutId;
    private int variableId;
    private ObservableList<T> list = new ObservableArrayList<>();

    public DataBindingAdapter(int layoutId, int variableId) {
        this.layoutId = layoutId;
        this.variableId = variableId;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        Object o = list.get(position);
        holder.binding.setVariable(variableId, o);
        if (onClickListener != null)
            holder.binding.setVariable(BR.onClickListener, this.onClickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        //TODO 这里要判断Item的类型，并返回对应的view
        return position;
    }

    static class BindingViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        BindingViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void remove(T o) {
        int size = list.size();
        list.remove(o);
        notifyDataSetChanged();
    }

    public void add(T o) {
        int size = list.size();
        list.add(o);
        notifyItemInserted(size);
    }

    public void addAll(Collection<T> collection) {
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public List<T> getList() {
        return list;
    }

    public int getListSize() {
        return list.size();
    }

    public void clearAll() {
        list.clear();
        notifyDataSetChanged();
    }

    private View.OnClickListener onClickListener = null;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
