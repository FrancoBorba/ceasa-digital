package br.com.uesb.ceasadigital.api.features.product.documentation;

import java.util.List;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.com.uesb.ceasadigital.api.features.product.dto.ProductRequestDTO;
import br.com.uesb.ceasadigital.api.features.product.dto.ProductResponseUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface ProductControllerDocs {
  
    @Operation(
    summary = "Find all products",
    tags = {"Product"},
    responses = {
         @ApiResponse(description = "Success" , responseCode = "200" ,
     content = {
      @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
       array = @ArraySchema(schema = @Schema(implementation = ProductResponseUserDTO.class))
      )}),
      @ApiResponse(description = "No content" , responseCode = "204" , content = @Content),
      @ApiResponse(description = "Bad request" , responseCode = "400" , content = @Content),
      @ApiResponse(description = "Unatorizhed" , responseCode = "401" , content = @Content),
      @ApiResponse(description = "Not found" , responseCode = "404" , content = @Content),
      @ApiResponse(description = "Internal server error" , responseCode = "500", content = @Content)
    }
  )
  public ResponseEntity<List<ProductResponseUserDTO>> findAll();

   @Operation(
    summary = "Find a single product" ,
    description = "Find a especifc product by your id",
    tags = {"Product"},
    responses = {
      @ApiResponse(description = "Success" , responseCode = "200" ,
       content = @Content(
        schema = @Schema(implementation = ProductResponseUserDTO.class)
       )),
      @ApiResponse(description = "No content" , responseCode = "204" , content = @Content),
      @ApiResponse(description = "Bad Request" , responseCode = "400" , content = @Content),
      @ApiResponse(description = "Unautorizhed" , responseCode = "401" , content = @Content),
      @ApiResponse(description = "Not found" , responseCode = "404" , content = @Content),
      @ApiResponse(description = "Internal Server Erros" , responseCode = "500" , content = @Content),
    }
  ) 
  public ProductResponseUserDTO findById(Long id);


    @Operation(
    summary = "Adds a new Product" ,
    description = "Adding a Product ",
    tags = {"Product"},
    responses = {
      @ApiResponse(description = "Success" , responseCode = "200" ,
       content = @Content(
        schema = @Schema(implementation = ProductResponseUserDTO.class)
       )),
      @ApiResponse(description = "No content" , responseCode = "204" , content = @Content),
      @ApiResponse(description = "Bad Request" , responseCode = "400" , content = @Content),
      @ApiResponse(description = "Unautorizhed" , responseCode = "401" , content = @Content),
      @ApiResponse(description = "Not found" , responseCode = "404" , content = @Content),
      @ApiResponse(description = "Internal Server Erros" , responseCode = "500" , content = @Content),
    }
  ) 
  public ProductResponseUserDTO createProduct(ProductRequestDTO productRequest);

    @Operation(
    summary = "Update a Product" ,
    description = "Update the values of a Product ",
    tags = {"Product"},
    responses = {
      @ApiResponse(description = "Success" , responseCode = "200" ,
       content = @Content(
        schema = @Schema(implementation = ProductResponseUserDTO.class)
       )),
      @ApiResponse(description = "No content" , responseCode = "204" , content = @Content),
      @ApiResponse(description = "Bad Request" , responseCode = "400" , content = @Content),
      @ApiResponse(description = "Unautorizhed" , responseCode = "401" , content = @Content),
      @ApiResponse(description = "Not found" , responseCode = "404" , content = @Content),
      @ApiResponse(description = "Internal Server Erros" , responseCode = "500" , content = @Content),
    }
  ) 
  public ProductResponseUserDTO updateProduct(
        Long id ,ProductRequestDTO productRequestDTO);


        @Operation(
    summary = "Delete a produtc",
    description = "Delete a product with especifc id",
    tags = {"Product"},
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
