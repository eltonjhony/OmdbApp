package com.movies.android.omdbapp.infraestructure;

/**
 * Created by eltonjhony on 4/6/17.
 */

public class Constants {

    private Constants() {
    }

    public interface PreferenceKeys {
        String SEARCHER = new String(new char[]{'a', 's', 'd', 'f', 'c', 'a', 'f', 'd', 'g',
                's', 'f', 'v', 'c', 'x', 'e', 'r', 'd', 't', 'y', 'j', 'h', 'g', 'f', 'd', 's', 'e' });

        String SEARCH_TEXT = new String(new char[]{'d', 'q', 'c', 'w', 'o', 'p', 'n', 'k', 'v',
                'r', 'g', 'q', 's', 'i', 'q' });
    }

    public interface ApplicationConfigurationParameter {
        String DEFAULT_HOST = "default_host";
        String API_KEY = "api_key";
    }
}
