package it.gabrieletondi.telldontaskkata.useCase

import it.gabrieletondi.telldontaskkata.repository.OrderRepository

class OrderApprovalUseCase(private val orderRepository: OrderRepository) {
    fun run(request: OrderApprovalRequest) {
        val order = orderRepository.getById(request.orderId)

        if (request.isApproved) order!!.approve() else order!!.reject()
        orderRepository.save(order)
    }

}

