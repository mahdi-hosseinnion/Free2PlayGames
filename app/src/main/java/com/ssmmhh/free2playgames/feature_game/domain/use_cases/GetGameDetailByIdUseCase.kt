package com.ssmmhh.free2playgames.feature_game.domain.use_cases

import com.ssmmhh.free2playgames.feature_game.data.util.Result
import com.ssmmhh.free2playgames.feature_game.domain.model.GameDetail
import com.ssmmhh.free2playgames.feature_game.domain.repository.GameRepository
import javax.inject.Inject

class GetGameDetailByIdUseCase
@Inject
constructor(
    private val repository: GameRepository
) {

    suspend operator fun invoke(id: Int): Result<GameDetail> {
        return repository.getGameDetailById(id)
    }
}