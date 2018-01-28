package net.kibotu.logger.demo;

import android.support.multidex.MultiDexApplication;

import net.kibotu.logger.logger.LogcatLogger;
import net.kibotu.logger.logger.Logger;


/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */
public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.with(this);
        Logger.addLogger(new LogcatLogger(), Logger.Level.DEBUG);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        Logger.onTerminate();
    }
}