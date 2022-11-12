package com.oneandonly.inventationblock.ui.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView

open class ContainerFragment: Fragment(){

    private var mFragmentId: Int = 0

    fun changeFragment(fragment: Fragment, id: Int, tag: String?= null) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(id, fragment, tag)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun popFragment() : Boolean {
        if (mFragmentId == -1) {
            return false
        }

        val fv: FragmentContainerView = view?.findViewById<View>(mFragmentId) as FragmentContainerView
        return false
    }
}