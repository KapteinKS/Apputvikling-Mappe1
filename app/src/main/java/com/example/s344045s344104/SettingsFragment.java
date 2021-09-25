package com.example.s344045s344104;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import s344045s344104.R;

public class SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey){
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s){
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o){
        return false;
    }
}
