package com.unip.apppedido.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unip.apppedido.R;
import com.unip.apppedido.models.CategoryModel;
import com.unip.apppedido.models.ShoppingCartModel;
import com.unip.apppedido.utils.MoneyFormatUtil;
import com.unip.apppedido.viewholders.CategoryViewHolder;
import com.unip.apppedido.viewholders.ShoppingCartViewHolder;

import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartViewHolder> {
    public interface IShoppingCartListener {
        void onClickListener(View view, ShoppingCartModel itemShoppingCart, boolean isToRemoveQuantity);
    }

    private IShoppingCartListener mShoppingCartListener;
    private List<ShoppingCartModel> mListShoppingCart;

    public ShoppingCartAdapter(List<ShoppingCartModel> listShoppingCart, IShoppingCartListener listener) {
        mListShoppingCart = listShoppingCart;
        mShoppingCartListener = listener;
    }

    @Override
    public ShoppingCartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View viewInflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_cart_adapter, parent, false);

        ShoppingCartViewHolder viewHolder = new ShoppingCartViewHolder(viewInflater);

        viewHolder.imageButtonAddMoreQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parent = (View) view.getParent().getParent().getParent();

                int position = (int) parent.getTag();

                mShoppingCartListener.onClickListener(viewInflater, mListShoppingCart.get(position), false);
            }
        });

        viewHolder.imageButtonRemoveQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parent = (View) view.getParent().getParent().getParent();

                int position = (int) parent.getTag();

                mShoppingCartListener.onClickListener(viewInflater, mListShoppingCart.get(position), true);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ShoppingCartViewHolder holder, int position) {
        ShoppingCartModel item = mListShoppingCart.get(position);

        holder.textViewNameProduct.setText(item.getName());
        holder.textViewQuantity.setText(String.valueOf(item.getQuantity()));
        holder.textViewTotalProduct.setText(MoneyFormatUtil.formatToMoney(item.getTotalProduct()));

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mListShoppingCart != null ? mListShoppingCart.size() : 0;
    }
}