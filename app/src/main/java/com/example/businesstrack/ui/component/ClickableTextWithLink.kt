package com.example.businesstrack.ui.component

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration



@Composable
fun ClickableTextWithLink(
    modifier: Modifier = Modifier,
    fullText: String,
    linkText: String,
    onClick: () -> Unit
) {
    val annotatedString = buildAnnotatedString {
        val start = fullText.indexOf(linkText)
        val end = start + linkText.length

        append(fullText)

        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            ),
            start = start,
            end = end
        )

        addStringAnnotation(
            tag = "LOGIN",
            annotation = "login",
            start = start,
            end = end
        )
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "LOGIN", start = offset, end = offset)
                .firstOrNull()?.let {
                    onClick()
                }
        },
        modifier = modifier
    )
}