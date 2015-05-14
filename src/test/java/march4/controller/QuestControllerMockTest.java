package march4.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import march4.model.Quest;
import march4.service.QuestService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;

@RunWith(MockitoJUnitRunner.class)
public class QuestControllerMockTest {
	
	private static final String STR_DUE = "due";

	private static final String STR_CONTENTS = "contents";

	private static final String STR_ORDER = "1";

	private static final String STR_POS_Y = "200";

	private static final String STR_POS_X = "100";

	private static final int INT_PID = 1000;

	@InjectMocks
	private QuestController questController;
	
	@Mock
	private QuestService questService;
	
	@Captor
	private ArgumentCaptor<Quest> questCaptor;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		// Process mock annotations
		MockitoAnnotations.initMocks(this);

		// Setup Spring test in standalone mode
		this.mockMvc = MockMvcBuilders.standaloneSetup(questController).build();
	}
	
	@Test
	public void request_S_RequestBody로_Quest값이_잘_받아지는지() throws Exception {
		
		Gson gson = new Gson();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("posX", STR_POS_X);
		paramMap.put("posY", STR_POS_Y);
		paramMap.put("order", STR_ORDER);
		paramMap.put("contents", STR_CONTENTS);
		paramMap.put("due", STR_DUE);
		String json = gson.toJson(paramMap);

		this.mockMvc.perform(post("/projects/" + INT_PID + "/quests")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk());
		
		verify(questService).insert(questCaptor.capture());
		Quest expectedQuest = questCaptor.getValue();

		assertThat(expectedQuest.getpId(), equalTo(INT_PID));
		assertThat(expectedQuest.getPosX(), equalTo(Integer.parseInt(STR_POS_X)));
		assertThat(expectedQuest.getPosY(), equalTo(Integer.parseInt(STR_POS_Y)));
		assertThat(expectedQuest.getOrder(), equalTo(Integer.parseInt(STR_ORDER)));
		assertThat(expectedQuest.getContents(), equalTo(STR_CONTENTS));
		assertThat(expectedQuest.getDue(), equalTo(STR_DUE));

	}

}
