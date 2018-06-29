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

import fr.inria.diverse.mobileprivacyprofiler.R;

public class JobList_Adapter extends BaseAdapter {
    Context context;
    Job[] jobs;
    LayoutInflater inflter;

    public JobList_Adapter(Context applicationContext, Job[] jobs) {
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
        protected Button jobBtn;
        protected Switch jobSwitch;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;


        if(convertView == null) {
            convertView = inflter.inflate(R.layout.joblist_listviewrow, null);
            viewHolder = new ViewHolder();
            viewHolder.jobBtn = (Button) convertView.findViewById(R.id.jobBtnId);
            viewHolder.jobSwitch = (Switch) convertView.findViewById(R.id.jobSwitchId);

            viewHolder.jobSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                    Job job = (Job)viewHolder.jobSwitch.getTag();
                    if (bChecked) {
                        runJob(job);
                    } else {
                        cancelJob(job);
                    }
                }
            });

            viewHolder.jobBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Job job = (Job)viewHolder.jobSwitch.getTag();
                    runScan(job);
                }
            });

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Job currentJob = (Job)getItem(position);
        viewHolder.jobBtn.setText("TEXT");

        viewHolder.jobSwitch.setTag(currentJob);

        //viewHolder.jobSwitch.setChecked(currentJob.isSelected());

        return convertView;

    }

    /**
     * Run the associated job
     */
    private void runJob(Job job){
        //TODO
    }

    /**
     * Cancel the associated job
     */
    private void cancelJob(Job job){
        //TODO
    }

    /**
     * Run the data collection once
     */
    private void runScan(Job job){
        //TODO
    }
}