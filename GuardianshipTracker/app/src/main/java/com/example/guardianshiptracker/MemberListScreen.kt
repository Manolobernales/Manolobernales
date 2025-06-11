package com.example.guardianshiptracker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.guardianshiptracker.data.MemberWithPayments

@Composable
fun MemberListScreen(viewModel: MemberViewModel) {
    val members by viewModel.members.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        AddMemberForm(onAdd = { name, fee -> viewModel.addMember(name, fee) })
        LazyColumn {
            items(members) { memberWithPayments ->
                MemberItem(memberWithPayments)
            }
        }
    }
}

@Composable
fun AddMemberForm(onAdd: (String, Double) -> Unit) {
    var name by remember { mutableStateOf("") }
    var fee by remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = fee,
            onValueChange = { fee = it },
            label = { Text("Cuota") },
            modifier = Modifier.weight(1f)
        )
        Button(onClick = {
            val feeDouble = fee.toDoubleOrNull() ?: 0.0
            onAdd(name, feeDouble)
            name = ""
            fee = ""
        }) {
            Text("Agregar")
        }
    }
}

@Composable
fun MemberItem(memberWithPayments: MemberWithPayments) {
    Card(modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth()) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = memberWithPayments.member.name)
            Text(text = "Cuota: ${memberWithPayments.member.monthlyFee}")
            val months = memberWithPayments.payments.joinToString { "${it.month}/${it.year}" }
            Text(text = "Pagos: $months")
        }
    }
}
