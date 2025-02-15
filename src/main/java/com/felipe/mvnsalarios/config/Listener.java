package com.felipe.mvnsalarios.config;
import com.felipe.mvnsalarios.service.PessoaService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class Listener implements ServletContextListener {

    private static boolean metodoExecutado = false;
    
    @Inject
    private PessoaService pessoaService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (!metodoExecutado) {
            pessoaService.lastId();
            metodoExecutado = true;
        }
    }

}

