package com.techacademy.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.techacademy.entity.User;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class UserControllerTest {
	private MockMvc mockMvc;

	private final WebApplicationContext webApplicationContext;

	UserControllerTest(WebApplicationContext context){
		this.webApplicationContext = context;
	}

	@BeforeEach
	void beforeEach() {
		// Spring Securityを有効にする
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.apply(springSecurity()).build();
	}

	@Test
	@DisplayName("User更新画面")
	@WithMockUser
	void testGetUser() throws Exception {
		// HTTPリクエストに対するレスポンスの検証
		MvcResult result = mockMvc.perform(get("/user/update/1/")) // URLにアクセス
				.andExpect(status().isOk()) // ステータスを確認
				.andExpect(model().attributeExists("user")) // Modelの内容を確認
				.andExpect(model().hasNoErrors()) // Modelのエラー有無の確認
				.andExpect(view().name("user/update")) // viewの確認
				.andReturn(); // 内容の取得

		// userの検証
        // Modelからuserを取り出す
		User user = (User)result.getModelAndView().getModel().get("user");
		assertEquals(1, user.getId());
		assertEquals("キラメキ太郎", user.getName());
	}

	@Test
	@DisplayName("User更新画面")
	@WithMockUser
	void testGetList() throws Exception{
		MvcResult result = mockMvc.perform(get("/user/list"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("userlist"))
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("user/list"))
				.andReturn();


		List<User> userlist = (List<User>)result.getModelAndView().getModel().get("userlist");
		assertEquals(3, userlist.size());
		for(int i = 0; i < userlist.size(); i++ ) {
			User user = userlist.get(i);
			if(i == 0 && 1 == user.getId()) {
				assertEquals("キラメキ太郎", user.getName());
			}else if(i == 1 && 2 == user.getId()) {
				assertEquals("キラメキ次郎", user.getName());
			}else if(i == 2 && 3 == user.getId()) {
				assertEquals("キラメキ花子", user.getName());
			}

		}

	}

}
