package com.zatsepinvl.activityplay.game

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.di.ViewModelBuilder
import com.zatsepinvl.activityplay.di.ViewModelKey
import com.zatsepinvl.activityplay.game.fragment.*
import com.zatsepinvl.activityplay.gameaction.GameActionService
import com.zatsepinvl.activityplay.gameaction.GameActionServiceImpl
import com.zatsepinvl.activityplay.game.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class GameModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun startRoundFragment(): StartRoundFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun inRoundFragment(): PlayRoundFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun canvasFragment(): CanvasFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun lastWordRoundFragment(): LastWordRoundFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun finishRoundFragment(): FinishRoundFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun finishGameFragment(): FinishGameFragment

    @Binds
    @Singleton
    abstract fun gameActionService(service: GameActionServiceImpl): GameActionService

    @Binds
    @IntoMap
    @ViewModelKey(GameRoomViewModel::class)
    abstract fun startRoundViewModel(gameRoomViewModel: GameRoomViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MultiplayerGameViewModel::class)
    abstract fun multiplayerGameViewModel(multiplayerGameViewModel: MultiplayerGameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoundGameViewModel::class)
    abstract fun roundGameViewModel(roundGameViewModel: RoundGameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoundUiViewModel::class)
    abstract fun roundUiViewModel(roundUiViewModel: RoundUiViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FinishGameViewModel::class)
    abstract fun finishGameViewModel(finishGameViewModel: FinishGameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TimerViewModel::class)
    abstract fun timerViewModel(timerViewModel: TimerViewModel): ViewModel


}