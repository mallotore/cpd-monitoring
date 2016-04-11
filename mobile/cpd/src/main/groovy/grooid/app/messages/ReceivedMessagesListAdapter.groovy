package grooid.app.messages

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import grooid.app.R
import groovy.transform.CompileStatic

@CompileStatic
public class ReceivedMessagesListAdapter extends BaseAdapter {

    private ArrayList<ReceivedMessage> listData
    private LayoutInflater layoutInflater

    public ReceivedMessagesListAdapter(Context context, ArrayList<ReceivedMessage> listData) {
        this.listData = listData
        layoutInflater = LayoutInflater.from(context)
    }

    @Override
    public int getCount() {
        return listData.size()
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position)
    }

    @Override
    public long getItemId(int position) {
        return position
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.received_messages_row_layout, null)
            holder = new ViewHolder()
            holder.titleView = (TextView) convertView.findViewById(R.id.title)
            holder.messageView = (TextView) convertView.findViewById(R.id.message)
            holder.dateView = (TextView) convertView.findViewById(R.id.date)
            convertView.setTag(holder)
        } else {
            holder = (ViewHolder) convertView.getTag()
        }

        holder.titleView.setText(listData.get(position).title)
        holder.messageView.setText(listData.get(position).message)
        holder.dateView.setText(listData.get(position).date)

        return convertView
    }

    static class ViewHolder {
        TextView titleView
        TextView messageView
        TextView dateView
    }
}