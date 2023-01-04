package com.ilham.meme_digest_uas

data class Comment(
    val memes_id: Int,
    val users_id: Int,
    val comment: String,
    val date: String,
    val username: String,
)
