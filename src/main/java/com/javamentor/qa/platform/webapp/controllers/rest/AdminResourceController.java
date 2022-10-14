package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.question.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.user.UserService;
import com.javamentor.qa.platform.webapp.configs.SwaggerConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Api(tags = SwaggerConfig.ADMIN_RESOURCE_CONTROLLER)
@Validated
public class AdminResourceController {

    private final UserService userService;
    private final AnswerService answerService;
    private final AnswerDtoService answerDtoService;

    @PostMapping("/delete/{email}")
    @Operation(summary = "Delete user", responses = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "User with submitted email not found")})
    public ResponseEntity<?> deleteUser(
            @ApiParam("Email of the user to be deleted")
            @NotBlank(message = "Email cannot be empty") @PathVariable("email") final String email) {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            userService.changeIsEnable(email);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @DeleteMapping("/answer/{answerId}/delete")
    @Operation(summary = "Delete an answer by id", responses = {
            @ApiResponse(responseCode = "200", description = "Answer deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Answer with submitted id not found")})
    public ResponseEntity<?> deleteAnswer(@ApiParam("ID of the question to be deleted")
                                          @PathVariable Long answerId) {
        Optional<Answer> answer = answerService.getById(answerId);
        if (answer.isPresent()) {
            if (!(answer.get().getIsDeleted() || answer.get().getIsDeletedByModerator())) {
                answerService.deleteByIdByModerator(answerId);
                return new ResponseEntity<>("Answer deleted successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Answer is already deleted", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("No answer with such id exists in DB", HttpStatus.BAD_REQUEST);
    }

}



