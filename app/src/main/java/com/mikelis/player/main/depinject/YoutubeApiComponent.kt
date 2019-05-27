package com.mikelis.player.main.depinject

import com.mikelis.player.main.fragment.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [YoutubeApiModule::class])
interface YoutubeApiComponent {
    fun inject(mainFragment: MainFragment)
}