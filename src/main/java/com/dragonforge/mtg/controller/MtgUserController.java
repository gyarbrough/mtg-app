package com.dragonforge.mtg.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.dragonforge.mtg.entity.User;
import com.dragonforge.mtg.entity.UserRequest;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

@RequestMapping("/users")
@OpenAPIDefinition(info = @Info(title = "Mtg User Service"),
  servers = {@Server(url = "http://localhost:8080", description = "Local server.")})
public interface MtgUserController {
  
  // @formatter:off
  @Operation(
      summary = "Create a user.",
      description = "Creates a user and stores username and email address.",
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "Returns the created user.",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = User.class))),
          @ApiResponse(
              responseCode = "400",
              description = "The request parameters are invalid.",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500",
              description = "An unplanned error occured.",
                  content = @Content(mediaType = "application/json"))
      },
      parameters = {
          @Parameter(
              name = "userRequest",
              required = true,
              description = "The user as JSON.")
      }
  )
  // @formatter:on
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  User createUser(@RequestBody UserRequest userRequest);
  
  // @formatter:off
  @Operation(
      summary = "Edit a user's information.",
      description = "Edit a user's email, username, or password.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Specified user is edited and returned.",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = User.class))),
          @ApiResponse(
              responseCode = "400",
              description = "The request parameters are invalid.",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404",
              description = "No users were found with the input criteria.",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500",
              description = "An unplanned error occured.",
                  content = @Content(mediaType = "application/json"))
      },
      parameters = {
          @Parameter(
              name = "username",
              allowEmptyValue = false,
              required = true,
              description = "User to be edited."),
          @Parameter(
              name = "dataField",
              allowEmptyValue = false,
              required = true,
              description = "The data field to apply the update to.(username, email, password)"),
          @Parameter(
              name = "value",
              allowEmptyValue = false,
              required = true,
              description = "The value to be applied to the selected field."),
          @Parameter(
              name = "password",
              allowEmptyValue = false,
              required = true,
              description = "Enter the current password to verify the account.")
      }
  )
  @PutMapping
  @ResponseStatus(code = HttpStatus.OK)
  User editUser(
      @RequestParam(required = true)
      String username,
      @RequestParam(required = true)
      String dataField, 
      @RequestParam(required = true)
      String value, 
      @RequestParam(required = true)
      String password);
  // @formatter:on
  
  // @formatter:off
  @Operation(
      summary = "Delete a user.",
      description = "Deletes the selected user.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Deletes a selected user.",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = User.class))),
          @ApiResponse(
              responseCode = "400",
              description = "The request parameters are invalid.",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404",
              description = "No users were found with the input criteria.",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500",
              description = "An unplanned error occured.",
                  content = @Content(mediaType = "application/json"))
      },
      parameters = {
          @Parameter(
              name = "username",
              allowEmptyValue = false,
              required = true,
              description = "Enter username or email address."),
          @Parameter(
              name = "password",
              allowEmptyValue = false,
              required = true,
              description = "Password for the account being deleted.")
      }
  )
  @DeleteMapping
  @ResponseStatus(code = HttpStatus.OK)
  void deleteUser(
      @RequestParam(required = true)
      String username, 
      @RequestParam(required = true)
      String password);
  // @formatter:on
}
