package uz.gita.tosh15;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LocalStorage.init(this);

    }
}
