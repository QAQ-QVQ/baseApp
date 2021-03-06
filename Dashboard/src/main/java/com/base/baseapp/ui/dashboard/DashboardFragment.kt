package com.base.baseapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.base.dashboard.R
import com.base.dashboard.databinding.FragmentDashboardBinding
import com.base.ui.BaseFragment


class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private val dashboardViewModel: DashboardViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(DashboardViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
           binding.textDashboard.text = it
        })
    }
}