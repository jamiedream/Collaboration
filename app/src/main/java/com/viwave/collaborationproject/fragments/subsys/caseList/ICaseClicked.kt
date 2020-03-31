/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.fragments.subsys.caseList

import com.viwave.collaborationproject.DB.remote.entity.CaseEntity

interface ICaseClicked{
    fun whichCase(case: CaseEntity)
}