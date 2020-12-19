package it.gabrieletondi.telldontaskkata.useCase

import it.gabrieletondi.telldontaskkata.repository.OrderRepository
import it.gabrieletondi.telldontaskkata.service.ShipmentService

class OrderShipmentUseCase(private val orderRepository: OrderRepository, private val shipmentService: ShipmentService) {
    fun run(request: OrderShipmentRequest) {
        val order = orderRepository.getById(request.orderId)
        if (order!!.created() || order.rejected()) {
            throw OrderCannotBeShippedException()
        }

        shipmentService.ship(order)
        order.ship()
        orderRepository.save(order)
    }
}