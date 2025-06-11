package com.example.guardianshiptracker.data

import kotlinx.coroutines.flow.Flow

class MemberRepository(private val dao: MemberDao) {
    val members: Flow<List<MemberWithPayments>> = dao.getMembers()

    suspend fun addMember(name: String, fee: Double) {
        dao.insertMember(Member(name = name, monthlyFee = fee))
    }

    suspend fun addPayment(memberId: Int, month: Int, year: Int, amount: Double) {
        dao.insertPayment(Payment(memberId = memberId, month = month, year = year, amount = amount))
    }
}
