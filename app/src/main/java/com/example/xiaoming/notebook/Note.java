package com.example.xiaoming.notebook;

/**
 * Created by xiaomi on 9/11/2016.
 */
public class Note {
    private long noteId, dateCreatedMilli;
    private String title,message;
    public enum Category {PERSONAL, TECHNICAL, QUOTE, FINANCE}

    private Category category;

    public Note(String title,String message, Category category)
    {
        noteId=0;
        dateCreatedMilli=0;
        this.title=title;
        this.message=message;
        this.category=category;
    }

    public Note(String title,String message, Category category,long noteId, long dateCreatedMilli)
    {
        this.noteId = noteId;
        this.dateCreatedMilli=dateCreatedMilli;
        this.title=title;
        this.message=message;
        this.category=category;
    }

    String getTitle()
    {
        return title;
    }

    String getMessage()
    {
        return message;
    }

    long getNoteId()
    {
        return noteId;
    }
    long getDateCreatedMilli()
    {
        return dateCreatedMilli;
    }

    Category getCategory() { return category; }

    public static int categoryToDrawable(Category noteCategory)
    {
        switch(noteCategory)
        {
            case PERSONAL:
                return R.drawable.a;
            case TECHNICAL:
                return R.drawable.b;
            case QUOTE:
                return R.drawable.c;
            case FINANCE:
                return R.drawable.d;
        }

        return R.drawable.a;
    }

    public int getAssociatedDrawable()
    {
        return categoryToDrawable(category);
    }




}
