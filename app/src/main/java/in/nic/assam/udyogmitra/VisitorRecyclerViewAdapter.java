package in.nic.assam.udyogmitra;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class VisitorRecyclerViewAdapter extends RecyclerView.Adapter<VisitorRecyclerViewAdapter.ViewHolder> {

    private final Context context;
    private final List<Visitor> visitorsList;

    Dialog dialog;
    EditText remarks;
    Button btnSave;
    String editRemarks;
    TextView tvDisplayName, tvDisplayOrgName, tvDisplayPhoneNumber, tvDisplayPurpose, tvDisplayDate_of_sub;


    public VisitorRecyclerViewAdapter(Context context, List<Visitor> visitorsList) {
        this.context = context;
        this.visitorsList = visitorsList;
    }

    // Where to get the single card as viewHolder Object
    @NonNull
    @Override
    public VisitorRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visitorcard, parent, false);

        return new VisitorRecyclerViewAdapter.ViewHolder(view);
    }

    // What will happen after we create the viewHolder object
    @Override
    public void onBindViewHolder(@NonNull VisitorRecyclerViewAdapter.ViewHolder holder, int position) {
        Visitor visitor = visitorsList.get(position);

        holder.tvVisitorName.setText(visitor.getVisitorName());
        holder.tvOrganisationName.setText(visitor.getOrganisationName());
        holder.tvPhoneNum.setText(visitor.getVisitorNumber());
        holder.tvPurpose.setText(visitor.getPurpose());
        holder.tvRemark.setText(visitor.getRemarks());
        holder.tvDate_of_sub.setText(visitor.getDate_of_sub());

    }

    // How many items?
    @Override
    public int getItemCount() {
        return visitorsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvVisitorName;
        public TextView tvOrganisationName;
        public TextView tvPhoneNum;
        public TextView tvPurpose;
        public TextView tvRemark;
        public TextView tvDate_of_sub;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            tvVisitorName = itemView.findViewById(R.id.visitorName);
            tvOrganisationName = itemView.findViewById(R.id.organisationName);
            tvPhoneNum = itemView.findViewById(R.id.phoneNum);
            tvPurpose = itemView.findViewById(R.id.purpose);
            tvRemark = itemView.findViewById(R.id.remark);
            tvDate_of_sub = itemView.findViewById(R.id.date_of_sub);

        }

        @Override
        public void onClick(View view) {

            int position = this.getAdapterPosition();
            Visitor visitor = visitorsList.get(position);
            String name = visitor.getVisitorName();
            String organisation = visitor.getOrganisationName();
            String phoneNumber = visitor.getVisitorNumber();
            String purpose = visitor.getPurpose();
            String date_of_sub = visitor.getDate_of_sub();
            String visitorSearchDistrict = visitor.getDistrict_name();


            dialog=new Dialog(context);
            dialog.setContentView(R.layout.dialog_gmremarks);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(true); //Optional
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            // Capture the layout's TextView and set the string as its text
            tvDisplayName = (TextView) dialog.findViewById(R.id.name);
            tvDisplayOrgName = (TextView) dialog.findViewById(R.id.organisation);
            tvDisplayPhoneNumber = (TextView) dialog.findViewById(R.id.phone);
            tvDisplayPurpose = (TextView) dialog.findViewById(R.id.purpose);
            tvDisplayDate_of_sub = (TextView) dialog.findViewById(R.id.date_of_sub);
            remarks = (EditText) dialog.findViewById(R.id.remarks);
            btnSave = (Button) dialog.findViewById(R.id.save);


            tvDisplayName.setText(name);
            tvDisplayOrgName.setText(organisation);
            tvDisplayPhoneNumber.setText(phoneNumber);
            tvDisplayPurpose.setText(purpose);
            tvDisplayDate_of_sub.setText(date_of_sub);
            dialog.show();

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(TextUtils.isEmpty(remarks.getText().toString())){
                        remarks.requestFocus();
                        remarks.setError("Give Some Remarks !");
                    }

                    else{
                        editRemarks = remarks.getText().toString();
                        dbHelper db = new dbHelper(context.getApplicationContext());
                        db.saveRemarks(visitorSearchDistrict,name,editRemarks);
                        Toast toast = Toast.makeText(context,"Remarks Saved...",Toast.LENGTH_SHORT);
                        toast.show();
                        dialog.dismiss();

                    }
                }
            });

        }
    }
}
