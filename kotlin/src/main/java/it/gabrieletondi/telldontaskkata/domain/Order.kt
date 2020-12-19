package it.gabrieletondi.telldontaskkata.domain

import it.gabrieletondi.telldontaskkata.useCase.ApprovedOrderCannotBeRejectedException
import it.gabrieletondi.telldontaskkata.useCase.RejectedOrderCannotBeApprovedException
import java.math.BigDecimal
import java.util.ArrayList

class Order {

    var total: BigDecimal? = null
    var currency: String? = null
    var items: List<OrderItem>? = null
    var tax: BigDecimal? = null
    var status: OrderStatus? = null
    var id = 0

    fun shipped() = status == OrderStatus.SHIPPED
    fun rejected() = status == OrderStatus.REJECTED
    fun approved() = status == OrderStatus.APPROVED
    fun approve() {
        if (rejected()) {
            throw RejectedOrderCannotBeApprovedException()
        }
        status = OrderStatus.APPROVED
    }

    fun reject() {
        if (approved()) {
            throw ApprovedOrderCannotBeRejectedException()
        }
        status = OrderStatus.REJECTED
    }


}

fun createEmpty(): Order {
    val order = Order()
    order.status = OrderStatus.CREATED
    order.items = ArrayList()
    order.currency = "EUR"
    order.total = BigDecimal("0.00")
    order.tax = BigDecimal("0.00")
    return order
}