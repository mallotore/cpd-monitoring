package grooid.app.alerts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import grooid.app.R
import groovy.transform.CompileStatic

@CompileStatic
public class AlertListAdapter extends BaseAdapter {

    private static final int FIRST_POSITION = 0
    private ArrayList<Alert> alerts
    private LayoutInflater layoutInflater

    public AlertListAdapter(Context context, ArrayList<Alert> alerts) {
        this.alerts = alerts
        layoutInflater = LayoutInflater.from(context)
    }

    @Override
    public int getCount() {
        return alerts.size()
    }

    @Override
    public Object getItem(int position) {
        return alerts.get(position)
    }

    @Override
    public long getItemId(int position) {
        return position
    }

    public void addAlert(Alert alert){
        this.alerts.add(FIRST_POSITION,alert)
        this.notifyDataSetChanged()
    }

    public void updateAlerts(ArrayList<Alert> alerts){
        this.alerts = alerts
        this.notifyDataSetChanged()
    }

    public void clearAlerts(){
        this.alerts = new ArrayList<Alert>()
        this.notifyDataSetChanged()
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.alerts_row_layout, null)
            holder = new ViewHolder()
            holder.titleView = (TextView) convertView.findViewById(R.id.title)
            holder.messageView = (TextView) convertView.findViewById(R.id.message)
            holder.dateView = (TextView) convertView.findViewById(R.id.date)
            convertView.setTag(holder)
        } else {
            holder = (ViewHolder) convertView.getTag()
        }

        holder.titleView.setText(alerts.get(position).title)
        holder.messageView.setText(alerts.get(position).message)
        holder.dateView.setText(alerts.get(position).date)

        return convertView
    }

    static class ViewHolder {
        TextView titleView
        TextView messageView
        TextView dateView
    }
}