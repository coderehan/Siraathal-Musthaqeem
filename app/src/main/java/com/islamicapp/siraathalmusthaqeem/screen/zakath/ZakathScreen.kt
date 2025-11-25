package com.islamicapp.siraathalmusthaqeem.screen.zakath

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun ZakathScreen(
    navController: NavController,
    viewModel: ZakathViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CommonActionBar(
                title = "Zakat Calculator",
                navController = navController
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Market Rates Section
            ZakatSectionCard(title = "Today's Market Rates (Per Gram)") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ZakatInputField(
                        value = state.goldRatePerGram,
                        onValueChange = viewModel::updateGoldRate,
                        label = "Gold Rate",
                        modifier = Modifier.weight(1f)
                    )
                    ZakatInputField(
                        value = state.silverRatePerGram,
                        onValueChange = viewModel::updateSilverRate,
                        label = "Silver Rate",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // 2. Assets Section
            ZakatSectionCard(title = "Your Assets") {
                ZakatInputField(
                    value = state.goldWeightGrams,
                    onValueChange = viewModel::updateGoldWeight,
                    label = "Gold Weight (Grams)"
                )
                Spacer(modifier = Modifier.height(8.dp))
                ZakatInputField(
                    value = state.silverWeightGrams,
                    onValueChange = viewModel::updateSilverWeight,
                    label = "Silver Weight (Grams)"
                )
                Spacer(modifier = Modifier.height(8.dp))
                ZakatInputField(
                    value = state.cashInHandBank,
                    onValueChange = viewModel::updateCash,
                    label = "Cash in Hand & Bank"
                )
                Spacer(modifier = Modifier.height(8.dp))
                ZakatInputField(
                    value = state.otherInvestments,
                    onValueChange = viewModel::updateInvestments,
                    label = "Other Investments / Shares"
                )
            }

            // 3. Liabilities Section
            ZakatSectionCard(title = "Liabilities (Deductions)") {
                ZakatInputField(
                    value = state.liabilities,
                    onValueChange = viewModel::updateLiabilities,
                    label = "Debts / Immediate Expenses"
                )
            }

            // Calculate Button
            Button(
                onClick = { viewModel.calculateZakat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green))
            ) {
                Text(text = "Calculate Zakat", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            // 4. Result Section
            if (state.calculated) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (state.isEligible) Color(0xFFDCFCE7) else Color(
                            0xFFF3F4F6
                        )
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (state.isEligible) "You are Eligible to Pay Zakat" else "You are NOT Eligible yet",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (state.isEligible) Color(0xFF166534) else Color(
                                    0xFF4B5563
                                )
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = Color.Black.copy(alpha = 0.1f))
                        Spacer(modifier = Modifier.height(12.dp))

                        ResultRow(label = "Total Net Worth", value = state.totalNetWorth)
                        ResultRow(label = "Nisab Threshold", value = state.nisabThreshold)

                        Spacer(modifier = Modifier.height(16.dp))

                        if (state.isEligible) {
                            Text(
                                text = "Zakat Payable (2.5%)",
                                color = Color(0xFF166534),
                                fontSize = 14.sp
                            )
                            Text(
                                text = String.format("%.2f", state.zakatPayable),
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF15803D)
                                )
                            )
                        }
                    }
                }
            }

            // Add extra space at bottom
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun ZakatSectionCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun ZakatInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { input ->
            // Only allow numeric input (decimal)
            if (input.all { it.isDigit() || it == '.' }) {
                onValueChange(input)
            }
        },
        label = { Text(label, fontSize = 14.sp) },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.green),
            unfocusedBorderColor = colorResource(id = R.color.green),
            focusedLabelColor = colorResource(id = R.color.green),
            cursorColor = colorResource(id = R.color.green)
        )
    )
}

@Composable
fun ResultRow(label: String, value: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color(0xFF6B7280), fontSize = 14.sp)
        Text(
            text = String.format("%.2f", value),
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1F2937)
        )
    }
}