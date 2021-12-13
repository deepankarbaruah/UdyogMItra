package in.nic.assam.udyogmitra.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.nic.assam.udyogmitra.model.GeneralManager;
import in.nic.assam.udyogmitra.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    final Context context;
    private final List<GeneralManager> generalManagerList;

    public RecyclerViewAdapter(Context context, List<GeneralManager> generalManagerList) {
        this.context = context;
        this.generalManagerList = generalManagerList;
    }

    // Where to get the single card as viewHolder Object
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    // What will happen after we create the viewHolder object
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        GeneralManager generalManager = generalManagerList.get(position);

        holder.gmName.setText(generalManager.getName());
        holder.designation.setText(generalManager.getDesignation());
        holder.phoneNum.setText(generalManager.getPhoneNumber());

    }

    // How many items?
    @Override
    public int getItemCount() {
        return generalManagerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView gmName;
        public final TextView designation;
        public final TextView phoneNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            gmName = itemView.findViewById(R.id.gmName);
            designation = itemView.findViewById(R.id.designation);
            phoneNum = itemView.findViewById(R.id.phoneNum);
        }

        @Override
        public void onClick(View view) {
            Log.d("ClickFromViewHolder", "Clicked");

        }
    }
}
