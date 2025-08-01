/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.tela.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luan Alves
 * 
 * Esta classe utilitária permite acessar o ApplicationContext do Spring
 * em qualquer parte da aplicação, inclusive em classes que não são gerenciadas diretamente pelo Spring,
 * como telas Swing (JFrame, JDialog, etc.).
 *
 * Como usar:
 * Use ApplicationContextProvider.getApplicationContext().getBean(SuaClasse.class)
 * para obter instâncias de beans gerenciados pelo Spring, como views, services, controllers, etc.
 *
 * Exemplo:
 * LarTemporarioView tela = ApplicationContextProvider
 *     .getApplicationContext()
 *     .getBean(LarTemporarioView.class);
 * tela.setVisible(true);
 * 
 * Importante:
 * Essa abordagem é útil quando você precisa integrar componentes fora do contexto Spring,
 * como eventos de UI (botões do Swing).
 * 
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext ac) {
        context = ac;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }
}