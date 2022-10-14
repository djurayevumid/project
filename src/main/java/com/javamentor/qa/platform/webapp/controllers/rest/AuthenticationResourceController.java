package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.authentication.AuthenticationRequestDto;
import com.javamentor.qa.platform.models.dto.token.TokenResponseDto;
import com.javamentor.qa.platform.models.dto.user.UserSupplierDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.jwt.JwtService;
import com.javamentor.qa.platform.webapp.configs.SwaggerConfig;
import com.javamentor.qa.platform.webapp.converters.mapper.UserConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Api(tags = {SwaggerConfig.AUTHENTICATION_CONTROLLER})
public class AuthenticationResourceController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserConverter userConverter;

    @PostMapping("/token")
    @Operation(summary = "Authenticate user", responses = {
            @ApiResponse(responseCode = "200", description = "Successful authentication"),
            @ApiResponse(responseCode = "400", description = "Invalid user credentials")})
    public ResponseEntity<TokenResponseDto> createToken(
            @ApiParam(value = "A JSON object containing user login and password", required = true)
            @Valid @RequestBody final AuthenticationRequestDto authDto) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword()));
        TokenResponseDto token = jwtService.createAccessToken(authDto.getRememberMe(),authDto.getUsername(),
                auth.getAuthorities()
                        .stream()
                        .findFirst()
                        .orElseThrow(NoSuchElementException::new)
                        .getAuthority());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/check/user")
    @Operation(summary = "checking user authorization", responses = {
            @ApiResponse(responseCode = "200", description = "user successful authorization"),
            @ApiResponse(responseCode = "403", description = "user failed authorization")})
    public ResponseEntity<UserSupplierDto> checkUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            if (Objects.equals(user.getRole().getAuthority(), "ROLE_USER")) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping(path = "/user")
    @Operation(summary = "Get current user dto", responses = {
            @ApiResponse(description = "Get current user dto success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserSupplierDto.class))),
            @ApiResponse(responseCode = "403", description = "user failed authorization")
    })
    public ResponseEntity <Object> getCurrentUserDto() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (principal instanceof User)
                ? ResponseEntity.ok(userConverter.UserToUserAuthDto((User) principal))
                : ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not authorized");
    }
}
