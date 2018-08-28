package com.blinkist.easylibrary.databinding

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater

inline fun <reified T : ViewDataBinding> Fragment.inflateBinding(@LayoutRes layoutId: Int): T {
    return DataBindingUtil.inflate(LayoutInflater.from(requireContext()), layoutId, null, false)
}
