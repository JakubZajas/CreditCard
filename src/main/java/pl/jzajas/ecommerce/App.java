package pl.jzajas.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.jzajas.ecommerce.catalog.ArrayListProductStorage;
import pl.jzajas.ecommerce.catalog.ProductCatalog;
import pl.jzajas.ecommerce.sales.SalesFacade;
import pl.jzajas.ecommerce.sales.cart.HashMapCartStorage;
import pl.jzajas.ecommerce.sales.offering.OfferCalculator;
import pl.jzajas.ecommerce.sales.payment.PaymentDetails;
import pl.jzajas.ecommerce.sales.payment.PaymentGateway;
import pl.jzajas.ecommerce.sales.payment.RegisterPaymentRequest;
import pl.jzajas.ecommerce.sales.reservation.ReservationRepository;

import java.math.BigDecimal;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        System.out.println("Here we go!!");
        SpringApplication.run(App.class, args);
    }

    @Bean
    ProductCatalog createCatalog() {
        var catalog = new ProductCatalog(new ArrayListProductStorage());
        var pid1 = catalog.addProduct("Lego set 8083", "nice one");
        catalog.changePrice(pid1, BigDecimal.valueOf(100.10));

        var pid2 = catalog.addProduct("Cobi set 8083", "nice one");
        catalog.changePrice(pid2, BigDecimal.valueOf(50.10));

        return catalog;
    }

    @Bean
    SalesFacade createSales() {
        return new SalesFacade(
                new HashMapCartStorage(),
                new OfferCalculator(),
                new PaymentGateway() {
                    @Override
                    public PaymentDetails registerPayment(RegisterPaymentRequest registerPaymentRequest) {
                        return null;
                    }
                },
                new ReservationRepository()
        );
    }
}
