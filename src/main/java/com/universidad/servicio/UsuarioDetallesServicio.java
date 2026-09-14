package com.universidad.servicio;

import com.universidad.IUsuarioCrud;
import com.universidad.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class UsuarioDetallesServicio implements UserDetailsService {

    @Autowired
    private IUsuarioCrud usuarioCrud;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar por id (cédula) o por email
        Usuario usuario = usuarioCrud.findById(username)
                .or(() -> usuarioCrud.findByEmailIgnoreCase(username))
                .orElse(null);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con identificador: " + username);
        }

        String rol = usuario.getRol() != null ? usuario.getRol().trim().toUpperCase() : "USER";
        String roleName = rol.contains("ADMIN") ? "ROLE_ADMIN" : "ROLE_USER";

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(roleName));

        return new User(usuario.getId(), usuario.getClave() != null ? usuario.getClave() : "", roles);
    }
}
