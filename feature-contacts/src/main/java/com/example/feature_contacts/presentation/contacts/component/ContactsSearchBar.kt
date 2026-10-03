package com.example.feature_contacts.presentation.contacts.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R

@Composable
fun ContactsSearchBar(

) {
    var keyword by remember {
        mutableStateOf("")
    }

    val focusRequester = remember { FocusRequester() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.Space.Md)
            .height(AppDimens.Layout.SearchBarHeight)
            .clip(RoundedCornerShape(AppDimens.Radius.Lg))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(horizontal = AppDimens.Space.Lg),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = AppIcons.Search,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.size(AppDimens.Icon.Md)
        )
        Spacer(modifier = Modifier.width(AppDimens.Space.Md))

        BasicTextField(
            value = keyword,
            onValueChange = {
                keyword = it
//                onSearch(keyword)
            },
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            cursorBrush = SolidColor(
                MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .weight(1f)
                .height(AppDimens.Space.Xl)
                .focusRequester(focusRequester),
            decorationBox = { innerTextField ->
                Box {
                    if (keyword.isEmpty()) {
                        Text(
                            text = stringResource(R.string.hint_search_contacts),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}