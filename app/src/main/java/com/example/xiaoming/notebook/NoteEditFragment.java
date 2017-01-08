package com.example.xiaoming.notebook;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEditFragment extends Fragment {
    private ImageButton icon;
    private EditText title, message;
    private Note.Category savedButtonCategory=Note.Category.PERSONAL;
    private AlertDialog categoryDialogObjct, confirmDialogObjct;

    private long NoteID=0;
    private boolean newNote=false;
    private static final String MODIFIED_CATEGORY="Modified category";
    public NoteEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_note_edit,container,false);


        Bundle bundle =this.getArguments();
        if(bundle!=null)
        {
            newNote=bundle.getBoolean(NoteDetailActivity.NEW_NOTE_EXTRA,false);
        }

        title =(EditText)fragmentView.findViewById(R.id.editNoteTitle);
        message =(EditText)fragmentView.findViewById(R.id.editNoteMessage);

        icon =(ImageButton) fragmentView.findViewById(R.id.editNoteButton);
        Button saved =(Button)fragmentView.findViewById(R.id.SaveNote);
        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialogObjct.show();
            }
        });

        Intent intent= getActivity().getIntent();

        NoteID=intent.getExtras().getLong(MainActivityListFragment.NOTE_ID_EXTRA,0);
        title.setText(intent.getExtras().getString(MainActivityListFragment.NOTE_Title_EXTRA));
        message.setText(intent.getExtras().getString(MainActivityListFragment.NOTE_Body_EXTRA));

       if(!newNote) {
           if (savedInstanceState != null) {
               savedButtonCategory = (Note.Category) savedInstanceState.get(MODIFIED_CATEGORY);

           } else {
               Note.Category cat = (Note.Category) intent.getExtras().getSerializable(MainActivityListFragment.NOTE_Category_EXTRA);
               savedButtonCategory = cat;

           }

           icon.setImageResource(Note.categoryToDrawable(savedButtonCategory));
       }

        buildCategoryDialog();

        buildConfirmDialog();

        icon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                categoryDialogObjct.show();
            }
        });
        Log.w("catbeforereturnview",savedButtonCategory.name());
        return fragmentView;

    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(MODIFIED_CATEGORY,savedButtonCategory);

    }
    private void buildCategoryDialog()
    {
        final String[] categories = new String[]{"Remaining Same","Personal","Technical","Quote","Finance"};
        AlertDialog.Builder categoryBuilder = new AlertDialog.Builder(getActivity());

        categoryBuilder.setTitle("Choose Note Type");

        categoryBuilder.setSingleChoiceItems(categories, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                //dismiss the windown, once user choose it;
                categoryDialogObjct.cancel();
                switch(item)
                {
                    case 0:
                        break;
                    case 1:
                        savedButtonCategory=Note.Category.PERSONAL;
                        icon.setImageResource(R.drawable.a);
                        break;
                    case 2:
                        savedButtonCategory=Note.Category.TECHNICAL;
                        icon.setImageResource(R.drawable.b);
                        break;
                    case 3:
                        savedButtonCategory=Note.Category.QUOTE;
                        icon.setImageResource(R.drawable.c);
                        break;
                    case 4:
                        savedButtonCategory=Note.Category.FINANCE;
                        icon.setImageResource(R.drawable.d);
                        break;

                }
                Log.w("category chose",savedButtonCategory.name());
            }
        });
        categoryDialogObjct=categoryBuilder.create();
    }

    private void buildConfirmDialog()
    {
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(getActivity());

        confirmBuilder.setTitle("Are you sure");

        confirmBuilder.setMessage("Are you sure you want to save the note?");
        confirmBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               NotebookDbAdapter dbAdapter = new NotebookDbAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                if(newNote)
                {
                    Log.w("category before create",savedButtonCategory.name());
                    dbAdapter.createNote(title.getText()+"",message.getText()+"",(savedButtonCategory==null)? Note.Category.PERSONAL:savedButtonCategory);

                }
                else
                {



                    Log.w("category before update",savedButtonCategory.name());
                    dbAdapter.updateNote(NoteID,title.getText()+"",message.getText()+"",savedButtonCategory);

                }
                dbAdapter.close();

                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });
        confirmBuilder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        confirmDialogObjct=confirmBuilder.create();
    }
}
