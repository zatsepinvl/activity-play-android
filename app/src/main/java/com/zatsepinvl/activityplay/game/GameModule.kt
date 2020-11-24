package com.zatsepinvl.activityplay.game

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activityplay.di.viewmodel.ViewModelBuilder
import com.zatsepinvl.activityplay.di.viewmodel.ViewModelKey
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
    abstract fun gameRoomViewModel(viewModel: GameRoomViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel::class)
    abstract fun gameRoundViewModel(viewModel: GameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoundUiViewModel::class)
    abstract fun roundUiViewModel(viewModel: RoundUiViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GameFinishViewModel::class)
    abstract fun gameFinishViewModel(viewModel: GameFinishViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GameEffectsViewModel::class)
    abstract fun effectsViewModel(viewModel: GameEffectsViewModel): ViewModel

}