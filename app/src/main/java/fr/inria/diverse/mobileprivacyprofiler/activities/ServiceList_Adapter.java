package fr.inria.diverse.mobileprivacyprofiler.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.evernote.android.job.Job;

import java.util.Enumeration;

import fr.inria.diverse.mobileprivacyprofiler.R;
import fr.inria.diverse.mobileprivacyprofiler.services.ServiceEnum;

public class ServiceList_Adapter extends BaseAdapter {
    Context context;
    ServiceEnum[] jobs;
    LayoutInflater inflter;

    public ServiceList_Adapter(Context applicationContext, ServiceEnum[] jobs) {
        this.context = applicationContext;
        this.jobs = jobs;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return jobs.length;
    }

    @Override
    public Object getItem(int i) {
        return jobs[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder {
        protected Button serviceBtn;
        protected Switch serviceSwitch;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;


        if(convertView == null) {
            convertView = inflter.inflate(R.layout.joblist_listviewrow, null);
            viewHolder = new ViewHolder();
            viewHolder.serviceBtn = (Button) convertView.findViewById(R.id.serviceBtnId);
            viewHolder.serviceSwitch = (Switch) convertView.findViewById(R.id.serviceSwitchId);

            viewHolder.serviceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                    ServiceEnum job = (ServiceEnum)viewHolder.serviceSwitch.getTag();
                    if (bChecked) {
                        runJob(job);
                        job.setSelected(bChecked);
                    } else {
                        cancelJob(job);
                        job.setSelected(bChecked);
                    }
                }
            });

            viewHolder.serviceBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ServiceEnum job = (ServiceEnum)viewHolder.serviceSwitch.getTag();
                    runScan(job);
                }
            });

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ServiceEnum currentService = (ServiceEnum)getItem(position);
        viewHolder.serviceBtn.setText(currentService.toString());

        viewHolder.serviceSwitch.setTag(currentService);

        viewHolder.serviceSwitch.setChecked(currentService.isSelected());

        return convertView;

    }

    /**
     * Run the associated job
     */
    private void runJob(ServiceEnum service){
        //TODO
    }

    /**
     * Cancel the associated job
     */
    private void cancelJob(ServiceEnum service){
        //TODO
    }

    /**
     * Run the data collection once
     */
    private void runScan(ServiceEnum service){
        service.call();
    }
}