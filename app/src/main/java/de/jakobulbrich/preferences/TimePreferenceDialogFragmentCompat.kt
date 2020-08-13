package de.jakobulbrich.preferences

import android.os.Build
import android.os.Bundle
import android.support.v7.preference.PreferenceDialogFragmentCompat
import android.text.format.DateFormat
import android.view.View
import android.widget.TimePicker

/**
 * The Dialog for the [TimePreference].
 */
class TimePreferenceDialogFragmentCompat : PreferenceDialogFragmentCompat() {
    /**
     * The TimePicker widget
     */
    private lateinit var mTimePicker: TimePicker

    /**
     * {@inheritDoc}
     */
    override fun onBindDialogView(view: View?) {
        super.onBindDialogView(view)

        mTimePicker = view?.findViewById(R.id.edit)
            ?: error("Dialog view must contain a TimePicker with id 'edit'")

        // Get the time from the related Preference
        val minutesAfterMidnight: Int = preference.time

        // Set the time to the TimePicker
        val hours = minutesAfterMidnight / 60
        val minutes = minutesAfterMidnight % 60
        val is24hour = DateFormat.is24HourFormat(context)

        mTimePicker.apply {
            setIs24HourView(is24hour)
            if (Build.VERSION.SDK_INT >= 23) {
                hour = hours
                minute = minutes
            } else {
                @Suppress("DEPRECATION")
                currentHour = hours
                @Suppress("DEPRECATION")
                currentMinute = minutes
            }
        }
    }

    /**
     * Called when the Dialog is closed.
     *
     * @param positiveResult Whether the Dialog was accepted or canceled.
     */
    override fun onDialogClosed(positiveResult: Boolean) {
        if (positiveResult) {
            val hours: Int
            val minutes: Int
            // Get the current values from the TimePicker
            if (Build.VERSION.SDK_INT >= 23) {
                hours = mTimePicker.hour
                minutes = mTimePicker.minute
            } else {
                @Suppress("DEPRECATION")
                hours = mTimePicker.currentHour
                @Suppress("DEPRECATION")
                minutes = mTimePicker.currentMinute
            }

            // Generate value to save
            val minutesAfterMidnight = hours * 60 + minutes

            // Save the value
            preference.apply {
                // This allows the client to ignore the user value.
                if (callChangeListener(minutesAfterMidnight)) {
                    // Save the value
                    time = minutesAfterMidnight
                }
            }
        }
    }

    override fun getPreference(): TimePreference {
        return super.getPreference() as? TimePreference
            ?: error("Preference is not a TimePreference")
    }

    companion object {
        /**
         * Creates a new Instance of the TimePreferenceDialogFragment and stores the key of the
         * related Preference
         *
         * @param key The key of the related Preference
         * @return A new Instance of the TimePreferenceDialogFragment
         */
        fun newInstance(key: String?) = TimePreferenceDialogFragmentCompat().apply {
            arguments = Bundle(1).apply {
                putString(ARG_KEY, key)
            }
        }
    }
}