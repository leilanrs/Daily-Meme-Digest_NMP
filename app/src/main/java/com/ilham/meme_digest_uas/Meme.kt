package com.ilham.meme_digest_uas


data class Meme(
    val id: Int,
    val memeUrl: String,
    val topText: String,
    val botText: String,
    val date: String,
    var likeCount: Int,
    val userId: Int,
    var isLiked: Boolean,
    var totalComments:Int
)
