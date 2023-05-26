package com.seleccion.seleccion.security.service;


import com.seleccion.seleccion.security.entity.Usuario;
import com.seleccion.seleccion.security.entity.UsuarioMain;
import com.seleccion.seleccion.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

/**
 * Clase que convierte la clase usuario en un UsuarioMain
 * UserDetailsService es propia de Spring Security
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    //UsuarioService usuarioService;
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        //Usuario usuario = usuarioService.getByUsuario(nombreUsuario).get();
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario).get();
        return UsuarioMain.build(usuario);
    }
}
