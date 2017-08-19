package com.vkomlev.app.controller;

import com.vkomlev.app.dto.IncomingObjectTestDTO;
import com.vkomlev.app.dto.ReferencedObjectTestDTO;
import com.vkomlev.app.dto.ReferencingObjectTestDTO;
import com.vkomlev.app.service.ObjectTestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) @WebMvcTest public class TestRestApiController {

    String testBody = "{\n" + "  \"object\" : [\n" + "    {\n" + "      \"id\" : \"1234\",\n"
            + "      \"value\" : \"P1\"\n" + "    },\n" + "    {\n" + "      \"id\" : \"1235\",\n"
            + "      \"value\" : \"P12\",\n" + "      \"dependent_id\" : \"1234\"\n" + "    },\n" + "   {\n"
            + "      \"id\" : \"1236\",\n" + "      \"value\" : \"P13\",\n" + "      \"dependent_id\" : \"1235\"\n"
            + "    },\n" + "   {\n" + "      \"id\" : \"1236\",\n" + "      \"value\" : \"P2\"\n" + "    },\n"
            + "   {\n" + "      \"id\" : \"1237\",\n" + "      \"value\" : \"P21\",\n"
            + "      \"dependent_id\" : \"1236\"\n" + "    },\n" + "   {\n" + "      \"id\" : \"1238\",\n"
            + "      \"value\" : \"P22\",\n" + "      \"dependent_id\" : \"1236\"\n" + "    }\n" + "  ]\n" + "} ";
    @InjectMocks private RestApiController restApiController;

    @MockBean private ObjectTestService objectTestService;

    @Autowired private MockMvc mvc;

    @Test public void testGetWithParents() throws Exception {
        ReferencedObjectTestDTO objectTestDTO = new ReferencedObjectTestDTO();
        expectObjectServiceFindWithParents("123", objectTestDTO);

        mvc.perform(MockMvcRequestBuilders.get("/api/testobject/123").accept(MediaType.APPLICATION_JSON)
                .param("hierarchy", "ASC")).andExpect(status().isOk())
                .andExpect(content().json("{\"id\":null,\"value\":null}"));
    }
    @Test public void testGetWithChild() throws Exception {
        ReferencingObjectTestDTO objectTestDTO = new ReferencingObjectTestDTO();
        expectObjectServiceFindWithChildren("123", objectTestDTO);

        mvc.perform(MockMvcRequestBuilders.get("/api/testobject/123").accept(MediaType.APPLICATION_JSON)
                .param("hierarchy", "DESC")).andExpect(status().isOk())
                .andExpect(content().json("{\"id\":null,\"value\":null}"));
    }

    @Test public void testGetWithInvalidParameter() throws Exception {
        ReferencingObjectTestDTO objectTestDTO = new ReferencingObjectTestDTO();
        expectObjectServiceFindWithChildren("123", objectTestDTO);

        mvc.perform(MockMvcRequestBuilders.get("/api/testobject/123").accept(MediaType.APPLICATION_JSON)
                .param("hierarchy", "DEeeSC")).andExpect(status().is4xxClientError());
    }
    @Test public void testPost() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/testobject").contentType(MediaType.APPLICATION_JSON)
                .content(testBody)).andExpect(status().isCreated());
        expectSaveObjects();
    }

    private void expectSaveObjects() {
        verify(objectTestService, times(1)).saveTestObjects(anyListOf(IncomingObjectTestDTO.class));
    }

    private void expectObjectServiceFindWithChildren(String id, ReferencingObjectTestDTO objectTestDTO) {
        when(objectTestService.findObjectWithChildren(id, 5)).thenReturn(objectTestDTO);
    }

    private void expectObjectServiceFindWithParents(String id, ReferencedObjectTestDTO objectTestDTO) {
        when(objectTestService.findObjectWithParents(id, 5)).thenReturn(objectTestDTO);
    }
}