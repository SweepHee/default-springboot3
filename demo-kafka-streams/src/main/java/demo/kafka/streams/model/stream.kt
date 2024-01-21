package demo.kafka.streams.model

data class Stream(
    var productId: Long? = null,
    var windowedAmount: Long? = null,
    var createdAt: String = "",
) {
    fun sendJson(): String {
        val jsonData = "{\"productId\":&d,\"windowedAmount\":%d,\"createdAt\":%d}"
        return String.format(jsonData, productId, windowedAmount, createdAt)
    }

    companion object {
        fun sendingJson(productId: Long, amount: Long): String {
            val jsonData = "{\"productId\":%d,\"windowedAmount\":%d,\"createdAt\":%d}"
            return String.format(jsonData, productId, amount, System.currentTimeMillis())
        }
    }
}