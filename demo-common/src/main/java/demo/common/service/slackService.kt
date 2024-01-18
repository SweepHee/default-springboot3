package demo.common.service

import com.slack.api.Slack
import com.slack.api.model.Attachment
import com.slack.api.model.block.DividerBlock
import com.slack.api.model.block.LayoutBlock
import com.slack.api.model.block.SectionBlock
import com.slack.api.model.block.composition.MarkdownTextObject
import com.slack.api.webhook.Payload
import com.slack.api.webhook.WebhookPayloads
import demo.common.model.Event
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async

open class SlackService {

    private val logger = KotlinLogging.logger { }

    companion object {

        // kpg callback 채널
        const val CALLBACK_URI = ""

        // app 로그 채널
        const val APP_LOG_URI = ""

        // error 채널
        const val ERROR_URI = ""

        // hotfix 채널
        const val HOT_FIX = ""

        const val CI = ""
    }

    private fun color(url: String) = when (url) {
        ERROR_URI, HOT_FIX -> "#FF0000"
        else -> "#6DDFE9"
    }

    private fun message(data: String): List<LayoutBlock> {
        val text = MarkdownTextObject.builder()
            .text(">$data")
            .build()
        return listOf(
            DividerBlock(),
            SectionBlock
                .builder()
                .text(text)
                .build(),
            DividerBlock()
        )
    }

    @Async
    @EventListener
    open fun listen(event: Event.SlackEvent) {
        sendMessage(
            event.uri,
            WebhookPayloads.payload {
                it.blocks(emptyList())
                    .attachments(
                        listOf(
                            Attachment
                                .builder()
                                .color(color(event.uri))
                                .text(event.data.toString())
                                .build()
                        )
                    )
            }
        )
    }

    fun sendMessage(url: String, text: String) {
        sendMessage(
            url,
            WebhookPayloads.payload {
                it.blocks(emptyList())
                    .attachments(
                        listOf(
                            Attachment
                                .builder()
                                .color(color(url))
                                .text(text)
                                .build()
                        )
                    )
            }
        )
    }

    private fun sendMessage(url: String, payload: Payload) {
        try {
            Slack.getInstance().send(url, payload)
        } catch (e: Exception) {
            logger.error(e) { "slack error" }
        }
    }
}
