package com.smlnskgmail.jaman.randomnotes.components.support;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptiveRecyclerView extends RecyclerView {

    private View emptyView;

    private final AdapterDataObserver dataObserver = new AdapterDataObserver() {
        private void validateList() {
            Adapter adapter = getAdapter();
            if (adapter != null && emptyView != null) {
                if (adapter.getItemCount() == 0) {
                    setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public void onChanged() {
            validateList();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            validateList();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            validateList();
        }
    };

    public AdaptiveRecyclerView(@NonNull Context context) {
        super(context);
    }

    public AdaptiveRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AdaptiveRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        Adapter currentAdapter = getAdapter();
        if (currentAdapter != null) {
            currentAdapter.unregisterAdapterDataObserver(dataObserver);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(dataObserver);
            dataObserver.onChanged();
        }
    }

}
