package scala.models

import models.Business
import org.scalatest.{ FreeSpec, Matchers }
import play.api.libs.json.Json

import scala.sample.SampleBusiness
import support.JsonString
import support.JsonString._

class BusinessSpec extends FreeSpec with Matchers with SampleBusiness {

  private trait Fixture {
    def expectedJsonStrOf(business: Business): String =
      JsonString.withObject(
        long("id", business.id),
        string("BusinessName", business.businessName),
        optionalLong("UPRN", business.uprn),
        optionalString("PostCode", business.postCode),
        optionalString("IndustryCode", business.industryCode),
        optionalString("LegalStatus", business.legalStatus),
        optionalString("TradingStatus", business.tradingStatus),
        optionalString("Turnover", business.turnover),
        optionalString("EmploymentBands", business.employmentBands),
        optionalSeqString("VatRefs", business.vatRefs),
        optionalSeqString("PayeRefs", business.payeRefs),
        optionalString("CompanyNo", business.companyNo)
      )
  }

  "A Business" - {
    "can be represented as JSON" - {
      "when all fields are defined" in new Fixture {
        Json.toJson(SampleBusinessWithAllFields) shouldBe Json.parse(expectedJsonStrOf(SampleBusinessWithAllFields))
      }

      "when only mandatory fields are defined" ignore new Fixture {
        // TODO: need to modify JsonString to handle empty values rather than None's
        Json.toJson(SampleBusinessWithNoOptionalFields) shouldBe Json.parse(expectedJsonStrOf(SampleBusinessWithNoOptionalFields))
      }
    }

    "can be represented securely with no UPRN or vat/paye refs" - {
      "when all fields are defined" in new Fixture {
        val business = Business(12345, "Big Company", Some(100L), Some("NP20 ABC"), Some("A"), Some("1"), Some("2"),
          Some("3"), Some("4"), Some(Seq("1123123,23324234")), Some(Seq("3424,23434")), Some("8797984"))

        val businessSecured = Business(12345, "Big Company", None, Some("NP20 ABC"), Some("A"), Some("1"), Some("2"),
          Some("3"), Some("4"), None, None, Some("8797984"))

        business.secured shouldBe businessSecured
      }
    }
  }
}
