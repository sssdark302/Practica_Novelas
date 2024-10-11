
package com.example.libreria_pde

data class Novel(
    var title: String = "",
    var author: String = "",
    var year: Int = 0,
    var favorite: Boolean = false,
    var id: Int = 0
) {
    constructor() : this("", "", 0, false, 0)
}
