package org.myapp.andreysumin2.domain.usecases

import org.myapp.andreysumin2.domain.entity.GameSettings
import org.myapp.andreysumin2.domain.entity.Level
import org.myapp.andreysumin2.domain.repository.GameRepository

class GetGameSettingsUseCase (private val repository: GameRepository){
    operator fun invoke(level: Level):GameSettings{
     return repository.getGameSettings(level )
    }
}