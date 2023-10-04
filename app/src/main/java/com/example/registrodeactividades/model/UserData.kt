package com.example.registrodeactividades.model

import java.util.Date

data class UserData(
    val id: String = "",
    var consumeWater: Int = 0,
    var currentMoney: String = "00.00",
    var dailyLives: Int = 0,
    var date: String? = "",
    var duolingo: Boolean? = false,
    var recentDate: Date = Date(),
    var extras: Int = 0,
    var lives: Int = 0,
    var lostMoney: String = "00.00",
    val name: String = "",
    var pointsEarned: Int = 0,
    var pointsGames: Int = 0,
    var pointsLost: Int = 0,
    var recentMoney: String = "00.00",
    var song: Int = 0
)

fun mapToUserData(data: Map<String, Any>): UserData {
    return UserData(
        id = data["id"] as? String ?: "",
        consumeWater = (data["consumeWater"] as? Long)?.toInt() ?: 0,
        currentMoney = data["currentMoney"] as? String ?: "00.00",
        dailyLives = (data["dailyLives"] as? Long)?.toInt() ?: 0,
        date = data["date"] as? String ?: "",
        duolingo = data["duolingo"] as? Boolean ?: false,
        recentDate = data["recentDate"] as? Date ?: Date(),
        extras = (data["extras"] as? Long)?.toInt() ?: 0,
        lives = (data["lives"] as? Long)?.toInt() ?: 0,
        lostMoney = data["lostMoney"] as? String ?: "00.00",
        name = data["name"] as? String ?: "",
        pointsEarned = (data["pointsEarned"] as? Long)?.toInt() ?: 0,
        pointsGames = (data["pointsGames"] as? Long)?.toInt() ?: 0,
        pointsLost = (data["pointsLost"] as? Long)?.toInt() ?: 0,
        recentMoney = data["recentMoney"] as? String ?: "00.00",
        song = (data["song"] as? Long)?.toInt() ?: 0
    )
}
