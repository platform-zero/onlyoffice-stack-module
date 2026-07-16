package org.webservices.testrunner.suites

import io.ktor.client.statement.*
import io.ktor.http.*
import org.webservices.testrunner.framework.*

suspend fun TestRunner.onlyofficeExtendedProductivityTests() = suite("OnlyOffice Extended Productivity Tests") {

fun requireStatus(response: HttpResponse, allowed: Set<HttpStatusCode>, message: String) {
        require(response.status in allowed) {
            "$message: ${response.status}"
        }
    }

test("OnlyOffice: Service is accessible") {
        val response = client.getRawResponse(endpoints.onlyoffice)
        requireOkOrRedirectResponse(response, "OnlyOffice service")
        println("      ✓ OnlyOffice endpoint returned ${response.status}")
    }

    test("OnlyOffice: Health check endpoint") {
        val response = client.getRawResponse("${endpoints.onlyoffice}/healthcheck")
        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldContain "true"
        println("      ✓ OnlyOffice health check passed")
    }

    test("OnlyOffice: Document conversion API exists") {
        val response = client.getRawResponse("${endpoints.onlyoffice}/ConvertService.ashx")
        response.status.value shouldBeOneOf listOf(200, 400, 401, 403, 405)
        println("      ✓ Conversion service endpoint: ${response.status}")
    }

    test("OnlyOffice: Document editing service exists") {
        val response = client.getRawResponse("${endpoints.onlyoffice}/coauthoring/CommandService.ashx")
        response.status.value shouldBeOneOf listOf(200, 400, 401, 403, 405)
        println("      ✓ Command service endpoint: ${response.status}")
    }

    test("OnlyOffice: Static resources are served") {
        val response = client.getRawResponse("${endpoints.onlyoffice}/web-apps/apps/api/documents/api.js")
        requireStatus(
            response,
            setOf(HttpStatusCode.OK, HttpStatusCode.NotModified),
            "OnlyOffice static assets were not served"
        )
        println("      ✓ Document editor API script available")
    }
}
