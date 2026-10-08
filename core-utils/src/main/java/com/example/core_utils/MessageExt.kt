package com.example.core_utils

import com.example.core_model.Message

fun Message.getReactionCount(): Map<String, Int> {
    return reactions
        .values
        .groupingBy { it }
        .eachCount()
}