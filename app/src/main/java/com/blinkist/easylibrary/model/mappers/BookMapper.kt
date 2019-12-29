package com.blinkist.easylibrary.model.mappers

import com.blinkist.easylibrary.model.local.LocalBook
import com.blinkist.easylibrary.model.presentation.Book
import com.blinkist.easylibrary.model.remote.RemoteBook
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class BookMapper @Inject constructor() {

  fun remoteToLocal(remotes: List<RemoteBook>) = remotes.map { remoteToLocal(it) }

  private fun remoteToLocal(remote: RemoteBook) =
    LocalBook(
      id = remote.id                       ?: throw IllegalArgumentException("Book has null id"),
      publishedDate = remote.publishedDate ?: throw IllegalArgumentException("Book has null publishedDate"),
      title = remote.title                 ?: throw IllegalArgumentException("Book has null title"),
      authors = remote.authors             ?: throw IllegalArgumentException("Book has null authors"),
      thumbnail = remote.thumbnail         ?: throw IllegalArgumentException("Book has null thumbnail"),
      url = remote.url                     ?: throw IllegalArgumentException("Book has null url")
    )

  fun localToPresentation(locals: List<LocalBook>) = locals.map { localToPresentation(it) }

  private fun localToPresentation(local: LocalBook) =
    Book(
      id = local.id,
      publishedDate = local.publishedDate.format((DateTimeFormatter.ISO_LOCAL_DATE)),
      publishedDateTime = local.publishedDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
      title = local.title,
      authors = local.authors,
      imageUrl = local.thumbnail,
      url = local.url
    )
}
