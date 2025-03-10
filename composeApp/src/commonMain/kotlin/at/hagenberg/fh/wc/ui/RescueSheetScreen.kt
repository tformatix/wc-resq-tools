package at.hagenberg.fh.wc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.hagenberg.fh.wc.viewmodel.RescueSheetViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun RescueSheetScreen(
    rescueSheetViewModel: RescueSheetViewModel = getViewModel(key = "rescueSheet",
        factory = viewModelFactory { RescueSheetViewModel() })
) {
    var authority by remember { mutableStateOf("FW") }
    var number by remember { mutableStateOf("KFZ1") }

    val isLoading by rescueSheetViewModel.isLoading.collectAsState()
    val errorMessage by rescueSheetViewModel.errorMessage.collectAsState()
    val feuerwehrAppCar by rescueSheetViewModel.feuerwehrAppCar.collectAsState()
    val euroRescueCars by rescueSheetViewModel.euroRescueCars.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            TextField(
                modifier = Modifier
                    .weight(0.9f)
                    .fillMaxHeight()
                    .padding(start = 8.dp),
                value = authority,
                onValueChange = {
                    authority = it
                },
                label = { Text("Behörde") },
                placeholder = { Text("FW") }
            )
            TextField(
                modifier = Modifier
                    .weight(1.6f)
                    .fillMaxHeight()
                    .padding(start = 8.dp),
                value = number,
                onValueChange = {
                    number = it
                },
                label = { Text("Vormerkzeichen") },
                placeholder = { Text("KFZ1") }
            )
            Button(
                modifier = Modifier
                    .weight(0.5f)
                    .size(48.dp)
                    .padding(start = 8.dp),
                shape = CircleShape,
                enabled = !isLoading,
                onClick = {
                    rescueSheetViewModel.findRescueSheet(authority, number)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White,
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (isLoading)
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        if (errorMessage != null)
            Text(
                errorMessage!!,
                modifier = Modifier.padding(16.dp),
                color = Color.Red
            )
        feuerwehrAppCar?.let {
            Divider(Modifier.padding(vertical = 16.dp))
            Text(
                "Information der Kennzeichenabfrage",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            FeuerwehrAppCarWidget(it)
        }
        euroRescueCars?.let {
            Divider(Modifier.padding(vertical = 16.dp))
            Text(
                "Verfügbare Rettungskarten",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            it.map { car ->
                Spacer(modifier = Modifier.height(16.dp))
                EuroRescueCarWidget(car)
            }
        }
    }
}