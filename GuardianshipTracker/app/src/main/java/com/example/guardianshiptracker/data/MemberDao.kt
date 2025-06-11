package com.example.guardianshiptracker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {
    @Query("SELECT * FROM Member")
    fun getMembers(): Flow<List<MemberWithPayments>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: Member): Long

    @Insert
    suspend fun insertPayment(payment: Payment)

    @Transaction
    @Query("SELECT * FROM Member WHERE id = :memberId")
    fun getMemberWithPayments(memberId: Int): Flow<MemberWithPayments>
}

data class MemberWithPayments(
    @Embedded val member: Member,
    @Relation(parentColumn = "id", entityColumn = "memberId")
    val payments: List<Payment>
)
