package it.gabrieletondi.telldontaskkata.domain

enum class OrderStatus {
    APPROVED {
        override fun approve(): OrderStatus {
            return APPROVED
        }

        override fun ship(): OrderStatus {
            return SHIPPED
        }

        override fun reject(): OrderStatus {
            throw ApprovedOrderCannotBeRejectedException()
        }
    },
    REJECTED {
        override fun approve(): OrderStatus {
            throw RejectedOrderCannotBeApprovedException()
        }

        override fun ship(): OrderStatus {
            throw OrderCannotBeShippedException()
        }

        override fun reject(): OrderStatus {
            return  REJECTED
        }
    },
    SHIPPED {
        override fun approve(): OrderStatus {
            throw ShippedOrdersCannotBeChangedException()
        }

        override fun ship(): OrderStatus {
            throw OrderCannotBeShippedTwiceException()
        }

        override fun reject(): OrderStatus {
            throw ShippedOrdersCannotBeChangedException()
        }
    },
    CREATED {
        override fun approve(): OrderStatus {
            return APPROVED
        }

        override fun ship(): OrderStatus {
            throw OrderCannotBeShippedException()
        }

        override fun reject(): OrderStatus {
            return REJECTED
        }
    };

    abstract fun approve():OrderStatus
    abstract fun ship():OrderStatus
    abstract fun reject():OrderStatus

}