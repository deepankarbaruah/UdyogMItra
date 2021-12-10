package in.nic.assam.udyogmitra;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VisitorRecyclerViewAdapter extends RecyclerView.Adapter<VisitorRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Visitor> visitorsList;

    Dialog dialog;
    EditText remarks;
    Button btnSave;
    String editRemarks;
    TextView displayName, displayOrgName, displayPhoneNumber, displayPurpose;



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

        holder.visitorName.setText(visitor.getVisitorName());
        holder.organisationName.setText(visitor.getOrganisationName());
        holder.phoneNum.setText(visitor.getVisitorNumber());
        holder.purpose.setText(visitor.getPurpose());
        holder.remark.setText(visitor.getRemarks());

    }

    // How many items?
    @Override
    public int getItemCount() {
        return visitorsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView visitorName;
        public TextView organisationName;
        public TextView phoneNum;
        public TextView purpose;
        public TextView remark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            visitorName = itemView.findViewById(R.id.visitorName);
            organisationName = itemView.findViewById(R.id.organisationName);
            phoneNum = itemView.findViewById(R.id.phoneNum);
            purpose = itemView.findViewById(R.id.purpose);
            remark = itemView.findViewById(R.id.remark);

        }

        @Override
        public void onClick(View view) {

            int position = this.getAdapterPosition();
            Visitor visitor = visitorsList.get(position);
            String name = visitor.getVisitorName();
            String organisation = visitor.getOrganisationName();
            String phoneNumber = visitor.getVisitorNumber();
            String purpose = visitor.getPurpose();
            String visitorSearchDistrict = visitor.getDistrict_name();


            dialog=new Dialog(context);
            dialog.setContentView(R.layout.dialog_gmremarks);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(true); //Optional
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            // Capture the layout's TextView and set the string as its text
            displayName = (TextView) dialog.findViewById(R.id.name);
            displayOrgName = (TextView) dialog.findViewById(R.id.organisation);
            displayPhoneNumber = (TextView) dialog.findViewById(R.id.phone);
            displayPurpose = (TextView) dialog.findViewById(R.id.purpose);
            remarks = (EditText) dialog.findViewById(R.id.remarks);
            btnSave = (Button) dialog.findViewById(R.id.save);

            Toast.makeText(context, "loading", Toast.LENGTH_SHORT).show();

//            displayName.setText(name);
//            displayOrgName.setText(organisation);
//            displayPhoneNumber.setText(phoneNumber);
//            displayPurpose.setText(purpose);

            displayName.setText("YES");
            dialog.show();

//
//            Intent intent = new Intent(context, GMRemarks.class);
//            intent.putExtra("name", name);
//            intent.putExtra("organisation", organisation);
//            intent.putExtra("number", phoneNumber);
//            intent.putExtra("purpose", purpose);
//            intent.putExtra("district", visitorSearchDistrict);
//            view.getContext().startActivity(intent);
//            Log.d("ClickFromViewHolder", "Clicked");

//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setPositiveButton("OKAY", (dialog, which) -> {
//                dialog.dismiss();
////                listener.onPositiveButtonClicked();
//
//            });
//
//            builder.setNegativeButton("NOPE", (dialog, which) -> {
//                dialog.dismiss();
////                listener.onNegativeButtonClicked();
//
//            });
//            builder.setTitle(context.getResources().getString(R.string.app_name));
//            builder.setMessage("ALERT....");
//            builder.setIcon(android.R.drawable.ic_dialog_alert);
//            builder.setCancelable(false);
//            builder.create().show();

//            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
//// ...Irrelevant code for customizing the buttons and title
//            LayoutInflater inflater = context.getLayoutInflater();
//            View dialogView = inflater.inflate(R.layout.alert_label_editor, null);
//            dialogBuilder.setView(dialogView);
//
//            EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
//            editText.setText("test label");
//            AlertDialog alertDialog = dialogBuilder.create();
//            alertDialog.show();

        }
    }
}
