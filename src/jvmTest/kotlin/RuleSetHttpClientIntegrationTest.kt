import com.mfrancza.jwtrevocation.client.RuleSetHttpClient
import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Tag
import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class RuleSetHttpClientIntegrationTest {

    private val serverUrl: String = System.getenv("JRM_TEST_SERVER_URL")
    private val accessToken: String = System.getenv("JRM_TEST_ACCESS_TOKEN")

    @Test
    @Tag("integration")
    fun rulesetCanBeRetrievedFromServer() = runTest {
        val client = RuleSetHttpClient(serverUrl, {
            loadTokens {
                BearerTokens(accessToken, "")
            }
        })

        assertTrue(client.ruleSet().timestamp < Instant.now().epochSecond + 5, "Rule Set should be returned with a timestamp around the present" )
    }

}