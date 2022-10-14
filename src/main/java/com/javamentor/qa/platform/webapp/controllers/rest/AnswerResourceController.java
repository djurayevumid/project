package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.answer.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.question.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.question.CommentAnswerService;
import com.javamentor.qa.platform.service.abstracts.model.question.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.question.VoteAnswerService;
import com.javamentor.qa.platform.webapp.configs.SwaggerConfig;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.javamentor.qa.platform.models.entity.question.answer.VoteType.DOWN_VOTE;
import static com.javamentor.qa.platform.models.entity.question.answer.VoteType.UP_VOTE;



@Api(tags = {SwaggerConfig.ANSWER_RESOURCE_CONTROLLER})
@RestController
@RequestMapping("/api/user/question/{questionId}/answer")
@AllArgsConstructor
public class AnswerResourceController {

    private final AnswerService answerService;
    private final AnswerDtoService answerDtoService;
    private final VoteAnswerService voteAnswerService;
    private final QuestionService questionService;


    @Operation(summary = "Delete an answer by id", responses = {
            @ApiResponse(description = "Answer was deleted from DB", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Answer.class))),
            @ApiResponse(description = "Received if no answer with such id exists in DB", responseCode = "400")
    })
    @DeleteMapping("/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long questionId, @PathVariable Long answerId) {
        Optional<Answer> answer = answerService.getById(answerId);
        if (answer.isPresent()) {
            if (!answer.get().getIsDeleted()) {
                answerService.deleteById(answerId);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>("Answer is already deleted", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("No answer with such id exists in DB", HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Get list of answers by question id", responses = {
            @ApiResponse(description = "Got list of answers", responseCode = "200",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = AnswerDto.class)))),
            @ApiResponse(description = "No answers with such question id - return empty list", responseCode = "200"),
            @ApiResponse(description = "Wrong type of question id", responseCode = "400")
    })
    @GetMapping
    public ResponseEntity<?> getAnswerByQuestionId(@PathVariable Long questionId) {
        return  ResponseEntity.ok(answerDtoService.getListAnswersOfQuestionId(questionId));
    }

    @Operation(summary = "Vote up for answer", responses = {
            @ApiResponse(responseCode = "200", description = "Vote up successful. Author's reputation increased"),
            @ApiResponse(responseCode = "400", description = "Cannot vote")})
    @PostMapping("{answerId}/upVote")
    public ResponseEntity<?> upVote(@PathVariable final Long questionId,
                                    @PathVariable final Long answerId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (voteAnswerService.isUserNonVoted(answerId, user.getId())) {
            Long totalCount = voteAnswerService.vote(answerId, user, UP_VOTE);
            return ResponseEntity.ok(totalCount) ;
        }
        return ResponseEntity.badRequest().body("User is already voted");
    }

    @Operation(summary = "Vote down for answer", responses = {
            @ApiResponse(responseCode = "200", description = "Vote down successful. Author's reputation decreased"),
            @ApiResponse(responseCode = "400", description = "Cannot vote")})
    @PostMapping("{answerId}/downVote")
    public ResponseEntity<?> downVote(@PathVariable final Long questionId,
                                      @PathVariable final Long answerId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (voteAnswerService.isUserNonVoted(answerId, user.getId())) {
            Long totalCount = voteAnswerService.vote(answerId, user, DOWN_VOTE);
            return ResponseEntity.ok(totalCount);
        }
        return ResponseEntity.badRequest().body("User is already voted");
    }

    @Operation(summary = "Add answer to question", responses = {
            @ApiResponse(responseCode = "200", description = "Answer added"),
            @ApiResponse(responseCode = "400", description = "Cannot add answer")})
    @PostMapping("/add")
    public ResponseEntity<?> addAnswer(@PathVariable final Long questionId, @RequestBody String answerString){
        Optional<Question> question = questionService.getById(questionId);
        if (question.isEmpty()){
            return ResponseEntity.badRequest().body("Question does not exist");
        }
        if (answerString.isBlank()) {
            return ResponseEntity.badRequest().body("Requested body is blank (empty, whitespace, or null)");
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Answer answer = new Answer(question.get(), user, answerString);
        answerService.persist(answer);
        return ResponseEntity.ok(answerDtoService.getListAnswersOfQuestionId(answer.getId()));

    }

    @Operation(summary = "Getting the page user of answer per week", responses = {
            @ApiResponse(responseCode = "200", description = "Got Page User of answer per week successful"),
            @ApiResponse(responseCode = "400", description = "Cannot got the page User of answer perWeek")})
    @GetMapping("/week")
    public ResponseEntity<?> getUserAnswerPerWeek() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(answerDtoService.getAnswerPerWeek(user.getId()));
   }



}

