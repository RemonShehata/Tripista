package com.iti.intake40.tripista.trip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.intake40.tripista.R;
import com.iti.intake40.tripista.core.model.Note;

import java.util.ArrayList;
import java.util.HashMap;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private HashMap<String, Note> notes ;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> noteList ;
    private  Note note;

    public NotesAdapter(HashMap<String, Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
        inflater = LayoutInflater.from(context);
        noteList = new ArrayList<>(notes.keySet());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.note_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String key = noteList.get(position);
        note = notes.get(key);
        holder.noteTitle.setText(note.getNoteDesc());
        if(note.getNoteState()==0)
            holder.state.setChecked(false);
        else
            holder.state.setChecked(true);

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView noteTitle;
        CheckBox state;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.note_title);
            state = itemView.findViewById(R.id.state);
        }
    }
}
