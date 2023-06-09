package com.seleccion.seleccion.security.controller;


import com.seleccion.seleccion.dto.Mensaje;
import com.seleccion.seleccion.security.dto.JwtDto;
import com.seleccion.seleccion.security.dto.LoginUsuario;
import com.seleccion.seleccion.security.dto.NuevoUsuario;
import com.seleccion.seleccion.security.entity.Rol;
import com.seleccion.seleccion.security.entity.Usuario;
import com.seleccion.seleccion.security.entity.UsuarioMain;
import com.seleccion.seleccion.security.enums.RolNombre;
import com.seleccion.seleccion.security.jwt.JwtProvider;
import com.seleccion.seleccion.security.service.RolService;
import com.seleccion.seleccion.security.service.UserDetailsServiceImpl;
import com.seleccion.seleccion.security.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserDetailsServiceImpl usuarioDetailsServiceImpl;

    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);

    //Espera un json y lo convierte a tipo clase NuevoUsuario
    @PostMapping("/nuevoUsuario")
    public ResponseEntity<?> nuevoUsuario(@Valid @RequestBody NuevoUsuario nuevoUsuario,
                                          BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(new Mensaje("Campos mal o email invalido"), HttpStatus.BAD_REQUEST);
        }
        if(usuarioService.existsByUsuario(nuevoUsuario.getNombreUsuario())){
            return new ResponseEntity<>(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
        if(usuarioService.existsByEmail(nuevoUsuario.getEmail())){
            return new ResponseEntity<>(new Mensaje("Ese email ya existe"), HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(),
                nuevoUsuario.getEmail(), passwordEncoder.encode(nuevoUsuario.getPassword()));

        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if(nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        usuario.setRoles(roles);

        usuarioService.save(usuario);

        return new ResponseEntity<>(new Mensaje("Usuario creado"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal"), HttpStatus.BAD_REQUEST);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword())
        );
        logger.info("authentication " + authentication.getPrincipal() + authentication.getAuthorities() + authentication.getDetails());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        logger.info(jwt.toString());
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        logger.info(userDetails.getAuthorities() + userDetails.getUsername() + userDetails.getPassword());
        UsuarioMain usuarioMain = (UsuarioMain) usuarioDetailsServiceImpl.loadUserByUsername(userDetails.getUsername());
        JwtDto jwtDto = new JwtDto(jwt, usuarioMain.getIdUsuario(), userDetails.getUsername(), userDetails.getAuthorities());
        logger.info(jwtDto.getBearer() + jwtDto.getNombreUsuario());
        return new ResponseEntity<>(jwtDto, HttpStatus.OK);
    }
}
