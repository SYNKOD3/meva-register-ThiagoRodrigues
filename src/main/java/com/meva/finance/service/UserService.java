package com.meva.finance.service;

import com.meva.finance.model.Family;
import com.meva.finance.model.User;
import com.meva.finance.repository.FamilyRepository;
import com.meva.finance.repository.UserRepository;
import com.meva.finance.request.CreateUserRequest;
import com.meva.finance.request.UpdateUserRequest;
import com.meva.finance.response.UserListResponse;
import com.meva.finance.response.UserResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;

    @Transactional
    public User register(CreateUserRequest data) {
        log.info("Processando registro para: {}", data.name());

        var userExist = userRepository.findByCpf(data.cpf());
        if (userExist.isPresent()) {
            throw new EntityExistsException("O usuário " + data.cpf() + " já está cadastrado no sistema!");
        } else {
            Family family;

            if (data.createFamilyRequest().idFamily() == 0 && data.createFamilyRequest().description() != null) {
                log.debug("Regra: Criando nova família '{}'", data.createFamilyRequest().description());

                family = familyRepository.save(new Family(data.createFamilyRequest()));
            } else if (data.createFamilyRequest().idFamily() > 0) {
                log.debug("Regra: Associando à família ID {}", data.createFamilyRequest().idFamily());
                family = familyRepository.findById(data.createFamilyRequest().idFamily())
                        .orElseThrow(() -> new EntityNotFoundException("Família informada não existe!"));
            } else {
                throw new IllegalArgumentException("Dados da família inválidos para cadastro.");
            }

            var user = new User(data, family);
            return userRepository.save(user);
        }
    }

    public Page<UserListResponse> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserListResponse::new);
    }

    public UserListResponse userDetail(String cpf) {
        var user = userRepository.findById(cpf)
                .orElseThrow(EntityNotFoundException::new);
        return new UserListResponse(user);
    }

    public UserResponse userUpdate(UpdateUserRequest data) {
        var user = userRepository.findById(data.cpf())
                .orElseThrow(EntityNotFoundException::new);
        user.updateInformation(data);
        return new UserResponse(user);
    }

    public void userDelete(String cpf) {
        userRepository.findById(cpf)
                .orElseThrow(EntityNotFoundException::new);
        userRepository.deleteById(cpf);
    }

}