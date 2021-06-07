package com.decagonhq.clads.ui.profile.editprofile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class EditProfilePagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val numOfTabs: Int = 4

    override fun getItemCount(): Int {
        return numOfTabs
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AccountFragment()
            1 -> SpecialtyFragment()
            2 -> PaymentMethodFragment()
            3 -> SecurityFragment()
            else -> AccountFragment()
        }
    }
}
