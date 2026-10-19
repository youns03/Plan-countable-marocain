package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BalanceType
import com.example.ui.theme.CreditGreen
import com.example.ui.theme.DebitBlue
import com.example.ui.theme.MonoFontFamily

@Composable
fun TAccountDiagram(
    accountCode: String,
    accountTitleFr: String,
    normalBalance: BalanceType,
    modifier: Modifier = Modifier
) {
    val isDebitNormal = normalBalance == BalanceType.DEBIT
    val isCreditNormal = normalBalance == BalanceType.CREDIT

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        // T-Account Title Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$accountCode : $accountTitleFr",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        Spacer(modifier = Modifier.height(6.dp))

        // Split T Column: Left = Débit, Right = Crédit
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(86.dp)
        ) {
            // Left Column: Débit
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp))
                    .background(if (isDebitNormal) DebitBlue.copy(alpha = 0.12f) else Color.Transparent)
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "مدين • Débit (+)",
                        fontFamily = MonoFontFamily,
                        fontSize = 12.sp,
                        fontWeight = if (isDebitNormal) FontWeight.Bold else FontWeight.Medium,
                        color = if (isDebitNormal) DebitBlue else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    if (isDebitNormal) {
                        Text(
                            text = "الرصيد الطبيعي\nSolde Débiteur",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = DebitBlue,
                            textAlign = TextAlign.Center,
                            lineHeight = 15.sp
                        )
                    } else {
                        Text(
                            text = "نقصان (-)",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            // Central vertical line (The stem of T)
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(86.dp)
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            )

            // Right Column: Crédit
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp))
                    .background(if (isCreditNormal) CreditGreen.copy(alpha = 0.12f) else Color.Transparent)
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "دائن • Crédit (+)",
                        fontFamily = MonoFontFamily,
                        fontSize = 12.sp,
                        fontWeight = if (isCreditNormal) FontWeight.Bold else FontWeight.Medium,
                        color = if (isCreditNormal) CreditGreen else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    if (isCreditNormal) {
                        Text(
                            text = "الرصيد الطبيعي\nSolde Créditeur",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = CreditGreen,
                            textAlign = TextAlign.Center,
                            lineHeight = 15.sp
                        )
                    } else {
                        Text(
                            text = "نقصان (-)",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}
