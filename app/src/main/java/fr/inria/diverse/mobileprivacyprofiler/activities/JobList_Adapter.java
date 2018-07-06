package fr.inria.diverse.mobileprivacyprofiler.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import fr.inria.diverse.mobileprivacyprofiler.R;
import fr.inria.diverse.mobileprivacyprofiler.utils.JobEnum;

public class JobList_Adapter extends BaseAdapter {
    Context context;
    JobEnum[] jobs;
    LayoutInflater inflter;

    public JobList_Adapter(Context applicationContext, JobEnum[] jobs) {
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
        protected TextView jobTextView;
        protected Switch jobSwitchId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;


        if(convertView == null) {
            convertView = inflter.inflate(R.layout.joblist_listviewrow, null);
            viewHolder = new ViewHolder();
            viewHolder.jobTextView = (TextView) convertView.findViewById(R.id.jobTextView);
            viewHolder.jobSwitchId = (Switch) convertView.findViewById(R.id.jobSwitchId);

            viewHolder.jobSwitchId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                    JobEnum job = (JobEnum)viewHolder.jobSwitchId.getTag();
                    if (bChecked) {
                        job.setSelected(bChecked);
                        if(Starting_CustomViewActivity.isCollectionRunning())
                            job.run();
                    } else {
                        job.setSelected(bChecked);
                        if(Starting_CustomViewActivity.isCollectionRunning())
                            job.cancel();
                    }
                }
            });

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        JobEnum currentService = (JobEnum)getItem(position);
        viewHolder.jobTextView.setText(currentService.toString());

        viewHolder.jobSwitchId.setTag(currentService);

        viewHolder.jobSwitchId.setChecked(currentService.isSelected());

        return convertView;

    }

}