package org.shop.prodyktu

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform