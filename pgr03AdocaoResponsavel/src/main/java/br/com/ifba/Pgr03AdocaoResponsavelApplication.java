package br.com.ifba;


import br.com.ifba.app.view.Main; 
import javax.swing.JFrame; 
import org.springframework.boot.builder.SpringApplicationBuilder; 
import org.springframework.context.ConfigurableApplicationContext; 
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication

public class Pgr03AdocaoResponsavelApplication {

	public static void main(String[] args) {
                
        // 1. Inicializa o contexto Spring Boot
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Pgr03AdocaoResponsavelApplication.class)
            .headless(false)
            .run(args);

        // 2. Obtém a instância da minha tela principal (Main) 
        Main telaPrincipal = context.getBean(Main.class); // <--- OBTÉM SEU BEAN 'Main'
        telaPrincipal.setVisible(true); // Torna a tela visível

        // 3. Fecha o contexto Spring quando a janela principal for fechada, garantindo o desligamento correto da aplicação.
        telaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Garante que feche toda a aplicação
        telaPrincipal.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                context.close(); // Fecha o contexto Spring, encerrando a aplicação
                System.out.println("Contexto Spring Boot fechado.");
            }
        });
        
	}
}    