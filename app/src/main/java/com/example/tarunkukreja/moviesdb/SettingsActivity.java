package com.example.tarunkukreja.moviesdb;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by tarunkukreja on 17/03/17.
 */

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener{
//
//    private static final String MY_PREFS = "Shared Preferences" ;
//    private static final String POPULAR = "popular movies key" ;
//    private static final String TOP_RATED = "top movies key" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_general);

        bindPreferenceSummaryToValue(findPreference(getString(R.string.movies_pref_key)));

//        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit() ;
//
//      //  editor.putString(POPULAR, getString(R.string.popular_sort)) ;
//        editor.putString(TOP_RATED, getString(R.string.top_rated_sort)) ;
//        editor.apply();

    }

    private void bindPreferenceSummaryToValue(Preference preference){
        preference.setOnPreferenceChangeListener(this);

        onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                                       .getString(preference.getKey(), " ")) ;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        ListPreference listPreference ;
        String preferenceVal = newValue.toString() ;

        if(preference instanceof ListPreference){
            listPreference = (ListPreference)preference ;
            int listPrefIndex = listPreference.findIndexOfValue(preferenceVal) ;

            if(listPrefIndex >= 0){
                preference.setSummary(listPreference.getEntries()[listPrefIndex]);

            }

        }

        else {
            preference.setSummary(preferenceVal);
        }
        return true;
    }
}
