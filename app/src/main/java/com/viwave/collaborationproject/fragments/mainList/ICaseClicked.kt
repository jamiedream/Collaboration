package com.viwave.collaborationproject.fragments.mainList

import com.viwave.collaborationproject.data.cases.Case

interface ICaseClicked{
    fun whichCase(case: Case)
}