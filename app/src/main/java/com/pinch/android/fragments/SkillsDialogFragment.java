package com.pinch.android.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.pinch.android.R;


public class SkillsDialogFragment extends DialogFragment {
    Button btnOk;
    Button btnCancel;
    EditText etSkill1;
    EditText etSkill2;
    EditText etSkill3;

    public SkillsDialogFragment() {
    }

    public static SkillsDialogFragment newInstance() {
        SkillsDialogFragment frag = new SkillsDialogFragment();
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_enter_skills, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews(view);
        setupListeners();
    }

    private void setupViews(View view) {
        btnOk = (Button) view.findViewById(R.id.btnOk);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        etSkill1 = (EditText) view.findViewById(R.id.etSkill1);
        etSkill2 = (EditText) view.findViewById(R.id.etSkill2);
        etSkill3 = (EditText) view.findViewById(R.id.etSkill3);
    }

    private void setupListeners() {
        ////////////////////////////////////////////////////////
        // On Click listener for Button "Ok"
        ////////////////////////////////////////////////////////
        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String skill1 = etSkill1.getText().toString();
                String skill2 = etSkill2.getText().toString();
                String skill3 = etSkill3.getText().toString();

                SkillsDialogListener listener = (SkillsDialogListener) getTargetFragment();
                listener.onFinishSkillsDialog(skill1, skill2, skill3);
                dismiss();
            }
        });
        ////////////////////////////////////////////////////////
        // On Click listener for Button "CANCEL"
        ////////////////////////////////////////////////////////
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface SkillsDialogListener {
        void onFinishSkillsDialog(String skill1,
                                  String skill2,
                                  String skill3);
    }


}
