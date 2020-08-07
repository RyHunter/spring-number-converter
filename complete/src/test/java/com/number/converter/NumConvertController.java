package com.number.converter;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class NumConvertController {

	@Autowired
	private MockMvc mvc;

	@Test //positive number
	public void getPositiveNumber() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/convertNumberToEnglish/123456").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("{\"status\":\"ok\",\"numInEnglish\":\"one hundred twenty three thousand four hundred fifty six\"}")));
	}

	@Test //negative number
	public void getNegativeNumber() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/convertNumberToEnglish/-123456").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("{\"status\":\"ok\",\"numInEnglish\":\"negative one hundred twenty three thousand four hundred fifty six\"}")));
	}

	@Test //zero padded
	public void getZeroPadded() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/convertNumberToEnglish/000056").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("{\"status\":\"ok\",\"numInEnglish\":\"fifty six\"}")));
	}

	@Test //invalid
	public void getInvalidString() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/convertNumberToEnglish/0sgn0056").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("{\"status\":\"Failed: check your input and try again\",\"numInEnglish\":\"\"}")));
	}

	@Test //too long
	public void getLongString() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/convertNumberToEnglish/087945878947547958749587437349759847598759870056").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("{\"status\":\"Failed: number too large\",\"numInEnglish\":\"\"}")));
	}
}
