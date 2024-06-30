package com.example.hospitalapp

data class QuestionnaireData(
    var operationNo: String? = null,
    var betPat: String? = null,
    var befAsept: String? = null,
    var aftBF: String? = null,
    var aftPat: String? = null,
    var aftPSurr: String? = null,
    var gloves:String? = null
) {
    constructor() : this(null, null, null, null, null, null,null)
}
