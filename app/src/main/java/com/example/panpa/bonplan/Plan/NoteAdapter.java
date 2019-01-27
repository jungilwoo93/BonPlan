package com.example.panpa.bonplan.Plan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.panpa.bonplan.Activities.MainActivity;
import com.example.panpa.bonplan.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class NoteAdapter extends BaseAdapter {
    private ArrayList<Note> listNote = new ArrayList<>();
    private Context context;

    public NoteAdapter(Context cont){
        //this.note=n;
        this.context=cont;
    }

    @Override
    public int getCount() {
        return listNote.size();
    }

    @Override
    public Object getItem(int position) {
        return listNote.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //Note note = getItem(position);
        //MainActivity.ViewHolder mainViewHolder = null;
        if(convertView== null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.list_item,null);
        }
        Note note = get(position);
        NoteAdapter.ViewHolder viewHolder = new NoteAdapter.ViewHolder();
        viewHolder.startTime = convertView.findViewById(R.id.startTimeInList);
        viewHolder.startTime.setText(note.getStartTime());
        Toast.makeText(context,note.getStartTime(), Toast.LENGTH_SHORT).show();
        viewHolder.title = convertView.findViewById(R.id.titleInList);
        viewHolder.title.setText(note.getTitle());
        viewHolder.switchValid = convertView.findViewById(R.id.valideNote);
        viewHolder.switchValid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()) Toast.makeText(context,compoundButton.getText().toString(),Toast.LENGTH_SHORT).show();
            }

            //Toast.makeText(context,"Button was clicked for list item "+ position, Toast.LENGTH_SHORT).show();

        });
        viewHolder.switchPost = convertView.findViewById(R.id.postponeNote);
        viewHolder.switchPost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()) Toast.makeText(context,compoundButton.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }




    public boolean add(Note object) {
        return listNote.add(object);
    }

    public boolean addAll(Collection<? extends Note> collection) {
        return listNote.addAll(collection);
    }

    public boolean containsAll(Collection<?> collection) {
        return listNote.containsAll(collection);
    }

    public boolean removeAll(Collection<?> collection) {
        return listNote.removeAll(collection);
    }

    public boolean addAll(int index, Collection<? extends Note> collection) {
        return listNote.addAll(index, collection);
    }

    public List<Note> subList(int start, int end) {
        return listNote.subList(start, end);
    }

    public void add(int index, Note object) {
        listNote.add(index, object);
    }

    public boolean remove(Object object) {
        return listNote.remove(object);
    }

    public Note set(int index, Note object) {
        return listNote.set(index, object);
    }

    public int indexOf(Object object) {
        return listNote.indexOf(object);
    }

    public ListIterator<Note> listIterator() {
        return listNote.listIterator();
    }

    public boolean retainAll(Collection<?> collection) {
        return listNote.retainAll(collection);
    }

    public void ensureCapacity(int minimumCapacity) {
        listNote.ensureCapacity(minimumCapacity);
    }

    public int size() {
        return listNote.size();
    }

    public Note remove(int index) {
        return listNote.remove(index);
    }

    public void clear() {
        listNote.clear();
    }

    public int lastIndexOf(Object object) {
        return listNote.lastIndexOf(object);
    }

    public <T> T[] toArray(T[] contents) {
        return listNote.toArray(contents);
    }

    public ListIterator<Note> listIterator(int location) {
        return listNote.listIterator(location);
    }

    public Iterator<Note> iterator() {
        return listNote.iterator();
    }

    public Object[] toArray() {
        return listNote.toArray();
    }

    public boolean contains(Object object) {
        return listNote.contains(object);
    }

    public Note get(int index) {
        return listNote.get(index);
    }

    public void trimToSize() {
        listNote.trimToSize();
    }

    public class ViewHolder{
        public TextView startTime;
        public TextView title;
        public CheckBox switchValid;
        public CheckBox switchPost;
    }
}


