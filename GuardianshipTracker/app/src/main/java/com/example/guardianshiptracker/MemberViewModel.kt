package com.example.guardianshiptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.guardianshiptracker.data.AppDatabase
import com.example.guardianshiptracker.data.MemberRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MemberViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: MemberRepository =
        MemberRepository(AppDatabase.getDatabase(application).memberDao())

    val members = repo.members.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )


    fun addMember(name: String, fee: Double) {
        viewModelScope.launch { repo.addMember(name, fee) }
    }

    fun addPayment(memberId: Int, month: Int, year: Int, amount: Double) {
        viewModelScope.launch { repo.addPayment(memberId, month, year, amount) }
    }
}

class MemberViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
