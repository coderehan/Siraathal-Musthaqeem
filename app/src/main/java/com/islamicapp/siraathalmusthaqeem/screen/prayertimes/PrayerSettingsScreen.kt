package com.islamicapp.siraathalmusthaqeem.screen.prayertimes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.utils.AsrSchoolMapper
import com.islamicapp.siraathalmusthaqeem.utils.LocationHelper
import com.islamicapp.siraathalmusthaqeem.utils.PreferencesManager
import com.islamicapp.siraathalmusthaqeem.utils.PrayerMethodMapper
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayerSettingsScreen(
    navController: NavController,
    currentMethod: Int,
    currentSchool: Int,
    latitude: Double,
    longitude: Double
) {
    val preferencesManager: PreferencesManager = koinInject()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var selectedMethod by remember { mutableIntStateOf(currentMethod) }
    var selectedSchool by remember { mutableIntStateOf(currentSchool) }
    var showMethodDropdown by remember { mutableStateOf(false) }
    var showSchoolDropdown by remember { mutableStateOf(false) }

    val locationName = remember {
        LocationHelper.getLocationName(context, latitude, longitude)
    }

    val timezone = remember { TimeZone.getDefault().id }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Prayer Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.green),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)  // ← Changed to white
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Globe Icon
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color(0xFFF0F0F0), CircleShape),  // ← Light gray circle
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = colorResource(id = R.color.green),
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Location Section
            Text(
                text = "Your Location",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black  // ← Changed to black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = locationName,
                fontSize = 14.sp,
                color = Color(0xFF6B7280)  // ← Gray text
            )

            Text(
                text = "Time Zone: $timezone",
                fontSize = 14.sp,
                color = Color(0xFF6B7280)  // ← Gray text
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Prayer Time Calculation Section
            Text(
                text = "Prayer Time Calculation",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,  // ← Changed to black
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Select your preferred calculation method or let the app find it for accurate prayer times in your area.",
                fontSize = 14.sp,
                color = Color(0xFF6B7280),  // ← Gray text
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Calculation Method Dropdown
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showMethodDropdown = !showMethodDropdown }
                    .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(12.dp)),  // ← Border
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)  // ← White background
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Calculation Method",
                            fontSize = 14.sp,
                            color = Color(0xFF6B7280)  // ← Gray text
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = PrayerMethodMapper.getMethodName(selectedMethod),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black  // ← Black text
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expand",
                        tint = Color.Black  // ← Black icon
                    )
                }
            }

            // Method Dropdown Menu
            if (showMethodDropdown) {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        PrayerMethodMapper.getAllMethods().forEach { (id, name) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedMethod = id
                                        showMethodDropdown = false
                                    }
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = name,
                                    fontSize = 16.sp,
                                    color = if (selectedMethod == id) colorResource(id = R.color.green) else Color.Black
                                )
                                if (selectedMethod == id) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        tint = colorResource(id = R.color.green),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            if (id != PrayerMethodMapper.getAllMethods().last().first) {
                                HorizontalDivider(
                                    color = Color(0xFFE5E7EB),
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Asr Calculation Dropdown
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showSchoolDropdown = !showSchoolDropdown }
                    .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Asr Calculation",
                            fontSize = 14.sp,
                            color = Color(0xFF6B7280)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = AsrSchoolMapper.getSchoolName(selectedSchool),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expand",
                        tint = Color.Black
                    )
                }
            }

            // School Dropdown Menu
            if (showSchoolDropdown) {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        listOf(
                            AsrSchoolMapper.HANAFI to "Hanafi",
                            AsrSchoolMapper.SHAFI_STANDARD to "Shafi"
                        ).forEach { (id, name) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedSchool = id
                                        showSchoolDropdown = false
                                    }
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = name,
                                    fontSize = 16.sp,
                                    color = if (selectedSchool == id) colorResource(id = R.color.green) else Color.Black
                                )
                                if (selectedSchool == id) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        tint = colorResource(id = R.color.green),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            if (id != AsrSchoolMapper.SHAFI_STANDARD) {
                                HorizontalDivider(
                                    color = Color(0xFFE5E7EB),
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Save Button
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        preferencesManager.saveCalculationMethod(selectedMethod)
                        preferencesManager.saveAsrSchool(selectedSchool)

                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("updated_method", selectedMethod)
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("updated_school", selectedSchool)
                        navController.popBackStack()
                    }
                },
                containerColor = colorResource(id = R.color.green),
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}