package com.decagonhq.clads.ui.client.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.decagonhq.clads.ui.client.ClientAccountFragment
import com.decagonhq.clads.ui.client.DeliveryAddressFragment
import com.decagonhq.clads.ui.client.MeasurementsFragment

class AddClientPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val numOfTabs: Int = 3

    override fun getItemCount(): Int {
        return numOfTabs
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ClientAccountFragment()
            1 -> MeasurementsFragment()
            2 -> DeliveryAddressFragment()
            else -> ClientAccountFragment()
        }
    }
}
