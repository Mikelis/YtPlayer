package com.mikelis.player.main.fragment

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mikelis.player.R
import com.mikelis.player.common.ParentFragment
import com.mikelis.player.main.depinject.DaggerYoutubeApiComponent
import com.mikelis.player.main.depinject.YoutubeApiModule
import com.mikelis.player.main.repository.model.VideoResponse
import com.mikelis.player.main.viewmodel.YoutubeApiViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.fragment_main_layout.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainFragment : ParentFragment() {
    lateinit var youtubeViewModel: YoutubeApiViewModel
    @Inject
    lateinit var viewModelFactory: YoutubeApiViewModel.Factory
    private val TAG = "MainFragment"
    private val subs: CompositeDisposable = CompositeDisposable()
    lateinit var ytPlayer: YouTubePlayer
    var videoId: String? = null

    private val searchQueryChange: BehaviorSubject<String> = BehaviorSubject.create()

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_layout
    }

    override fun injectDependencies() {
        DaggerYoutubeApiComponent.builder()
            .youtubeApiModule(YoutubeApiModule())
            .build().inject(this)
        youtubeViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(YoutubeApiViewModel::class.java)
    }

    override fun setViews(){
        search_edit_text.setText(youtubeViewModel.getKeyword())
    }

    override fun setListeners() {
        lifecycle.addObserver(youtube_player_view)
        setSearchListener()
        search_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchQueryChange.onNext(s.toString())
            }

        })
        youtube_player_view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                ytPlayer = youTubePlayer
                if (videoId != null) {
                    youTubePlayer.loadVideo(videoId!!, 0f)
                }
            }
        })
    }

    private fun setSearchListener() {
        subs.add(
            searchQueryChange.toFlowable(BackpressureStrategy.LATEST).toObservable()
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    search(result)
                }, { t: Throwable? -> Log.v(TAG, t?.message) })
        )
    }

    private fun search(keyword: String) {
        youtubeViewModel.search(keyword).observe(viewLifecycleOwner, Observer {
            when {
                it.State == VideoResponse.Companion.State.OK -> {
                    progress_bar.visibility = View.INVISIBLE
                    videoId = it.videos?.items?.get(0)?.id?.videoId
                    if (::ytPlayer.isInitialized && videoId != null) {
                        ytPlayer.loadVideo(videoId!!, 0f)
                    }
                }
                it.State == VideoResponse.Companion.State.FAIL -> {
                    progress_bar.visibility = View.INVISIBLE
                    Toast.makeText(context, resources.getString(R.string.error_text), Toast.LENGTH_SHORT).show()
                }
                it.State == VideoResponse.Companion.State.LOADING -> {
                    progress_bar.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        subs.clear()
    }
}