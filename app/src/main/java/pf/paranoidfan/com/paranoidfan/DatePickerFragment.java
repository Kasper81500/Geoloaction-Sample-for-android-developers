package pf.paranoidfan.com.paranoidfan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by KasperStar on 8/5/2016.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    DatePickerDialog mDialog;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        mDialog = new DatePickerDialog(getActivity(), this, year, month, day);

        if(getActivity().getClass().equals(AddPaymentActivity.class)) {
            (mDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
        }
        // Create a new instance of DatePickerDialog and return it
        return mDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        if(getActivity().getClass().equals(SignupActivity.class)) {
            ((SignupActivity) getActivity()).setBirthday(year, month, day);
        }else if(getActivity().getClass().equals(EditProfileActivity.class)) {
            ((EditProfileActivity) getActivity()).setBirthday(year, month, day);
        } else if(getActivity().getClass().equals(AddPaymentActivity.class)){
            ((AddPaymentActivity) getActivity()).setBirthday(year, month, day);
        }
    }
}