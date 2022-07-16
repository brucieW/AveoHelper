package com.aveo.presentation.screens.residents

import com.aveo.domain.repository.ResidentRepository

class ResidentsViewModel(
    private val residentRepository: ResidentRepository
) {
    val residents1 = residentRepository.getResidentsBelowUnit(70L)
    val residents2 = residentRepository.getResidentsAboveUnit(69L)
}