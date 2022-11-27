package com.dragonforge.mtg.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.dragonforge.mtg.entity.Card;
import com.dragonforge.mtg.entity.Deck;
import com.dragonforge.mtg.entity.DeckRequest;
import com.dragonforge.mtg.entity.User;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

@Validated
@RequestMapping("/decks")
@OpenAPIDefinition(info = @Info(title = "Deck Service"),
servers = {@Server(url = "http://localhost:8080", description = "Local server.")})
public interface MtgDeckController {
  
  // @formatter:off
  @Operation(
      summary = "Create a deck.",
      description = "Creates a deck for the specified user.",
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "Returns the created deck.",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Deck.class))),
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
              name = "deckRequest",
              required = true,
              description = "The deck as JSON.")
      }
  )
  // @formatter:on
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  Deck createDeck(@RequestBody DeckRequest deckRequest);
  
  // @formatter:off
  @Operation(
      summary = "Update a deck.",
      description = "Add/remove cards from a deck for the specified user.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Returns the updated deck.",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Deck.class))),
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
              name = "deckRequest",
              required = true,
              description = "The deck as JSON.")
      }
  )
  // @formatter:on
  @PutMapping
  @ResponseStatus(code = HttpStatus.OK)
  Deck updateDeck(@RequestBody DeckRequest deckRequest);
  
// @formatter:off
  @Operation(
      summary = "Return a list of cards for a specific deck",
      description = "Returns a list of decks for the specified user.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Returns the list of cards.",
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
              description = "User the deck belongs to."),
          @Parameter(
              name = "deckName",
              allowEmptyValue = false,
              required = true,
              description = "Name of a deck.")
      }
  )
  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  List<Card> fetchDeckCards(
      @RequestParam(required = true)
      String username,
      @RequestParam(required = true)
      String deckName);
  // @formatter:on
  
  // @formatter:off
  @Operation(
      summary = "Delete a deck.",
      description = "Deletes a deck for a user with the specified deck name.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Deck has been deleted.",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = User.class))),
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
              description = "Owner of the deck"),
          @Parameter(
              name = "password",
              allowEmptyValue = false,
              required = true,
              description = "Enter your password."),
          @Parameter(
              name = "deckName",
              allowEmptyValue = false,
              required = true,
              description = "Name of the deck"),
      }
  )
  @DeleteMapping
  @ResponseStatus(code = HttpStatus.OK)
  void deleteDeck(
      @RequestParam(required = true)
      String username,
      @RequestParam(required = true)
      String password,
      @RequestParam(required = true)
      String deckName);
  // @formatter:on
}
