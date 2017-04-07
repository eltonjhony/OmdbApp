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

        String PAGER_INDEX = new String(new char[]{'p', 'u', 'e', 'r', 'c', 'o', 'r', 'w', 'e',
                'f', 'f', 'l', 'd', 'v', 'l', 'p', 'o', 'q', 'r', 'f', 'h', 'p', 'u', 'i', 'e', 'm' });
    }

    public interface ApplicationConfigurationParameter {
        String DEFAULT_HOST = "default_host";
        String API_KEY = "api_key";
    }
}
