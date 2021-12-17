package in.nic.assam.udyogmitra.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import in.nic.assam.udyogmitra.R;
import in.nic.assam.udyogmitra.model.Visitor;


public class SubVisitorRVAdapter extends RecyclerView.Adapter<SubVisitorRVAdapter.MyViewHolder> {

    private final Context context;
    private final List<Visitor> visitorsList;

    Dialog dialog;
    TextView tvDisplayName, tvDisplayOrgName, tvDisplayPhoneNumber, tvDisplayPurpose, tvDisplayDate_of_sub, tvRemarks;


    public SubVisitorRVAdapter(Context context, List<Visitor> visitorsList) {
        this.context = context;
        this.visitorsList = visitorsList;
    }

    @NonNull
    @Override
    public SubVisitorRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent,false);
        return new MyViewHolder(view);
    }

    // What will happen after we create the viewHolder object
    @Override
    public void onBindViewHolder(@NonNull SubVisitorRVAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Visitor visitor = visitorsList.get(position);

        holder.tvVisitorName.setText(visitor.getVisitorName());
        holder.tvOrganisationName.setText(visitor.getOrganisationName());
        holder.tvPhoneNum.setText(visitor.getVisitorNumber());
//        holder.tvPurpose.setText(visitor.getPurpose());
//        holder.tvRemark.setText(visitor.getRemarks());
//        holder.tvDate_of_sub.setText(visitor.getDate_of_sub());

        holder.viewBtn.setOnClickListener(view -> {
//                int position = this.getAdapterPosition();
            Visitor visitor1 = visitorsList.get(position);
            String name = visitor1.getVisitorName();
            String organisation = visitor1.getOrganisationName();
            String phoneNumber = visitor1.getVisitorNumber();
            String purpose = visitor1.getPurpose();
            String date_of_sub = visitor1.getDate_of_sub();
            String visitorSearchDistrict = visitor1.getDistrict_name();
            String gmRemarks = visitor1.getRemarks();
            int remarks_status = visitor1.getRemarks_status();


            dialog=new Dialog(context);
            dialog.setContentView(R.layout.dialog_remarks_view);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(true); //Optional
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            // Capture the layout's TextView and set the string as its text
            tvDisplayName = (TextView) dialog.findViewById(R.id.name);
            tvDisplayOrgName = (TextView) dialog.findViewById(R.id.organisation);
            tvDisplayPhoneNumber = (TextView) dialog.findViewById(R.id.phone);
            tvDisplayPurpose = (TextView) dialog.findViewById(R.id.purpose);
            tvDisplayDate_of_sub = (TextView) dialog.findViewById(R.id.date_of_sub);
            tvRemarks = (TextView) dialog.findViewById(R.id.remarks);

            tvDisplayName.setText(name);
            tvDisplayOrgName.setText(organisation);
            tvDisplayPhoneNumber.setText(phoneNumber);
            tvDisplayPurpose.setText(purpose);
            tvDisplayDate_of_sub.setText(date_of_sub);
            tvRemarks.setText(gmRemarks);
            dialog.show();

        });

    }

    // How many items?
    @Override
    public int getItemCount() {
        return visitorsList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public final TextView tvVisitorName;
        public final TextView tvOrganisationName;
        public final TextView tvPhoneNum;
//        public final TextView tvPurpose;
//        public final TextView tvRemark;
//        public final TextView tvDate_of_sub;
        final Button viewBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvVisitorName = itemView.findViewById(R.id.visitorName);
            tvOrganisationName = itemView.findViewById(R.id.organisationName);
            tvPhoneNum = itemView.findViewById(R.id.phoneNum);
//            tvPurpose = itemView.findViewById(R.id.purpose);
//            tvRemark = itemView.findViewById(R.id.remark);
//            tvDate_of_sub = itemView.findViewById(R.id.date_of_sub);
            viewBtn = itemView.findViewById(R.id.viewBtn);
        }
    }
}
