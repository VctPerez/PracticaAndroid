package com.viewnext.practicaandroid.domain.data

import kotlinx.serialization.Serializable

@Serializable
data class UserDetailsResponse(
    val cau : String = "",
    val requestStatus: String = "",
    val type: String = "",
    val compensation: String = "",
    val installationPower: String = ""
)
