package it.gabrieletondi.telldontaskkata.useCase

import it.gabrieletondi.telldontaskkata.domain.Order
import it.gabrieletondi.telldontaskkata.domain.OrderStatus
import it.gabrieletondi.telldontaskkata.doubles.TestOrderRepository
import it.gabrieletondi.telldontaskkata.doubles.TestShipmentService
import org.hamcrest.Matchers
import org.junit.Assert.assertThat
import org.junit.Test
import kotlin.test.assertNull

class OrderShipmentUseCaseTest {
    private val orderRepository: TestOrderRepository = TestOrderRepository()
    private val shipmentService: TestShipmentService = TestShipmentService()
    private val useCase = OrderShipmentUseCase(orderRepository, shipmentService)
    @Test
    @Throws(Exception::class)
    fun shipApprovedOrder() {
        val initialOrder = Order.createEmptyOrder()
        initialOrder.id = 1
        initialOrder.approve()
        orderRepository.addOrder(initialOrder)
        val request = OrderShipmentRequest()
        request.orderId = 1
        useCase.run(request)
        assertThat(orderRepository.savedOrder?.status, Matchers.`is`(OrderStatus.SHIPPED))
        assertThat(shipmentService.shippedOrder, Matchers.`is`(initialOrder))
    }

    @Test(expected = OrderCannotBeShippedException::class)
    @Throws(Exception::class)
    fun createdOrdersCannotBeShipped() {
        val initialOrder = Order.createEmptyOrder()
        initialOrder.id = 1
        orderRepository.addOrder(initialOrder)
        val request = OrderShipmentRequest()
        request.orderId = 1
        useCase.run(request)
        assertNull(orderRepository.savedOrder)
        assertNull(shipmentService.shippedOrder)
    }

    @Test(expected = OrderCannotBeShippedException::class)
    @Throws(Exception::class)
    fun rejectedOrdersCannotBeShipped() {
        val initialOrder = Order.createEmptyOrder()
        initialOrder.id = 1
        initialOrder.reject()
        orderRepository.addOrder(initialOrder)
        val request = OrderShipmentRequest()
        request.orderId = 1
        useCase.run(request)
        assertNull(orderRepository.savedOrder)
        assertNull(shipmentService.shippedOrder)
    }

    @Test(expected = OrderCannotBeShippedTwiceException::class)
    @Throws(Exception::class)
    fun shippedOrdersCannotBeShippedAgain() {
        val initialOrder = Order.createEmptyOrder()
        initialOrder.id = 1
        initialOrder.approve()
        initialOrder.ship()
        orderRepository.addOrder(initialOrder)
        val request = OrderShipmentRequest()
        request.orderId = 1
        useCase.run(request)
        assertNull(orderRepository.savedOrder)
        assertNull(shipmentService.shippedOrder)
    }
}