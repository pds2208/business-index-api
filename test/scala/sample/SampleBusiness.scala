package scala.sample

import models.Business

trait SampleBusiness {
  val SampleBusinessString = "10205415,TEST GRILL LTD,380268,86762,2,A,A,B,ID80 5QB,105463,210926,29531562"
  val SampleBusinessId = 10205415L
  val SampleBusinessName = "TEST GRILL LTD"
  val SampleUPRN = Some(380268L)
  val SamplePostCode = Some("ID80 5QB")
  val SampleIndustryCode = Some("86762")
  val SampleLegalStatus = Some("2")
  val SampleTradingStatus = Some("A")
  val SampleTurnover = Some("A")
  val SampleEmploymentBands = Some("B")
  val SampleVatRefs = Some(Seq("105463"))
  val SamplePayeRefs = Some(Seq("210926"))
  val SampleCompanyNo = Some("29531562")

  val SampleBusinessString1 = "87504854,GO LIVE TEST LIMITED,578740,27520,6,D,F,O,ZD38 1TI,81153,162306,96743003"
  val SampleBusinessId1 = 87504854L
  val SampleBusinessName1 = "GO LIVE TEST LIMITED"
  val SampleUPRN1 = Some(578740L)
  val SamplePostCode1 = Some("ZD38 1TI")
  val SampleIndustryCode1 = Some("27520")
  val SampleLegalStatus1 = Some("6")
  val SampleTradingStatus1 = Some("D")
  val SampleTurnover1 = Some("F")
  val SampleEmploymentBands1 = Some("O")
  val SampleVatRefs1 = Some(Seq("81153"))
  val SamplePayeRefs1 = Some(Seq("162306"))
  val SampleCompanyNo1 = Some("96743003")

  val SampleBusinessWithAllFields: Business = Business(
    SampleBusinessId, SampleBusinessName, SampleUPRN, SamplePostCode, SampleIndustryCode, SampleLegalStatus,
    SampleTradingStatus, SampleTurnover, SampleEmploymentBands, SampleVatRefs, SamplePayeRefs, SampleCompanyNo
  )

  val SampleBusinessWithAllFields1: Business = Business(
    SampleBusinessId1, SampleBusinessName1, SampleUPRN1, SamplePostCode1, SampleIndustryCode1, SampleLegalStatus1,
    SampleTradingStatus1, SampleTurnover1, SampleEmploymentBands1, SampleVatRefs1, SamplePayeRefs1, SampleCompanyNo1
  )

  val SampleBusinessWithNoOptionalFields: Business = Business(
    SampleBusinessId, SampleBusinessName, None, None, None, None, None, None, None, None, None, None
  )

  val SampleBusinessWithNoOptionalFields1: Business = Business(
    SampleBusinessId1, SampleBusinessName1, None, None, None, None, None, None, None, None, None, None
  )

  def aBusinessSample(ubrn: Long, template: Business = SampleBusinessWithAllFields): Business =
    template.copy(id = ubrn)
}
