package com.viwave.collaborationproject.fragments.subsys.caseList

import com.viwave.collaborationproject.data.cases.Case

interface ICaseClicked{
    fun whichCase(case: Case)
}