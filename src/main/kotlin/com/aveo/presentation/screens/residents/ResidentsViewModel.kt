package com.aveo.presentation.screens.residents

import androidx.compose.runtime.*
import com.aveo.domain.repository.ResidentRepository

class ResidentsViewModel(
    private val residentRepository: ResidentRepository
) {
    val radioOptions = listOf("Unit Order", "Name Order")

    var selectedOrderValue = mutableStateOf(radioOptions[0])

    var residents1 = residentRepository.getResidentsBelowUnit(70L)
    var residents2 = residentRepository.getResidentsAboveUnit(69L)

    var residents3 = residentRepository.getResidentsByLastName()

    fun onOrderChange(order: String) {
        selectedOrderValue.value = order
    }
}