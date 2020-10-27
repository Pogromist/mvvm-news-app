package com.example.myapplication.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.myapplication.R

class SearchDialogFragment : AppCompatDialogFragment() {

    private lateinit var listener: SearchDialogListener
    private lateinit var editText: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity?.layoutInflater?.inflate(R.layout.search_window, null)
        if (view != null) {
            editText = view.findViewById(R.id.edit_query)
        }

        builder.setView(view)
            .setNegativeButton(
                "Cancel"
            ) { dialogInterface, i -> }
            .setPositiveButton(
                "Search"
            ) { dialogInterface, i ->
                val searchQuery: String = editText.text.toString()
                listener.applyText(searchQuery)
            }

        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as SearchDialogListener
    }

    interface SearchDialogListener {
        fun applyText(searchQuery: String)
    }
}