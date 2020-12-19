package it.gabrieletondi.telldontaskkata.useCase

import it.gabrieletondi.telldontaskkata.domain.Order
import it.gabrieletondi.telldontaskkata.doubles.TestOrderRepository
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.nullValue
import org.junit.Assert.assertThat
import org.junit.Test

class OrderApprovalUseCaseTest {
    private val orderRepository: TestOrderRepository = TestOrderRepository()
    private val useCase = OrderApprovalUseCase(orderRepository)

    @Test
    @Throws(Exception::class)
    fun approvedExistingOrder() {
        val initialOrder = Order.createEmptyOrder()
        initialOrder.id = 1
        orderRepository.addOrder(initialOrder)
        val request = OrderApprovalRequest()
        request.orderId = 1
        request.isApproved = true
        useCase.run(request)
        val savedOrder: Order = orderRepository.savedOrder!!
        assertThat(savedOrder.approved(), `is`(true))
    }

    @Test
    @Throws(Exception::class)
    fun rejectedExistingOrder() {
        val initialOrder = Order.createEmptyOrder()
        initialOrder.id = 1
        orderRepository.addOrder(initialOrder)
        val request = OrderApprovalRequest()
        request.orderId = 1
        request.isApproved = false
        useCase.run(request)
        val savedOrder: Order = orderRepository.savedOrder!!
        assertThat(savedOrder.rejected(), `is`(true))
    }

    @Test(expected = RejectedOrderCannotBeApprovedException::class)
    @Throws(Exception::class)
    fun cannotApproveRejectedOrder() {
        val initialOrder = Order.createEmptyOrder()
        initialOrder.reject()
        initialOrder.id = 1
        orderRepository.addOrder(initialOrder)
        val request = OrderApprovalRequest()
        request.orderId = 1
        request.isApproved = true
        useCase.run(request)
        assertThat(orderRepository.savedOrder, `is`(nullValue()))
    }

    @Test(expected = ApprovedOrderCannotBeRejectedException::class)
    @Throws(Exception::class)
    fun cannotRejectApprovedOrder() {
        val initialOrder = Order.createEmptyOrder()
        initialOrder.approve()
        initialOrder.id = 1
        orderRepository.addOrder(initialOrder)
        val request = OrderApprovalRequest()
        request.orderId = 1
        request.isApproved = false
        useCase.run(request)
        assertThat(orderRepository.savedOrder, `is`(nullValue()))
    }

    @Test(expected = ShippedOrdersCannotBeChangedException::class)
    @Throws(Exception::class)
    fun shippedOrdersCannotBeApproved() {
        val initialOrder = Order.createEmptyOrder()
        initialOrder.ship()
        initialOrder.id = 1
        orderRepository.addOrder(initialOrder)
        val request = OrderApprovalRequest()
        request.orderId = 1
        request.isApproved = true
        useCase.run(request)
        assertThat(orderRepository.savedOrder, `is`(nullValue()))
    }

    @Test(expected = ShippedOrdersCannotBeChangedException::class)
    @Throws(Exception::class)
    fun shippedOrdersCannotBeRejected() {
        val initialOrder = Order.createEmptyOrder()
        initialOrder.ship()
        initialOrder.id = 1
        orderRepository.addOrder(initialOrder)
        val request = OrderApprovalRequest()
        request.orderId = 1
        request.isApproved = false
        useCase.run(request)
        assertThat(orderRepository.savedOrder, `is`(nullValue()))
    }
}