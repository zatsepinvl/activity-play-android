package com.zatsepinvl.activity.play.game

import androidx.lifecycle.ViewModel
import com.zatsepinvl.activity.play.di.ViewModelBuilder
import com.zatsepinvl.activity.play.di.ViewModelKey
import com.zatsepinvl.activity.play.game.fragment.FinishGameFragment
import com.zatsepinvl.activity.play.game.fragment.FinishRoundFragment
import com.zatsepinvl.activity.play.game.fragment.InFrameFragment
import com.zatsepinvl.activity.play.game.fragment.StartRoundFragment
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
    abstract fun inRoundFragment(): InFrameFragment

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
    abstract fun dictionaryService(service: DictionaryServiceImpl): DictionaryService

    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel::class)
    abstract fun gameViewModel(gameViewModel: GameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FinishGameViewModel::class)
    abstract fun finishGameViewModel(finishGameViewModel: FinishGameViewModel): ViewModel
}