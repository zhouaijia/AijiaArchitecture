package com.aijia.android.activityresult

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager


/**
 * @author aijia
 * @since 2020/05/26
 */
class ActivityResultHelper(private val mActivity: FragmentActivity?) {
    private var mActivityResultFragment: ActivityResultFragment? = null

    init {
        mActivityResultFragment = getActivityResultFragment()
    }

    companion object {
        private const val TAG = "ActivityResultFragment"

        fun init(activity: FragmentActivity?): ActivityResultHelper? {
            return ActivityResultHelper(activity)
        }
    }

    private fun getActivityResultFragment(): ActivityResultFragment? {
        val fragmentManager: FragmentManager? = mActivity?.supportFragmentManager
        var activityResultFragment: ActivityResultFragment? = fragmentManager?.findFragmentByTag(
            Companion.TAG
        ) as ActivityResultFragment
        if (activityResultFragment == null) {
            activityResultFragment = ActivityResultFragment.getInstance()
            fragmentManager.beginTransaction().add(activityResultFragment, Companion.TAG).commitAllowingStateLoss()
            fragmentManager.executePendingTransactions()
        }

        return activityResultFragment
    }

    fun startActivityForResult(clazz: Class<*>?, callback: Callback?) {
        val intent = Intent(mActivity, clazz)
        startActivityForResult(intent, callback)
    }

    fun startActivityForResult(intent: Intent?, callback: Callback?) {
        mActivityResultFragment?.startActivityForResult(intent, callback)
    }

    interface Callback {
        fun onActivityResult(resultCode: Int, data: Intent?)
    }
}