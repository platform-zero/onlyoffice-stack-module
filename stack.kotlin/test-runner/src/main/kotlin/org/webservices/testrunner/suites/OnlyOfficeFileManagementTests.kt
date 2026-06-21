package org.webservices.testrunner.suites

import io.ktor.client.statement.*
import io.ktor.http.*
import org.webservices.testrunner.framework.*

suspend fun TestRunner.onlyofficeFileManagementTests() = suite("OnlyOffice File Management Tests") {
test("OnlyOffice document server is healthy") {
        val response = client.getRawResponse("${env.endpoints.onlyoffice}/healthcheck")
        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldContain "true"
    }

    test("OnlyOffice web interface responds") {
        val response = client.getRawResponse("${env.endpoints.onlyoffice}/")
        requireOkOrRedirectResponse(response, "OnlyOffice web interface")
    }
}
