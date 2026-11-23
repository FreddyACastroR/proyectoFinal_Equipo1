package mx.itson.hogarea.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.unit.sp

private val base = Typography()

val Typography = Typography(
    bodyLarge = base.bodyLarge.copy(fontSize = 18.sp, lineHeight = 26.sp),
    bodyMedium = base.bodyMedium.copy(fontSize = 16.sp, lineHeight = 24.sp),
    bodySmall = base.bodySmall.copy(fontSize = 14.sp, lineHeight = 20.sp),
    titleMedium = base.titleMedium.copy(fontSize = 18.sp, lineHeight = 26.sp),
    titleSmall = base.titleSmall.copy(fontSize = 16.sp, lineHeight = 24.sp),
    labelLarge = base.labelLarge.copy(fontSize = 15.sp, lineHeight = 20.sp),
    labelMedium = base.labelMedium.copy(fontSize = 13.sp, lineHeight = 18.sp),
    labelSmall = base.labelSmall.copy(fontSize = 12.sp, lineHeight = 16.sp),
)