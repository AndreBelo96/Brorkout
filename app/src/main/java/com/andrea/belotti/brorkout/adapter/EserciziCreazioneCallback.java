package com.andrea.belotti.brorkout.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class EserciziCreazioneCallback extends ItemTouchHelper.Callback {

    private ListaEserciziTouchHelperContract listaEserciziTouchHelperContract;

    public EserciziCreazioneCallback(ListaEserciziTouchHelperContract listaEserciziTouchHelperContract) {
        this.listaEserciziTouchHelperContract = listaEserciziTouchHelperContract;

    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlag, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        this.listaEserciziTouchHelperContract.onRowMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());

        return true;
    }


    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {

        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof ItemEsercizioCreateAdapter.ViewHolder) {
                ItemEsercizioCreateAdapter.ViewHolder myViewHolder = (ItemEsercizioCreateAdapter.ViewHolder) viewHolder;
                listaEserciziTouchHelperContract.onRowSelected(myViewHolder);
            }
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if (viewHolder instanceof ItemEsercizioCreateAdapter.ViewHolder) {
            ItemEsercizioCreateAdapter.ViewHolder myViewHolder = (ItemEsercizioCreateAdapter.ViewHolder) viewHolder;
            listaEserciziTouchHelperContract.onRowClear(myViewHolder);
        }

    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    public interface ListaEserciziTouchHelperContract {

        void onRowMove(int from, int to);
        void onRowSelected(ItemEsercizioCreateAdapter.ViewHolder myViewHolder);
        void onRowClear(ItemEsercizioCreateAdapter.ViewHolder myViewHolder);

    }
}
