package com.ssmmhh.free2playgames.featureGame.domain.useCases

import com.ssmmhh.free2playgames.featureGame.data.util.Result
import com.ssmmhh.free2playgames.featureGame.domain.model.GameDetail
import com.ssmmhh.free2playgames.featureGame.domain.repository.GameRepository
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
