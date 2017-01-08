package com.example.xiaoming.notebook;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteViewFragment extends Fragment {


    public NoteViewFragment() {
        // Required empty public constructor
    }

@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


}
 @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View fragmentView = inflater.inflate(R.layout.fragment_note_view,container,false);


     TextView title =(TextView)fragmentView.findViewById(R.id.viewNoteTitle);
     TextView message =(TextView)fragmentView.findViewById(R.id.viewNoteMessage);
     ImageView icon =(ImageView) fragmentView.findViewById(R.id.viewNoteIcon);

     Intent intent= getActivity().getIntent();

     title.setText(intent.getExtras().getString(MainActivityListFragment.NOTE_Title_EXTRA));
     message.setText(intent.getExtras().getString(MainActivityListFragment.NOTE_Body_EXTRA));
     Note.Category cat= (Note.Category)intent.getExtras().getSerializable(MainActivityListFragment.NOTE_Category_EXTRA);

     icon.setImageResource(Note.categoryToDrawable(cat));

     return fragmentView;


    }

}
