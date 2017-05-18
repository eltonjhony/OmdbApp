package com.movies.android.aou.browse.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.movies.android.aou.infraestructure.ApplicationConfiguration;
import com.movies.android.aou.infraestructure.MyLog;

/**
 * Created by eltonjhony on 10/04/17.
 */
public class FeaturedVideoFragment extends YouTubePlayerSupportFragment {

    public static final String VIDEO_URL_EXTRA = "com.movies.android.aou.VIDEO_URL";

    private YouTubePlayer mYoutubePlayer;

    public static FeaturedVideoFragment newInstance(String videoUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_URL_EXTRA, videoUrl);
        FeaturedVideoFragment fragment = new FeaturedVideoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(ApplicationConfiguration.getYoutubeKey(), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                mYoutubePlayer = youTubePlayer;
                mYoutubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                if (!b) {
                    mYoutubePlayer.cueVideo(getArguments().getString(FeaturedVideoFragment.VIDEO_URL_EXTRA), 0);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                MyLog.info(FeaturedVideoFragment.class.getSimpleName(), "An error occurs to play featured video");
            }
        });
    }

}
