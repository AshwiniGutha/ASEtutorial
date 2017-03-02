package ashwini.watch;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class LibraryAdapter extends BaseAdapter {

    Context context;
    List<Book> booksList;

    public LibraryAdapter(Context context, List<Book> booksList) {
        this.context = context;
        this.booksList = booksList;
    }

    @Override
    public int getCount() {
        return booksList.size();
    }

    @Override
    public Object getItem(int position) {
        return booksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = parent.inflate(context, R.layout.searchbook_layout, null);


        Book book = (Book) getItem(position);

        return view;
    }
}