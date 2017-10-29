package com.unip.apppedido.viewholders;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unip.apppedido.R;

public class CategoryViewHolder extends ViewHolder {

    public TextView textViewNameCategory;
    public RelativeLayout relativeLayoutCategory;

    public CategoryViewHolder(View itemView) {
        super(itemView);

        textViewNameCategory = (TextView) itemView.findViewById(R.id.textViewNameCategory);
        relativeLayoutCategory = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutCategory);
    }
}
