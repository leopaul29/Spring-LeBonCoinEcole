package com.leopaulmartin.spring.leboncoinecole.restcontrollers;

import com.leopaulmartin.spring.leboncoinecole.persistence.entities.Category;
import com.leopaulmartin.spring.leboncoinecole.services.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//https://www.baeldung.com/guide-to-jayway-jsonpath
@RunWith(SpringRunner.class)
/*
To test the Controllers, we can use @WebMvcTest. It will auto-configure the Spring MVC infrastructure for our unit tests.
 */
@WebMvcTest(CategoryRestController.class)
public class CategoryRestControllerIntegrationTest {

	public static final String API_ROOT = "/api/v1/categories";

	@Autowired
	private MockMvc mvc;

	/*
	Since we are only focused on the Controller code, it is natural to mock the Service layer code for our unit tests
	 */
	@MockBean
	private CategoryService service;

	// write test cases here
	@Test
	public void givenCategories_whenGetCategories_thenReturnJsonArray()
			throws Exception {
		Category device = new Category("device");
		List<Category> allCategories = Arrays.asList(device);
		given(service.getAllCategories()).willReturn(allCategories);

		mvc.perform(get(API_ROOT)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
//				.andExpect(assertThat(JsonPath(),"$", hasSize(1))
//				.andExpect(assertThat(JsonPath("$[0].name"), is(device.getLabel()))));

	}

	@Test
	public void testGetCategoryById()
			throws Exception {
		Category device = new Category("device");
		given(service.getCategoryById(1l)).willReturn(device);

		mvc.perform(get(API_ROOT + "/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	private Category createRandomBook() {
		Category category = new Category("azerty");
		return category;
	}

	private String createBookAsUri(Category category) {
//		Response response = given()
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.body(category)
//				.post(API_ROOT);
//		return API_ROOT + "/" + response.jsonPath().get("id");
		return null;
	}
}
