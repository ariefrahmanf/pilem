package com.ariefrahmanfajri.pilem.di

import com.ariefrahmanfajri.pilem.databinding.FragmentSeeMoreNowPlayingBinding
import com.ariefrahmanfajri.pilem.presentation.detail.MovieDetailFragment
import com.ariefrahmanfajri.pilem.presentation.home.HomeFragment
import com.ariefrahmanfajri.pilem.presentation.home.nowplaying.SeeMoreNowPlayingFragment
import com.ariefrahmanfajri.pilem.presentation.playvideo.PlayVideoFragment
import com.ariefrahmanfajri.pilem.presentation.search.SearchFragment
import com.ariefrahmanfajri.pilem.presentation.tvshow.TvShowFragment
import com.ariefrahmanfajri.pilem.presentation.tvshow.detail.TvShowDetailFragment
import dagger.Component

@Component(modules = [ApiModule::class])
interface AppComponent {

    /*    @Component.Factory
        interface Factory{
            fun create(@BindsInstance context: Context): AppComponent
        }*/
    fun inject(homeFragment: HomeFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(movieDetailFragment: MovieDetailFragment)
    fun inject(tvShowFragment: TvShowFragment)
    fun inject(tvShowDetailFragment: TvShowDetailFragment)
    fun inject(playVideoFragment: PlayVideoFragment)
    fun inject(seeMoreNowPlayingFragment: SeeMoreNowPlayingFragment)
}