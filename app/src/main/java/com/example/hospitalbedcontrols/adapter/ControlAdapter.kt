package com.example.hospitalbedcontrols.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalbedcontrols.R
import com.example.hospitalbedcontrols.data.ControlSource
import com.example.hospitalbedcontrols.model.BluetoothViewModel

class ControlAdapter(private val context: Context?, private val result: MutableLiveData<String>) :
    RecyclerView.Adapter<ControlAdapter.ControlViewHolder>() {
    private val data = ControlSource.controls

    private lateinit var viewModel: BluetoothViewModel

    class ControlViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val controlImage: ImageView = view.findViewById(R.id.control_image)
        val controlName: TextView = view.findViewById(R.id.control_name)
        val downBtn: AppCompatImageButton = view.findViewById(R.id.down_button)
        val upBtn: AppCompatImageButton = view.findViewById(R.id.up_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ControlViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.control_list_item, parent, false)

        viewModel = ViewModelProvider(context as AppCompatActivity)[BluetoothViewModel::class.java]
        return ControlViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ControlViewHolder, position: Int) {
        val controlItem = data[position]
        holder.controlImage.setImageResource(controlItem.imageResourceId)
        holder.controlName.text = controlItem.name
        holder.downBtn.setOnLongClickListener {
            viewModel.writeBle(controlItem.downBtnPress)
            Log.d(TAG, "down Button pressed")
            true
        }

        holder.downBtn.setOnTouchListener{view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    // Perform action on button release
                    viewModel.writeBle(byteArrayOf(0x03))
                    Log.d(TAG, "down Button released")
                }
            }
            view.onTouchEvent(motionEvent)
        }
        holder.upBtn.setOnLongClickListener {
            viewModel.writeBle(controlItem.upBtnPress)
            Log.d(TAG, "up Button pressed")
            true
        }
        holder.upBtn.setOnTouchListener{view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    // Perform action on button release
                    viewModel.writeBle(byteArrayOf(0x03))
                    Log.d(TAG, "up Button released")
                }
            }
            view.onTouchEvent(motionEvent)
        }

        result.observe(context as LifecycleOwner) {
            val words = it.split(" ")
            if (words.size != 4)
                return@observe
            if (words[0].toRegex(RegexOption.IGNORE_CASE)
                    .containsMatchIn(controlItem.name)
            ) { //matches voice to control
                if (words[1].toRegex(RegexOption.IGNORE_CASE).containsMatchIn("up")) { //up button
                    Log.d(TAG, "up " + words[2])
                    holder.upBtn.performClick()
                } else { //down button
                    Log.d(TAG, "down " + words[2])
                    holder.downBtn.performClick()

                }
            }
        }
    }

}

const val TAG = "ControlAdapter"
