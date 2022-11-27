package com.dragonforge.mtg.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.dragonforge.mtg.entity.Card;
import com.dragonforge.mtg.entity.CardRequest;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

@RequestMapping("/cards")
@OpenAPIDefinition(info = @Info(title = "Mtg Card Service"),
servers = {@Server(url = "http://localhost:8080", description = "Local server.")})
public interface MtgCardController {
  
  // @formatter:off
  @Operation(
      summary = "Creates a card.",
      description = "Returns a created card.",
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "The created card is returned.",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Card.class))),
          @ApiResponse(
              responseCode = "400",
              description = "The request parameters are invalid.",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404",
              description = "No cards were found with the input criteria.",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500",
              description = "An unplanned error occured.",
                  content = @Content(mediaType = "application/json"))
      },
      parameters = {
          @Parameter(
              name = "cardRequest",
              required = true,
              description = "The card as JSON")
      }
  )
  // @formatter:on
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  Card createCard(@RequestBody CardRequest cardRequest);
  
  // @formatter:off
  @Operation(
      summary = "Returns a list of cards.",
      description = "Returns a list of cards using various search parameters. Returns all cards if no parameters defined.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "A list of cards is returned.",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Card.class))),
          @ApiResponse(
              responseCode = "400",
              description = "The request parameters are invalid.",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404",
              description = "No cards were found with the input criteria.",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500",
              description = "An unplanned error occured.",
                  content = @Content(mediaType = "application/json"))
      },
      parameters = {
          @Parameter(
              name = "searchType",
              allowEmptyValue = true,
              required = false,
              description = "The card attribute used to search for cards. "
                  + "(name, set_code, set_name, rarity)"),
          @Parameter(
              name = "keyWord",
              allowEmptyValue = true,
              required = false,
              description = "The search term used to retrieve cards based on search parameter "
                  + "(card name, set code, set name, or rarity)."),
          @Parameter(
              name = "sortBy",
              allowEmptyValue = true,
              required = false,
              description = "Parameter to arrange the returned list. "
                  + "(name, set_code, set_name, type_line, rarity)")
      }
  )
  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  List<Card> fetchCards(
      @RequestParam(required = false) String searchType,
      @RequestParam(required = false) String keyWord,
      @RequestParam(required = false) String sortBy);
  // @formatter:on

}
