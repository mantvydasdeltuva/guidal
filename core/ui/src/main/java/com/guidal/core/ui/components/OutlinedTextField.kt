package com.guidal.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guidal.core.ui.theme.GuidalTheme

// TODO KDoc when finalized
// TODO Android test
@Composable
fun OutlinedTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isError: Boolean = false,
    placeholder: String = "",
    supportingText: String = "",
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    androidx.compose.material3.OutlinedTextField(

        // Required
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
            )
        },
        value = value,
        onValueChange = { onValueChange(it) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,

        // Optional
        modifier = modifier
            .fillMaxWidth(),
        enabled = !isLoading,
        isError = isError,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        supportingText = {
            Text(
                text = supportingText,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 10.dp)
            )
        },
        singleLine = singleLine,
        visualTransformation = visualTransformation,

        // Constant
        textStyle = MaterialTheme.typography.bodyLarge,
        shape = MaterialTheme.shapes.extraSmall,
        colors = OutlinedTextFieldDefaults.colors(
            // Container color
            disabledContainerColor = MaterialTheme.colorScheme.surfaceBright,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
            errorContainerColor = MaterialTheme.colorScheme.surfaceBright,

            // Border color
            disabledBorderColor = MaterialTheme.colorScheme.surfaceDim,
            unfocusedBorderColor = MaterialTheme.colorScheme.surfaceDim,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            errorBorderColor = MaterialTheme.colorScheme.error,

            // Label color
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            errorLabelColor = MaterialTheme.colorScheme.error,

            // Placeholder color
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            errorPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,

            // Text color
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            errorTextColor = MaterialTheme.colorScheme.error,

            // Support text color
            disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            errorSupportingTextColor = MaterialTheme.colorScheme.error
        )
    )
}

@Preview(showBackground = false)
@Composable
private fun PreviewOutlinedTextField() {
    var name by remember { mutableStateOf("") }

    GuidalTheme {
        OutlinedTextField(
            label = "Name",
            value = name,
            onValueChange = { name = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { }
            )
        )
    }
}