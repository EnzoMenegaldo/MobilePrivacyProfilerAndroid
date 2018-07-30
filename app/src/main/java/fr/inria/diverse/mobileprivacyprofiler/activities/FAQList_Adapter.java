package fr.inria.diverse.mobileprivacyprofiler.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.fasterxml.jackson.databind.JsonNode;

import fr.inria.diverse.mobileprivacyprofiler.R;

public class FAQList_Adapter extends BaseAdapter {

    private static final String QUESTION_TAG = "question";
    private static final String ANSWER_TAG = "answer";

    Context context;
    JsonNode[] questions;
    LayoutInflater inflter;

    public FAQList_Adapter(Context applicationContext, JsonNode[] questions) {
        this.context = applicationContext;
        this.questions = questions;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return questions.length;
    }

    @Override
    public Object getItem(int i) {
        return questions[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder {
        protected TextView faq_list_answer;
        protected TextView faq_list_question;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;


        if (convertView == null) {
            convertView = inflter.inflate(R.layout.faq_list, null);
            viewHolder = new ViewHolder();
            viewHolder.faq_list_answer = (TextView) convertView.findViewById(R.id.faq_list_answer);
            viewHolder.faq_list_question = (TextView) convertView.findViewById(R.id.faq_list_question);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        JsonNode currentQuestion = (JsonNode) getItem(position);
        viewHolder.faq_list_question.setText(currentQuestion.get(QUESTION_TAG).asText());
        viewHolder.faq_list_answer.setText(currentQuestion.get(ANSWER_TAG).asText());

        viewHolder.faq_list_question.setTag(currentQuestion);

        return convertView;

    }

}