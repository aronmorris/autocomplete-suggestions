package com.aronmorris.autocompletesuggestions;

import com.aronmorris.autocompletesuggestions.api.SuggestionController;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AutocompleteSuggestionsApplicationTests {

	@Autowired
	private SuggestionController controller;

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

}
