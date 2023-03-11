# jwt-revocation-ruleset-client

Multiplatform library for reading RuleSets from a JWT Revocation RuleSet server.

### Usage

Initialize the client with the server URL and tokens to use for auth.   Call ruleSet() to get the current RuleSet.  Use isMet to determine if the token has been revoked.

```kotlin
val client = RuleSetHttpClient(serverUrl, {
    loadTokens {
        BearerTokens(accessToken, refresh)
    }
})

val revoked = client.ruleSet().isMet(claims)
```