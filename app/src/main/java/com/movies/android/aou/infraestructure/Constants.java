package com.movies.android.aou.infraestructure;

/**
 * Created by eltonjhony on 4/6/17.
 */
public class Constants {

    private Constants() {
    }

    public interface PreferenceKeys {

        String SEARCHER_KEY = new String(new char[]{'a', 's', 'd', 'e', 'c', 'a', 'f', 'd', 'g',
                'h', 'u', 'i', 'r', 'x', 'e', 'r', 'd', 't', 'y', 't', 'q', 's', 't', 'd', 's', 'e' });

        String PAGER_KEY = new String(new char[]{'s', 'r', 'd', 'f', 'c', 'a', 'f', 'q', 'l',
                's', 'f', 'o', 'c', 'r', 'r', 'r', 'd', 't', 'y', 'q', 'h', 'g', 'f', 'd', 'r', 'w' });

        String SHOW_HIDE_KEY = new String(new char[]{'e', 'y', 'b', 'f', 'e', 'a', 'x', 'd', 'x',
                'q', 'e', 'r', 'c', 'r', 'r', 'j', 'g', 'r', 'e', 'i', 'u', 'y', 'f', 'r', 't', 'w' });;
    }

    public interface ApplicationConfigurationParameter {
        String DEFAULT_HOST = "default_host";
        String API_KEY = "api_key";
        String YOUTUBE_KEY = "yt_key";
    }
}
