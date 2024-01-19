package demo.common.model

class KafkaTopic {
    companion object {
        const val TOPIC5: String = "topic5"
        const val CHECKOUT_COMPLETE_TOPIC: String = "checkout.complete.v1"
    }

    enum class Topic(val topic: String) {
        TOPIC5(KafkaTopic.TOPIC5),
        CHECKOUT_COMPLETE_TOPIC(KafkaTopic.CHECKOUT_COMPLETE_TOPIC)
        ;
    }
}




class KafkaGroup {
    companion object {
        const val SHIPMENT_V1 = "shipment.group.v1"
    }

    enum class Group(val group: String) {
        SHIPMENT_V1(KafkaGroup.SHIPMENT_V1)
    }
}

