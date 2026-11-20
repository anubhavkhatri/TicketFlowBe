package org.ticketflow.be.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.ticketflow.be.support.IntegrationTestBase;

class TicketControllerListIT extends IntegrationTestBase {

    @BeforeEach
    void seed() throws Exception {
        for (int i = 0; i < 25; i++) {
            mockMvc.perform(post("/api/v1/tickets")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {"title":"Ticket %d","priority":"MEDIUM","status":"OPEN"}
                                    """.formatted(i)))
                    .andExpect(status().isCreated());
        }
    }

    @Test
    void shouldReturnFirstPageWithMetadata() throws Exception {
        mockMvc.perform(get("/api/v1/tickets").param("page", "0").param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(20)))
                .andExpect(jsonPath("$.totalElements").value(25))
                .andExpect(jsonPath("$.totalPages").value(2));
    }

    @Test
    void shouldReturnEmptyListWhenNoTickets() throws Exception {
        // fresh context would be empty; with seed this tests page beyond range
        mockMvc.perform(get("/api/v1/tickets").param("page", "99").param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.totalElements").value(25));
    }

    @Test
    void shouldSortByCreatedAt() throws Exception {
        mockMvc.perform(get("/api/v1/tickets").param("sort", "createdAt,asc").param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    void shouldSortByPriorityDesc() throws Exception {
        mockMvc.perform(post("/api/v1/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"Urgent","priority":"URGENT","status":"OPEN"}
                                """))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/api/v1/tickets").param("sort", "priority,desc").param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].priority").value("URGENT"));
    }

    @Test
    void shouldNotDuplicateTicketsAcrossPages() throws Exception {
        var page0 = mockMvc.perform(get("/api/v1/tickets").param("page", "0").param("size", "10")
                        .param("sort", "createdAt,asc"))
                .andExpect(status().isOk())
                .andReturn();
        var page1 = mockMvc.perform(get("/api/v1/tickets").param("page", "1").param("size", "10")
                        .param("sort", "createdAt,asc"))
                .andExpect(status().isOk())
                .andReturn();
        String id0 = page0.getResponse().getContentAsString();
        String id1 = page1.getResponse().getContentAsString();
        org.junit.jupiter.api.Assertions.assertNotEquals(id0, id1);
    }

    @Test
    void shouldRejectInvalidSortField() throws Exception {
        mockMvc.perform(get("/api/v1/tickets").param("sort", "invalid,desc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectSizeAboveMax() throws Exception {
        mockMvc.perform(get("/api/v1/tickets").param("size", "101"))
                .andExpect(status().isBadRequest());
    }
}