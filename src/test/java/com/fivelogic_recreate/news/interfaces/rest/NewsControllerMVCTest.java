package com.fivelogic_recreate.news.interfaces.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fivelogic_recreate.fixture.News.TestNewsQueryResponse;
import com.fivelogic_recreate.news.application.NewsService;
import com.fivelogic_recreate.news.application.command.dto.*;
import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;
import com.fivelogic_recreate.news.domain.NewsStatus;
import com.fivelogic_recreate.news.interfaces.rest.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NewsController.class)
@DisplayName("NewsController MVC 테스트")
class NewsControllerMVCTest {
        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private NewsService newsService;

        @Test
        @DisplayName("뉴스 생성 API 호출 성공")
        void createNews_success() throws Exception {
                CreateNewsRequest request = new CreateNewsRequest(
                                "제목", "설명", "본문", "http://video.url", "author1");
                NewsCreateResult result = new NewsCreateResult(1L, "제목", "author1", NewsStatus.DRAFT);

                given(newsService.createNews(any(NewsCreateCommand.class))).willReturn(result);

                mockMvc.perform(post("/api/news")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value(201))
                                .andExpect(jsonPath("$.message").value("뉴스 생성 완료"))
                                .andExpect(jsonPath("$.data.id").value(1L))
                                .andExpect(jsonPath("$.data.title").value("제목"))
                                .andExpect(jsonPath("$.data.authorId").value("author1"))
                                .andExpect(jsonPath("$.data.status").value("DRAFT"));
        }

        @Test
        @DisplayName("뉴스 단일 조회 API 호출 성공")
        void getNews_success() throws Exception {
                Long newsId = 1L;
                NewsQueryResponse response = new TestNewsQueryResponse(
                                "제목", "설명", "본문", "http://video.url", "author1",
                                LocalDateTime.of(2023, 1, 1, 10, 0), NewsStatus.DRAFT);

                given(newsService.getNews(newsId)).willReturn(response);

                mockMvc.perform(get("/api/news/{newsId}", newsId))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value(200))
                                .andExpect(jsonPath("$.data.news.title").value("제목"))
                                .andExpect(jsonPath("$.data.news.description").value("설명"));
        }

        @Test
        @DisplayName("뉴스 목록 조회 API 호출 성공")
        void getNewsList_success() throws Exception {
                NewsQueryResponse response1 = new TestNewsQueryResponse(
                                "제목1", "설명1", "본문1", "url1", "author1", LocalDateTime.of(2023, 1, 1, 10, 0),
                                NewsStatus.DRAFT);
                NewsQueryResponse response2 = new TestNewsQueryResponse(
                                "제목2", "설명2", "본문2", "url2", "author2", LocalDateTime.of(2023, 1, 1, 11, 0),
                                NewsStatus.DRAFT);

                Page<NewsQueryResponse> page = new PageImpl<>(List.of(response1, response2));

                given(newsService.getNewsList(any(Pageable.class))).willReturn(page);

                mockMvc.perform(get("/api/news")
                                .param("page", "0")
                                .param("size", "10"))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value(200))
                                .andExpect(jsonPath("$.data.newsList.length()").value(2))
                                .andExpect(jsonPath("$.data.newsList[0].title").value("제목1"))
                                .andExpect(jsonPath("$.data.totalCount").value(2))
                                .andExpect(jsonPath("$.data.pageSize").exists())
                                .andExpect(jsonPath("$.data.pageNumber").exists());
        }

        @Test
        @DisplayName("뉴스 수정 API 호출 성공")
        void updateNews_success() throws Exception {
                Long newsId = 1L;
                UpdateNewsRequest request = new UpdateNewsRequest("새제목", "새설명", "새본문", "http://new.url");
                NewsUpdateResult result = new NewsUpdateResult(newsId, "새제목", "새설명", "새본문", "http://new.url",
                                NewsStatus.DRAFT);

                given(newsService.updateNews(any(NewsUpdateCommand.class))).willReturn(result);

                mockMvc.perform(put("/api/news/{newsId}", newsId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value(200))
                                .andExpect(jsonPath("$.data.title").value("새제목"))
                                .andExpect(jsonPath("$.data.videoUrl").value("http://new.url"));
        }

        @Test
        @DisplayName("뉴스 발행 API 호출 성공")
        void publishNews_success() throws Exception {
                Long newsId = 1L;
                PublishNewsRequest request = new PublishNewsRequest();

                mockMvc.perform(post("/api/news/{newsId}/publish", newsId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value(200))
                                .andExpect(jsonPath("$.message").value("뉴스 발행 완료"));

                verify(newsService).publishNews(any(NewsPublishCommand.class));
        }

        @Test
        @DisplayName("뉴스 숨김 API 호출 성공")
        void hideNews_success() throws Exception {
                Long newsId = 1L;
                HideNewsRequest request = new HideNewsRequest();

                mockMvc.perform(post("/api/news/{newsId}/hide", newsId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value(200))
                                .andExpect(jsonPath("$.message").value("뉴스 숨김 완료"));

                verify(newsService).hideNews(any(NewsHideCommand.class));
        }

        @Test
        @DisplayName("뉴스 숨김 해제 API 호출 성공")
        void unhideNews_success() throws Exception {
                Long newsId = 1L;
                UnhideNewsRequest request = new UnhideNewsRequest();

                mockMvc.perform(post("/api/news/{newsId}/unhide", newsId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value(200))
                                .andExpect(jsonPath("$.message").value("뉴스 숨김 해제 완료"));

                verify(newsService).unhideNews(any(NewsUnHideCommand.class));
        }

        @Test
        @DisplayName("뉴스 삭제 API 호출 성공")
        void deleteNews_success() throws Exception {
                Long newsId = 1L;

                mockMvc.perform(delete("/api/news/{newsId}", newsId))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value(200))
                                .andExpect(jsonPath("$.message").value("뉴스 삭제 완료"));

                verify(newsService).deleteNews(any(NewsDeleteCommand.class));
        }
}
