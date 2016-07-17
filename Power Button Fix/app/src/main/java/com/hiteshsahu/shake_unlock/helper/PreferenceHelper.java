/**
 *
 */
package com.hiteshsahu.shake_unlock.helper;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * @author Hitesh
 */
public class PreferenceHelper {

    private static PreferenceHelper preferenceHelperInstance;

    /**
     * The app context.
     */
    private static Context appContext;

    private PreferenceHelper() {
    }

    public static PreferenceHelper getPrefernceHelperInstace() {

        if (null == preferenceHelperInstance) {
            preferenceHelperInstance = new PreferenceHelper();
        }
        return preferenceHelperInstance;
    }

    /**
     * @param appContext
     * @param preferenceKey
     * @param valueToSet
     */
    public void setBoolean(Context appContext, String preferenceKey, Boolean valueToSet) {

        PreferenceManager.getDefaultSharedPreferences(appContext).edit()
                .putBoolean(preferenceKey, valueToSet).apply();
    }

    /**
     * @param appContext
     * @param preferenceKey
     * @param valueToSet
     */
    public void setInteger(Context appContext, String preferenceKey, int valueToSet) {

        PreferenceManager.getDefaultSharedPreferences(appContext).edit()
                .putInt(preferenceKey, valueToSet).apply();
    }

    /**
     * @param appContext
     * @param preferenceKey
     * @param valueToSet
     */
    public void setFloat(Context appContext, String preferenceKey, float valueToSet) {

        PreferenceManager.getDefaultSharedPreferences(appContext).edit()
                .putFloat(preferenceKey, valueToSet).apply();
    }

    /**
     * @param appContext
     * @param preferenceKey
     * @param valueToSet
     */
    public void setString(Context appContext, String preferenceKey, String valueToSet) {

        PreferenceManager.getDefaultSharedPreferences(appContext).edit()
                .putString(preferenceKey, valueToSet).apply();
    }


    /**
     * @param appContext
     * @param preferenceKey
     * @param defaultValue
     * @return
     */
    public boolean getBoolean(Context appContext, String preferenceKey,
                              Boolean defaultValue) {

        return PreferenceManager.getDefaultSharedPreferences(appContext)
                .getBoolean(preferenceKey, defaultValue);
    }

    /**
     * @param appContext
     * @param preferenceKey
     * @param defaultValue
     * @return
     */
    public int getInteger(Context appContext, String preferenceKey, int defaultValue) {

        return PreferenceManager.getDefaultSharedPreferences(appContext)
                .getInt(preferenceKey, defaultValue);
    }

    /**
     * @param appContext
     * @param preferenceKey
     * @param defaultValue
     * @return
     */
    public float getString(Context appContext, String preferenceKey, float defaultValue) {

        return PreferenceManager.getDefaultSharedPreferences(appContext)
                .getFloat(preferenceKey, defaultValue);
    }

    /**
     * @param appContext
     * @param preferenceKey
     * @param defaultValue
     * @return
     */
    public String getString(Context appContext, String preferenceKey, String defaultValue) {

        return PreferenceManager.getDefaultSharedPreferences(appContext)
                .getString(preferenceKey, defaultValue);
    }

}
