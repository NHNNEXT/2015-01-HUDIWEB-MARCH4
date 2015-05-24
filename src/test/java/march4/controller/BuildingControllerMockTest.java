package march4.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import march4.model.Building;
import march4.service.BuildingService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;

import com.google.gson.Gson;

@RunWith(MockitoJUnitRunner.class)
public class BuildingControllerMockTest {
	
	private static final String BUILDING_DEL = "/building/del";
	private static final String BUILDING_ADD = "/building/add";
	private static final String SHARED = "shared_ok";
	private static final String NAME = "dec7";
	private static final int UID = 1;
	private static final int PID = 10;

	@InjectMocks
	private BuildingController buildingController;
	
	@Mock
	private BuildingService buildingService;
	
	@Captor
	private ArgumentCaptor<Building> buildingArg;
	
	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(buildingController).build();
	}
	
	@Test
	public void addBuilding_S() throws Exception {
		when(buildingService.getLastpId()).thenReturn(PID);

		MvcResult mvcResult = this.mockMvc.perform(post(BUILDING_ADD)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"uid\":\"" + UID + "\",\"name\":\"" + NAME + "\",\"shared\":\"" + SHARED + "\"}"))
				.andExpect(status().isOk())
				.andReturn();
		
		// buildingService로 전달되는 Building parameter값 잡아내기
		verify(buildingService).addBuilding(buildingArg.capture());
		Building actualBuildingArg = buildingArg.getValue();
		
		// BuildingService#addBuilding으로 전달되는 parameter 검증
		assertThat(actualBuildingArg.getUid(), equalTo(UID));
		assertThat(actualBuildingArg.getName(), equalTo(NAME));
		assertThat(actualBuildingArg.getShared(), equalTo(SHARED));
		
		// MVC 반환값 검증
		String responseResultString = mvcResult.getResponse().getContentAsString();
		Gson gson = new Gson();
		Building actualResponseResult = gson.fromJson(responseResultString, Building.class);
		assertThat(actualResponseResult.getPid(), equalTo(PID));
	}
	
	@Test
	public void delBuilding_S() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(post(BUILDING_DEL)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"pid\":\"" + PID + "\"}"))
				.andExpect(status().isOk())
				.andReturn();
		
		// buildingService#delBuilding 실행여부 확인
		verify(buildingService, times(1)).delBuilding(PID);
		
		// MVC 반환값 검증
		String responseResultString = mvcResult.getResponse().getContentAsString();
		Gson gson = new Gson();
		boolean actualResponseResult = gson.fromJson(responseResultString, Boolean.class);
		assertThat(actualResponseResult, equalTo(true));
	}
	
	@Test
	public void delBuilding_F_WHEN_예외_발생시() throws Exception {
		
		// buildingService#delBuilding 실행시 예외 발생
		doThrow(Exception.class).when(buildingService).delBuilding(anyInt());
				
		MvcResult mvcResult = this.mockMvc.perform(post(BUILDING_DEL)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"pid\":\"" + PID + "\"}"))
				.andExpect(status().isOk())
				.andReturn();
		
		// MVC 반환값 검증
		String responseResultString = mvcResult.getResponse().getContentAsString();
		Gson gson = new Gson();
		boolean actualResponseResult = gson.fromJson(responseResultString, Boolean.class);
		assertThat(actualResponseResult, equalTo(false));
	}

}
