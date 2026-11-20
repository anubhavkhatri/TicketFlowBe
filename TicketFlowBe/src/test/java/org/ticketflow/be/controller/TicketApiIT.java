package org.ticketflow.be.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.ticketflow.be.support.IntegrationTestBase;

class TicketApiIT extends IntegrationTestBase {

    @Test
    void viewUpdateSearchFilterCommentFlow() throws Exception {
        MvcResult created = mockMvc.perform(post("/api/v1/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"Searchable login bug","description":"details","priority":"HIGH","status":"OPEN"}
                                """))
                .andExpect(status().isCreated())
                .andReturn();
        String id = readJsonField(created, "id");

        mockMvc.perform(get("/api/v1/tickets/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Searchable login bug"));

        mockMvc.perform(patch("/api/v1/tickets/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"status":"RESOLVED"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("RESOLVED"));

        mockMvc.perform(post("/api/v1/tickets/" + id + "/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"body":"Fixed","author":"agent1"}
                                """))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/tickets/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments[0].body").value("Fixed"));

        mockMvc.perform(get("/api/v1/tickets").param("q", "login"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Searchable login bug"));

        mockMvc.perform(get("/api/v1/tickets").param("status", "RESOLVED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].status").value("RESOLVED"));

        mockMvc.perform(get("/api/v1/tickets/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}