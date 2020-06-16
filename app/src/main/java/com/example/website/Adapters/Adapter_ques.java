package com.example.website.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.website.model.AnswerResponse;
import com.example.website.R;
import com.example.website.model.QuestionResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

public class Adapter_ques extends RecyclerView.Adapter<Adapter_ques.views_ques> {
    private List<QuestionResponse> ques;
   // private List<AnswerResponse> ans;
    private Context context;

    public Adapter_ques(Context context,List<QuestionResponse> ques) {
        this.ques = ques;
        //this.ans = ans;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_ques.views_ques onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_question_card,parent,false);
        return new Adapter_ques.views_ques(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_ques.views_ques holder, int i) {
        QuestionResponse q = ques.get(i);
       // AnswerResponse a = ans.get(i);
        Document document1 = Jsoup.parse(q.getContent().getRendered());
   //     Document document2 = Jsoup.parse(a.getContent().getRendered());

            holder.question_title.setText(Jsoup.parse(q.getTitle().getRendered()).text());
            holder.date_title.setText(q.getDate_gmt().substring(0, 10));
            holder.questions.setText(document1.text().substring(0,document1.text().length()-1));
            holder.date_ques.setText(q.getDate_gmt().substring(0, 10));
            boolean isExpanded = q.isExpanded();
            holder.expandableLayout.setVisibility(isExpanded? View.VISIBLE:View.GONE);
            holder.ll_Ques.setVisibility(isExpanded?View.VISIBLE:View.GONE);
            holder.ll_ques_title.setVisibility(isExpanded?View.GONE:View.VISIBLE);

       //     holder.answers.setText(document2.text());
          //  holder.date_ans.setText(a.getDate_gmt().substring(0,10));
    switch (i%4){
        case 0:holder.linearLayoutBorder.setBackgroundColor(Color.RED);
        break;
        case 1:holder.linearLayoutBorder.setBackgroundColor(Color.rgb(230,207,13));
        break;
        case 2:holder.linearLayoutBorder.setBackgroundColor(Color.rgb(50,202,236));
            break;
        case 3:holder.linearLayoutBorder.setBackgroundColor(Color.rgb(0,128,0));
            break;
        }

        }




    @Override
    public int getItemCount() {
        if(ques!=null){
            return ques.size();}
        else{
            return 0;
        }
    }

    public class views_ques extends RecyclerView.ViewHolder {
        TextView question_title,date_title,questions,date_ques,answers,date_ans;
        ImageView author_ques,author_ans;
        ConstraintLayout expandableLayout;
        LinearLayout ll_ques_title,ll_Ques,linearLayoutBorder;

        public views_ques(@NonNull View itemView) {
            super(itemView);
            linearLayoutBorder = itemView.findViewById(R.id.linear_layout_border);
            ll_Ques = itemView.findViewById(R.id.ll_question);
            ll_ques_title = itemView.findViewById(R.id.ll_quesTitle);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            questions = itemView.findViewById(R.id.question);
            date_ques = itemView.findViewById(R.id.ques_date);
            author_ques = itemView.findViewById(R.id.author_ques);
            answers = itemView.findViewById(R.id.answer);
            date_ans = itemView.findViewById(R.id.date_ans);
            author_ans = itemView.findViewById(R.id.author_ans);
            question_title = itemView.findViewById(R.id.question_title);
            date_title = itemView.findViewById(R.id.ques_title_date);

            ll_ques_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    QuestionResponse questionResponse = ques.get(getAdapterPosition());
                    questionResponse.setExpanded(!questionResponse.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            ll_Ques.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    QuestionResponse questionResponse = ques.get(getAdapterPosition());
                    questionResponse.setExpanded(!questionResponse.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}

