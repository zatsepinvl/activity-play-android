package com.zatsepinvl.activityplay.firebase

import com.zatsepinvl.activityplay.firebase.firestore.FirestoreProvider
import com.zatsepinvl.activityplay.firebase.provider.FirestoreMultiplayerRoomStateStorage
import com.zatsepinvl.activityplay.multiplayer.room.storage.MultiplayerRoomStateStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {
    @Provides
    @Singleton
    fun multiplayerRoomStateStorage(firestoreProvider: FirestoreProvider): MultiplayerRoomStateStorage {
        return FirestoreMultiplayerRoomStateStorage(firestoreProvider)
    }
}

