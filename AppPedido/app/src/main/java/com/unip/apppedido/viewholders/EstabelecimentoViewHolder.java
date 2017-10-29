package com.unip.apppedido.viewholders;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unip.apppedido.R;

public class EstabelecimentoViewHolder extends ViewHolder {
    
    public TextView textViewNameEstabelecimento;
    public RelativeLayout relativeLayoutEstabelecimento;
    
    public EstabelecimentoViewHolder(View itemView) {
        super(itemView);

        textViewNameEstabelecimento = (TextView) itemView.findViewById(R.id.textViewNameEstabelecimento);
        relativeLayoutEstabelecimento = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutEstabelecimento);
    }
}
