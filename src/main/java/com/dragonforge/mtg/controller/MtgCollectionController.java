package com.dragonforge.mtg.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.dragonforge.mtg.entity.Card;
import com.dragonforge.mtg.entity.Collection;
import com.dragonforge.mtg.entity.CollectionRequest;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

@Validated
@RequestMapping("/collections")
@OpenAPIDefinition(info = @Info(title = "Collection Service"),
servers = {@Server(url = "http://localhost:8080", description = "Local server.")})
public interface MtgCollectionController {
  
  // @formatter:off
  @Operation(
      summary = "Create a collection.",
      description = "Starts a collection of cards for a user.",
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "Returns the created collection.",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Collection.class))),
          @ApiResponse(
              responseCode = "400",
              description = "The request parameters are invalid.",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404",
              description = "A component was not found with the input criteria.",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500",
              description = "An unplanned error occured.",
              content = @Content(mediaType = "application/json"))
      },
      parameters = {
          @Parameter(
              name = "collectionRequest",
              required = true,
              description = "The collection as JSON.")
      }
  )
  // @formatter:on
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  Collection createCollection(@RequestBody CollectionRequest collectionRequest);
  
  // @formatter:off
  @Operation(
      summary = "Update a collection.",
      description = "Add or remove cards from a user's collection.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Returns the updated collection.",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Collection.class))),
          @ApiResponse(
              responseCode = "400",
              description = "The request parameters are invalid.",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404",
              description = "A component was not found with the input criteria.",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500",
              description = "An unplanned error occured.",
              content = @Content(mediaType = "application/json"))
      },
      parameters = {
          @Parameter(
              name = "collectionRequest",
              required = true,
              description = "The collection as JSON.")
      }
  )
  // @formatter:on
  @PutMapping
  @ResponseStatus(code = HttpStatus.OK)
  Collection updateCollection(@RequestBody CollectionRequest collectionRequest);
  
  // @formatter:off
  @Operation(
      summary = "Retrieve a collection.",
      description = "Retrieve a user's collection of cards.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Returns the collection.",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Card.class))),
          @ApiResponse(
              responseCode = "400",
              description = "The request parameters are invalid.",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404",
              description = "A component was not found with the input criteria.",
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
              description = "Retrieve collection for specified user.")
      }
  )
  // @formatter:on
  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  List<Card> fetchCollectionCards(@RequestParam(required = true) String username);

}
