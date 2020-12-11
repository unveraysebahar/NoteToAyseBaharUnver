package ise308.unver.aysebahar.notetoaysebaharunver

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import android.widget.Button;

class DialogShowNote : DialogFragment() {

    private var note: Note? = null

    override fun
            onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // All the other code goes here

        val builder = AlertDialog.Builder(this.requireActivity())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_show_note, null)
        val txtTitle =
            dialogView.findViewById(R.id.txtTitle) as TextView
        val txtDescription =
            dialogView.findViewById(R.id.txtDescription) as TextView
        txtTitle.text = note!!.title
        txtDescription.text = note!!.description
        val txtImportant =
            dialogView.findViewById(R.id.textViewImportant) as TextView
        val txtTodo =
            dialogView.findViewById(R.id.textViewTodo) as TextView
        val txtIdea =
            dialogView.findViewById(R.id.textViewIdea) as TextView

        if (!note!!.important){
            txtImportant.visibility = View.GONE
        }
        if (!note!!.todo){
            txtTodo.visibility = View.GONE
        }
        if (!note!!.idea){
            txtIdea.visibility = View.GONE
        }

        val btnOK = dialogView.findViewById(R.id.btnOK) as Button

        // builder.setView(dialogView).setMessage("Your Note")

        builder.setView(dialogView).setMessage(
            resources.getString(R.string.your_note))

        btnOK.setOnClickListener({
            dismiss()
        })
        return builder.create()

    }
    // Receive a note from the MainActivity class
    fun sendNoteSelected(noteSelected: Note) {
        note = noteSelected
    }

}