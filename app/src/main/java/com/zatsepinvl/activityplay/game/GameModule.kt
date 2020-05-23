package com.zatsepinvl.activityplay.game

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.di.ViewModelBuilder
import com.zatsepinvl.activityplay.di.ViewModelKey
import com.zatsepinvl.activityplay.game.fragment.*
import com.zatsepinvl.activityplay.game.service.*
import com.zatsepinvl.activityplay.game.viewmodel.FinishGameViewModel
import com.zatsepinvl.activityplay.game.viewmodel.PlayRoundViewModel
import com.zatsepinvl.activityplay.game.viewmodel.StartRoundViewModel
import com.zatsepinvl.activityplay.game.viewmodel.TimerViewModel
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
    abstract fun gameStateRepository(repo: LocalGameStateRepository): GameStateRepository

    @Binds
    @Singleton
    abstract fun gameService(service: GameServiceImpl): GameService

    @Binds
    @Singleton
    abstract fun gameActionService(service: GameActionServiceImpl): GameActionService

    @Binds
    @IntoMap
    @ViewModelKey(StartRoundViewModel::class)
    abstract fun startRoundViewModel(startRoundViewModel: StartRoundViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlayRoundViewModel::class)
    abstract fun playRoundViewModel(playRoundViewModel: PlayRoundViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FinishGameViewModel::class)
    abstract fun finishGameViewModel(finishGameViewModel: FinishGameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TimerViewModel::class)
    abstract fun timerViewModel(timerViewModel: TimerViewModel): ViewModel
}