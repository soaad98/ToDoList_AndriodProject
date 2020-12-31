package com.lab.todolist.Utils;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListPaddingDecoration extends RecyclerView.ItemDecoration {
    private final static int PADDING_IN_DIPS = 26;
    private final int mPadding;

    public ListPaddingDecoration(@NonNull Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, PADDING_IN_DIPS, metrics);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int itemPosition = parent.getChildAdapterPosition(view);
        if (itemPosition == RecyclerView.NO_POSITION) {
            return;
        }
        outRect.bottom = mPadding;

        final RecyclerView.Adapter adapter = parent.getAdapter();
        if ((adapter != null) && (itemPosition == adapter.getItemCount() - 1)) {
            outRect.bottom = mPadding;
        }
    }

}