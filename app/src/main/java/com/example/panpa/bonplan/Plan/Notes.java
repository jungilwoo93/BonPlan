package com.example.panpa.bonplan.Plan;

import java.util.ArrayList;
import java.util.List;

public class Notes {
    private ArrayList<Note> notes;
    public Notes(){
        notes = new ArrayList<Note>();
    }

    public ArrayList<Note> getNotes(){
        /*if(notes == null){
            notes = new ArrayList<Note>();
        }*/
        return this.notes;
    }

    public void add(Note note){
        this.notes.add(note);
    }

    public Note get(int pos){
        return this.notes.get(pos);
    }

    public void update(Note from , Note to){
        if(getNotes().contains(from)){
            int index = getNotes().indexOf(from);
            getNotes().remove(from);
            getNotes().set(index,to);
        }

    }

    public void delete(Note note){
        if(getNotes().contains(note)){
            getNotes().remove(note);
        }
    }
}
