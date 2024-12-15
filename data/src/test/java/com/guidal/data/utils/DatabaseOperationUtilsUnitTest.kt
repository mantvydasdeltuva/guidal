package com.guidal.data.utils

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DatabaseOperationUtilsUnitTest {
    private val databaseOperationUtils = DatabaseOperationUtils()

    @Test
    fun `test safeDatabaseOperation success`() {
        val result = databaseOperationUtils.safeDatabaseOperation {
            "Success Data"
        }

        assertTrue(result is DatabaseOperation.Success)
        assertEquals("Success Data", (result as DatabaseOperation.Success).data)
    }

    @Test
    fun `test safeDatabaseOperation failure`() {
        val exception = Exception("Database error")

        val result = databaseOperationUtils.safeDatabaseOperation {
            throw exception
        }

        assertTrue(result is DatabaseOperation.Failure)
        assertEquals(exception, (result as DatabaseOperation.Failure).exception)
    }

    @Test
    fun `test mapSuccess on Success case`() {
        val result = DatabaseOperation.Success("Initial Data").mapSuccess { data ->
            data.length
        }

        assertTrue(result is DatabaseOperation.Success)
        assertEquals(12, (result as DatabaseOperation.Success).data)
    }

    @Test
    fun `test mapSuccess on Failure case`() {
        val exception = Exception("Database error")
        val result = DatabaseOperation.Failure<String>(exception).mapSuccess { it.length }

        assertTrue(result is DatabaseOperation.Failure)
        assertEquals(exception, (result as DatabaseOperation.Failure).exception)
    }

    @Test
    fun `test onSuccess on Success case`() = runBlocking {
        var successCalled = false
        val result = DatabaseOperation.Success("Data").onSuccess { data ->
            successCalled = data == "Data"
        }

        assertTrue(successCalled)
        assertTrue(result is DatabaseOperation.Success)
    }

    @Test
    fun `test onSuccess on Failure case`() = runBlocking {
        var successCalled = false
        val exception = Exception("Database error")
        val result = DatabaseOperation.Failure<String>(exception).onSuccess {
            successCalled = true
        }

        assertFalse(successCalled)
        assertTrue(result is DatabaseOperation.Failure)
    }

    @Test
    fun `test onFailure on Failure case`() {
        var failureCalled = false
        val exception = Exception("Database error")
        val result = DatabaseOperation.Failure<String>(exception).onFailure { ex ->
            failureCalled = ex == exception
        }

        assertTrue(failureCalled)
        assertTrue(result is DatabaseOperation.Failure)
    }

    @Test
    fun `test onFailure on Success case`() {
        var failureCalled = false
        val result = DatabaseOperation.Success("Data").onFailure {
            failureCalled = true
        }

        assertFalse(failureCalled)
        assertTrue(result is DatabaseOperation.Success)
    }
}