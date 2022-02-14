package com.ssmmhh.free2playgames.feature_game.data.util

object Constants {
    const val NETWORK_TIMEOUT: Long = 5_000
    private const val NETWORK_BASIC_SOLUTION = "Check your internet connection"
    const val NETWORK_TIMEOUT_ERROR = "Network call took too long!\n$NETWORK_BASIC_SOLUTION"
    const val NETWORK_UNKNOWN_ERROR =
        "While making calls to the network something unexpected happened!\n$NETWORK_BASIC_SOLUTION"
    const val NETWORK_CONNECTION_ERROR =
        "There is problem while connecting to the network! \n$NETWORK_BASIC_SOLUTION"
}