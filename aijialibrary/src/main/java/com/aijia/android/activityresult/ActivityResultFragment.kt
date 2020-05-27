package com.aijia.android.activityresult

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import androidx.fragment.app.Fragment
import java.util.*

/**
 * @author aijia
 * @since 2020/05/26
 */
class ActivityResultFragment: Fragment() {
    private val mCallbacks: SparseArray<ActivityResultHelper.Callback> = SparseArray()
    private val mCodeGenerator = Random()

    companion object {
        fun getInstance(): ActivityResultFragment {
            return ActivityResultFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    fun startActivityForResult(intent: Intent?, callback: ActivityResultHelper.Callback?) {
        val requestCode: Int = getRequestCode()
        mCallbacks.put(requestCode, callback)
        startActivityForResult(intent, requestCode)
    }

    /**
     * 生成唯一的requestCode(五位随机数)，最多尝试10次
     * */
    private fun getRequestCode(): Int {
        var requestCode: Int
        var tryCount = 0
        do {
            requestCode = mCodeGenerator.nextInt(10000)
            tryCount ++
        } while (mCallbacks.indexOfKey(requestCode) >= 0 && 10 > tryCount )

        return requestCode
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val callback: ActivityResultHelper.Callback? = mCallbacks.get(requestCode)
        mCallbacks.remove(requestCode)
        callback?.onActivityResult(resultCode, data)
    }
}