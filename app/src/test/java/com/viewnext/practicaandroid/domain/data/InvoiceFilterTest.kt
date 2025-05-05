package com.viewnext.practicaandroid.domain.data

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class InvoiceFilterTest {

    @Nested
    inner class IsEmpty {

        @Test
        fun isEmpty_whenAllFieldsAreDefault_returnsTrue() {
            val invoiceFilter = InvoiceFilter()

            assertTrue(invoiceFilter.isEmpty())
        }

        @Test
        fun isEmpty_whenAllFieldsAreNotDefault_returnsFalse() {
            val invoiceFilter = InvoiceFilter(
                startDate = "2023-10-01",
                endDate = "2023-10-31",
                maxAmount = 100f,
                minAmount = 50f,
                isPaid = true,
                isCancelled = true,
                isFixedFee = true,
                isPending = true,
                isPaymentPlan = true
            )

            assertFalse(invoiceFilter.isEmpty())
        }

        @Test
        fun isEmpty_whenOnlyStartDateIsNotDefault_returnsFalse() {
            val invoiceFilter = InvoiceFilter(startDate = "2023-10-01")

            assertFalse(invoiceFilter.isEmpty())
        }

        @Test
        fun isEmpty_whenOnlyEndDateIsNotDefault_returnsFalse() {
            val invoiceFilter = InvoiceFilter(endDate = "2023-10-31")

            assertFalse(invoiceFilter.isEmpty())
        }

        @Test
        fun isEmpty_whenMinAmountIsNotDefault_returnsFalse() {
            val invoiceFilter = InvoiceFilter(minAmount = 10f)

            assertFalse(invoiceFilter.isEmpty())
        }


        @Test
        fun isEmpty_whenMaxAmountIsNotDefault_returnsFalse() {
            val invoiceFilter = InvoiceFilter(maxAmount = 200f)

            assertFalse(invoiceFilter.isEmpty())
        }

        @Test
        fun isEmpty_whenMinAmountAndMaxAmountAreNotDefault_returnsFalse() {
            val invoiceFilter = InvoiceFilter(minAmount = 10f, maxAmount = 200f)

            assertFalse(invoiceFilter.isEmpty())
        }

        @Test
        fun isEmpty_whenIsPaidIsNotDefault_returnsFalse() {
            val invoiceFilter = InvoiceFilter(isPaid = true)

            assertFalse(invoiceFilter.isEmpty())
        }

        @Test
        fun isEmpty_whenIsCancelledIsNotDefault_returnsFalse() {
            val invoiceFilter = InvoiceFilter(isCancelled = true)

            assertFalse(invoiceFilter.isEmpty())
        }

        @Test
        fun isEmpty_whenOnlyIsFixedFeeIsNotDefault_returnsFalse() {
            val filter = InvoiceFilter(isFixedFee = true)
            assertFalse(filter.isEmpty())
        }

        @Test
        fun isEmpty_whenOnlyIsPendingIsNotDefault_returnsFalse() {
            val filter = InvoiceFilter(isPending = true)
            assertFalse(filter.isEmpty())
        }

        @Test
        fun isEmpty_whenOnlyIsPaymentPlanIsNotDefault_returnsFalse() {
            val filter = InvoiceFilter(isPaymentPlan = true)
            assertFalse(filter.isEmpty())
        }
    }

    @Nested
    inner class IsDefaultStartDate{
        @Test
        fun isDefaultStartDate_whenStartDateIsDefault_returnsTrue() {
            val invoiceFilter = InvoiceFilter(
                startDate = DEFAULT_START_DATE,
                endDate = "2023-10-31",
                maxAmount = 100f,
                minAmount = 50f,
                isPaid = true,
                isCancelled = true,
                isFixedFee = true,
                isPending = true,
                isPaymentPlan = true
            )

            assertTrue(invoiceFilter.isDefaultStartDate())
        }

        @Test
        fun isDefaultStartDate_whenStartDateIsNotDefault_returnsFalse() {
            val invoiceFilter = InvoiceFilter(
                startDate = "2023-10-01",
                endDate = "2023-10-31",
                maxAmount = 100f,
                minAmount = 50f,
                isPaid = true,
                isCancelled = true,
                isFixedFee = true,
                isPending = true,
                isPaymentPlan = true
            )

            assertFalse(invoiceFilter.isDefaultStartDate())
        }
    }

    @Nested
    inner class IsDefaultEndDate{
        @Test
        fun isDefaultEndDate_whenEndDateIsDefault_returnsTrue() {
            val invoiceFilter = InvoiceFilter(
                startDate = "2023-10-01",
                endDate = DEFAULT_END_DATE,
                maxAmount = 100f,
                minAmount = 50f,
                isPaid = true,
                isCancelled = true,
                isFixedFee = true,
                isPending = true,
                isPaymentPlan = true
            )

            assertTrue(invoiceFilter.isDefaultEndDate())
        }

        @Test
        fun isDefaultEndDate_whenEndDateIsNotDefault_returnsFalse() {
            val invoiceFilter = InvoiceFilter(
                startDate = "2023-10-01",
                endDate = "2023-10-31",
                maxAmount = 100f,
                minAmount = 50f,
                isPaid = true,
                isCancelled = true,
                isFixedFee = true,
                isPending = true,
                isPaymentPlan = true
            )

            assertFalse(invoiceFilter.isDefaultEndDate())
        }
    }

    @Nested
    inner class MinDateGreaterThanEndDate{
        @Test
        fun minDateGreaterThanEndDate_whenStartDateIsGreaterThanEndDate_returnsTrue() {
            val invoiceFilter = InvoiceFilter(
                startDate = "2023-10-31",
                endDate = "2023-10-01",
                maxAmount = 100f,
                minAmount = 50f,
                isPaid = true,
                isCancelled = true,
                isFixedFee = true,
                isPending = true,
                isPaymentPlan = true
            )

            assertTrue(invoiceFilter.minDateGreaterThanEndDate())
        }

        @Test
        fun minDateGreaterThanEndDate_whenStartDateIsLessThanEndDate_returnsFalse() {
            val invoiceFilter = InvoiceFilter(
                startDate = "2023-10-01",
                endDate = "2023-10-31",
                maxAmount = 100f,
                minAmount = 50f,
                isPaid = true,
                isCancelled = true,
                isFixedFee = true,
                isPending = true,
                isPaymentPlan = true
            )

            assertFalse(invoiceFilter.minDateGreaterThanEndDate())
        }

        @Test
        fun minDateGreaterThanEndDate_whenStartDateIsEqualToEndDate_returnsFalse() {
            val invoiceFilter = InvoiceFilter(
                startDate = "2023-10-01",
                endDate = "2023-10-01",
                maxAmount = 100f,
                minAmount = 50f,
                isPaid = true,
                isCancelled = true,
                isFixedFee = true,
                isPending = true,
                isPaymentPlan = true
            )

            assertFalse(invoiceFilter.minDateGreaterThanEndDate())
        }
    }
}