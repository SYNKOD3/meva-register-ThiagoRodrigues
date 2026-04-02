package com.meva.finance.controller;

import com.meva.finance.repository.UserRepository;
import com.meva.finance.request.UserRegistryData;
import com.meva.finance.request.UserUpdateData;
import com.meva.finance.response.UserListingData;
import com.meva.finance.response.UserResponseData;
import com.meva.finance.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity register(@RequestBody @Valid UserRegistryData data) {
        var user = userService.register(data);

        var uri = UriComponentsBuilder.fromPath("/users/{cpf}")
                .buildAndExpand(user.getCpf()).toUri();

        return ResponseEntity.created(uri).body(new UserResponseData(user)); // Retorna 201 OK com os dados do usuário criado
    }

    @GetMapping
    public ResponseEntity<Page<UserListingData>> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        log.info("Nova solicitação de registros recebida!");
        var page = userRepository.findAll(pageable).map(UserListingData::new);
        return ResponseEntity.ok(page); // Retorna 200 OK com a página
    }

    @GetMapping("/{cpf}")
    public ResponseEntity detail(@PathVariable String cpf) {
        log.info("Nova solicitação de registros detalhados recebida!");
        var user = userRepository.findById(cpf)
                .orElseThrow(EntityNotFoundException::new);
        return ResponseEntity.ok(new UserListingData(user)); // Retorna 200 OK com a página
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid UserUpdateData data) {
        log.info("Nova solicitação de atualização de usuário recebida!");
        var user = userRepository.findById(data.cpf())
                .orElseThrow(EntityNotFoundException::new);
        user.updateInformation(data);

        return ResponseEntity.ok(new UserResponseData(user)); // Retorna 200 OK com os dados atualizados
    }

    @DeleteMapping("delete/{cpf}")
    @Transactional
    public ResponseEntity exclude(@PathVariable String cpf) {
        log.info("Nova solicitação de exclusão de usuário recebida!");
        userRepository.findById(cpf)
                .orElseThrow(EntityNotFoundException::new);
        userRepository.deleteById(cpf);

        return ResponseEntity.noContent().build(); // Retorna 204 - No Content (Sucesso sem corpo de resposta)
    }
}