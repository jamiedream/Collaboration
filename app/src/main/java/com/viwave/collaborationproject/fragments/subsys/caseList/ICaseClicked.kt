package com.viwave.collaborationproject.fragments.subsys.caseList

import com.viwave.collaborationproject.DB.remote.entity.CaseEntity

interface ICaseClicked{
    fun whichCase(case: CaseEntity)
}