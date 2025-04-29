package com.viewnext.practicaandroid.domain.repository

import com.viewnext.practicaandroid.core.db.dao.InvoiceDao
import com.viewnext.practicaandroid.core.db.entity.InvoiceEntity
import com.viewnext.practicaandroid.dataretrofit.service.InvoiceApiService
import com.viewnext.practicaandroid.domain.data.InvoiceFilter
import com.viewnext.practicaandroid.domain.data.InvoicesResponse
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

private val invoicesList = listOf(
    InvoiceEntity(
        id = 1,
        date = "03/10/2023",
        amount = 100.0,
        status = "Pagada"
    ),
    InvoiceEntity(
        id = 2,
        date = "04/10/2023",
        amount = 200.0,
        status = "Pendiente"
    ),
)
private val invoicesListDB = listOf(
    InvoiceEntity(
        id = 1,
        date = "2023-10-01",
        amount = 100.0,
        status = "Pagada"
    ),
    InvoiceEntity(
        id = 2,
        date = "2023-10-02",
        amount = 200.0,
        status = "Pendiente"
    ),
)

class InvoiceRespositoryTest {

    private val invoiceApiService: InvoiceApiService = mock(InvoiceApiService::class.java)

    @Nested
    inner class NetworkInvoiceRepository {

        @Test
        fun getInvoices_returnsAList() {
            runTest {
//                val invoiceApiService: InvoiceApiService = mock(InvoiceApiService::class.java)
                `when`(invoiceApiService.getInvoices()).thenReturn(
                    InvoicesResponse(
                        invoices = invoicesList,
                        total = invoicesList.size
                    )
                )
                val networkInvoiceRepository = NetworkInvoiceRepository(invoiceApiService)

                val invoicesResponse = networkInvoiceRepository.getInvoices()

                assertEquals(invoicesResponse.invoices, invoicesList)
            }
        }

        @Test
        fun getInvoicesWithFilter_returnsFullList() {
            runTest {
//                val invoiceApiService: InvoiceApiService = mock(InvoiceApiService::class.java)
                `when`(invoiceApiService.getInvoices()).thenReturn(
                    InvoicesResponse(
                        invoices = invoicesList,
                        total = invoicesList.size
                    )
                )

                val networkInvoiceRepository = NetworkInvoiceRepository(invoiceApiService)


                val invoicesResponse =
                    networkInvoiceRepository.getInvoicesWithFilter(
                        InvoiceFilter()
                    )

                assertEquals(invoicesResponse.invoices, invoicesList)
            }
        }
    }

    @Nested
    inner class OfflineInvoiceRepository {

        @Test
        fun getInvoices_returnsAList() {
            runTest {
                val invoiceDao: InvoiceDao = mock(InvoiceDao::class.java)
                `when`(invoiceDao.getAllInvoices()).thenReturn(flowOf(invoicesList))

                val offlineInvoiceRepository = OfflineInvoiceRepository(invoiceDao)

                val invoicesResponse = offlineInvoiceRepository.getInvoices()

                assertEquals(invoicesResponse.invoices, invoicesList)
            }
        }

        @Test
        fun getInvoicesWithFilter_emptyFilter_returnsFullList() {
            runTest {
                val invoiceDao: InvoiceDao = mock(InvoiceDao::class.java)
                `when`(invoiceDao.getInvoices(
                    minAmount = any(),
                    maxAmount = any(),
                    startDate = any(),
                    endDate = any(),
                    isPaid = any(),
                    isCancelled = any(),
                    isFixedFee = any(),
                    isPending = any(),
                    isPaymentPlan = any()
                )).thenReturn(flowOf((invoicesList)))

                val offlineInvoiceRepository = OfflineInvoiceRepository(invoiceDao)

                val invoicesResponse =
                    offlineInvoiceRepository.getInvoicesWithFilter(
                        InvoiceFilter()
                    )

                assertEquals(invoicesResponse.invoices, invoicesList)
            }
        }

        @Test
        fun getInvoicesWithFilter_notEmptyFilter_returnsFilteredList() {
            runTest {
                val invoiceDao: InvoiceDao = mock(InvoiceDao::class.java)
                `when`(invoiceDao.getInvoices(
                    minAmount = any(),
                    maxAmount = any(),
                    startDate = any(),
                    endDate = any(),
                    isPaid = any(),
                    isCancelled = any(),
                    isFixedFee = any(),
                    isPending = any(),
                    isPaymentPlan = any()
                )).thenReturn(flowOf((listOf(invoicesList[0]))))

                val offlineInvoiceRepository = OfflineInvoiceRepository(invoiceDao)

                val invoicesResponse =
                    offlineInvoiceRepository.getInvoicesWithFilter(
                        InvoiceFilter(
                            isPaid = true
                        )
                    )

                assertEquals( listOf(invoicesList[0]), invoicesResponse.invoices)
            }
        }

        @Test
        fun insertInvoices_insertsInvoices() {
            runTest {
                val invoiceDao: InvoiceDao = mock(InvoiceDao::class.java)
                `when`(invoiceDao.deleteAllInvoices()).thenReturn(Unit)
                val offlineInvoiceRepository = OfflineInvoiceRepository(invoiceDao)

                offlineInvoiceRepository.insertInvoices(invoicesListDB)

                // Verify that the insert method was called with the correct argument
                invoicesListDB.forEach { invoice ->
                    verify(invoiceDao).insertInvoice(invoice)
                }
            }
        }
    }

