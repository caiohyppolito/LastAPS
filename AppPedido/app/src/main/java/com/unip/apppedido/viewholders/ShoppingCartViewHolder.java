package com.unip.apppedido.viewholders;

import android.media.Image;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unip.apppedido.R;

public class ShoppingCartViewHolder extends ViewHolder {

    public TextView textViewNameProduct;
    public TextView textViewQuantity;
    public TextView textViewTotalProduct;

    public ImageButton imageButtonAddMoreQuantity;
    public ImageButton imageButtonRemoveQuantity;

    public RelativeLayout relativeLayoutCart;

    public ShoppingCartViewHolder(View itemView) {
        super(itemView);

        textViewNameProduct = (TextView) itemView.findViewById(R.id.textViewNameProduct);
        textViewQuantity = (TextView) itemView.findViewById(R.id.textViewQuantity);
        textViewTotalProduct = (TextView) itemView.findViewById(R.id.textViewTotalProduct);

        imageButtonAddMoreQuantity = (ImageButton) itemView.findViewById(R.id.imageButtonAddQuantity);
        imageButtonRemoveQuantity = (ImageButton) itemView.findViewById(R.id.imageButtonRemoveQuantity);

        relativeLayoutCart = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutCart);
    }
}
