package it.gabrieletondi.telldontaskkata.domain

import it.gabrieletondi.telldontaskkata.useCase.RejectedOrderCannotBeApprovedException

enum class OrderStatus {
    APPROVED {
        override fun approve(): OrderStatus {
            TODO("Not yet implemented")
        }
    },
    REJECTED {
        override fun approve(): OrderStatus {
            throw RejectedOrderCannotBeApprovedException()
        }
    },
    SHIPPED {
        override fun approve(): OrderStatus {
            TODO("Not yet implemented")
        }
    },
    CREATED {
        override fun approve(): OrderStatus {
            return APPROVED
        }
    };

    abstract  fun approve():OrderStatus
}