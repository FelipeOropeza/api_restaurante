package com.api_restaurante.api.infra;

import com.api_restaurante.api.model.Usuario;
import com.api_restaurante.api.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserSeeder implements CommandLineRunner {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserSeeder(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.findByEmail("admin@restaurante.com") == null) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setEmail("admin@restaurante.com");
            admin.setSenha(passwordEncoder.encode("admin123"));
            admin.setTipo(com.api_restaurante.api.model.TipoUsuario.ADMIN);
            repository.save(admin);
            System.out.println(">>> Usuario ADMIN criado com sucesso! Email: admin@restaurante.com | Senha: admin123");
        }
    }
}
