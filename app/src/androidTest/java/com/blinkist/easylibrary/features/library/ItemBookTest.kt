package com.blinkist.easylibrary.features.library

import android.view.LayoutInflater
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.blinkist.easylibrary.databinding.ItemBookBinding
import com.blinkist.easylibrary.model.newBook
import com.blinkist.easylibrary.model.presentation.Book
import com.blinkist.easylibrary.test.MaterialComponentsRule
import com.blinkist.easylibrary.test.targetContext
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemBookTest {

  @get:Rule var materialComponentsRule = MaterialComponentsRule()

  @Test @UiThreadTest
  fun testBookItem() {
    val book = newBook()

    val binding = inflateAndBind(book)

    assertThat(binding.titleTextView.text).isEqualTo(book.title)
    assertThat(binding.authorsTextView.text).isEqualTo(book.authors)
    assertThat(binding.publishedDateTextView.text).isEqualTo(book.publishedDateText)
  }

  private fun inflateAndBind(book: Book) =
    ItemBookBinding.inflate(LayoutInflater.from(targetContext)).apply {
      item = book
      executePendingBindings()
    }
}
