package com.base.baseapp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.base.notifications.R
import com.base.notifications.databinding.FragmentNotificationsBinding
import com.base.ui.BaseFragment
import com.base.ui.BaseViewModel


class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>() {

    private val notificationsViewModel: NotificationsViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(NotificationsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
           binding.textNotifications.text = it
        })
    }
}