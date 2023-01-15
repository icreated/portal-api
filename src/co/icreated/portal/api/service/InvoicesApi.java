/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (6.2.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package co.icreated.portal.api.service;

import co.icreated.portal.api.model.DocumentDto;
import co.icreated.portal.api.model.InvoiceDto;
import co.icreated.portal.api.model.OpenItemDto;
import co.icreated.portal.api.model.PortalErrorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Validated
@Tag(name = "Invoices", description = "Operations about invoices")
public interface InvoicesApi {

    /**
     * GET /invoices/{id} : Get invoice
     * Get invoice by id
     *
     * @param id Invoice id (required)
     * @return OK (status code 200)
     *         or Unexpected error (status code 200)
     */
    @Operation(
        operationId = "getInvoice",
        summary = "Get invoice",
        tags = { "Invoices" },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDto.class))
            }),
            @ApiResponse(responseCode = "200", description = "Unexpected error", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PortalErrorDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/invoices/{id}",
        produces = { "application/json" }
    )
    ResponseEntity<InvoiceDto> getInvoice(
        @Parameter(name = "id", description = "Invoice id", required = true) @PathVariable("id") Integer id
    );


    /**
     * GET /invoices : Get invoices
     * Get user invoices
     *
     * @return OK (status code 200)
     *         or Unexpected error (status code 200)
     */
    @Operation(
        operationId = "getInvoices",
        summary = "Get invoices",
        tags = { "Invoices" },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentDto.class))
            }),
            @ApiResponse(responseCode = "200", description = "Unexpected error", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PortalErrorDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/invoices",
        produces = { "application/json" }
    )
    ResponseEntity<List<DocumentDto>> getInvoices(
        
    );


    /**
     * GET /invoices/openitems : Get open items
     * Get open items
     *
     * @return OK (status code 200)
     *         or Unexpected error (status code 200)
     */
    @Operation(
        operationId = "getOpenItems",
        summary = "Get open items",
        tags = { "Invoices" },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = OpenItemDto.class))
            }),
            @ApiResponse(responseCode = "200", description = "Unexpected error", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PortalErrorDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/invoices/openitems",
        produces = { "application/json" }
    )
    ResponseEntity<List<OpenItemDto>> getOpenItems(
        
    );

}
