package com.example.feature_conversation.domain.model

import com.example.core_model.Conversation
import com.example.core_model.Message
import com.example.core_model.ReplyPreview
import com.example.core_model.composite.MediaPreview

sealed class ConversationDetailAction {

    data object CloseReplyPreview: ConversationDetailAction()

    // REQUEST
    data class SendFriendRequest(
        val userId: String
    ): ConversationDetailAction()

    data class RecallFriendRequest(
        val userId: String
    ): ConversationDetailAction()


    // Media
    data class MediaSelected(
        val fileInfos: List<FileInfo>
    ): ConversationDetailAction()

    data class DownloadImage(
        val imageMessage: Message.Image
    ): ConversationDetailAction()

    data class DownloadVideo(
        val videoMessage: Message.Video
    ): ConversationDetailAction()

    data class OpenMedia(
        val mediaPreview: MediaPreview
    ): ConversationDetailAction()


    // File
    data class FileSelected(
        val fileInfos: List<FileInfo>
    ): ConversationDetailAction()

    data class DownloadFile(
        val fileMessage: Message.File
    ): ConversationDetailAction()

    data class OpenFile(
        val fileMessage: Message.File
    ): ConversationDetailAction()


    // Sticker
    data object StickerSelected: ConversationDetailAction()


    // Message
    data class SendMessage(
        val message: String
    ): ConversationDetailAction()

    data class SendReplyMessage(
        val text: String,
        val replyPreview: ReplyPreview
    ): ConversationDetailAction()

    data class ReactToMessage(
        val messageId: String,
        val emoji: String
    ): ConversationDetailAction()

    data class RemoveReaction(
        val messageId: String
    ): ConversationDetailAction()

    data class Reply(
        val message: Message,
        val senderName: String
    ): ConversationDetailAction()

    data class Forward(
        val messageId: String
    ): ConversationDetailAction()

    data class Copy(
        val text: String
    ): ConversationDetailAction()

    data class Pin(
        val messageId: String,
        val pinnedMessageCount: Int
    ): ConversationDetailAction()
}