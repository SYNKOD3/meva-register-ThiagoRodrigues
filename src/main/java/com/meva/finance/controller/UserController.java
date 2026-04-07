package com.meva.finance.controller;

import com.meva.finance.request.CreateUserRequest;
import com.meva.finance.request.UpdateUserRequest;
import com.meva.finance.response.UserListResponse;
import com.meva.finance.response.UserResponse;
import com.meva.finance.service.UserService;
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

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid CreateUserRequest data) {
        var user = userService.register(data);

        var uri = UriComponentsBuilder.fromPath("/users/{cpf}")
                .buildAndExpand(user.getCpf()).toUri();

        return ResponseEntity.created(uri).body(new UserResponse(user)); // Retorna 201 OK com os dados do usuário criado
    }

    @GetMapping("/list")
    public ResponseEntity<Page<UserListResponse>> listUsers(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        log.info("Nova solicitação de registros recebida!");
        var listUsers = userService.listUsers(pageable);

        return ResponseEntity.ok(listUsers); // Retorna 200 OK com a página
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<UserListResponse> userDetail(@PathVariable String cpf) {
        log.info("Nova solicitação de registros detalhados recebida!");
        var userDetail = userService.userDetail(cpf);

        return ResponseEntity.ok(userDetail); // Retorna 200 OK com a página
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity userUpdate(@RequestBody @Valid UpdateUserRequest data) {
        log.info("Nova solicitação de atualização de usuário recebida!");
        var userUpdate = userService.userUpdate(data);

        return ResponseEntity.ok(userUpdate); // Retorna 200 OK com os dados atualizados
    }

    @DeleteMapping("/delete/{cpf}")
    @Transactional
    public ResponseEntity<Void> userDelete(@PathVariable String cpf) {
        log.info("Nova solicitação de exclusão de usuário recebida!");
        userService.userDelete(cpf);

        return ResponseEntity.noContent().build(); // Retorna 204 - No Content (Sucesso sem corpo de resposta)
    }
}