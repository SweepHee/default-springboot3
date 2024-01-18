package demo.common.service

import demo.common.model.Event
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.*

@Service
class EmitService(private val eventPublisher: ApplicationEventPublisher) {
    fun sendSlackEvent(data: Any, uri: String = SlackService.APP_LOG_URI) {
        eventPublisher.publishEvent(Event.SlackEvent(UUID.randomUUID(), data, uri))
    }
}
