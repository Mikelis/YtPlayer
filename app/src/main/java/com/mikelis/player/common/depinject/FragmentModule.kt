package com.mikelis.player.common.depinject

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: Fragment) {
    @Provides
    fun fragment(): Fragment = fragment

}