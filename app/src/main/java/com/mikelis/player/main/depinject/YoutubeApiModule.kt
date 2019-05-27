package com.mikelis.player.main.depinject

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mikelis.player.main.repository.YoutubeApiRepository
import com.mikelis.player.main.viewmodel.YoutubeApiViewModel
import dagger.Module
import dagger.Provides

@Module
class YoutubeApiModule {
    @Provides
    fun viewModel(fragment: Fragment, factory: YoutubeApiViewModel.Factory): YoutubeApiViewModel {
        return ViewModelProviders.of(fragment, factory).get(YoutubeApiViewModel::class.java)
    }

    @Provides
    fun viewModelFactory(): YoutubeApiViewModel.Factory {
        return YoutubeApiViewModel.Factory(YoutubeApiRepository())
    }


}