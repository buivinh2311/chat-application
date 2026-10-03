package com.example.core_utils

import androidx.compose.foundation.lazy.LazyListState

val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 ||
            firstVisibleItemScrollOffset > 0

val LazyListState.isAtBottom: Boolean
    get() {
        val layoutInfo = layoutInfo
        val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
        val totalItems = layoutInfo.totalItemsCount

        return totalItems > 0 &&
                lastVisibleItem?.index == totalItems - 1 &&
                lastVisibleItem.offset + lastVisibleItem.size <=
                layoutInfo.viewportEndOffset
    }