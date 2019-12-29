package com.blinkist.easylibrary.model.mappers

import com.blinkist.easylibrary.di.ServiceModule
import com.blinkist.easylibrary.model.local.LocalBook
import com.blinkist.easylibrary.model.presentation.Book
import com.blinkist.easylibrary.model.remote.RemoteBook
import java.text.DateFormat
import javax.inject.Inject

class BookMapper @Inject constructor(
  @ServiceModule.ServiceDateFormat private val dateFormat: DateFormat
) {

  fun remoteToLocal(remotes: List<RemoteBook>) = remotes.map { remoteToLocal(it) }

  private fun remoteToLocal(remote: RemoteBook) =
    LocalBook(
      id = remote.id                       ?: throw IllegalArgumentException("Book has null id"),
      publishedDate = remote.publishedDate ?: throw IllegalArgumentException("Book has null publishedDate"),
      publishedDateTime = dateFormat.parse(remote.publishedDate)?.time
                                           ?: throw IllegalArgumentException("Book has invalid publishedDate"),
      title = remote.title                 ?: throw IllegalArgumentException("Book has null title"),
      authors = remote.authors             ?: throw IllegalArgumentException("Book has null authors"),
      thumbnail = remote.thumbnail         ?: throw IllegalArgumentException("Book has null thumbnail"),
      url = remote.url                     ?: throw IllegalArgumentException("Book has null url")
    )

  fun localToPresentation(locals: List<LocalBook>) = locals.map { localToPresentation(it) }

  private fun localToPresentation(local: LocalBook) =
    Book(
      id = local.id,
      publishedDate = local.publishedDate,
      publishedDateTime = local.publishedDateTime,
      title = local.title,
      authors = local.authors,
      imageUrl = local.thumbnail,
      url = local.url
    )
}
