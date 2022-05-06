package com.base.baseapp.ui.about

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.base.about.databinding.FragmentAboutBinding
import com.base.ui.BaseFragment


class AboutFragment : BaseFragment<FragmentAboutBinding>() {
    private val aboutViewModel: AboutViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(AboutViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textAbout.text = it
        })

    }
}