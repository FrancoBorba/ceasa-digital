package br.com.uesb.ceasadigital.api.features.categoria.documentation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.com.uesb.ceasadigital.api.features.categoria.dto.CategoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface CategoryControllerDocs {
  
  @Operation(
    summary = "Find all categories",
    tags = {"Category"},
    responses = {
      @ApiResponse(description = "Success", responseCode = "200",
        content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = CategoryDTO.class))
          )
        }),
      @ApiResponse(description = "No content", responseCode = "204", content = @Content),
      @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
    }
  )
  public ResponseEntity<Page<CategoryDTO>> findAll(Pageable pageable);

  @Operation(
    summary = "Find a single category",
    description = "Find a specific category by its id",
    tags = {"Category"},
    responses = {
      @ApiResponse(description = "Success", responseCode = "200",
        content = @Content(
          schema = @Schema(implementation = CategoryDTO.class)
        )),
      @ApiResponse(description = "No content", responseCode = "204", content = @Content),
      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    }
  )
  public ResponseEntity<CategoryDTO> findById(Long id);

  @Operation(
    summary = "Adds a new Category",
    description = "Adding a Category",
    tags = {"Category"},
    responses = {
      @ApiResponse(description = "Created", responseCode = "201",
        content = @Content(
          schema = @Schema(implementation = CategoryDTO.class)
        )),
      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    }
  )
  public ResponseEntity<CategoryDTO> insert(CategoryDTO categoryDTO);

  @Operation(
    summary = "Update a Category",
    description = "Update the values of a Category",
    tags = {"Category"},
    responses = {
      @ApiResponse(description = "Success", responseCode = "200",
        content = @Content(
          schema = @Schema(implementation = CategoryDTO.class)
        )),
      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    }
  )
  public ResponseEntity<CategoryDTO> update(Long id, CategoryDTO categoryDTO);

  @Operation(
    summary = "Delete a category",
    description = "Delete a category with specific id",
    tags = {"Category"},
    responses = {
      @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    }
  )
  public ResponseEntity<Void> delete(Long id);

}
