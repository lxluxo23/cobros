package cl.myccontadores.cobros.service;

import cl.myccontadores.cobros.entity.Usuario;
import cl.myccontadores.cobros.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public Usuario crearUsuario(Usuario usuario) {
       String password = passwordEncoder.encode(usuario.getPassword());
       usuario.setPassword(password);
       return usuarioRepository.save(usuario);
    }
}
