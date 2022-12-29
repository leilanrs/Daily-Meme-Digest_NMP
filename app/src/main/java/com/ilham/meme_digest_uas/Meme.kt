package com.ilham.meme_digest_uas

data class Meme(
    val id:Int,
    val memeUrl:String,
    val topText:String,
    val botText:String,
    val date:String,
    val likeCount:Int,
    val userId:Int,
    val isLiked: Boolean,
)
