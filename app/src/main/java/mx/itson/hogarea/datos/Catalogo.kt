package mx.itson.hogarea.datos

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Balcony
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Cabin
import androidx.compose.material.icons.filled.Chair
import androidx.compose.material.icons.filled.Chalet
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Cottage
import androidx.compose.material.icons.filled.Desk
import androidx.compose.material.icons.filled.Dining
import androidx.compose.material.icons.filled.Garage
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.HolidayVillage
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Shower
import androidx.compose.material.icons.filled.Villa
import androidx.compose.material.icons.filled.Weekend
import androidx.compose.material.icons.filled.Yard
import androidx.compose.ui.graphics.vector.ImageVector

val nombresDia = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")

fun textoDias(dias: Set<Int>): String {
    val nombres = dias.sorted().map { nombresDia[it].lowercase() }
    val texto = if (nombres.size <= 1) nombres.joinToString() else nombres.dropLast(1).joinToString(", ") + " y " + nombres.last()
    return texto.replaceFirstChar { it.uppercase() }
}

val rolesFamiliares = listOf("Mamá", "Papá", "Hijo o hija", "Roomie", "Otro")

val iconosHabitacion: List<ImageVector> = listOf(
    Icons.Filled.Kitchen,
    Icons.Filled.Weekend,
    Icons.Filled.Bathtub,
    Icons.Filled.Bed,
    Icons.Filled.Grass,
    Icons.Filled.LocalLaundryService,
    Icons.Filled.Garage,
    Icons.Filled.Dining,
    Icons.Filled.Yard,
    Icons.Filled.Balcony,
    Icons.Filled.Shower,
    Icons.Filled.Chair,
    Icons.Filled.Desk,
    Icons.Filled.Pets,
    Icons.Filled.MeetingRoom,
    Icons.Filled.CleaningServices,
)

val iconosHogar: List<ImageVector> = listOf(
    Icons.Filled.Home,
    Icons.Filled.House,
    Icons.Filled.Apartment,
    Icons.Filled.Cottage,
    Icons.Filled.Villa,
    Icons.Filled.Cabin,
    Icons.Filled.Chalet,
    Icons.Filled.HolidayVillage,
)