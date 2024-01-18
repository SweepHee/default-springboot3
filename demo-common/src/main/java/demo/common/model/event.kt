package demo.common.model

import java.util.*

sealed class Event(val operation: String) {
    data class SlackEvent(val key: UUID, val data: Any? = null, val uri: String = "") : Event("slack")

}
