package com.example.demo.model

class Topic {
    companion object {
        const val TOPIC5: String = "topic5"
        const val CHECKOUT_COMPLETE_TOPIC: String = "checkout.complete.v1"
    }
}

enum class TopicName(val topic: String) {
    TOPIC5(Topic.TOPIC5),
    CHECKOUT_COMPLETE_TOPIC(Topic.CHECKOUT_COMPLETE_TOPIC)
    ;
}