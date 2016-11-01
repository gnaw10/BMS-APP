package com.example.gnaw.bms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gnaw on 2016/10/30.
 */

public class BookAdapter extends BaseAdapter {
    private List<Book> books;
    private LayoutInflater mInflater;
    BookAdapter(Context context, List<Book> books)
    {
        this.books = books;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = books.get(position);
        View v = convertView;
        if(convertView == null)
        {
            v = mInflater.inflate(R.layout.book , parent , false);
        }

        ((TextView)v.findViewById(R.id.textTitle)).setText("--"+book.getId()+"--  "+book.getName());

        if(book.getUser_id() == 0)
        {
            ((TextView)v.findViewById(R.id.textUser)).setText("可借");
            ((TextView)v.findViewById(R.id.borrowButton)).setEnabled(true);
        }
        else
        {
            ((TextView)v.findViewById(R.id.textUser)).setText("不可借,在"+book.getBorrowUser()+"处");
            ((TextView)v.findViewById(R.id.borrowButton)).setEnabled(false);
        }

        ((TextView)v.findViewById(R.id.textState)).setText(book.getCoverUrl());
        return v;
    }

}
