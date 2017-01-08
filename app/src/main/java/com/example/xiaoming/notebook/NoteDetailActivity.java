package com.example.xiaoming.notebook;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NoteDetailActivity extends AppCompatActivity {

    public static String NEW_NOTE_EXTRA ="New Note";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_note_detail);
        createAndAddFragment();
     
    }

    private void createAndAddFragment()
    {
        Intent intent = getIntent();
        MainActivity.FragmentToLaunch ftl=(MainActivity.FragmentToLaunch)intent.getSerializableExtra(MainActivityListFragment.NOTE_Fragment_To_Load_EXTRA);

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();


        switch(ftl)
        {
            case EDIT:
                NoteEditFragment noteEditFragment=new NoteEditFragment();
                fragmentTransaction.add(R.id.note_container,noteEditFragment,"NOTE_EDIT_FRAGMENT");
                setTitle(R.string.EditFragmentTitle);
                break;
            case VIEW:
                NoteViewFragment noteViewFragment=new NoteViewFragment();
                fragmentTransaction.add(R.id.note_container,noteViewFragment,"NOTE_VIEW_FRAGMENT");
                setTitle(R.string.ViewFragmentTitle);
                break;
            case CREATE:
                NoteEditFragment noteCreateFragment=new NoteEditFragment();
                Bundle bundle =new Bundle();
                bundle.putBoolean(NEW_NOTE_EXTRA,true);
                noteCreateFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.note_container,noteCreateFragment,"NOTE_CREATE_FRAGMENT");
                setTitle(R.string.CreateFragmentTitle);
                break;

        }

        fragmentTransaction.commit();
    }
}
