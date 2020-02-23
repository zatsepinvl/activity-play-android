package com.zatsepinvl.activity.play.game

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.di.ViewModelBuilder
import com.zatsepinvl.activity.play.di.ViewModelKey
import com.zatsepinvl.activity.play.game.fragment.*
import com.zatsepinvl.activity.play.game.service.GameService
import com.zatsepinvl.activity.play.game.service.GameServiceImpl
import com.zatsepinvl.activity.play.game.service.GameStateRepository
import com.zatsepinvl.activity.play.game.service.LocalGameStateRepository
import com.zatsepinvl.activity.play.game.viewmodel.FinishGameViewModel
import com.zatsepinvl.activity.play.game.viewmodel.PlayRoundViewModel
import com.zatsepinvl.activity.play.game.viewmodel.StartRoundViewModel
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
}