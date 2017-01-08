package com.example.xiaoming.notebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by xiaomi on 9/11/2016.
 */
public class NoteAdapter extends ArrayAdapter<Note> {
    public NoteAdapter(Context context, ArrayList<Note> arraylist)
    {
        super(context,0,arraylist);

    }

    public static class ViewHolder
    {
        TextView noteTitle;
        TextView noteBody;
        ImageView noteIcon;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Note note= getItem(position);

        ViewHolder viewholder;
        if (convertView ==null)
        {
            viewholder = new ViewHolder();
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_row,parent,false);
            viewholder.noteTitle=(TextView)convertView.findViewById(R.id.listItemNoteTitle);
            viewholder.noteIcon= (ImageView)convertView.findViewById(R.id.listItemNoteImg);
            viewholder.noteBody= (TextView)convertView.findViewById(R.id.listItemNoteBody);
            convertView.setTag(viewholder);
        }
        else
        {
            viewholder=(ViewHolder)convertView.getTag();
        }
        viewholder.noteTitle.setText(note.getTitle());
        viewholder.noteBody.setText(note.getMessage());
        viewholder.noteIcon.setImageResource(note.getAssociatedDrawable());

        return convertView;
    }
}
