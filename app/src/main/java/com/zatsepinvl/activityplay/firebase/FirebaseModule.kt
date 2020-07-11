package com.zatsepinvl.activityplay.firebase

import android.content.Context
import com.zatsepinvl.activityplay.BuildConfig
import com.zatsepinvl.activityplay.firebase.firestore.FirestoreLocalProvider
import com.zatsepinvl.activityplay.firebase.firestore.FirestoreProvider
import com.zatsepinvl.activityplay.firebase.firestore.FirestoreRemoteProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {
    @Provides
    @Singleton
    fun firestoreProvider(context: Context): FirestoreProvider {
        return if (BuildConfig.DEBUG) {
            FirestoreLocalProvider(context)
        } else {
            FirestoreRemoteProvider()
        }
    }
}