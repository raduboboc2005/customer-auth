package com.rb.authentication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Value("${auth.token.url}")
    private String authTokenUrl;

    @Value("${oauthz.service.url}")
    private String oauthzServiceUrl;

    @Value("${client_id}")
    private String clientId;

    @Value("${client_secret}")
    private String clientSecret;

    @Value("${grant_type}")
    private String grantType;

    @Value("${authz.scope}")
    private String authzScope;

    @Bean
    public OAuth2RestTemplate restTemplate() {
        List<String> scopes = new ArrayList<>();
        scopes.add(authzScope);

        ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
        resource.setAccessTokenUri(oauthzServiceUrl + authTokenUrl);
        resource.setClientId(clientId);
        resource.setClientSecret(clientSecret);
        resource.setGrantType(grantType);
        resource.setScope(scopes);

        resource.setAuthenticationScheme(AuthenticationScheme.form);
        AccessTokenRequest request = new DefaultAccessTokenRequest();

        return new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext(request));
    }

}
