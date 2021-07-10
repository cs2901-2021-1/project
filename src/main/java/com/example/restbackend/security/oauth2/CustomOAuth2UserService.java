package com.example.restbackend.security.oauth2;

import com.example.restbackend.customException.OAuth2AuthenticationProcessingException;
import com.example.restbackend.model.usuario.Usuario;
import com.example.restbackend.model.usuario.UsuarioRepository;
import com.example.restbackend.model.usuarioDTO.UsuarioDTO;
import com.example.restbackend.security.UserPrincipal;
import com.example.restbackend.security.oauth2.oauthUser.OAuth2UserInfo;
import com.example.restbackend.security.oauth2.oauthUser.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try{
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        }catch (AuthenticationException ex){
            throw ex;
        }catch (Exception ex){
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        Optional<Usuario> usuarioOptional = usuarioRepository.findUsuarioByEmail(oAuth2UserInfo.getEmail());
        Usuario usuario;
        if(usuarioOptional.isPresent()) {
            usuario = usuarioOptional.get();
            usuario = updateExistingUser(usuario, oAuth2UserInfo);
        } else {
            usuario = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }
        return UserPrincipal.create(usuario, oAuth2User.getAttributes());
    }

    private Usuario registerNewUser(OAuth2UserInfo oAuth2UserInfo) {
        Usuario usuario = new Usuario();
        usuario.setGoogleId(oAuth2UserInfo.getId());
        usuario.setName(oAuth2UserInfo.getName());
        usuario.setEmail(oAuth2UserInfo.getEmail());
        usuario.setImageUrl(oAuth2UserInfo.getImageUrl());
        return usuarioRepository.save(usuario);
    }

    private Usuario updateExistingUser(Usuario usuario, OAuth2UserInfo oAuth2UserInfo) {
        usuario.setName(oAuth2UserInfo.getName());
        usuario.setImageUrl(oAuth2UserInfo.getImageUrl());
        usuario.setGoogleId(oAuth2UserInfo.getId());
        return usuarioRepository.save(usuario);
    }
}
