package com.example.mappe1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;

public class PreferenceFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey){
        addPreferenceFromResource(R.xml.preferences);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s){

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o){
        return false;
    }
}
