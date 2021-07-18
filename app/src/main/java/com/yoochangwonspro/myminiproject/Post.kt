package com.yoochangwonspro.myminiproject

import java.io.Serializable

class Post(
    var owner: String? = null,
    var content: String? = null,
    var image: String? = null
) : Serializable