package de.jakobulbrich.preferences

import android.content.Context
import android.content.res.TypedArray
import android.support.v7.preference.DialogPreference
import android.util.AttributeSet

/**
 * A Preference to select a specific Time with a [android.widget.TimePicker].
 */
class TimePreference @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.preferenceStyle,
    defStyleRes: Int = defStyleAttr
) : DialogPreference(context, attrs, defStyleAttr, defStyleRes) {

    /**
     * In Minutes after midnight
     */
    var time: Int = DEFAULT_VALUE
        set(value) {
            field = value

            // Save to SharedPreferences
            persistInt(value)
        }


    /**
     * Called when a Preference is being inflated and the default value attribute needs to be read.
     *
     * The type of this preference is Int, so we read the default value from the attributes as Int.
     * Fallback value is set to 0.
     */
    override fun onGetDefaultValue(a: TypedArray?, index: Int): Int {
        return a?.getInt(index, DEFAULT_VALUE) ?: DEFAULT_VALUE
    }

    /**
     * Returns the layout resource that is used as the content View for the dialog
     */
    override fun getDialogLayoutResource() = R.layout.pref_dialog_time

    /**
     * Implement this to set the initial value of the Preference.
     */
    override fun onSetInitialValue(defaultValue: Any?) {
        // If the value can be restored, do it. If not, use the default value.
        time = getPersistedInt((defaultValue as? Int) ?: DEFAULT_VALUE)
    }

    companion object {
        private const val DEFAULT_VALUE = 0
    }
}