package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.answer.UserProfileAnswerDto;
import com.javamentor.qa.platform.models.dto.BookMarksDto;
import com.javamentor.qa.platform.models.dto.page.PageDto;
import com.javamentor.qa.platform.models.dto.question.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import com.javamentor.qa.platform.models.dto.user.UserSupplierDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.abstracts.model.user.UserService;
import com.javamentor.qa.platform.webapp.configs.SwaggerConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Api(tags = {SwaggerConfig.USER_CONTROLLER})
@RestController
@Validated
@RequestMapping("/api/user")
public class UserResourceController {

    private final UserDtoService userDtoService;
    private final UserService userService;

    public UserResourceController(UserDtoService userDtoService, UserService userService,
                                  QuestionDtoService questionService) {
        this.userDtoService = userDtoService;
        this.userService = userService;
    }

    @GetMapping(path = "/{userId}")
    @Operation(summary = "Get user dto", responses = {
            @ApiResponse(description = "Get user dto success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserSupplierDto.class))),
            @ApiResponse(description = "User not found", responseCode = "404", content = @Content)
    })
    public ResponseEntity<Object> getUserDto(@PathVariable("userId") Long id) {
        Optional<UserSupplierDto> dto = userDtoService.getUserDtoById(id);
        return dto.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is absent or wrong Id")
                : ResponseEntity.ok(dto.get());
    }

    @GetMapping(path = "/new")
    @Operation(summary = "Get page with pagination by users' persist datetime", responses = {
            @ApiResponse(description = " success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(description = "there isn`t curPage parameter in url or parameters in url are not positives numbers", responseCode = "400")
    })
    public ResponseEntity<?> getPageDtoByUserPersistDate(
            @ApiParam(value = "positive number representing number of current page", required = true)
            @RequestParam @Positive(message = "current page must be positive number") int currPage,
            @ApiParam(value = "positive number representing number of items to show on page")
            @RequestParam(required = false, defaultValue = "10") @Positive(message = "items must be positive number") int items,
            @RequestParam(required = false, defaultValue = "") String filter) {
        Map<Object, Object> map = new HashMap<>();
        map.put("class", "paginationByPersistDate");
        map.put("filter", filter);
        PageDto<UserDto> page = userDtoService.getPage(currPage, items, map);
        return ResponseEntity.ok(page);
    }

    @GetMapping(path = "/reputation")
    @Operation(summary = "Get page pagination users dto reputation", responses = {
            @ApiResponse(description = "Get page dto of users dto success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageDto.class))),
            @ApiResponse(description = "Wrong parameters current page or items", responseCode = "400", content = @Content)
    })
    public ResponseEntity<?> getReputation(@RequestParam int currPage, @RequestParam(required = false, defaultValue = "10") int items,
                                           @RequestParam(required = false, defaultValue = "") String filter) {
        Map<Object, Object> map = new HashMap<>();
        map.put("class", "UserReputation");
        map.put("filter", filter);
        return ResponseEntity.ok(userDtoService.getPage(currPage, items, map));
    }

    @Operation(summary = "change user password", responses = {
            @ApiResponse(description = "Password was changed", responseCode = "200"),
            @ApiResponse(description = "Password not changed", responseCode = "400")
    })
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(
            @ApiParam(value = "Password of the user to be changed", required = true)
            @NotBlank(message = "Password cannot be empty") @RequestBody final String password,
            Authentication authentication) {

        boolean onlyLatinAlphabet = password.matches("^[a-zA-Z0-9!@#$%^&*()-=+|\\\\,.:;~_<>?{}\\[\\]\"']+$");

        if (password.length() < 6 || password.length() > 12) {
            return new ResponseEntity<>("Length of password from 6 to 12 symbols", HttpStatus.BAD_REQUEST);
        }

        if (!onlyLatinAlphabet) {
            return new ResponseEntity<>("Use only latin alphabet, numbers and special chars", HttpStatus.BAD_REQUEST);
        }
        String email = ((User) authentication.getPrincipal()).getEmail();
        userService.changePasswordByEmail(email, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(path = "/vote")
    @Operation(summary = "Get page pagination users dto by vote", responses = {
            @ApiResponse(description = "success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(description = "Wrong parameters current page or items", responseCode = "400", content = @Content)
    })
    public ResponseEntity<?> getPaginationByVote(@RequestParam int currPage, @RequestParam(required = false, defaultValue = "10") int items,
                                                 @RequestParam(required = false, defaultValue = "") String filter) {
        Map<Object, Object> map = new HashMap<>();
        map.put("class", "UserPaginationByVote");
        map.put("filter", filter);
        return ResponseEntity.ok(userDtoService.getPage(currPage, items, map));
    }

    @Operation(summary = "Get user's bookmarks DTOs", responses = {
        @ApiResponse(description = "success", responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookMarksDto.class)))
    })
    @GetMapping("/profile/bookmarks")
    public ResponseEntity<?> getBookMarksDto(Authentication auth) {
        Long userId = ((User) auth.getPrincipal()).getId();
        return ResponseEntity.ok(userDtoService.getBookMarksDtoByUserId(userId));
    }

    @GetMapping("/profile/answers")
    @Operation(summary = "Get List of UserProfileAnswersDto for authenticated user", responses = {
            @ApiResponse(description = "success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserProfileAnswerDto.class))),
    })
    public ResponseEntity<?> getAnswersDtoByUserId(Authentication auth){
        Long userId = ((User) auth.getPrincipal()).getId();
        return ResponseEntity.ok(userDtoService.getAnswersDtoByUserId(userId));
    }

    @Operation(summary = "Get user's deleted questions", responses = {
        @ApiResponse(description = "success", responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserProfileQuestionDto.class))),
    })
    @GetMapping("/profile/delete/questions")
    public ResponseEntity<?> getDeletedQuestions(Authentication auth) {
        Long userId = ((User) auth.getPrincipal()).getId();
        return ResponseEntity.ok(userDtoService.getDeletedUserProfileQuestionDtos(userId));
    }

    @GetMapping(path="/profile/questions")
    @Operation(summary = "Get all questions by authorized user", responses = {
            @ApiResponse(description = "success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Question.class))),
            @ApiResponse(description = "Wrong parameters current page or items", responseCode = "400", content = @Content)
    })
    public ResponseEntity<?> getUserProfileQuestionDto() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userDtoService.getAllUserProfileQuestionDtoByUserId(user.getId()));
    }

    @GetMapping(path = "/answer/top10")
    @Operation(summary = "Get list of top 10 users-dto by quantity of answers last week", responses = {
            @ApiResponse(description = "Successfully get top 10 users", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    })
    public ResponseEntity<?> getTop10UsersByAnswers() {
        List<UserDto> userDtoList = userDtoService.getTop10Users();
        return ResponseEntity.ok(userDtoList);
    }

    @GetMapping(path = "/answer/top10/month")
    @Operation(summary = "Get list of top 10 users-dto by quantity of answers last month", responses = {
            @ApiResponse(description = "Successfully get into the top 10 users for a last month", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    })
    public ResponseEntity<?> getTop10UsersByAnswersLastMonth() {
        List<UserDto> userDtoList = userDtoService.getTop10UsersByAnswersLastMonth();
        return ResponseEntity.ok(userDtoList);
    }

    @GetMapping(path = "/answer/top10/year")
    @Operation(summary = "Get list of top 10 users-dto by quantity of answers last year", responses = {
            @ApiResponse(description = "Successfully get into the top 10 users for a last year", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    })
    public ResponseEntity<?> getTop10UsersByAnswersLastYear() {
        List<UserDto> userDtoList = userDtoService.getTop10UsersByAnswersLastYear();
        return ResponseEntity.ok(userDtoList);
    }

    @GetMapping(path="/profile/answer/week")
    @Operation(summary = "Get count of answers by week by authorized user", responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)))
    })
    public ResponseEntity<Long> getCountAnswersByWeekByAuthorizedUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userDtoService.getCountAnswersByWeek(user.getId()));
    }

}
