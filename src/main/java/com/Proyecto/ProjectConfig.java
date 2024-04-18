/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Proyecto;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class ProjectConfig implements WebMvcConfigurer {

    /* Los siguientes métodos son para incorporar el tema de internacionalización en el proyecto */

 /* localeResolver se utiliza para crear una sesión de cambio de idioma */
    @Bean
    public LocaleResolver localeResolver() {
        var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.getDefault());
        slr.setLocaleAttributeName("session.current.locale");
        slr.setTimeZoneAttributeName("session.current.timezone");

        return slr;
    }

    /* localeChangeInterceptor se utiliza para crear un interceptor de cambio de idioma */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        var lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    //Bean para poder acceder a los Messages.properties en código...
    @Bean("messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /* Los siguiente métodos son para implementar el tema de seguridad dentro del proyecto */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/usuario/Registrarse").setViewName("/usuario/Registrarse");
    }

    /* El siguiente método se utiliza para completar la clase no es 
    realmente funcional, la próxima semana se reemplaza con usuarios de BD */
//    @Bean
//    public UserDetailsService users() {
//        UserDetails admin = User.builder()
//                .username("juan")
//                .password("{noop}123")
//                .roles("ADMINISTRATIVO")
//                .build();
//        UserDetails tech = User.builder()
//                .username("rebeca")
//                .password("{noop}456")
//                .roles("TECNICO")
//                .build();
//        UserDetails user = User.builder()
//                .username("pedro")
//                .password("{noop}789")
//                .roles("USUARIO")
//                .build();
//        return new InMemoryUserDetailsManager(user, tech, admin);
//    }
    
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((request) -> request
                .requestMatchers("/", "/errores/**", "/error",
                        "/reportes/**", "/js/**", "/webjars/**", "/css/**",
                        "/usuario/Registrarse", "/usuario/guardar", "/registro/Registrarse",
                        "/registro/crearUsuario", "/registro/salida",
                        "/registro/activacion/**", "registro/activar",
                        "/registro/recordar", "/registro/recordarUsuario",
                        "/usuario/listadoPersonal/**", "usuario/modificar/**",
                        "/usuario/guardarActualizacion", "/registro/insertarUsuario", 
                        "/registro/insertarUsuarioSP")
                .permitAll()
                .requestMatchers(
                        "/usuario/**", "/provincia/listado", "/canton/listado",
                        "/distrito/listado", "reportes/historicoTickets**",
                        "/ticket/listado/solicitudesRegistradas/**", "eliminarUsuarioSP/**"
                ).hasRole("ADMINISTRATIVO")
                .requestMatchers(
                        "/ticket/listado",
                        "ticket/listado/asignados",
                        "ticket/listado/historicoAtencion",
                        "ticket/atencion/**"
                ).hasAnyRole("TECNICO", "ADMINISTRATIVO")
                //                        .hasRole("TECNICO")
                //                ).hasAnyRole("ADMIN", "VENDEDOR")
                .requestMatchers("/index", "/nosotros/acercaDeNosotros", "/ticket/nuevo",
                        "/ticket/guardar", "ticket/ver/**", "ticket/editar/**",
                        "ticket/query**", "/ticket/listado/historicoUsuario",
                        "ticket/listado/ticketsEnProceso", "/ticket/guardarSP",
                        "/ticket/actualizarSP", "/ticket/eliminarSP/**", "usuario/editarUsuarioSP/**"
                ).hasAnyRole("USUARIO", "TECNICO", "ADMINISTRATIVO")
                //                .hasRole("USUARIO")
                )
                .formLogin(
                        (form) -> form.loginPage("/login").permitAll()
                )
                .logout((logout) -> logout.permitAll());
        return http.build();
    }

}
