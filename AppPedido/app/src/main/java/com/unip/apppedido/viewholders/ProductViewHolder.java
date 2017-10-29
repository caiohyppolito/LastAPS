package com.unip.apppedido.viewholders;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unip.apppedido.R;

public class ProductViewHolder extends ViewHolder {

    public TextView textViewNameProduct;
    public RelativeLayout relativeLayoutProduct;

    public ProductViewHolder(View itemView) {
        super(itemView);

        textViewNameProduct = (TextView) itemView.findViewById(R.id.textViewNameProduct);
        relativeLayoutProduct = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutProduct);
    }
}