    @Nested
    inner class InvocieReposiotryWrapperTest{

        private val invoiceDao: InvoiceDao = mock(InvoiceDao::class.java)
        private val invoiceApiService: InvoiceApiService = mock(InvoiceApiService::class.java)

        private fun setupInvoiceDaoMock(){
            `when`(invoiceDao.getInvoices(
                minAmount = any(),
                maxAmount = any(),
                startDate = any(),
                endDate = any(),
                isPaid = any(),
                isCancelled = any(),
                isFixedFee = any(),
                isPending = any(),
                isPaymentPlan = any()
            )).thenReturn(flowOf(invoicesListDB))

            `when`(invoiceDao.getAllInvoices()).thenReturn(flowOf(invoicesListDB))
        }

        private fun setupNetworkApiServiceMock(){
            runTest {
                `when`(invoiceApiService.getInvoices()).thenReturn(
                    InvoicesResponse(
                        invoices = invoicesList,
                        total = invoicesList.size
                    )
                )
            }
        }

        @Test
        fun getInvoices_firstCall_callsNetworkRepository() {
            runTest {
                setupNetworkApiServiceMock()
                val networkInvoiceRepository = NetworkInvoiceRepository(invoiceApiService)
                val networkInvoiceRepositorySpy = spy(networkInvoiceRepository)
                val offlineInvoiceRepository = OfflineInvoiceRepository(invoiceDao)
                val invoiceRepository = InvoiceRepositoryWrapper(
                     offlineInvoiceRepository,
                    networkInvoiceRepositorySpy
                )

                invoiceRepository.getInvoices()

                verify(networkInvoiceRepositorySpy).getInvoices()
            }
        }

        @Test
        fun getInvoices_nextCalls_callsOfflineRepository() {
            runTest {

                setupNetworkApiServiceMock()
                setupInvoiceDaoMock()

                val offlineInvoiceRepository = OfflineInvoiceRepository(invoiceDao)
                val offlineInvoiceRepositorySpy = spy(offlineInvoiceRepository)

                val networkInvoiceRepository = NetworkInvoiceRepository(invoiceApiService)
                val invoiceRepository = InvoiceRepositoryWrapper(
                    offlineInvoiceRepositorySpy,
                    networkInvoiceRepository
                )

                invoiceRepository.getInvoices()
                invoiceRepository.getInvoices()

                verify(offlineInvoiceRepositorySpy).getInvoices()
            }
        }

        @Test
        fun getInvoicesWithFilter_callsOfflineRepository() {
            runTest {
                setupInvoiceDaoMock()
                val offlineInvoiceRepository = OfflineInvoiceRepository(invoiceDao)
                val offlineInvoiceRepositorySpy = spy(offlineInvoiceRepository)
                val networkInvoiceRepository = NetworkInvoiceRepository(invoiceApiService)
                val invoiceRepository = InvoiceRepositoryWrapper(
                    offlineInvoiceRepositorySpy,
                    networkInvoiceRepository
                )

                invoiceRepository.getInvoicesWithFilter(InvoiceFilter())

                verify(offlineInvoiceRepositorySpy).getInvoicesWithFilter(any())
            }
        }
    }

    @Nested
    inner class TestInvoiceRepositoryTest{

        private val invoiceRepository: TestInvoiceRepository = TestInvoiceRepository()

        @Test
        fun getInvoices_returnsNotEmptyList(){
            runTest {
                val invoicesResponse = invoiceRepository.getInvoices()

                assertTrue(invoicesResponse.total > 0)
            }
        }

        @Test
        fun getInvoicesWithFilter_returnsNotEmptyList(){
            runTest {
                val invoicesResponse = invoiceRepository.getInvoicesWithFilter(InvoiceFilter())

                assertTrue(invoicesResponse.total > 0)
            }
        }

    }
}

