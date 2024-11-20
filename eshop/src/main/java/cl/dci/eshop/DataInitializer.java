package cl.dci.eshop;

import cl.dci.eshop.auth.User;
import cl.dci.eshop.model.Carrito;
import cl.dci.eshop.repository.CarritoRepository;
import cl.dci.eshop.repository.UserRepository;
import cl.dci.eshop.security.ApplicationUserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository, CarritoRepository carritoRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String password = passwordEncoder.encode("password");

            // Crear usuarios de ejemplo
            User user1 = new User("customer1", password, ApplicationUserRole.CUSTOMER);
            User user2 = new User("customer2", password, ApplicationUserRole.CUSTOMER);
            User user3 = new User("customer3", password, ApplicationUserRole.CUSTOMER);
            User user4 = new User("admin", password, ApplicationUserRole.ADMIN);

            // Crear carritos para cada usuario
            Carrito c1 = new Carrito();
            Carrito c2 = new Carrito();
            Carrito c3 = new Carrito();
            Carrito c4 = new Carrito();

            c1.setUser(user1);
            c2.setUser(user2);
            c3.setUser(user3);
            c4.setUser(user4);

            // Guardar usuarios y carritos en la base de datos
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);

            carritoRepository.save(c1);
            carritoRepository.save(c2);
            carritoRepository.save(c3);
            carritoRepository.save(c4);

            System.out.println("Usuarios y carritos creados exitosamente");
        };
    }
}
