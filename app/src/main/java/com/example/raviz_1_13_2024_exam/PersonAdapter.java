package com.example.raviz_1_13_2024_exam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// PersonAdapter.java
// PersonAdapter.java
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private List<PersonModel> people;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(PersonModel person);
    }

    public PersonAdapter(List<PersonModel> people, OnItemClickListener listener) {
        this.people = people;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listlayout, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        PersonModel person = people.get(position);
        holder.firstNameTextView.setText(person.getFirst_name() + " " + person.getLast_name());
        holder.emailTextView.setText(person.getEmail());
        holder.ims.setImageResource(R.drawable.manim);

        // Set click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(person); // Pass the entire PersonModel object
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView firstNameTextView;
        TextView emailTextView;
        ImageView ims;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            firstNameTextView = itemView.findViewById(R.id.personName);
            emailTextView = itemView.findViewById(R.id.personemail);
            ims = itemView.findViewById(R.id.imageView4);
        }
    }

    public void updateData(List<PersonModel> newPeople) {
        this.people = newPeople;
        notifyDataSetChanged();
    }

    public void removeUser(String userId) {
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getPerson_id().equals(userId)) {
                people.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }
}
