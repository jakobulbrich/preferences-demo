package de.jakobulbrich.preferences

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat

/**
 * The Preference Fragment which shows the Preferences as a List and handles the Dialogs for the
 * Preferences.
 */
class PreferenceFragmentCustom : PreferenceFragmentCompat() {

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.app_preferences)
    }

    override fun onDisplayPreferenceDialog(preference: Preference?) {

        // Try if the preference is one of our custom Preferences
        val dialogFragment: DialogFragment? = when (preference) {
            is TimePreference -> {
                // Create a new instance of TimePreferenceDialogFragment with the key of the related
                // Preference
                TimePreferenceDialogFragmentCompat.newInstance(preference.getKey())
            }
            else -> null
        }

        if (dialogFragment != null) {
            // The dialog was created (it was one of our custom Preferences), show the dialog for it
            dialogFragment.setTargetFragment(this, 0)
            dialogFragment.show(
                fragmentManager,
                "android.support.v7.preference.PreferenceFragment.DIALOG"
            )
        } else {
            // Dialog creation could not be handled here. Try with the super method.
            super.onDisplayPreferenceDialog(preference)
        }
    }
}