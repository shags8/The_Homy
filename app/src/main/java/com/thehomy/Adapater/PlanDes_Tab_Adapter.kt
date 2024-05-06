package com.thehomy.Adapater

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.thehomy.Description
import com.thehomy.Plans

class PlanDes_Tab_Adapter(fm : FragmentActivity):FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Description()
            1 -> Plans()
            else -> Plans()
        }
    }

}