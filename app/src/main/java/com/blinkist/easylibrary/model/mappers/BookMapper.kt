package com.blinkist.easylibrary.model.mappers

import com.blinkist.easylibrary.model.local.LocalBook
import com.blinkist.easylibrary.model.presentation.Book
import com.blinkist.easylibrary.model.remote.RemoteBook
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class BookMapper @Inject constructor() {

  fun remoteToLocal(remotes: List<RemoteBook>) = remotes.map { remoteToLocal(it) }

  private fun remoteToLocal(remote: RemoteBook) =
    LocalBook(
      id = remote.id,
      publishedDate = remote.publishedDate,
      title = remote.title,
      authors = remote.authors,
      thumbnail = remote.thumbnail,
      url = remote.url
    )

  fun localToPresentation(locals: List<LocalBook>) = locals.map { localToPresentation(it) }

  private fun localToPresentation(local: LocalBook) =
    Book(
      id = local.id,
      publishedDate = local.publishedDate,
      publishedDateText = local.publishedDate.format((DateTimeFormatter.ISO_LOCAL_DATE)),
      title = local.title,
      authors = local.authors,
      imageUrl = local.thumbnail,
      url = local.url
    )
}
