package com.decagonhq.clads.ui.client

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class AddClientPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val numOfTabs: Int = 5

    override fun getItemCount(): Int {
        return numOfTabs
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ClientAccountFragment()
            1 -> MeasurementsFragment()
            2 -> DeliveryAddressFragment()
            3 -> NativeFragment()
            4 -> EnglishFragment()
            else -> ClientAccountFragment()
        }
    }
}
