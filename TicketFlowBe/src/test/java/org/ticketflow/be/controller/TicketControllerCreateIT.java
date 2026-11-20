package org.ticketflow.be.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.ticketflow.be.support.IntegrationTestBase;

class TicketControllerCreateIT extends IntegrationTestBase {

    @Test
    void shouldCreateTicket() throws Exception {
        mockMvc.perform(post("/api/v1/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"Login issue","priority":"HIGH","status":"OPEN"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Login issue"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void shouldRejectMissingTitle() throws Exception {
        mockMvc.perform(post("/api/v1/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"   ","priority":"HIGH"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"));
    }

    @Test
    void shouldCreateUnassigned() throws Exception {
        mockMvc.perform(post("/api/v1/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"Triage","priority":"MEDIUM","status":"OPEN"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.assignee").isEmpty());
    }
}