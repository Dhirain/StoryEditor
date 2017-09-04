package com.example.dj.storyeditor.ui.custom;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dj.storyeditor.R;

/**
 * Created by DJ on 03-09-2017.
 */

public class SaveAsDialog extends DialogFragment {
    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;
    private String currentFileName;
    private EditText editText;
    private Button saveButton;
    private Button cancelButton;


    public static SaveAsDialog newInstance(Context context, String currentFileName){
        SaveAsDialog saveAsDialog = new SaveAsDialog();
        saveAsDialog.mListener = (NoticeDialogListener) context;
        saveAsDialog.currentFileName = currentFileName;
        return saveAsDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.save_as_popup ,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUI(view);
        clickListner(view);
    }

    private void setUI(View view) {
        editText = (EditText) view.findViewById(R.id.editText);
        editText.setText(currentFileName);
    }

    private void clickListner(View view) {
        view.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDialogPositiveClick(SaveAsDialog.this,editText.getText().toString());
                Log.d("Dialog", "onClick: "+editText.getText().toString());
                Toast.makeText(getActivity(), editText.getText().toString() + " saved!", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveAsDialog.this.dismiss();
            }
        });

    }

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog,String fileName);
    }



    /*// Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }*/
}
