package com.meva.finance.service;

import com.meva.finance.model.Family;
import com.meva.finance.model.User;
import com.meva.finance.repository.FamilyRepository;
import com.meva.finance.repository.UserRepository;
import com.meva.finance.request.UserRegistryData;
import com.meva.finance.request.UserUpdateData;
import com.meva.finance.response.UserListingData;
import com.meva.finance.response.UserResponseData;
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
    public User register(UserRegistryData data) {
        log.info("Processando registro para: {}", data.name());

        var userExist = userRepository.findByCpf(data.cpf());
        if (userExist.isPresent()) {
            throw new EntityExistsException("O usuário " + data.cpf() + " já está cadastrado no sistema!");
        } else {
            Family family;

            if (data.familyDTO().idFamily() == 0 && data.familyDTO().description() != null) {
                log.debug("Regra: Criando nova família '{}'", data.familyDTO().description());

                family = familyRepository.save(new Family(data.familyDTO()));
            } else if (data.familyDTO().idFamily() > 0) {
                log.debug("Regra: Associando à família ID {}", data.familyDTO().idFamily());
                family = familyRepository.findById(data.familyDTO().idFamily())
                        .orElseThrow(() -> new EntityNotFoundException("Família informada não existe!"));
            } else {
                throw new IllegalArgumentException("Dados da família inválidos para cadastro.");
            }

            var user = new User(data, family);
            return userRepository.save(user);
        }
    }

    public Page<UserListingData> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserListingData::new);
    }

    public UserListingData userDetail(String cpf) {
        var user = userRepository.findById(cpf)
                .orElseThrow(EntityNotFoundException::new);
        return new UserListingData(user);
    }

    public UserResponseData userUpdate(UserUpdateData data) {
        var user = userRepository.findById(data.cpf())
                .orElseThrow(EntityNotFoundException::new);
        user.updateInformation(data);
        return new UserResponseData(user);
    }

    public void userDelete(String cpf) {
        userRepository.findById(cpf)
                .orElseThrow(EntityNotFoundException::new);
        userRepository.deleteById(cpf);
    }

}