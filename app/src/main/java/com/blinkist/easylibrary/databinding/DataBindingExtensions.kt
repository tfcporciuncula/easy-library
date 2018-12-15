package com.blinkist.easylibrary.databinding

import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

inline fun <reified T : ViewDataBinding> Fragment.inflateBinding(@LayoutRes layoutId: Int): T {
    return DataBindingUtil.inflate(LayoutInflater.from(requireContext()), layoutId, null, false)
}
