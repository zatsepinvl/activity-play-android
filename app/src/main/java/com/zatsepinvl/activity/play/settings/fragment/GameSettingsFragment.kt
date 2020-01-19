package com.zatsepinvl.activity.play.settings.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.settings.fragment.ActivityPlayPreference.MAX_SCORE
import com.zatsepinvl.activity.play.settings.fragment.ActivityPlayPreference.ROUND_TIME_SECONDS

enum class ActivityPlayPreference(
    val id: String
) {
    ROUND_TIME_SECONDS("roundTimeSeconds"),
    MAX_SCORE("maxScore")

}

class GameSettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        findPreference<SeekBarPreference>(ROUND_TIME_SECONDS.id)?.increment(10)
        findPreference<SeekBarPreference>(MAX_SCORE.id)?.increment(10)
    }


}

private fun SeekBarPreference.increment(by: Int) {
    value
    setOnPreferenceChangeListener { _, newValue ->
        value = (newValue as Int) / by * by
        false
    }
}