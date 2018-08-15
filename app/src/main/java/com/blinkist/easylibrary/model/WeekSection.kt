package com.blinkist.easylibrary.model

import com.blinkist.easylibrary.library.Librariable
import java.util.*

data class WeekSection(
    val initialDate: Date,
    val finalDate: Date
) : Librariable
