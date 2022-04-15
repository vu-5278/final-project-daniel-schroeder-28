package com.vanderbilt.flashcardapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FlashcardSetsAdapter extends RecyclerView.Adapter<FlashcardSetsAdapter.ViewHolder> {

    private final ArrayList<String> sets;

    public FlashcardSetsAdapter(ArrayList<String> setNames) {
        sets = setNames;
    }

    @NonNull
    @Override
    public FlashcardSetsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.flashcard_sets, parent, false);

        return new ViewHolder(v, new MySetsClickListener() {
            @Override
            public void onView(int p) {
                TextView textView = MySetsActivity.recyclerView.getLayoutManager().findViewByPosition(p).findViewById(R.id.textViewSetName);
                AppGlobals.setLastSetStudied(textView.getText().toString());

                Intent intentStudy = new Intent(context, StudyActivity.class);
                context.startActivity(intentStudy);
            }

            @Override
            public void onEdit(int p) {
                TextView textView = MySetsActivity.recyclerView.getLayoutManager().findViewByPosition(p).findViewById(R.id.textViewSetName);
                AppGlobals.setUnfinishedSet(AppGlobals.getUnfinishedSetFromSetName(textView.getText().toString()));

                Intent intentEdit = new Intent(context, AddSetActivity.class);
                intentEdit.putExtra("editingExistingSet",true);
                intentEdit.putExtra("existingSetName",textView.getText().toString());
                context.startActivity(intentEdit);
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull FlashcardSetsAdapter.ViewHolder holder, int position) {
        String set = sets.get(position);

        TextView textView = holder.textViewSetName;
        textView.setText(set);
        Button buttonView = holder.buttonViewSet;
        buttonView.setText("View");
        buttonView.setEnabled(true);
        Button buttonEdit = holder.buttonEditSet;
        buttonEdit.setText("Edit");
        buttonEdit.setEnabled(true);
    }

    @Override
    public int getItemCount() {
        return sets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewSetName;
        public Button buttonViewSet;
        public Button buttonEditSet;
        public MySetsClickListener clickListener;

        public ViewHolder(View v, MySetsClickListener clickListener) {
            super(v);

            textViewSetName = v.findViewById(R.id.textViewSetName);
            buttonViewSet = v.findViewById(R.id.buttonViewSet);
            buttonEditSet = v.findViewById(R.id.buttonEditSet);
            this.clickListener = clickListener;


            buttonViewSet.setOnClickListener(this);
            buttonEditSet.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonViewSet:
                    clickListener.onView(this.getLayoutPosition());
                    break;
                case R.id.buttonEditSet:
                    clickListener.onEdit(this.getLayoutPosition());
                    break;
                default:
                    break;
            }
        }
    }

    public interface MySetsClickListener {
        void onEdit(int p);
        void onView(int p);
    }
}

