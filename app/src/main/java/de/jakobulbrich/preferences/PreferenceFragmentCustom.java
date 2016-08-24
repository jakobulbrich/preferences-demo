package de.jakobulbrich.preferences;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * The Preference Fragment which shows the Preferences as a List and handles the Dialogs for the
 * Preferences.
 *
 * @author Jakob Ulbrich
 */
public class PreferenceFragmentCustom extends PreferenceFragmentCompat {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.app_preferences);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDisplayPreferenceDialog(Preference preference) {

        // Try if the preference is one of our custom Preferences
        DialogFragment dialogFragment = null;
        if (preference instanceof TimePreference) {
            // Create a new instance of TimePreferenceDialogFragment with the key of the related
            // Preference
            dialogFragment = TimePreferenceDialogFragmentCompat.newInstance(preference.getKey());
        }


        if (dialogFragment != null) {
            // The dialog was created (it was one of our custom Preferences), show the dialog for it
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(this.getFragmentManager(), "android.support.v7.preference" +
                    ".PreferenceFragment.DIALOG");
        } else {
            // Dialog creation could not be handled here. Try with the super method.
            super.onDisplayPreferenceDialog(preference);
        }

    }
}
