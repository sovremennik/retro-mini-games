package state

import viewmodel.GameViewModel

interface GameState {
    fun start(game: GameViewModel) {}
    fun pause(game: GameViewModel) {}
}

class WaitingState : GameState {
    override fun start(game: GameViewModel) {
        game.isPaused.value = false
        game.state = PlayingState()
    }
}

class PlayingState : GameState {
    override fun pause(game: GameViewModel) {
        game.isPaused.value = true
        game.state = PausedState()
    }
}

class PausedState : GameState {
    override fun start(game: GameViewModel) {
        game.isPaused.value = false
        game.state = PlayingState()
    }
}
