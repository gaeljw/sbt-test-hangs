package pact

import org.scalatest.BeforeAndAfter
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import pact4s.provider.StateManagement.StateManagementFunction
import pact4s.provider._
import pact4s.scalatest.PactVerifier
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.{Application, Mode}

import scala.concurrent.duration.DurationInt


class PactVerificationsTest extends PlaySpec with GuiceOneServerPerSuite with BeforeAndAfter with PactVerifier with MockitoSugar {

  override def fakeApplication(): Application = {
    new GuiceApplicationBuilder()
      .in(Mode.Test)
      .build()
  }

  override def provider: ProviderInfoBuilder = ProviderInfoBuilder(
    "publisher-users-ws",
    PactSource
      .PactBrokerWithSelectors("http://pact-broker.mycompany.net")
      .withConsumerVersionSelectors(ConsumerVersionSelectors.branch("mainbranch"))
      .withPendingPactsEnabled(ProviderTags("currentbranch"))
  )
    // Targeting the Play server started for tests
    .withHost("localhost")
    .withPort(port)
    // Setup the state expected by consumers contracts
    .withStateManagementFunction(StateManagementFunction {
      case ProviderState(_, _) =>
        // Do nothing
        ()
    })

  "Publisher Users WS" should {

    "verify pacts" in {

      val providerVersion = s"0.0.1-SNAPSHOT-xyz"

      verifyPacts(providerBranch = Some(Branch("currentbranch")), publishVerificationResults = Some(PublishVerificationResults(providerVersion)), verificationTimeout = Some(10.seconds))

    }

  }

}
