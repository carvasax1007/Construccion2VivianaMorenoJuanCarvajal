package app.adapters.rest;

import app.Exceptions.AuthenticationException;
import app.Exceptions.BusinessException;
import app.domain.models.Invoice;
import app.domain.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @GetMapping("/invoices")
    public ResponseEntity getInvoices(@RequestParam long sellerDocument) {
        try {
            List<Invoice> invoices = sellerService.getInvoicesBySeller(sellerDocument);
            return new ResponseEntity(invoices, HttpStatus.OK);
        } catch (AuthenticationException ae) {
            return new ResponseEntity(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity("Error al obtener las facturas: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity getInvoice(
            @PathVariable long invoiceId,
            @RequestParam long sellerDocument) {
        try {
            Invoice invoice = sellerService.getInvoiceById(invoiceId, sellerDocument);
            return new ResponseEntity(invoice, HttpStatus.OK);
        } catch (AuthenticationException ae) {
            return new ResponseEntity(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity("Error al obtener la factura: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 