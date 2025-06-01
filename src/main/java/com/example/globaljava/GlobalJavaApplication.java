package com.example.globaljava;

import com.example.globaljava.model.*;
import com.example.globaljava.repositories.*;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

@RequiredArgsConstructor
@SpringBootApplication
@EnableAdminServer
@Configuration
public class GlobalJavaApplication {

    private final RoleRepository roleRepository;
    private final UserRepository userSecurityRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final AlertaRepository alertaRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final HistoricoRepository historicoRepository;

    public static void main(String[] args) {
        SpringApplication.run(GlobalJavaApplication.class, args);
    }

    @EventListener(value = ApplicationReadyEvent.class)
    public void setupDados() {
        Role roleAdmin = roleRepository.findByName("ADMIN")
                .orElseGet(() -> roleRepository.save(new Role(null, "ADMIN")));

        UserSecurity admin = new UserSecurity();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setEnabled(true);
        admin.getRoles().add(roleAdmin);

        userSecurityRepository.save(admin);

        Historico historico = Historico.builder()
                .quantidadeAlertas(0)
                .alertas(new ArrayList<>())
                .build();

        historico = historicoRepository.save(historico);

        Usuario usuarioSalvo = Usuario.builder()
                .cpfUser("59997259092")
                .nomeUser("Teste inicial")
                .sobrenomeUser("Spring")
                .telefoneUser("1234567890")
                .dataNascimentoUser(LocalDate.of(1990, 1, 1))
                .emailUser("mariateste.fogolin@example.com")
                .historico(historico)
                .endereco(null)
                .build();

        usuarioSalvo = usuarioRepository.save(usuarioSalvo);

        Alerta alerta = Alerta.builder()
                .dataAlerta(new java.sql.Date(System.currentTimeMillis()))
                .horario(java.time.LocalTime.now())
                .latitude(-23.5505)
                .longitude(-46.6333)
                .evento("Teste de Alerta")
                .gravidade(3)
                .usuario(usuarioSalvo)
                .historico(historico)
                .build();

        alertaRepository.save(alerta);

        historico.getAlertas().add(alerta);
        historico.setQuantidadeAlertas(historico.getQuantidadeAlertas() + 1);
        historicoRepository.save(historico);

        Endereco endereco = Endereco.builder()
                .ruaEndereco("Rua Exemplo")
                .bairroEndereco("Centro")
                .cidadeEndereco("São Paulo")
                .estadoEndereco("SP")
                .cepEndereco("01001-000")
                .build();

        Funcionario funcionario = Funcionario.builder()
                .registroFuncionario(12345678L)
                .nomeFuncionario("João")
                .sobrenomeFuncionario("Silva")
                .telefoneFuncionario("11999999999")
                .tipoFuncionario("Técnico")
                .dataInscricaoFuncionario(Date.valueOf(LocalDate.now()))
                .emailFuncionario("joao.silva@example.com")
                .endereco(endereco)
                .build();

        endereco.setFuncionario(funcionario);

        funcionarioRepository.save(funcionario);

        System.out.println("Usuário de segurança 'admin' criado com sucesso.");
    }

}
