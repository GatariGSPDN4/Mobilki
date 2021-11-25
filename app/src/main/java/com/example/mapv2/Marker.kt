package com.example.mapv2

import java.util.*


data class Marker(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var rate: Double = 0.0,
    var name: String,
    var city: String,
    var street: String,
    var description: String,
) {}