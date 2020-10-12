package com.zatsepinvl.activityplay.firebase.multiplayer

import com.zatsepinvl.activityplay.firebase.firestore.FirestoreProvider
import com.zatsepinvl.activityplay.firebase.firestore.FirestoreRealtimeStorage
import com.zatsepinvl.activityplay.multiplayer.room.model.MultiplayerRoomState
import com.zatsepinvl.activityplay.multiplayer.room.storage.MultiplayerRoomStateStorage

private const val DEFAULT_COLLECTION_NAME = "multiplayer_rooms"

class FirestoreMultiplayerRoomStateStorage(firestoreProvider: FirestoreProvider) :
    FirestoreRealtimeStorage<MultiplayerRoomState>(
        firestoreProvider = firestoreProvider,
        dataType = MultiplayerRoomState::class,
        collectionName = DEFAULT_COLLECTION_NAME
    ),
    MultiplayerRoomStateStorage