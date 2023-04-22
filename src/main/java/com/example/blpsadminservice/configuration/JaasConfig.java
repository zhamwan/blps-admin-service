package com.example.blpsadminservice.configuration;

import com.example.blpsadminservice.repositories.UserRepository;
import com.example.blpsadminservice.security.CustomAuthorityGranter;
import com.example.blpsadminservice.security.JaasLoginModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.AbstractJaasAuthenticationProvider;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.authentication.jaas.DefaultJaasAuthenticationProvider;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;

import javax.security.auth.login.AppConfigurationEntry;
import java.util.HashMap;
import java.util.Map;

import static javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag.REQUIRED;

@Configuration
@RequiredArgsConstructor
public class JaasConfig {
    private final UserRepository userRepository;

    @Bean
    public InMemoryConfiguration configuration(UserRepository userRepository) {
        Map<String, UserRepository> options = new HashMap<>();
        options.put("userRepository", userRepository);
        AppConfigurationEntry[] configurationEntries = new AppConfigurationEntry[]{
                new AppConfigurationEntry(
                        JaasLoginModule.class.getCanonicalName(),
                        REQUIRED,
                        options)
        };
        Map<String, AppConfigurationEntry[]> map = new HashMap<>();
        map.put("SPRINGSECURITY", configurationEntries);
        return new InMemoryConfiguration(map);
    }

    @Bean
    public AbstractJaasAuthenticationProvider jaasAuthenticationProvider(javax.security.auth.login.Configuration configuration) {
        DefaultJaasAuthenticationProvider provider = new DefaultJaasAuthenticationProvider();
        provider.setConfiguration(configuration);
        provider.setAuthorityGranters(new AuthorityGranter[]{
                new CustomAuthorityGranter(userRepository)
        });
        return provider;
    }
}
