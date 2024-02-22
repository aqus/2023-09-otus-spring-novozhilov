package ru.otus.catalog.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.otus.catalog.dto.AuthorDto;
import ru.otus.catalog.security.SecurityConfiguration;
import ru.otus.catalog.security.UserService;
import ru.otus.catalog.services.AuthorService;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Проверяет доступность контроллера авторов")
@WebMvcTest(AuthorController.class)
@Import(SecurityConfiguration.class)
class AuthorControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private UserService userService;

    private List<AuthorDto> authorsDto;

    @BeforeEach
    void setUp() {
        authorsDto = List.of(
                new AuthorDto(1, "Author_1"),
                new AuthorDto(2, "Author_2"),
                new AuthorDto(3, "Author_3")
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @ArgumentsSource(TestArgumentsProvider.class)
    void shouldCheckEndpointAvailability(RequestPostProcessor user,
                              ResultMatcher resultMatcher) throws Exception {
        when(authorService.findAll()).thenReturn(authorsDto);

        mockMvc.perform(get("/api/v1/authors").with(user))
                .andExpect(resultMatcher);
    }

    static class TestArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                    Arguments.of(user("user"), status().isOk()),
                    Arguments.of(anonymous(), status().is3xxRedirection())
            );
        }
    }

}