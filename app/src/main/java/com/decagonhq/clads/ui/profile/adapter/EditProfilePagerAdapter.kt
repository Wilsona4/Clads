package com.decagonhq.clads.ui.profile.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment
import com.decagonhq.clads.ui.profile.editprofile.PaymentMethodFragment
import com.decagonhq.clads.ui.profile.editprofile.SecurityFragment
import com.decagonhq.clads.ui.profile.editprofile.SpecialtyFragment

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
