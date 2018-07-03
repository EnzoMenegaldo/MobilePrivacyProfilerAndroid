package fr.inria.diverse.mobileprivacyprofiler.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import fr.inria.diverse.mobileprivacyprofiler.R;
import fr.inria.diverse.mobileprivacyprofiler.utils.JobEnum;

public class ServiceList_Adapter extends BaseAdapter {
    Context context;
    JobEnum[] jobs;
    LayoutInflater inflter;

    public ServiceList_Adapter(Context applicationContext, JobEnum[] jobs) {
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
                    JobEnum job = (JobEnum)viewHolder.serviceSwitch.getTag();
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
                    JobEnum job = (JobEnum)viewHolder.serviceSwitch.getTag();

                }
            });

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        JobEnum currentService = (JobEnum)getItem(position);
        viewHolder.serviceBtn.setText(currentService.toString());

        viewHolder.serviceSwitch.setTag(currentService);

        viewHolder.serviceSwitch.setChecked(currentService.isSelected());

        return convertView;

    }

    /**
     * Run the associated job
     */
    private void runJob(JobEnum job){
        job.run();
    }

    /**
     * Cancel the associated job
     */
    private void cancelJob(JobEnum job){
        job.cancel();
    }


}