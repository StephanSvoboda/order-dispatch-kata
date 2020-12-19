package it.gabrieletondi.telldontaskkata.domain

import java.math.BigDecimal
import java.util.ArrayList

class Order {

    private constructor()

    var total: BigDecimal? = null
    var currency: String? = null
    var items: List<OrderItem>? = null
    var tax: BigDecimal? = null
    var status: OrderStatus? = null
        private set
    var id = 0

    companion object {
        fun createEmptyOrder() : Order {
            val order = Order()
            order.status = OrderStatus.CREATED
            order.items = ArrayList()
            order.currency = "EUR"
            order.total = BigDecimal("0.00")
            order.tax = BigDecimal("0.00")
            return order
        }
    }

    fun approve() {
        status = status!!.approve()
    }

    fun reject() {
        status = status!!.reject()
    }

    fun ship() {
        status = status!!.ship()
    }


}