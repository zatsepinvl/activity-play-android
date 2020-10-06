package com.zatsepinvl.activityplay.firebase.provider

import com.zatsepinvl.activityplay.firebase.firestore.FirestoreProvider
import com.zatsepinvl.activityplay.firebase.firestore.FirestoreRealtimeStorage
import com.zatsepinvl.activityplay.multiplayer.room.model.MultiplayerRoomState
import com.zatsepinvl.activityplay.multiplayer.room.storage.MultiplayerRoomStateStorage

class FirestoreMultiplayerRoomStateStorage(firestoreProvider: FirestoreProvider) :
    FirestoreRealtimeStorage<MultiplayerRoomState>(firestoreProvider, MultiplayerRoomState::class),
    MultiplayerRoomStateStorage