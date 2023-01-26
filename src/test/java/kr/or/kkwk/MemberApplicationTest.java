package kr.or.kkwk;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
public class MemberApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void getMemeberTest() throws Exception {
        this.mockMvc.perform(get("/getMember"))
                .andExpect(status().isOk())
                .andDo(document(

                        "get-member"
                        , responseFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("ID"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                        )
                                )
                );
    }

    @Test
    public void getMemeberPostTest() throws Exception {
       // MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Map<String, String> input = new HashMap<>();
        input.put("id", "admin");
        input.put("password", "admin");
        this.mockMvc.perform(
                     post("/getMemberPost")
                             .contentType(MediaType.APPLICATION_JSON)
                             .content(objectMapper.writeValueAsString(input))

                )
                .andExpect(status().isOk())
                .andDo(document(
                                "get-member-post"
                                , requestFields(
                                        fieldWithPath("id").type(JsonFieldType.STRING).description("ID")
                                                   ,fieldWithPath("password").type(JsonFieldType.STRING).description("password")
                                 )
                                 , responseFields(
                                        fieldWithPath("id").type(JsonFieldType.STRING).description("ID"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                                )
                        )
                );
    }

}

