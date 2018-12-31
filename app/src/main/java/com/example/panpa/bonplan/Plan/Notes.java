package com.example.panpa.bonplan.Plan;

import java.util.ArrayList;
import java.util.List;

public class Notes {
    private List<Note> notes;
    public Notes(){

    }

    public List<Note> getNotes(){
        if(notes == null){
            notes = new ArrayList<Note>();
        }
        return this.notes;
    }

    public void add(Note note){
        getNotes().add(note);
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
