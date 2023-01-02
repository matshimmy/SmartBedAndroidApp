package com.example.hospitalbedcontrols.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalbedcontrols.R
import com.example.hospitalbedcontrols.data.ControlSource
//import com.example.hospitalbedcontrols.const.Layout

class ControlAdapter (
    private val context: Context?
        ): RecyclerView.Adapter<ControlAdapter.ControlViewHolder>() {
            private val data = ControlSource.controls

            class ControlViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
                val controlImage: ImageView = view!!.findViewById(R.id.control_image)
                val controlName: TextView = view!!.findViewById(R.id.control_name)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ControlViewHolder {
                val adapterLayout = LayoutInflater.from(parent.context)
                    .inflate(R.layout.control_list_item, parent, false)

                return ControlViewHolder(adapterLayout)
            }

            override fun getItemCount(): Int = data.size

            override fun onBindViewHolder(holder: ControlViewHolder, position: Int) {
                val resources = context?.resources

                val controlItem = data[position]
                holder.controlImage.setImageResource(controlItem.imageResourceId)
                holder.controlName.text = controlItem.name

                // TODO: come back to this and compare w dogglers

            }

        }