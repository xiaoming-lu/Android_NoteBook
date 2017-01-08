package com.example.xiaoming.notebook;



import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;


import android.support.v4.content.ContextCompat;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityListFragment extends ListFragment {

    private NoteAdapter noteAdapter;
    private ArrayList<Note> notes;

    public static final String NOTE_ID_EXTRA="com.example.xiaoming.notebook.Note_ID";
    public static final String NOTE_Title_EXTRA="com.example.xiaoming.notebook.Note_Title";
    public static final String NOTE_Body_EXTRA="com.example.xiaoming.notebook.Note_Body";
    public static final String NOTE_Category_EXTRA="com.example.xiaoming.notebook.Note_Category";
    public static final String NOTE_Fragment_To_Load_EXTRA="com.example.xiaoming.notebook.Note_Fragment_To_Load";

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        notes= new ArrayList<Note>();
       NotebookDbAdapter dbAdapter=new NotebookDbAdapter(getActivity().getBaseContext());
        dbAdapter.open();
        notes=dbAdapter.getAllNotes();

        dbAdapter.close();

        noteAdapter= new NoteAdapter(getActivity(),notes);
        setListAdapter(noteAdapter);



        registerForContextMenu(getListView());
        /*String[] values=new String[]{"one","two","three","four","five"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(getActivity(),R.layout.testing,values);
        setListAdapter(adapter);*/
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater menuInflater=getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu,menu);

    }
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        int rowPosition=info.position;

        Note note= (Note)getListAdapter().getItem(rowPosition);
     switch(item.getItemId())
     {
         case R.id.edit:
             LaunchNoteDetailActivity(rowPosition,MainActivity.FragmentToLaunch.EDIT);
             break;
         case R.id.delete:
             NotebookDbAdapter dbAdapter = new NotebookDbAdapter(getActivity().getBaseContext());
             dbAdapter.open();
             dbAdapter.deleteNote(note.getNoteId());
             notes.clear();
             notes.addAll(dbAdapter.getAllNotes());
             noteAdapter.notifyDataSetChanged();
             dbAdapter.close();

            return true;
     }
    return super.onContextItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View w, int position, long id){
        super.onListItemClick(l,w,position,id);
        LaunchNoteDetailActivity(position,MainActivity.FragmentToLaunch.VIEW);
    }

    private void LaunchNoteDetailActivity(int position, MainActivity.FragmentToLaunch ftl)
    {
        Note note= (Note) getListAdapter().getItem(position);
        Intent  intent =new Intent(getActivity(),NoteDetailActivity.class);
        intent.putExtra(MainActivityListFragment.NOTE_ID_EXTRA,note.getNoteId());
        intent.putExtra(MainActivityListFragment.NOTE_Title_EXTRA,note.getTitle());
        intent.putExtra(MainActivityListFragment.NOTE_Body_EXTRA,note.getMessage());
        intent.putExtra(MainActivityListFragment.NOTE_Category_EXTRA,note.getCategory());

        switch(ftl) {

            case EDIT:
                intent.putExtra(MainActivityListFragment.NOTE_Fragment_To_Load_EXTRA,MainActivity.FragmentToLaunch.EDIT);
                break;
            case VIEW:
                intent.putExtra(MainActivityListFragment.NOTE_Fragment_To_Load_EXTRA,MainActivity.FragmentToLaunch.VIEW);
                break;
        }

        startActivity(intent);


    }
}
